package graph;

public class Edge {

    protected Vertice first;
    protected Vertice second;
    protected double weight;
    protected boolean selected;

    public Edge() {
    }

    public Edge(Vertice first, Vertice second) {
        this.first = first;
        this.second = second;
        this.setWeight();
    }

    public Edge(Vertice first, Vertice second, double weight) {
        this.first = first;
        this.second = second;
        this.weight = weight;
    }

    public boolean isLinking(Vertice v) {
        return first.equals(v) || second.equals(v);
    }

    public boolean isLinking(Vertice v1, Vertice v2) {
        if (v1.equals(v2)) {
            return false;
        }
        return isLinking(v1) && isLinking(v2);
    }

    public boolean isAdjacentTo(Edge e) {
        return isLinking(e.first) || isLinking(e.second);
    }

    public boolean isLinkingSameVerticesOf(Edge e) {
        return isLinking(e.first, e.second);
    }

    public void setFirstVertice(Vertice from) {
        this.first = from;
    }

    public Vertice getFirstVertice() {
        return this.first;
    }

    public void setSecondVertice(Vertice to) {
        this.second = to;
    }

    public Vertice getSecondVertice() {
        return second;
    }

    public void setWeight(double weight) {
        if (weight >= 0) {
            this.weight = weight;
        } else {
            throw new IllegalArgumentException("Weight must be positive");
        }
    }

    public final void setWeight() {
        this.weight = this.first.getDistanceFrom(this.second);
    }

    public double getWeight() {
        return weight;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof Edge) {
            Edge e = (Edge) obj;
            return this.isLinkingSameVerticesOf(e) && this.weight == e.weight;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 11 * hash + (this.first != null ? this.first.hashCode() : 0);
        hash = 11 * hash + (this.second != null ? this.second.hashCode() : 0);
        hash = 11 * hash + (int) (Double.doubleToLongBits(this.weight)
                ^ (Double.doubleToLongBits(this.weight) >>> 32));
        return hash;
    }
}
