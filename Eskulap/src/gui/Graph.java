package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
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
    private static final int SIZE_X = 1000;
    private static final int SIZE_Y = 500;

    public Graph() {
        init();
    }

    private void init() {
        setPreferredSize(new Dimension(SIZE_X, SIZE_Y));
        setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
        setLayout(new GridLayout(1, 1));
    }

    public void loadMap(Map map) {
        Hospital[] hos = map.getHospitals();
        Construction[] con = map.getConstructs();
        Road[] ros = map.getRoads();
        hospitals = new Rectangle2D[hos.length];
        for (int i = 0; i < hospitals.length; i++) {
            if (hos[i].getBedNumber() != 0) {
                hospitals[i] = new Rectangle2D.Double(hos[i].getWsp().x - 5, hos[i].getWsp().y - 5, 10, 10);
            } else {
                hospitals[i] = new Rectangle2D.Double(hos[i].getWsp().x - 2, hos[i].getWsp().y - 5, 4, 10);
            }
        }
        constructs = new Ellipse2D[con.length];
        for (int i = 0; i < constructs.length; i++) {
            constructs[i] = new Ellipse2D.Double(con[i].getWsp().x - 4, con[i].getWsp().y - 4, 8, 8);
        }
        roads = new Line2D[ros.length];
        for (int i = 0; i < ros.length; i++) {
            roads[i] = new Line2D.Double(hos[ros[i].getIdFirst() - 1].getWsp().x, hos[ros[i].getIdFirst() - 1].getWsp().y,
                    hos[ros[i].getIdSecond() - 1].getWsp().x, hos[ros[i].getIdSecond() - 1].getWsp().y);
        }
        scaleX = (SIZE_X - 10) / findMax(hos)[0];
        scaleY = (SIZE_Y - 10) / findMax(hos)[1];
        repaint();
    }

    private int[] findMax(Hospital[] hospitals) {
        int maxX = hospitals[0].getWsp().x;
        int maxY = hospitals[0].getWsp().y;
        for (Hospital h : hospitals) {
            if (maxX < h.getWsp().x) {
                maxX = h.getWsp().x;
            }
            if (maxY < h.getWsp().y) {
                maxY = h.getWsp().y;
            }
        }
        int[] max = new int[2];
        max[0] = maxX;
        max[1] = maxY;
        return max;
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
                drawScaledObject(g, r, scaleX, scaleY);
                g.setColor(Color.BLACK);
                g.drawString(hos_index.toString(), (float) (r.getCenterX() * scaleX), (float) (r.getCenterY() * scaleY));
                hos_index++;
            }
            for (Ellipse2D e : constructs) {
                drawScaledObject(g, e, scaleX, scaleY);
                g.setColor(Color.BLACK);
                g.drawString(con_index.toString(), (float) (e.getCenterX() * scaleX), (float) (e.getCenterY() * scaleY));
                con_index++;
            }
            for (Line2D l : roads) {
                drawScaledObject(g, l, scaleX, scaleY);
            }
        } else {
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, SIZE_X, SIZE_Y);
            g.setColor(Color.BLACK);
            g.drawString("Wczytaj mapę.", SIZE_X / 2, SIZE_Y / 2);
        }
    }

    private static void drawScaledObject(Graphics2D g, Shape shape, double scaleX, double scaleY) {
        AffineTransform at = AffineTransform.getScaleInstance(scaleX, scaleY);
        g.setColor(Color.RED);
        g.draw(at.createTransformedShape(shape));
        g.fill(at.createTransformedShape(shape));
    }

}
