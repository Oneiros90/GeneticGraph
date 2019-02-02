package graphNPC.MDS;

import graph.Graph;
import graph.Vertice;
import graphNPC.BooleanEvaluator;
import java.util.LinkedList;
import org.jgap.FitnessFunction;
import org.jgap.Gene;
import org.jgap.IChromosome;
import org.jgap.impl.BooleanGene;

/** Questa classe si occupa di valutare la bontà di una soluzione generata dall'algoritmo genetico */
public class MDSFitnessFunction extends FitnessFunction implements BooleanEvaluator{

    private Graph graph;

    public MDSFitnessFunction(Graph graph) {
        this.graph = graph;
    }

    @Override
    protected double evaluate(IChromosome chromosome) {
        //Ottengo la soluzione rappresentata dal cromosoma
        Gene[] genes = chromosome.getGenes();
        boolean[] solution = new boolean[genes.length];
        for (int i = 0; i < genes.length; i++){
            solution[i] = ((BooleanGene) genes[i]).booleanValue();
        }

        //La valuto
        double value = evaluate(this.graph, solution);

        //Escludo il cromosoma se la soluzione non è valida
        if (value == -1){
            chromosome.setIsSelectedForNextGeneration(false);
            return Double.MAX_VALUE;
        }

        return value;
    }

    @Override
    public double evaluate(boolean[] solution) {
        return evaluate(graph, solution);
    }

    protected static double evaluate(Graph g, boolean[] solution) {
        //Ottengo gli archi rappresentati dalla soluzione
        LinkedList<Vertice> vertices = new LinkedList<Vertice>();
        for (int i = 0; i < g.getVerticesCount(); i++){
            if (solution[i]){
                vertices.add(g.getVertice(i));
            }
        }

        //Controllo che i vertici rappresentati dal cromosoma dominino tutti gli altri
        //(altrimenti il cromosoma rappresenta una soluzione non valida)
        for (Vertice v1 : g.getVertices()) { //Per ogni vertice...
            if (!vertices.contains(v1)) { //...non rappresentato dal cromosoma...
                boolean isDominated = false; //...controllo che sia dominato da almeno un vertice dominante...
                for (Vertice v2 : vertices) {
                    if (v1.isLinkedTo(v2)) {
                        isDominated = true;
                        break;
                    }
                }
                if (!isDominated) { //...se non lo è, la soluzione viene scartata
                    return -1;
                }
            }
        }

        return vertices.size();
    }
}
