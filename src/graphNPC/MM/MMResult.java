package graphNPC.MM;

import graph.Edge;
import graph.Graph;
import java.util.LinkedList;
import java.util.List;
import org.jgap.IChromosome;
import org.jgap.impl.BooleanGene;

public class MMResult {

    private List<Edge> edges;
    private int fitness;

    public MMResult(IChromosome best, Graph graph) {
        this.edges = new LinkedList<Edge>();
        for (int i = 0; i < graph.getEdgesCount(); i++) {
            if (((BooleanGene) best.getGene(i)).booleanValue()) {
                this.edges.add(graph.getEdge(i));
            }

        }
        this.fitness = (int) best.getFitnessValue();
    }

    public MMResult(boolean[] solution, Graph graph) {
        this.edges = new LinkedList<Edge>();
        for (int i = 0; i < graph.getEdgesCount(); i++) {
            if (solution[i]) {
                this.edges.add(graph.getEdge(i));
            }

        }
        this.fitness = (int) MMFitnessFunction.evaluate(graph, solution);
    }

    public int getFitness() {
        return fitness;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public boolean isBetterThen(MMResult result) {
        return this.fitness >= result.fitness;
    }

    @Override
    public String toString() {
        String string = "Maximum Matching: ";
        for (Edge e : edges) {
            string += e.getFirstVertice().getName() + "-" + e.getSecondVertice().getName() + ", ";
        }
        string = string.substring(0, string.length() - 2);
        return string + " (Fitness: " + fitness + ")";
    }
}
