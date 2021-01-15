package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Ellipse2D;
import java.util.Timer;
import java.util.TimerTask;
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
    private Patient patient;
    private int[] path;
    private double scaleX;
    private double scaleY;
    private int minimum_x;
    private int minimum_y;
    private int maximum_x;
    private int maximum_y;
    private Timer animation;
    private static final int X_SIZE = 1000;
    private static final int Y_SIZE = 500;
    private static final int RADIUS = 10;
    private static final int PATIENT_RADIUS = 5;
    private static final int SPACE = 50;

    public Graph() {
        init();
    }

    private void init() {
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
            System.out.println(dx + " " + dy);
            if (Double.isInfinite(dy)) {
                dx = 0;
                dy = getDy();
            } else if (dy > -1 && dy != 0 && dy < 1) {
                dx /= Math.abs(dy);
                dy = getDy();
            }
            System.out.println(dx + " " + dy);
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

    public void loadPatient(Patient p, int[] pth) {
        patient = p;
        p.move(scale_x(p.getWsp().x), scale_y(p.getWsp().y));
        path = pth;
        animate();
    }

    public void loadMap(Map map) {
        Hospital[] hos = map.getHospitals();
        Construction[] con = map.getConstructs();
        Road[] ros = map.getRoads();
        findScale(hos, con);
        hospitals = new Rectangle2D[hos.length];
        for (int i = 0; i < hospitals.length; i++) {
            int x = scale_x(hos[i].getWsp().x);
            int y = scale_y(hos[i].getWsp().y);
            if (hos[i].getBedNumber() != 0) {
                hospitals[i] = new Rectangle2D.Double(x - RADIUS, y - RADIUS, 2 * RADIUS, 2 * RADIUS);
            } else {
                hospitals[i] = new Rectangle2D.Double(x - 2, y - 5, 4, 10);
            }
        }
        constructs = new Ellipse2D[con.length];
        for (int i = 0; i < constructs.length; i++) {
            int x = scale_x(con[i].getWsp().x);
            int y = scale_y(con[i].getWsp().y);
            constructs[i] = new Ellipse2D.Double(x - RADIUS, y - RADIUS, RADIUS * 2, RADIUS * 2);
        }
        roads = new Line2D[ros.length];
        for (int i = 0; i < ros.length; i++) {
            int x_0 = scale_x(hos[ros[i].getIdFirst() - 1].getWsp().x);
            int x_1 = scale_x(hos[ros[i].getIdSecond() - 1].getWsp().x);
            int y_0 = scale_y(hos[ros[i].getIdFirst() - 1].getWsp().y);
            int y_1 = scale_y(hos[ros[i].getIdSecond() - 1].getWsp().y);
            roads[i] = new Line2D.Double(x_0, y_0, x_1, y_1);
        }
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

    @Override
    protected void paintComponent(Graphics gr) {
        super.paintComponent(gr);
        Graphics2D g = (Graphics2D) gr;
        Font font = new Font("Serif", Font.PLAIN, 15);
        g.setFont(font);
        Integer hos_index = 1;
        Integer con_index = 1;

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, X_SIZE, Y_SIZE);
        if (hospitals != null) {
            drawAxis(g);
            for (Rectangle2D r : hospitals) {
                g.setColor(Color.RED);
                g.fill(r);
                g.setColor(Color.BLACK);
                g.drawString(hos_index.toString(), (float) r.getCenterX() - 2, (float) r.getCenterY() + 5);
                hos_index++;
            }
            for (Ellipse2D e : constructs) {
                g.setColor(Color.RED);
                g.fill(e);
                g.setColor(Color.BLACK);
                g.drawString(con_index.toString(), (float) e.getCenterX() - 2, (float) e.getCenterY() + 5);
                con_index++;
            }
            for (Line2D l : roads) {
                g.setColor(Color.RED);
                g.draw(l);
            }
            if (patient != null) {
                g.setColor(Color.GREEN);
                g.fillOval(patient.getWsp().x - PATIENT_RADIUS, patient.getWsp().y - PATIENT_RADIUS, 2 * PATIENT_RADIUS, 2 * PATIENT_RADIUS);
            }
        } else {
            g.setColor(Color.BLACK);
            g.drawString("Wczytaj państwo.", X_SIZE / 2, Y_SIZE / 2);
        }
    }

}
