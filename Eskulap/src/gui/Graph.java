package gui;

import eskulap.JarvisMarch;
import eskulap.RayCasting;
import floyd_warshall.FloydWarshallAlgorithm;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import storage.Construction;
import storage.Hospital;
import storage.Map;
import storage.Patient;
import storage.Road;

public class Graph extends JPanel {

    private Rectangle2D[] hospitals;
    private Ellipse2D[] constructs;
    private Line2D[] roads;
    private Point[] border_points;
    private Polygon border;
    private Patient patient;
    private int[] path;
    private double scaleX;
    private double scaleY;
    private int minimum_x;
    private int minimum_y;
    private int maximum_x;
    private int maximum_y;
    private boolean draw_hospitals, draw_objects, draw_roads;
    private Timer animation;
    private FloydWarshallAlgorithm fwa;
    private final CenterPane center_pane;
    private static final int X_SIZE = 1000;
    private static final int Y_SIZE = 500;
    private static final int RADIUS = 10;
    private static final int PATIENT_RADIUS = 5;
    private static final int SPACE = 50;

    public Graph(CenterPane pane) {
        center_pane = pane;
        init();
    }

    private void init() {
        draw_hospitals = true;
        draw_objects = true;
        draw_roads = true;
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (center_pane.getBoard().getWest().mapLoaded() && !patientLoaded() && e.isControlDown()) {
                    loadPatient(new Patient(0, e.getPoint()));
                }
            }
        });
        setPreferredSize(new Dimension(X_SIZE, Y_SIZE));
        setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
    }

    private void animate() {
        if (animation != null) {
            animation.cancel();
        }
        animation = new Timer();
        animation.scheduleAtFixedRate(new animTask(), 0, 50);
    }

    public void toggleHospitals() {
        draw_hospitals = !draw_hospitals;
        repaint();
    }

    public void toggleObjects() {
        draw_objects = !draw_objects;
        repaint();
    }

    public void toggleRoads() {
        draw_roads = !draw_roads;
        repaint();
    }

    public boolean patientExists() {
        return patient != null;
    }

    private class animTask extends TimerTask {

        int i;
        double p_x, p_y;
        double dx, dy;
        Point target;
        Point start;

        private animTask() {
            i = 0;
            start = patient.getWsp();
            p_x = patient.getWsp().x;
            p_y = patient.getWsp().y;
            getTarget();
            getLine();
        }

        private int getDx() {
            int dX = target.x - start.x;
            if (dX < 0) {
                dX = -1;
            } else {
                dX = 1;
            }
            return dX;
        }

        private int getDy() {
            int dY = target.y - start.y;
            if (dY < 0) {
                dY = -1;
            } else {
                dY = 1;
            }
            return dY;
        }

        private void getTarget() {
            target = new Point((int) hospitals[path[i]].getCenterX(), (int) hospitals[path[i]].getCenterY());
        }

        private boolean crossedX() {
            return (target.x - start.x) * (target.x - patient.getWsp().x) <= 0;
        }

        private boolean crossedY() {
            return (target.y - start.y) * (target.y - patient.getWsp().y) <= 0;
        }

        private double direction() {
            return 1.0 * (target.y - patient.getWsp().y) / (target.x - patient.getWsp().x);
        }

        private void getLine() {
            dx = getDx();
            dy = direction();
            if (Double.isInfinite(dy)) {
                dx = 0;
                dy = getDy();
            } else if (dy > -1 && dy != 0 && dy < 1) {
                dx /= Math.abs(dy);
                dy = getDy();
            }
        }

        @Override
        public void run() {
            repaint();
            p_x += dx;
            p_y += dy;
            patient.move((int) p_x, (int) p_y);
            if (crossedX() && crossedY()) {
                patient.move(target.x, target.y);
                p_x = patient.getWsp().x;
                p_y = patient.getWsp().y;
                repaint();
                if (++i < path.length) {
                    start = patient.getWsp();
                    getTarget();
                    getLine();
                } else {
                    patient = null;
                    cancel();
                }
            }
        }

    }

    public int[] getPath() {
        return path;
    }

    public void loadPatient(Patient p) {
        patient = p;
        if (center_pane.getBoard().getWest().mapLoaded()) {
            if (new RayCasting().isInside(border_points, border_points.length, patient.getWsp())) {
                routePatient();
            } else {
                JOptionPane.showMessageDialog(new JFrame(), "Pacjent jest poza granicami państwa.");
            }
        } else {
            JOptionPane.showMessageDialog(new JFrame(), "Najpierw wczytaj państwo!");
        }
    }

    public void scalePatient(Patient p) {
        patient = p;
        for (Point pt : border_points) {
            System.out.println(pt);
        }
        System.out.println(p.getWsp());
        if (new RayCasting().isInside(border_points, border_points.length, patient.getWsp())) {
            patient.move(scale_x(p.getWsp().x), scale_y(p.getWsp().y));
            routePatient();
        } else {
            JOptionPane.showMessageDialog(new JFrame(), "Pacjent jest poza granicami państwa.");
        }
    }

    private void routePatient() {
        int closest = patient.findNearestHospital(center_pane.getBoard().getWest().getMap().getHospitals());
        fwa.reset();
        path = fwa.getPath(closest, fwa.getClosestVertex(closest));
        animate();
        for (int i = 0; i < path.length; i++) {
            center_pane.print("" + path[i]);
            if (i < path.length - 1) {
                center_pane.print(" -> ");
            }
        }
        center_pane.print("\n");
    }

    public void loadMap() {
        Map map = center_pane.getBoard().getWest().getMap();
        Hospital[] hos = map.getHospitals();
        Construction[] con = map.getConstructs();
        Road[] ros = map.getRoads();
        ArrayList<Point> points = new ArrayList<>();
        findScale(hos, con);
        hospitals = new Rectangle2D[hos.length];
        for (int i = 0; i < hospitals.length; i++) {
            points.add(hos[i].getWsp());
            int x = scale_x(hos[i].getWsp().x);
            int y = scale_y(hos[i].getWsp().y);
            hospitals[i] = new Rectangle2D.Double(x - RADIUS, y - RADIUS, 2 * RADIUS, 2 * RADIUS);
        }
        constructs = new Ellipse2D[con.length];
        for (int i = 0; i < constructs.length; i++) {
            points.add(con[i].getWsp());
            int x = scale_x(con[i].getWsp().x);
            int y = scale_y(con[i].getWsp().y);
            constructs[i] = new Ellipse2D.Double(x - RADIUS, y - RADIUS, RADIUS * 2, RADIUS * 2);
        }
        points = new JarvisMarch().calculateBorder(points.toArray(new Point[0]), points.size());
        int[] xpoints = new int[points.size()];
        int[] ypoints = new int[points.size()];
        border_points = new Point[points.size()];
        for (int i = 0; i < points.size(); i++) {
            Point p = points.get(i);
            border_points[i] = p;
            xpoints[i] = scale_x(p.x);
            ypoints[i] = scale_y(p.y);
        }
        border = new Polygon(xpoints, ypoints, points.size());
        roads = new Line2D[ros.length];
        for (int i = 0; i < ros.length; i++) {
            int x_0 = scale_x(hos[ros[i].getIdFirst() - 1].getWsp().x);
            int x_1 = scale_x(hos[ros[i].getIdSecond() - 1].getWsp().x);
            int y_0 = scale_y(hos[ros[i].getIdFirst() - 1].getWsp().y);
            int y_1 = scale_y(hos[ros[i].getIdSecond() - 1].getWsp().y);
            roads[i] = new Line2D.Double(x_0, y_0, x_1, y_1);
        }
        map.addCrossings();
        fwa = new FloydWarshallAlgorithm(map);
        fwa.applyAlgorithm();
        repaint();
    }

    private int scale_x(int x) {
        return (int) (scaleX * (x - minimum_x)) + SPACE + RADIUS;
    }

    private int scale_y(int y) {
        return (int) (scaleY * (y - minimum_y)) + SPACE + RADIUS;
    }

    private void findScale(Hospital[] hos, Construction[] con) {
        int maxX = hos[0].getWsp().x;
        int maxY = hos[0].getWsp().y;
        int minX = maxX;
        int minY = maxY;
        for (Hospital h : hos) {
            int x = h.getWsp().x;
            int y = h.getWsp().y;
            if (maxX < x) {
                maxX = x;
            } else if (minX > x) {
                minX = x;
            }
            if (maxY < y) {
                maxY = y;
            } else if (minY > y) {
                minY = y;
            }
        }
        for (Construction c : con) {
            int x = c.getWsp().x;
            int y = c.getWsp().y;
            if (maxX < x) {
                maxX = x;
            } else if (minX > x) {
                minX = x;
            }
            if (maxY < y) {
                maxY = y;
            } else if (minY > y) {
                minY = y;
            }
        }
        scaleX = (X_SIZE - 2 * SPACE) / (maxX - minX);
        scaleY = (Y_SIZE - 2 * SPACE) / (maxY - minY);
        minimum_x = minX;
        minimum_y = minY;
        maximum_x = maxX;
        maximum_y = maxY;
    }

    private void drawAxis(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawString("" + minimum_x, 2 * SPACE, Y_SIZE - SPACE);
        g.drawString("" + maximum_x, X_SIZE - SPACE, Y_SIZE - SPACE);
        g.drawString("" + minimum_y, SPACE, SPACE);
        g.drawString("" + maximum_y, SPACE, Y_SIZE - 2 * SPACE);
    }

    private void drawHospitals(Graphics2D g) {
        int index = 1;
        for (Rectangle2D r : hospitals) {
            g.setColor(Color.RED);
            g.fill(r);
            g.setColor(Color.BLACK);
            g.drawString("" + index, (int) r.getCenterX() - 2, (int) r.getCenterY() + 5);
            index++;
        }
    }

    private void drawObjects(Graphics2D g) {
        int index = 1;
        for (Ellipse2D e : constructs) {
            g.setColor(Color.RED);
            g.fill(e);
            g.setColor(Color.BLACK);
            g.drawString("" + index, (int) e.getCenterX() - 2, (int) e.getCenterY() + 5);
            index++;
        }
    }

    private void drawRoads(Graphics2D g) {
        for (Line2D l : roads) {
            g.setColor(Color.RED);
            g.draw(l);
        }
    }

    public boolean patientLoaded() {
        return patient != null;
    }

    @Override
    protected void paintComponent(Graphics gr) {
        super.paintComponent(gr);
        Graphics2D g = (Graphics2D) gr;
        Font font = new Font("Serif", Font.PLAIN, 15);
        g.setFont(font);

        if (hospitals != null) {
            drawAxis(g);
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(0, 0, X_SIZE, Y_SIZE);
            g.setColor(Color.WHITE);
            g.fillPolygon(border);
            if (draw_hospitals) {
                drawHospitals(g);
            }
            if (draw_objects) {
                drawObjects(g);
            }
            if (draw_roads) {
                drawRoads(g);
            }
            if (patient != null) {
                g.setColor(Color.GREEN);
                g.fillOval(patient.getWsp().x - PATIENT_RADIUS, patient.getWsp().y - PATIENT_RADIUS, 2 * PATIENT_RADIUS, 2 * PATIENT_RADIUS);
            }
        } else {
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, X_SIZE, Y_SIZE);
            g.setColor(Color.BLACK);
            g.drawString("Wczytaj państwo.", X_SIZE / 2, Y_SIZE / 2);
        }
    }

}
