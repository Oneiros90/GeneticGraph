package graph;

import java.awt.Point;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class Vertice {

    protected String name;
    protected Point location;
    protected Graph graph;
    protected boolean selected;
    protected double value;

    public Vertice() {
    }

    public Vertice(String name, int x, int y) {
        this();
        this.name = name;
        this.location = new Point(x, y);
    }

    public Vertice(String name, Point location) {
        this(name, location.x, location.y);
    }

    public Point getLocation() {
        return location;
    }

    public int getX() {
        return location.x;
    }

    public int getY() {
        return location.y;
    }

    public void setLocation(Point location) {
        this.location = location;
        for (Edge e : getStar()) {
            e.setWeight();
        }
    }

    public void setLocation(int x, int y) {
        this.setLocation(new Point(x, y));
    }

    public double getDistanceFrom(Vertice n) {
        Point p1 = this.getLocation();
        Point p2 = n.getLocation();
        return java.awt.geom.Point2D.distance(p1.x, p1.y, p2.x, p2.y);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Edge> getStar() {
        List<Edge> star = new LinkedList<Edge>();
        for (Edge e : graph.edges) {
            if (e.isLinking(this)) {
                star.add(e);
            }
        }
        return star;
    }

    public List<Vertice> getLinkedVertices() {
        List<Vertice> vertices = new LinkedList<Vertice>();
        for (Edge e : getStar()) {
            vertices.add((e.first.equals(this)) ? e.second : e.first);
        }
        return vertices;
    }

    public boolean isLinkedTo(Vertice vertice) {
        for (Vertice v : getLinkedVertices()) {
            if (v.equals(vertice)) {
                return true;
            }
        }
        return false;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof Vertice) {
            Vertice v = (Vertice) obj;
            return this.name.equals(v.name)
                    && this.location.equals(v.location);
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = 59 * hash + (this.location != null ? this.location.hashCode() : 0);
        return hash;
    }

    public static class DistanceComparator implements Comparator<Vertice> {

        private Vertice vertice;

        public DistanceComparator(Vertice v) {
            this.vertice = v;
        }

        @Override
        public int compare(Vertice v1, Vertice v2) {
            double a = vertice.getDistanceFrom(v1);
            double b = vertice.getDistanceFrom(v2);
            return (int) (a - b);
        }
    }
}