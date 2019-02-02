package graphNPC.TSP;

import graph.Graph;
import graph.Vertice;
import graphNPC.IntegerEvaluator;
import java.util.LinkedList;
import org.jgap.FitnessFunction;
import org.jgap.Gene;
import org.jgap.IChromosome;
import org.jgap.impl.IntegerGene;

/** Questa classe si occupa di valutare la bontà di una soluzione generata dall'algoritmo genetico */
public class TSPFitnessFunction extends FitnessFunction implements IntegerEvaluator{

    public static int NOT_LINKED_VERTICES_MOLTIPLICATOR = 1000;
    private Graph graph;

    public TSPFitnessFunction(Graph graph) {
        this.graph = graph;
    }

    @Override
    protected double evaluate(IChromosome chromosome) {
        //Ottengo i geni del cromosoma ed i loro valori numerici
        Gene[] genes = chromosome.getGenes();
        int[] solution = new int[genes.length];
        for (int i = 0; i < solution.length; i++){
            solution[i] = ((IntegerGene) genes[i]).intValue();
        }

        //Valuto la soluzione
        return evaluate(graph, solution);
    }

    /** Ritorna la distanza tra i vertici indicati dai geni from e to*/
    private static double distance(Graph g, Vertice from, Vertice to) {
        double distance = g.getDirectDistance(from, to);
        if (distance == Double.POSITIVE_INFINITY) {
            return NOT_LINKED_VERTICES_MOLTIPLICATOR * from.getDistanceFrom(to);
        }
        return distance;
    }

    @Override
    public double evaluate(int[] solution) {
        return evaluate(graph, solution);
    }

    protected static double evaluate(Graph g, int[] s) {
        //Ottengo i vertici rappresentati dalla soluzione
        Vertice[] vertices = new Vertice[s.length];
        for (int i = 0; i < vertices.length; i++) {
            vertices[i] = g.getVertice(s[i]);
        }

        //Calcolo la distanza percorsa nel visitare tutti i vertici rappresentati dai geni del cromosoma
        double totalDist = 0;
        for (int i = 0; i < vertices.length - 1; i++) {
            totalDist += distance(g, vertices[i], vertices[i + 1]);
        }

        //Ad essa, aggiungo la distanza necessaria per tornare al vertice iniziale
        totalDist += distance(g, vertices[vertices.length - 1], vertices[0]);

        //Ritorno la distanza totale
        return totalDist;
    }
}
