package graphNPC.MIS;

import graph.Graph;
import graph.Vertice;
import graphNPC.BooleanEvaluator;
import java.util.LinkedList;
import org.jgap.FitnessFunction;
import org.jgap.Gene;
import org.jgap.IChromosome;
import org.jgap.impl.BooleanGene;

/** Questa classe si occupa di valutare la bontà di una soluzione generata dall'algoritmo genetico */
public class MISFitnessFunction extends FitnessFunction implements BooleanEvaluator{

    private Graph graph;

    public MISFitnessFunction(Graph graph) {
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
        //Ottengo i vertici rappresentati dalla soluzione
        LinkedList<Vertice> vertices = new LinkedList<Vertice>();
        for (int i = 0; i < g.getVerticesCount(); i++){
            if (solution[i]){
                vertices.add(g.getVertice(i));
            }
        }

        //Se almeno due dei vertici rappresentati dal cromosoma sono collegati da un arco,
        //elimino il cromosoma dalla popolazione e ritorno il valore minimo come fitness
        for (Vertice v1: vertices){
            for (Vertice v2: vertices){
                if (v1 != v2){
                    if (v1.isLinkedTo(v2)){
                        return -1;
                    }
                }
            }
        }

        return vertices.size();
    }
}
