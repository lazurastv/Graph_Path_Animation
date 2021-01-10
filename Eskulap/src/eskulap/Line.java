package eskulap;

import java.awt.Point;
import java.util.ArrayList;

public class Line {

    private final double a, b;
    public final Point s, e;

    public Line(Point start, Point end) {
        s = start;
        e = end;
        a = 1.0 * (end.y - start.y) / (end.x - start.x);
        b = start.y - a * start.x;
    }
    
    public Point crossLines(Line with) {
        double left = a - with.a;
        double right = with.b - b;
        double x = right / left;
        double y = s.y + a * (x - s.x);
        return new Point((int)x, (int)y);
    }
    
    public boolean contains(Point p) {
        if (p.x == s.x && p.y == s.y || p.x == e.x && p.y == e.y) {
            return false;
        }
        int x_left = s.x;
        int x_right = e.x;
        if (x_left > x_right) {
            x_left = x_right;
            x_right = s.x;
        }
        int y_up = s.y;
        int y_down = e.y;
        if (y_up > y_down) {
            y_up = y_down;
            y_down = s.y;
        }
        return (p.x >= x_left && p.x <= x_right && p.y >= y_up && p.y <= y_down);
    }
    
    public double length() {
        return Math.sqrt(Math.pow(e.x - s.x, 2) + Math.pow(e.y - s.y, 2));
    }

    public double distance(boolean start, Point p) {
        if (start) {
            return Math.sqrt(Math.pow(p.x - s.x, 2) + Math.pow(p.y - s.y, 2));
        } else {
            return Math.sqrt(Math.pow(p.x - e.x, 2) + Math.pow(p.y - e.y, 2));
        }
    }
    
    public static ArrayList<Line> makeLines(Hospital[] hos, Road[] ros) {
        ArrayList<Line> lines = new ArrayList<>();
        Point[] start = new Point[ros.length];
        Point[] end = new Point[ros.length];
        for (Hospital h : hos) {
            for (int i = 0; i < ros.length; i++) {
                if (h.id == ros[i].idHospitalFirst) {
                    start[i] = h.wsp;
                } else if (h.id == ros[i].idHospitalSecond) {
                    end[i] = h.wsp;
                }
            }
        }
        for (int i = 0; i < ros.length; i++) {
            lines.add(new Line(start[i], end[i]));
        }
        return lines;
    }
    
    @Override
    public String toString() {
        return s.x + " " + s.y + " -> " + e.x + " " + e.y;
    }
}
