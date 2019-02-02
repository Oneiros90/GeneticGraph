package graphNPC.MEDS;

import graph.Edge;
import graph.Graph;
import graphNPC.BooleanEvaluator;
import java.util.LinkedList;
import org.jgap.FitnessFunction;
import org.jgap.Gene;
import org.jgap.IChromosome;
import org.jgap.impl.BooleanGene;

/** Questa classe si occupa di valutare la bontà di una soluzione generata dall'algoritmo genetico */
public class MEDSFitnessFunction extends FitnessFunction implements BooleanEvaluator{

    private Graph graph;

    public MEDSFitnessFunction(Graph graph) {
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
        double value = evaluate(solution);

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
        LinkedList<Edge> edges = new LinkedList<Edge>();
        for (int i = 0; i < g.getEdgesCount(); i++){
            if (solution[i]){
                edges.add(g.getEdge(i));
            }
        }

        //Controllo che gli archi rappresentati dal cromosoma dominino tutti gli altri
        //(altrimenti il cromosoma non rappresenta una soluzione valida)
        for (Edge e1 : g.getEdges()) { //Per ogni arco...
            if (!edges.contains(e1)) { //...non rappresentato dal cromosoma...
                boolean isDominated = false; //...controllo che sia dominato da almeno un arco dominante...
                for (Edge e2 : edges) {
                    if (e1.isAdjacentTo(e2)) {
                        isDominated = true;
                        break;
                    }
                }
                if (!isDominated) { //...se non lo è, la soluzione viene scartata
                    return -1;
                }
            }
        }

        return edges.size();
    }
}
