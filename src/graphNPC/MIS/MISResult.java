package graphNPC.MIS;

import graph.Graph;
import graph.Vertice;
import java.util.LinkedList;
import java.util.List;
import org.jgap.IChromosome;
import org.jgap.impl.BooleanGene;

public class MISResult {

    private List<Vertice> vertices;
    private int fitness;
    protected IChromosome solution;

    public MISResult(IChromosome best, Graph graph) {
        this.vertices = new LinkedList<Vertice>();
        for (int i = 0; i < graph.getVerticesCount(); i++) {
            if (((BooleanGene) best.getGene(i)).booleanValue()) {
                this.vertices.add(graph.getVertice(i));
            }

        }
        this.fitness = (int) best.getFitnessValue();
        this.solution = best;
    }

    public MISResult(boolean[] solution, Graph graph) {
        this.vertices = new LinkedList<Vertice>();
        for (int i = 0; i < graph.getVerticesCount(); i++){
            if (solution[i]){
                vertices.add(graph.getVertice(i));
            }
        }
        this.fitness = (int) MISFitnessFunction.evaluate(graph, solution);
    }

    public int getFitness() {
        return fitness;
    }

    public List<Vertice> getVertices() {
        return vertices;
    }

    public boolean isBetterThen(MISResult result) {
        return this.fitness >= result.fitness;
    }

    @Override
    public String toString() {
        String string = "Maximum Indipendent Set: ";
        for (Vertice n : vertices) {
            string += n.getName() + ", ";
        }
        string = string.substring(0, string.length() - 2);
        return string + " (Fitness: " + fitness + ")";
    }
}
