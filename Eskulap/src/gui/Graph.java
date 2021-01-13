package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import storage.Hospital;
import storage.Road;

public class Graph extends JPanel {
    
    private Rectangle2D[] hospitals;
    private Line2D[] roads;
    private double scaleX;
    private double scaleY;
    private static final int SIZE_X = 1000;
    private static final int SIZE_Y = 500;

    public Graph(Hospital[] hospitals, Road[] roads) {
        //setMaximumSize(new Dimension(SIZE_X, SIZE_Y));
        //setMinimumSize(new Dimension(SIZE_X, SIZE_Y));
        setPreferredSize(new Dimension(SIZE_X, SIZE_Y));
        setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
        this.hospitals = new Rectangle2D[hospitals.length];
        for (int i = 0; i < hospitals.length; i++) {
            if (hospitals[i].getBedNumber() != 0) {
                this.hospitals[i] = new Rectangle2D.Double(hospitals[i].getWsp().x - 5, hospitals[i].getWsp().y - 5, 10, 10);
            } else {
                this.hospitals[i] = new Rectangle2D.Double(hospitals[i].getWsp().x - 2, hospitals[i].getWsp().y - 5, 4, 10);
            }
        }
        this.roads = new Line2D[roads.length];
        for (int i = 0; i < roads.length; i++) {
            this.roads[i] = new Line2D.Double(hospitals[roads[i].getIdFirst() - 1].getWsp().x, hospitals[roads[i].getIdFirst() - 1].getWsp().y,
                    hospitals[roads[i].getIdSecond() - 1].getWsp().x, hospitals[roads[i].getIdSecond() - 1].getWsp().y);
        }
        scaleX = (SIZE_X - 10) / findMax(hospitals)[0];
        scaleY = (SIZE_Y - 10) / findMax(hospitals)[1];
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
        g.setColor(Color.RED);

        Font font = new Font("Serif", Font.PLAIN, 15);
        g.setFont(font);
        Integer index = 1;

        for (Rectangle2D r : hospitals) {
            drawScaledObject(g, r, scaleX, scaleY);
            g.setColor(Color.BLACK);
            g.drawString(index.toString(), (float) (r.getCenterX() * scaleX), (float) (r.getCenterY() * scaleY));
            g.setColor(Color.RED);
            index++;
        }
        for (Line2D l : roads) {
            drawScaledObject(g, l, scaleX, scaleY);
        }
    }

    private static void drawScaledObject(Graphics2D g, Shape shape, double scaleX, double scaleY) {
        AffineTransform at = AffineTransform.getScaleInstance(scaleX, scaleY);
        g.draw(at.createTransformedShape(shape));
        g.fill(at.createTransformedShape(shape));
    }
    
}
