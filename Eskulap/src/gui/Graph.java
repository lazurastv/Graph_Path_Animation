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
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import storage.Construction;
import storage.Hospital;
import storage.Map;
import storage.Patient;
import storage.Road;

public class Graph extends JPanel {

    private Map map;
    private Rectangle2D[] hospitals;
    private Point label;
    private int hovered_id;
    private Ellipse2D[] constructs;
    private Line2D[] roads;
    private Point[] border_points;
    private Polygon border;
    private Patient patient;
    private double scaleX;
    private double scaleY;
    private int minimum_x;
    private int minimum_y;
    private int maximum_x;
    private int maximum_y;
    private boolean draw_hospitals, draw_objects, draw_roads;
    private Animator animation;
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
            int id = 1;

            @Override
            public void mouseClicked(MouseEvent e) {
                if (map != null && e.isControlDown()) {
                    addPatient(new Patient(id++, unscale_x(e.getPoint().x), unscale_y(e.getPoint().y)));
                }
            }
        });
        addMouseMotionListener(new MouseAdapter() {

            @Override
            public void mouseMoved(MouseEvent e) {
                if (map != null) {
                    for (int i = 0; i < hospitals.length; i++) {
                        if (Math.abs(e.getX() - hospitals[i].getCenterX()) <= RADIUS
                                && Math.abs(e.getY() - hospitals[i].getCenterY()) <= RADIUS
                                && map.getHospitals()[i].getBedNumber() != 0) {
                            hovered_id = i;
                            label = e.getPoint();
                            repaint();
                            break;
                        } else if (i == hospitals.length - 1) {
                            hovered_id = -1;
                        }
                    }
                }
            }

        });
        setPreferredSize(new Dimension(X_SIZE, Y_SIZE));
        setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
    }

    public void setPatient(Patient p) {
        patient = p;
    }

    public void setSpeed(int s) {
        animation.setPercent(s);
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

    public CenterPane getCenter() {
        return center_pane;
    }

    public Patient getPatient() {
        return patient;
    }

    public void addPatient(Patient p) {
        patient = p;
        if (map != null) {
            if (new RayCasting().isInside(border_points, border_points.length, patient.getWsp())) {
                patient.move(scale_x(p.getWsp().x), scale_y(p.getWsp().y));
                animation.addPatient(patient);
            } else {
                center_pane.print("Pacjent " + p.getId() + " jest poza granicami państwa.\n");
            }
        } else {
            JOptionPane.showMessageDialog(new JFrame(), "Najpierw wczytaj państwo!");
        }
    }

    public Map getMap() {
        return map;
    }

    public void loadMap() {
        map = center_pane.getBoard().getWest().getMap();
        Hospital[] hos = map.getHospitals();
        Construction[] con = map.getConstructs();
        Road[] ros = map.getRoads();
        ArrayList<Point> points = new ArrayList<>();
        findScale(hos, con);
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
        if (animation != null) {
            animation.interrupt();
        }
        animation = new Animator(this, hospitals, map);
        animation.start();
        repaint();
    }

    private int scale_x(int x) {
        return (int) (scaleX * (x - minimum_x)) + SPACE + RADIUS;
    }

    public int unscale_x(int x) {
        return (int) ((x - RADIUS - SPACE) / scaleX) + minimum_x;
    }

    private int scale_y(int y) {
        return (int) (scaleY * (y - minimum_y)) + SPACE + RADIUS;
    }

    public int unscale_y(int y) {
        return (int) ((y - RADIUS - SPACE) / scaleY) + minimum_y;
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
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(0, 0, X_SIZE, Y_SIZE);
            g.setColor(Color.WHITE);
            g.fillPolygon(border);
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
            if (patient != null) {
                g.setColor(Color.GREEN);
                g.fillOval(patient.getWsp().x - PATIENT_RADIUS, patient.getWsp().y - PATIENT_RADIUS, 2 * PATIENT_RADIUS, 2 * PATIENT_RADIUS);
            }
            if (hovered_id != -1) {
                g.setColor(Color.WHITE);
                g.fillRect((int)label.getX(), (int)label.getY() - 30, 250, 30);
                Hospital hos = map.getHospitals()[hovered_id];
                g.setColor(Color.BLACK);
                int queue = -hos.getFreeBedCount();
                if (queue < 0) {
                    queue = 0;
                }
                g.drawString(queue + "/" + hos.getFreeBedCount() + "/" + hos.getBedNumber(), (int)label.getX(), (int)label.getY() - 18);
                g.drawString("Kolejka/Wolne łóżka/Wszystkie łóżka", (int)label.getX(), (int)label.getY() - 4);
            }
        } else {
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, X_SIZE, Y_SIZE);
            g.setColor(Color.BLACK);
            g.drawString("Wczytaj państwo.", X_SIZE / 2, Y_SIZE / 2);
        }
    }

}
