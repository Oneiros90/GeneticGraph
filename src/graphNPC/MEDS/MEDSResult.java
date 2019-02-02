package graphNPC.MEDS;

import graph.Edge;
import graph.Graph;
import java.util.LinkedList;
import java.util.List;
import org.jgap.IChromosome;
import org.jgap.impl.BooleanGene;

public class MEDSResult {

    private List<Edge> edges;
    private int fitness;
    protected IChromosome solution;

    public MEDSResult(IChromosome best, Graph graph) {
        this.edges = new LinkedList<Edge>();
        for (int i = 0; i < graph.getEdgesCount(); i++) {
            if (((BooleanGene) best.getGene(i)).booleanValue()) {
                this.edges.add(graph.getEdges().get(i));
            }

        }
        this.fitness = (int) best.getFitnessValue();
        this.solution = best;
    }

    public MEDSResult(boolean[] solution, Graph graph) {
        this.edges = new LinkedList<Edge>();
        for (int i = 0; i < graph.getEdgesCount(); i++) {
            if (solution[i]) {
                this.edges.add(graph.getEdge(i));
            }

        }
        this.fitness = (int) MEDSFitnessFunction.evaluate(graph, solution);
    }

    public int getFitness() {
        return fitness;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public boolean isBetterThen(MEDSResult result) {
        return this.fitness <= result.fitness;
    }

    @Override
    public String toString() {
        String string = "Minimum Edge Dominating Set: ";
        for (Edge e : edges) {
            string += e.getFirstVertice().getName() + "-" + e.getSecondVertice().getName() + ", ";
        }
        string = string.substring(0, string.length() - 2);
        return string + " (Fitness: " + fitness + ")";
    }
}
