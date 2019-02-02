package graphNPC.MM;

import graph.Edge;
import graph.Graph;
import graphNPC.BooleanEvaluator;
import java.util.LinkedList;
import org.jgap.FitnessFunction;
import org.jgap.Gene;
import org.jgap.IChromosome;
import org.jgap.impl.BooleanGene;

/** Questa classe si occupa di valutare la bontà di una soluzione generata dall'algoritmo genetico */
public class MMFitnessFunction extends FitnessFunction implements BooleanEvaluator{

    private Graph graph;

    public MMFitnessFunction(Graph graph) {
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
            return 0;
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

        //Se almeno due degli archi rappresentati dal cromosoma sono adiacenti,
        //elimino il cromosoma dalla popolazione e ritorno il valore minimo come fitness
        for (Edge e1: edges){
            for (Edge e2: edges){
                if (e1 != e2){
                    if (e1.isAdjacentTo(e2)){
                        return -1;
                    }
                }
            }
        }

        return edges.size();
    }
}
