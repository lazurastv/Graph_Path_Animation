package eskulap;

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
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Board extends JFrame {

    Hospital[] hospitals;
    Road[] roads;
    private JPanel panel;
    private JPanel box;
    int width = 1200;
    int height = 600;

    public Board(Hospital[] hospitals, Road[] roads) {
        super("Eskulap");
        this.panel = new Graph(hospitals, roads);
		this.box = new JPanel();
        this.hospitals = hospitals;
        this.roads = roads;
        initComponents();
    }

    private void initComponents() {
        setSize(width, height);
        getContentPane().add(box);
        box.add(panel);
        box.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
}

class Graph extends JPanel {

    Rectangle2D[] hospitals;
    Line2D[] roads;
    double scaleX;
    double scaleY;

    public Graph(Hospital[] hospitals, Road[] roads) {
        setMaximumSize(new Dimension(1000, 500));
        setMinimumSize(new Dimension(1000, 500));
        setPreferredSize(new Dimension(1000, 500));
        this.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
        this.hospitals = new Rectangle2D[hospitals.length];
        for (int i = 0; i < hospitals.length; i++) {
            if (hospitals[i].bedNumber != 0) {
                this.hospitals[i] = new Rectangle2D.Double(hospitals[i].wsp.x - 5, hospitals[i].wsp.y - 5, 10, 10);
            } else {
                this.hospitals[i] = new Rectangle2D.Double(hospitals[i].wsp.x - 2, hospitals[i].wsp.y - 5, 4, 10);
            }
        }
        this.roads = new Line2D[roads.length];
        for (int i = 0; i < roads.length; i++) {
            this.roads[i] = new Line2D.Double(hospitals[roads[i].idHospitalFirst - 1].wsp.x, hospitals[roads[i].idHospitalFirst - 1].wsp.y,
                    hospitals[roads[i].idHospitalSecond - 1].wsp.x, hospitals[roads[i].idHospitalSecond - 1].wsp.y);
        }
        scaleX = 990 / findMax(hospitals)[0];
        scaleY = 490 / findMax(hospitals)[1];
    }

    private int[] findMax(Hospital[] hospitals) {
        int maxX = hospitals[0].wsp.x;
        int maxY = hospitals[0].wsp.y;
        for (Hospital h : hospitals) {
            if (maxX < h.wsp.x) {
                maxX = h.wsp.x;
            }
            if (maxY < h.wsp.y) {
                maxY = h.wsp.y;
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
