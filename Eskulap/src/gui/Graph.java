package gui;

import eskulap.JarvisMarch;
import eskulap.RayCasting;
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
import javax.swing.BorderFactory;
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
    private Point label;
    private int hovered_id;
    private int minimum_x;
    private int minimum_y;
    private int maximum_x;
    private int maximum_y;
    private double scaleX;
    private double scaleY;
    private boolean draw_hospitals;
    private boolean draw_objects;
    private boolean draw_roads;
    private Animator animation;
    private final Map map;
    private final CenterPane center_pane;
    private static final int X_SIZE = 1000;
    private static final int Y_SIZE = 500;
    private static final int RADIUS = 10;
    private static final int PATIENT_RADIUS = 5;
    private static final int SPACE = 50;

    public Graph(CenterPane pane, Map m) {
        hovered_id = -1;
        center_pane = pane;
        draw_hospitals = true;
        draw_objects = true;
        draw_roads = true;
        map = m;
        init();
        loadMap();
        repaint();
    }

    private void init() {
        addMouseListener(new MouseAdapter() {
            private int id = 1;

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.isControlDown()) {
                    addPatient(new Patient(id++, unscale_x(e.getPoint().x), unscale_y(e.getPoint().y)));
                }
            }
        });
        addMouseMotionListener(new MouseAdapter() {

            @Override
            public void mouseMoved(MouseEvent e) {
                hovered_id = -1;
                for (int i = 0; i < hospitals.length; i++) {
                    if (Math.abs(e.getX() - hospitals[i].getCenterX()) <= RADIUS
                            && Math.abs(e.getY() - hospitals[i].getCenterY()) <= RADIUS
                            && map.getHospitals()[i].getBedNumber() != 0) {
                        hovered_id = i;
                        label = e.getPoint();
                        break;
                    }
                }
                repaint();
            }

        });
        setPreferredSize(new Dimension(X_SIZE, Y_SIZE));
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }

    public void print(String s) {
        center_pane.print(s);
    }

    public void setSpeed(int s) {
        Animator.setPercent(s);
    }

    public void toggle(int id) {
        switch (id) {
            case 0:
                draw_hospitals = !draw_hospitals;
                break;
            case 1:
                draw_objects = !draw_objects;
                break;
            case 2:
                draw_roads = !draw_roads;
        }
        repaint();
    }

    public void addPatient(Patient p) {
        if (new RayCasting(border_points).isInside(p.getWsp())) {
            p.move(scale_x(p.getWsp().x), scale_y(p.getWsp().y));
            animation.addPatient(p);
        } else {
            center_pane.print("Pacjent " + p.getId() + " jest poza granicami państwa.\n");
        }
    }

    private void loadHospitals(ArrayList<Point> points) {
        Hospital[] hos = map.getHospitals();
        hospitals = new Rectangle2D[hos.length];
        for (int i = 0; i < hospitals.length; i++) {
            int x = scale_x(hos[i].getWsp().x);
            int y = scale_y(hos[i].getWsp().y);
            if (hos[i].getBedNumber() != 0) {
                hospitals[i] = new Rectangle2D.Double(x - RADIUS, y - RADIUS, 2 * RADIUS, 2 * RADIUS);
                points.add(hos[i].getWsp());
            } else {
                hospitals[i] = new Rectangle2D.Double(x, y, 0, 0);
            }
        }
    }

    private void loadConstructs(ArrayList<Point> points) {
        Construction[] con = map.getConstructs();
        constructs = new Ellipse2D[con.length];
        for (int i = 0; i < constructs.length; i++) {
            points.add(con[i].getWsp());
            int x = scale_x(con[i].getWsp().x);
            int y = scale_y(con[i].getWsp().y);
            constructs[i] = new Ellipse2D.Double(x - RADIUS, y - RADIUS, RADIUS * 2, RADIUS * 2);
        }
    }

    private void loadBorder(ArrayList<Point> points) {
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
    }

    private void loadRoads() {
        Hospital[] hos = map.getHospitals();
        Road[] ros = map.getRoads();
        roads = new Line2D[ros.length];
        for (int i = 0; i < ros.length; i++) {
            int x_0 = scale_x(hos[ros[i].getIdFirst() - 1].getWsp().x);
            int x_1 = scale_x(hos[ros[i].getIdSecond() - 1].getWsp().x);
            int y_0 = scale_y(hos[ros[i].getIdFirst() - 1].getWsp().y);
            int y_1 = scale_y(hos[ros[i].getIdSecond() - 1].getWsp().y);
            roads[i] = new Line2D.Double(x_0, y_0, x_1, y_1);
        }
    }

    private void loadMap() {
        ArrayList<Point> points = new ArrayList<>();
        findScale();
        loadHospitals(points);
        loadConstructs(points);
        loadBorder(points);
        loadRoads();
        animation = new Animator(this, hospitals, map);
        animation.start();
    }

    public void stopAnimation() {
        animation.interrupt();
    }

    private int scale_x(int x) {
        return (int) (scaleX * (x - minimum_x)) + SPACE + RADIUS;
    }

    private int scale_y(int y) {
        return (int) (scaleY * (y - minimum_y)) + SPACE + RADIUS;
    }

    public int unscale_x(int x) {
        return (int) ((x - RADIUS - SPACE) / scaleX) + minimum_x;
    }

    public int unscale_y(int y) {
        return (int) ((y - RADIUS - SPACE) / scaleY) + minimum_y;
    }

    private void findScale() {
        Hospital[] hos = map.getHospitals();
        Construction[] con = map.getConstructs();
        Point[] points = new Point[hos.length + con.length];
        for (int i = 0; i < hos.length; i++) {
            points[i] = hos[i].getWsp();
        }
        for (int i = 0; i < con.length; i++) {
            points[i + hos.length] = con[i].getWsp();
        }
        int maxX = hos[0].getWsp().x;
        int maxY = hos[0].getWsp().y;
        int minX = maxX;
        int minY = maxY;
        for (Point p : points) {
            if (maxX < p.x) {
                maxX = p.x;
            } else if (minX > p.x) {
                minX = p.x;
            }
            if (maxY < p.y) {
                maxY = p.y;
            } else if (minY > p.y) {
                minY = p.y;
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
        g.setColor(Color.RED);
        g.drawString("" + minimum_x, SPACE, Y_SIZE - SPACE / 2);
        g.drawString("" + maximum_x, X_SIZE - SPACE / 2, Y_SIZE - SPACE / 2);
        g.drawString("" + minimum_y, SPACE / 2, SPACE / 2);
        g.drawString("" + maximum_y, SPACE / 2, Y_SIZE - SPACE);
    }

    private void drawHospitals(Graphics2D g) {
        for (int i = 0; i < map.getHospitals().length; i++) {
            Rectangle2D r = hospitals[i];
            g.setColor(Color.RED);
            g.fill(r);
            g.setColor(Color.BLACK);
            if (map.getHospitals()[i].getBedNumber() != 0) {
                g.drawString("" + map.getHospitals()[i].getId(), (int) r.getCenterX() - 2, (int) r.getCenterY() + 5);
            }
        }
    }

    private void drawObjects(Graphics2D g) {
        for (int i = 0; i < map.getConstructs().length; i++) {
            Ellipse2D e = constructs[i];
            g.setColor(Color.RED);
            g.fill(e);
            g.setColor(Color.BLACK);
            g.drawString("" + map.getConstructs()[i].getId(), (int) e.getCenterX() - 2, (int) e.getCenterY() + 5);
        }
    }

    private void drawRoads(Graphics2D g) {
        for (Line2D l : roads) {
            g.setColor(Color.RED);
            g.draw(l);
        }
    }

    private void drawBorder(Graphics2D g) {
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, X_SIZE, Y_SIZE);
        g.setColor(Color.WHITE);
        g.fillPolygon(border);
    }

    private void drawPatient(Graphics2D g) {
        Patient p = animation.getPatient();
        g.setColor(Color.GREEN);
        g.fillOval(p.getWsp().x - PATIENT_RADIUS, p.getWsp().y - PATIENT_RADIUS, 2 * PATIENT_RADIUS, 2 * PATIENT_RADIUS);
    }

    private void drawInfo(Graphics2D g) {
        g.setColor(Color.WHITE);
        int x = label.x;
        int y = label.y;
        if (X_SIZE - x < 220) {
            x -= 220;
        }
        if (y > 30) {
            y -= 30;
        }
        g.fillRect(x, y, 220, 30);
        Hospital hos = map.getHospitals()[hovered_id];
        g.setColor(Color.BLACK);
        int free = hos.getFreeBedCount();
        int queue = 0;
        if (free < 0) {
            queue = -free;
            free = 0;
        }
        g.drawString(queue + "/" + free + "/" + hos.getBedNumber(), x, y + 13);
        g.drawString("Kolejka/Wolne łóżka/Wszystkie łóżka", x, y + 27);
    }

    private boolean patientLoaded() {
        return animation.getPatient() != null;
    }

    @Override
    protected void paintComponent(Graphics gr) {
        super.paintComponent(gr);
        Graphics2D g = (Graphics2D) gr;
        Font font = new Font("Serif", Font.PLAIN, 15);
        g.setFont(font);
        drawBorder(g);
        drawAxis(g);
        if (draw_hospitals) {
            drawHospitals(g);
        }
        if (draw_objects) {
            drawObjects(g);
        }
        if (draw_roads) {
            drawRoads(g);
        }
        if (patientLoaded()) {
            drawPatient(g);
        }
        if (hovered_id != -1) {
            drawInfo(g);
        }
    }

}
