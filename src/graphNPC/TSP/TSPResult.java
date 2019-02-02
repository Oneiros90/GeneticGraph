package graphNPC.TSP;

import graph.Edge;
import graph.Graph;
import graph.Vertice;
import java.util.LinkedList;
import java.util.List;
import org.jgap.Gene;
import org.jgap.IChromosome;
import org.jgap.impl.IntegerGene;

public class TSPResult {

    private List<Vertice> vertices;
    private List<Edge> path;
    private double fitness;

    public TSPResult(IChromosome best, Graph graph) {
        Gene[] genes = best.getGenes();
        int[] solution = new int[genes.length];
        for (int i = 0; i < solution.length; i++) {
            solution[i] = ((IntegerGene) genes[i]).intValue();
        }
        this.init(solution, graph);
    }

    public TSPResult(int[] solution, Graph graph) {
        this.init(solution, graph);
    }

    private void init(int[] solution, Graph graph) {
        this.vertices = new LinkedList<Vertice>();
        for (int i = 0; i < solution.length; i++) {
            Vertice n = graph.getVertice(solution[i]);
            this.vertices.add(n);
        }

        this.path = new LinkedList<Edge>();
        for (int i = 0; i < vertices.size() - 1; i++) {
            Edge e = new Edge(vertices.get(i), vertices.get(i + 1));
            this.path.add(e);
        }
        Edge e = new Edge(vertices.get(vertices.size() - 1), vertices.get(0));
        this.path.add(e);

        this.fitness = TSPFitnessFunction.evaluate(graph, solution);
    }

    public double getFitness() {
        return fitness;
    }

    public List<Vertice> getVertices() {
        return vertices;
    }

    public List<Edge> getPath() {
        return path;
    }

    public boolean isBetterThen(TSPResult result) {
        return this.fitness < result.fitness;
    }

    @Override
    public String toString() {
        String string = "TSP's solution: ";
        for (Vertice n : vertices) {
            string += n.getName() + " - ";
        }
        return string + "1 (Fitness: " + (int) fitness + ")";
    }
}
