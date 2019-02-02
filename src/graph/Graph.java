package graph;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Graph {

    protected List<Vertice> vertices;
    protected List<Edge> edges;

    public Graph() {
        this.vertices = new LinkedList<Vertice>();
        this.edges = new LinkedList<Edge>();
    }

    public void complete() {
        loop1:
        for (Vertice v1 : vertices) {
            loop2:
            for (Vertice v2 : vertices) {
                loop3:
                {
                    if (v1.equals(v2)) {
                        continue loop2;
                    }
                    for (Edge e : edges) {
                        if (e.isLinking(v1, v2)) {
                            continue loop2;
                        }
                    }
                    this.edges.add(new Edge(v1, v2));
                }
            }
        }
    }

    public void autolink() {
        if (this.getVerticesCount() > 1) {
            this.clearEdges();

            //Per ogni vertice v del grafo
            for (Vertice v : this.vertices) {

                //Collego v ai 4 vertici più vicini ad esso
                List<Vertice> linkedVertices = new LinkedList<Vertice>();
                linkedVertices.addAll(this.vertices);
                Collections.sort(linkedVertices, new Vertice.DistanceComparator(v));
                for (int i = 0; i < 4 && i < linkedVertices.size(); i++) {
                    Edge e = new Edge(v, linkedVertices.get(i));
                    if (!e.getFirstVertice().equals(e.getSecondVertice()) && !this.contains(e)) {
                        this.edges.add(e);
                    }
                }
            }
        }
    }

    public void clear() {
        this.vertices.clear();
        this.edges.clear();
    }

    public void add(Vertice v) {
        v.graph = this;
        this.vertices.add(v);
    }

    public void addVertices(List<Vertice> vertices) {
        for (Vertice v : vertices) {
            this.add(v);
        }
    }

    public void addVertices(Graph g) {
        this.addVertices(g.vertices);
    }

    public void remove(Vertice v) {
        for (Edge e : v.getStar()) {
            this.edges.remove(e);
        }
        this.vertices.remove(v);
    }

    public void add(Edge e) {
        Vertice first = e.getFirstVertice();
        Vertice second = e.getSecondVertice();

        if (first == null || second == null || !this.vertices.contains(first) || !this.vertices.contains(second)
                || first.equals(second) || this.contains(e)) {
            throw new IllegalArgumentException("Trying to add an invalid edge");
        }
        this.edges.add(e);
    }

    public void addEdges(List<Edge> edges) {
        for (Edge e : edges) {
            this.add(e);
        }
    }

    public void remove(Edge e) {
        this.edges.remove(e);
    }

    public void clearEdges() {
        this.edges.clear();
    }

    public double getDirectDistance(Vertice v1, Vertice v2) {
        for (Edge e : edges) {
            if (e.isLinking(v1, v2)) {
                return v1.getDistanceFrom(v2);
            }
        }
        return Double.POSITIVE_INFINITY;
    }

    public List<Vertice> getVertices() {
        return vertices;
    }

    public Vertice getVertice(int index) {
        return vertices.get(index);
    }

    public int getVerticesCount() {
        return vertices.size();
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public Edge getEdge(int index) {
        return edges.get(index);
    }

    public int getEdgesCount() {
        return edges.size();
    }

    public boolean contains(Edge e) {
        return this.edges.contains(e);
    }

    /*private static final Comparator<Vertice> VERTICES_VALUE_COMPARATOR = new Comparator<Vertice>() {

    @Override
    public int compare(Vertice n1, Vertice n2) {
    if (n1.value > n2.value) {
    return 1;
    } else if (n1.value == n2.value) {
    return 0;
    } else {
    return -1;
    }
    }
    };

    public List<Vertice> getShortestPath(Vertice v1, Vertice v2) {
    PriorityQueue<Vertice> queue = new PriorityQueue<Vertice>(100, VERTICES_VALUE_COMPARATOR);
    queue.add(v1);

    HashMap<Vertice, Vertice> map = new HashMap<Vertice, Vertice>();

    LinkedList<Vertice> path = new LinkedList<Vertice>();

    for (Vertice v : vertices) {
    v.value = Double.POSITIVE_INFINITY;
    }
    v1.value = 0;

    Vertice u = null;
    do {
    try {
    u = queue.remove();
    } catch (NoSuchElementException e) {
    return new LinkedList<Vertice>();
    }

    for (Edge e : u.getStar()) {
    Vertice v = (e.first.equals(u)) ? e.second : e.first;
    double potential = u.value + e.weight;

    if (v.value == Double.POSITIVE_INFINITY) {
    v.value = potential;
    queue.add(v);
    map.put(v, u);
    } else if (v.value > potential) {
    v.value = potential;
    map.put(v, u);
    }
    }
    } while (!u.equals(v2));

    do {
    path.add(u);
    u = map.get(u);
    } while (u != null);

    Collections.reverse(path);

    return path;
    }

    public double getShortestDistance(Vertice n1, Vertice n2) {
    List<Vertice> path = getShortestPath(n1, n2);
    if (path.isEmpty()) {
    return Double.POSITIVE_INFINITY;
    }
    return path.get(path.size() - 1).value;
    }*/
}
