package storage;

import java.awt.Point;
import java.util.ArrayList;

public class Line {

    private final double a, b;
    private final Point start, end;

    public Line(Point s, Point e) {
        start = s;
        end = e;
        a = 1.0 * (end.y - start.y) / (end.x - start.x);
        b = start.y - a * start.x;
    }
    
    public Point crossLines(Line with) {
        double left = a - with.a;
        double right = with.b - b;
        double x = right / left;
        double y = start.y + a * (x - start.x);
        return new Point((int)x, (int)y);
    }
    
    public boolean contains(Point p) {
        if (p.x == start.x && p.y == start.y || p.x == end.x && p.y == end.y) {
            return false;
        }
        int x_left = start.x;
        int x_right = end.x;
        if (x_left > x_right) {
            x_left = x_right;
            x_right = start.x;
        }
        int y_up = start.y;
        int y_down = end.y;
        if (y_up > y_down) {
            y_up = y_down;
            y_down = start.y;
        }
        return (p.x >= x_left && p.x <= x_right && p.y >= y_up && p.y <= y_down);
    }
    
    public double length() {
        return Math.sqrt(Math.pow(end.x - start.x, 2) + Math.pow(end.y - start.y, 2));
    }

    public double distance(boolean from_start, Point p) {
        if (from_start) {
            return Math.sqrt(Math.pow(p.x - start.x, 2) + Math.pow(p.y - start.y, 2));
        } else {
            return Math.sqrt(Math.pow(p.x - end.x, 2) + Math.pow(p.y - end.y, 2));
        }
    }
    
    public static ArrayList<Line> makeLines(Hospital[] hos, Road[] ros) {
        ArrayList<Line> lines = new ArrayList<>();
        Point[] start = new Point[ros.length];
        Point[] end = new Point[ros.length];
        for (Hospital h : hos) {
            for (int i = 0; i < ros.length; i++) {
                if (h.getId() == ros[i].getIdFirst()) {
                    start[i] = h.getWsp();
                } else if (h.getId() == ros[i].getIdSecond()) {
                    end[i] = h.getWsp();
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
        return start.x + " " + start.y + " -> " + end.x + " " + end.y;
    }
    
    public Point getStart() {
        return start;
    }
    
    public Point getEnd() {
        return end;
    }
}
