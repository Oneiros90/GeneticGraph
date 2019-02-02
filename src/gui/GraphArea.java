package gui;

import graph.Edge;
import graph.Graph;
import graph.Vertice;
import graphNPC.MDS.MDSManager;
import graphNPC.MDS.MDSResult;
import graphNPC.MEDS.MEDSManager;
import graphNPC.MEDS.MEDSResult;
import graphNPC.MIS.MISManager;
import graphNPC.MIS.MISResult;
import graphNPC.MM.MMManager;
import graphNPC.MM.MMResult;
import graphNPC.TSP.TSPManager;
import graphNPC.TSP.TSPResult;
import java.awt.*;
import java.awt.event.*;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JPanel;
import util.FileManager;

public class GraphArea extends JPanel {

    //Background
    public static final BufferedImage BACKGROUND = FileManager.getImageFromResource("/images/bg.png");
    //Tools
    public static final int NO_TOOL = -1;
    public static final int TOOL_SELECT = 0;
    public static final int TOOL_NEW_VERTICE = 1;
    public static final int TOOL_NEW_EDGE = 2;
    //Cursors
    public static final Cursor DEFAULT_CURSOR = new Cursor(Cursor.DEFAULT_CURSOR);
    public static final Cursor HAND_CURSOR = new Cursor(Cursor.HAND_CURSOR);
    public static final Cursor CROSSHAIR_CURSOR = new Cursor(Cursor.CROSSHAIR_CURSOR);
    //Vertice ray
    public static final int VERTICE_RAY = 20;
    //Colors
    public static final Color VERTICE_COLOR = Color.white;
    public static final Color VERTICE_SELECTION = Color.red;
    public static final Color EDGE_COLOR = Color.black;
    public static final Color EDGE_SELECTION = Color.red;
    //Font
    public static final Font VERTICE_FONT = new Font("Trebuchet", 0, 18);
    //Strokes
    public static final BasicStroke STROKE_1 = new BasicStroke(1);
    public static final BasicStroke STROKE_2 = new BasicStroke(2);
    public static final BasicStroke STROKE_3 = new BasicStroke(3);
    public static final BasicStroke DASHED_STROKE_1 = new BasicStroke(1.0f, BasicStroke.CAP_BUTT,
            BasicStroke.JOIN_MITER, 10.0f, new float[]{10.0f}, 0.0f);
    //Fields
    private Graph graph;
    private int tool;
    private Vertice vertice;
    private Edge newEdge, selectedEdge;
    //Results
    private TSPResult tspResult;
    private MMResult mmResult;
    private MISResult misResult;
    private MDSResult mdsResult;
    private MEDSResult medsResult;
    //Logger
    private Logger logger;

    public GraphArea() {
        super(null);
        this.setBackground(Color.white);

        this.graph = new Graph();
        this.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                Point clicked = e.getPoint();
                if (tool == TOOL_SELECT) {
                    Vertice v = pointedVertice(clicked);
                    if (v != null) {
                        select(v);
                    } else {
                        Edge edge = pointedEdge(clicked);
                        if (edge != null) {
                            select(edge);
                        } else {
                            if (vertice != null) {
                                vertice.setSelected(false);
                                repaint();
                            }
                            if (selectedEdge != null) {
                                selectedEdge.setSelected(false);
                                repaint();
                            }
                        }
                    }
                } else if (tool == TOOL_NEW_VERTICE) {
                    newVertice(clicked);
                } else if (tool == TOOL_NEW_EDGE) {
                    Vertice v = pointedVertice(clicked);
                    if (v != null) {
                        if (newEdge == null) {
                            newEdgeFrom(v);
                        } else {
                            newEdgeTo(v);
                        }
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (tool == TOOL_SELECT) {
                } else if (tool == TOOL_NEW_VERTICE) {
                } else if (tool == TOOL_NEW_EDGE) {
                    if (newEdge != null) {
                        Vertice v = pointedVertice(e.getPoint());
                        if (v != null) {
                            newEdgeTo(v);
                        }
                        newEdge = null;
                        vertice.setSelected(false);
                        repaint();
                    }
                }
            }
        });
        this.addMouseMotionListener(new MouseMotionAdapter() {

            @Override
            public void mouseMoved(MouseEvent e) {
                Point mouseLocation = e.getPoint();
                if (tool == TOOL_SELECT) {
                    if (pointedVertice(mouseLocation) != null || pointedEdge(mouseLocation) != null) {
                        setCursor(HAND_CURSOR);
                    } else {
                        setCursor(DEFAULT_CURSOR);
                    }
                } else if (tool == TOOL_NEW_EDGE) {
                    if (newEdge != null) {
                        repaint();
                    } else {
                        if (pointedVertice(mouseLocation) != null) {
                            setCursor(CROSSHAIR_CURSOR);
                        } else {
                            setCursor(DEFAULT_CURSOR);
                        }
                    }
                }
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                Point clicked = e.getPoint();
                if (tool == TOOL_SELECT) {
                    Vertice v = pointedVertice(clicked);
                    if (vertice != null && v != null && selectedEdge == null) {
                        vertice.setLocation(clicked);
                        setCursor(HAND_CURSOR);
                    }
                } else if (tool == TOOL_NEW_VERTICE) {
                    vertice.setLocation(clicked);
                }
                repaint();
            }
        });
        this.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent ev) {
                if (ev.getKeyCode() == KeyEvent.VK_DELETE) {
                    for (Vertice v : graph.getVertices()) {
                        if (v.isSelected()) {
                            graph.remove(v);
                            break;
                        }
                    }
                    int i = 1;
                    for (Vertice v : graph.getVertices()) {
                        v.setName(i + "");
                        i++;
                    }
                    for (Edge e : graph.getEdges()) {
                        if (e.isSelected()) {
                            graph.remove(e);
                            break;
                        }
                    }
                    repaint();
                }
            }
        });
        this.logger = new Logger() {

            @Override
            public void log(Object s) {
                System.out.println(s);
            }
        };
    }

    public void setTool(int t) {
        this.tool = t;
        if (this.tool == NO_TOOL) {
            this.setCursor(DEFAULT_CURSOR);
        } else if (this.tool == TOOL_SELECT) {
            setCursor(DEFAULT_CURSOR);
        } else if (this.tool == TOOL_NEW_VERTICE) {
            this.setCursor(CROSSHAIR_CURSOR);
        } else if (this.tool == TOOL_NEW_EDGE) {
            setCursor(DEFAULT_CURSOR);
        }
        if (this.vertice != null) {
            this.vertice.setSelected(false);
            this.vertice = null;
        }
        if (this.newEdge != null) {
            this.newEdge = null;
        }
        if (this.tool != NO_TOOL) {
            this.clearAlgorithmSolutions();
            repaint();
        }
    }

    public void setLogger(Logger l) {
        this.logger = l;
    }

    public void clearGraph() {
        graph.clear();
        this.clearAlgorithmSolutions();
    }

    public void removeEdges() {
        graph.clearEdges();
        this.clearAlgorithmSolutions();
    }

    public void complete() {
        graph.complete();
        this.clearAlgorithmSolutions();
    }

    public void autolink() {
        graph.autolink();
        this.clearAlgorithmSolutions();
    }

    /***************************************************************************
     *                             SELECTION                                   *
     **************************************************************************/
    private void select(Vertice pointed) {
        if (this.vertice != null) {
            this.vertice.setSelected(false);
        }
        if (this.selectedEdge != null) {
            this.selectedEdge.setSelected(false);
            this.selectedEdge = null;
        }
        pointed.setSelected(true);
        this.vertice = pointed;
        repaint();
    }

    private void select(Edge pointedEdge) {
        if (this.selectedEdge != null) {
            this.selectedEdge.setSelected(false);
        }
        if (this.vertice != null) {
            this.vertice.setSelected(false);
            this.vertice = null;
        }
        pointedEdge.setSelected(true);
        this.selectedEdge = pointedEdge;
        repaint();
    }

    /***************************************************************************
     *                              CREATION                                   *
     **************************************************************************/
    private void newVertice(Point p) {
        this.vertice = new Vertice(this.graph.getVertices().size() + 1 + "", p);
        this.graph.add(this.vertice);
        repaint();
    }

    private void newEdgeFrom(Vertice pointed) {
        select(pointed);
        this.setCursor(CROSSHAIR_CURSOR);
        this.newEdge = new Edge();
        this.newEdge.setFirstVertice(this.vertice);
    }

    private void newEdgeTo(Vertice pointed) {
        if (pointed == newEdge.getFirstVertice()) {
            return;
        }
        newEdge.setSecondVertice(pointed);
        newEdge.setWeight();
        graph.add(newEdge);
    }

    /***************************************************************************
     *                          ELEMENT POINTED                                *
     **************************************************************************/
    private Vertice pointedVertice(Point p) {
        Vertice pVertice = null;
        double pDistance = -1;

        for (Vertice v : graph.getVertices()) {
            double d = p.distance(v.getLocation());

            if (d < VERTICE_RAY && (pDistance == -1 || d < pDistance)) {
                pVertice = v;
                pDistance = d;
            }
        }
        return pVertice;
    }

    private Edge pointedEdge(Point p) {
        for (Edge e : graph.getEdges()) {
            Point v1 = e.getFirstVertice().getLocation();
            Point v2 = e.getSecondVertice().getLocation();
            double distance = Line2D.Double.ptSegDist(v1.x, v1.y, v2.x, v2.y, p.x, p.y);
            if (distance < 3) {
                return e;
            }
        }
        return null;
    }

    /***************************************************************************
     *                                TSP                                      *
     **************************************************************************/
    public void findTSPSolution(boolean b) throws InterruptedException {
        this.clearAlgorithmSolutionsExcept(this.tspResult);
        if (this.graph.getVerticesCount() > 1) {
            TSPManager ts = new TSPManager(graph);
            TSPResult result = b? ts.findGASolution() : ts.findBFSOptimal();
            if (tspResult == null || result.isBetterThen(tspResult)) {
                this.logger.log(result);
                tspResult = result;
                repaint();
            }
            this.setTool(NO_TOOL);
        }
    }

    public boolean isTSPCalculated() {
        return this.tspResult != null;
    }

    public void clearTSPSolution() {
        this.tspResult = null;
    }

    /***************************************************************************
     *                                 MM                                      *
     **************************************************************************/
    public void findMMSolution(boolean b) throws InterruptedException {
        this.clearAlgorithmSolutionsExcept(this.mmResult);
        if (this.graph.getVerticesCount() > 1 && this.graph.getEdgesCount() > 0) {
            MMManager mm = new MMManager(graph);
            MMResult result = b ? mm.findGASolution() : mm.findBFSOptimal();
            if (result != null && (mmResult == null || result.isBetterThen(mmResult))) {
                this.logger.log(result);
                mmResult = result;
                repaint();
            }
            this.setTool(NO_TOOL);
        }
    }

    public boolean isMMCalculated() {
        return this.mmResult != null;
    }

    public void clearMMSolution() {
        this.mmResult = null;
    }

    /***************************************************************************
     *                                MIS                                      *
     **************************************************************************/
    public void findMISSolution(boolean b) throws InterruptedException {
        this.clearAlgorithmSolutionsExcept(this.misResult);
        if (this.graph.getVerticesCount() > 1 && this.graph.getEdgesCount() > 0) {
            MISManager mis = new MISManager(graph);
            MISResult result = b ? mis.findGASolution() : mis.findBFSOptimal();
            if (result != null && (misResult == null || result.isBetterThen(misResult))) {
                this.logger.log(result);
                misResult = result;
                repaint();
            }
            this.setTool(NO_TOOL);
        }
    }

    public boolean isMISCalculated() {
        return this.misResult != null;
    }

    public void clearMISSolution() {
        this.misResult = null;
    }

    /***************************************************************************
     *                                MDS                                      *
     **************************************************************************/
    public void findMDSSolution(boolean b) throws InterruptedException {
        this.clearAlgorithmSolutionsExcept(this.mdsResult);
        if (this.graph.getVerticesCount() > 1 && this.graph.getEdgesCount() > 0) {
            MDSManager mds = new MDSManager(graph);
            MDSResult result = b ? mds.findGASolution() : mds.findBFSOptimal();
            if (result != null && (mdsResult == null || result.isBetterThen(mdsResult))) {
                this.logger.log(result);
                mdsResult = result;
                repaint();
            }
            this.setTool(NO_TOOL);
        }
    }

    public boolean isMDSCalculated() {
        return this.mdsResult != null;
    }

    public void clearMDSSolution() {
        this.mdsResult = null;
    }

    /***************************************************************************
     *                                MEDS                                     *
     **************************************************************************/
    public void findMEDSSolution(boolean b) throws InterruptedException {
        this.clearAlgorithmSolutionsExcept(this.medsResult);
        if (this.graph.getVerticesCount() > 1 && this.graph.getEdgesCount() > 0) {
            MEDSManager meds = new MEDSManager(graph);
            MEDSResult result = b ? meds.findGASolution() : meds.findBFSOptimal();
            if (result != null && (medsResult == null || result.isBetterThen(medsResult))) {
                this.logger.log(result);
                medsResult = result;
                repaint();
            }
            this.setTool(NO_TOOL);
        }
    }

    public boolean isMEDSCalculated() {
        return this.medsResult != null;
    }

    public void clearMEDSSolution() {
        this.medsResult = null;
    }

    /***************************************************************************
     *                                OTHER                                    *
     **************************************************************************/
    protected void clearAlgorithmSolutions(){
        this.clearAlgorithmSolutionsExcept(null);
        repaint();
    }

    private void clearAlgorithmSolutionsExcept(Object obj) {
        if (!(obj instanceof TSPResult)) {
            this.tspResult = null;
        }
        if (!(obj instanceof MMResult)) {
            this.mmResult = null;
        }
        if (!(obj instanceof MISResult)) {
            this.misResult = null;
        }
        if (!(obj instanceof MDSResult)) {
            this.mdsResult = null;
        }
        if (!(obj instanceof MEDSResult)) {
            this.medsResult = null;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        //Disegno lo sfondo
        if (this.getBackground().equals(Color.white)) {
            for (int i = 0; i < this.getWidth(); i += BACKGROUND.getWidth()) {
                for (int j = 0; j < this.getHeight(); j += BACKGROUND.getHeight()) {
                    g.drawImage(BACKGROUND, i, j, this);
                }
            }
        }

        //Ottengo e preparo il Graphics2D
        Graphics2D g2D = (Graphics2D) g;
        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        //Ottengo eventuali elementi evidenziati da algoritmi eseguiti
        List<Edge> tspPath = new LinkedList<Edge>();
        if (isTSPCalculated()) {
            tspPath = tspResult.getPath();
        }

        List<Edge> mmEdges = new LinkedList<Edge>();
        if (isMMCalculated()) {
            mmEdges = mmResult.getEdges();
        }

        List<Vertice> misVertices = new LinkedList<Vertice>();
        if (isMISCalculated()) {
            misVertices = misResult.getVertices();
        }

        List<Vertice> mdsVertices = new LinkedList<Vertice>();
        if (isMDSCalculated()) {
            mdsVertices = mdsResult.getVertices();
        }

        List<Edge> medsEdges = new LinkedList<Edge>();
        if (isMEDSCalculated()) {
            medsEdges = medsResult.getEdges();
        }

        //Disegno gli archi ed, eventualmente, li evidenzio
        Collection<Edge> edges = this.graph.getEdges();
        for (Edge e : edges) {
            if (e.isSelected() || tspPath.contains(e) || mmEdges.contains(e) || medsEdges.contains(e)) {
                g2D.setColor(EDGE_SELECTION);
                g2D.setStroke(STROKE_3);
            } else {
                g2D.setColor(EDGE_COLOR);
                g2D.setStroke(STROKE_2);
            }
            Point n1 = e.getFirstVertice().getLocation();
            Point n2 = e.getSecondVertice().getLocation();
            g2D.drawLine(n1.x, n1.y, n2.x, n2.y);
        }

        //Disegno gli archi tratteggiati del TSP
        g2D.setColor(EDGE_SELECTION);
        g2D.setStroke(DASHED_STROKE_1);
        for (Edge e : tspPath) {
            if (!edges.contains(e)) {
                Point n1 = e.getFirstVertice().getLocation();
                Point n2 = e.getSecondVertice().getLocation();
                g2D.drawLine(n1.x, n1.y, n2.x, n2.y);
            }
        }

        //Disegno l'arco di costruzione se l'utente ne sta creando uno nuovo
        if (this.newEdge != null) {
            g2D.setColor(EDGE_COLOR);
            g2D.setStroke(STROKE_2);
            Point n1 = this.newEdge.getFirstVertice().getLocation();
            Point n2 = getMouseLocation();
            g2D.drawLine(n1.x, n1.y, n2.x, n2.y);
        }

        //Disegno i vertici
        Collection<Vertice> vertices = graph.getVertices();
        //g2D.setFont(VERTICE_FONT);
        for (Vertice v : vertices) {

            //Ottengo l'angolo in alto a sinistra del vertice
            Point p = new Point(v.getX() - VERTICE_RAY + 2, v.getY() - VERTICE_RAY + 2);

            //Disegno il vertice
            g2D.setColor(VERTICE_COLOR);
            g2D.fillOval(p.x, p.y, VERTICE_RAY * 2, VERTICE_RAY * 2);

            //Disegno il bordo del vertice a seconda della sua selezione
            if (v.isSelected() || misVertices.contains(v) || mdsVertices.contains(v)) {
                g2D.setColor(VERTICE_SELECTION);
                g2D.setStroke(STROKE_3);
            } else {
                g2D.setColor(Color.black);
                g2D.setStroke(STROKE_2);
            }
            g2D.drawOval(p.x, p.y, VERTICE_RAY * 2, VERTICE_RAY * 2);

            //Ottengo il punto in cui scrivere l'etichetta del vertice
            p.setLocation(p.x + VERTICE_RAY, p.y + VERTICE_RAY);
            Point s = getStringLocation(v.getName(), g2D.getFont(), g2D.getFontRenderContext(), p);

            //Scrivo l'etichetta
            g2D.drawString(v.getName(), s.x, s.y);
        }
    }

    /**
     * Restituisce la posizione del mouse relativamente a questo pannello
     * @return La posizione del mouse relativamente a questo pannello
     */
    private Point getMouseLocation() {
        Point mouse = java.awt.MouseInfo.getPointerInfo().getLocation();
        Point area = this.getLocationOnScreen();
        mouse.setLocation(mouse.x - area.x, mouse.y - area.y);
        return mouse;
    }

    /**
     * Restituisce l'angolo in alto a sinistra di una stringa <code>s</code> che ha il font
     * <code>f</code> ed è centrata in <code>p</code>.
     * @param s La stringa che si vuole disegnare
     * @param f Il font della stringa
     * @param frc Il FontRenderContext della stringa
     * @param p Il punto dove centrare la stringa
     * @return L'angolo in alto a sinistra della stringa
     */
    private Point getStringLocation(String s, Font f, FontRenderContext frc, Point p) {
        Rectangle2D rec = f.getStringBounds(s, frc);
        LineMetrics lm = f.getLineMetrics(s, frc);

        int x = p.x - (int) rec.getWidth() / 2;
        int y = p.y - (int) (lm.getLeading() - lm.getAscent()) / 2;
        return new Point(x, y);
    }
}

interface Logger {

    public void log(Object s);
}