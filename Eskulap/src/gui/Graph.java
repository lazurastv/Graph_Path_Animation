package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Ellipse2D;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import storage.Construction;
import storage.Hospital;
import storage.Map;
import storage.Road;

public class Graph extends JPanel {

    private Rectangle2D[] hospitals;
    private Ellipse2D[] constructs;
    private Line2D[] roads;
    private double scaleX;
    private double scaleY;
    private int x_offset;
    private int y_offset;
    private static final int X_SIZE = 1000;
    private static final int Y_SIZE = 500;
    private static final int RADIUS = 10;
    private static final int SPACE = 10;

    public Graph() {
        init();
    }

    private void init() {
        setPreferredSize(new Dimension(X_SIZE, Y_SIZE));
        setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
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
        return (int)(scaleX * (x - x_offset)) + SPACE + RADIUS;
    }
    
    private int scale_y(int y) {
        return (int)(scaleY * (y - y_offset)) + SPACE + RADIUS;
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
        x_offset = minX;
        y_offset = minY;
    }

    @Override
    protected void paintComponent(Graphics gr) {
        super.paintComponent(gr);
        Graphics2D g = (Graphics2D) gr;
        Font font = new Font("Serif", Font.PLAIN, 15);
        g.setFont(font);
        Integer hos_index = 1;
        Integer con_index = 1;

        if (hospitals != null) {
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
        } else {
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, X_SIZE, Y_SIZE);
            g.setColor(Color.BLACK);
            g.drawString("Wczytaj państwo.", X_SIZE / 2, Y_SIZE / 2);
        }
    }

}
