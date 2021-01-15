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
        if (start.equals(with.start) || start.equals(with.end) || end.equals(with.start) || end.equals(with.end)) {
            return null;
        }
        double left = a - with.a;
        double right = with.b - b;
        double x = right / left;
        double y = start.y + a * (x - start.x);
        return new Point((int) x, (int) y);
    }

    public boolean contains(Point p) {
        if (p.equals(start) || p.equals(end)) {
            return false;
        } else {
            return (p.x >= getLeftX() && p.x <= getRightX() && p.y >= getUpY() && p.y <= getDownY());
        }
    }

    public double length() {
        return Math.sqrt(Math.pow(end.x - start.x, 2) + Math.pow(end.y - start.y, 2));
    }

    public double distFromStart(Point p) {
        return Math.sqrt(Math.pow(p.x - start.x, 2) + Math.pow(p.y - start.y, 2));
    }

    public double distFromEnd(boolean from_start, Point p) {
        return Math.sqrt(Math.pow(p.x - end.x, 2) + Math.pow(p.y - end.y, 2));
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
    
    public boolean startIsLeft() {
        return start.x <= end.x;
    }

    public Point getStart() {
        return start;
    }

    public Point getEnd() {
        return end;
    }

    public int getLeftX() {
        if (start.x < end.x) {
            return start.x;
        } else {
            return end.x;
        }
    }

    public int getRightX() {
        if (start.x > end.x) {
            return start.x;
        } else {
            return end.x;
        }
    }

    public int getUpY() {
        if (start.y < end.y) {
            return start.y;
        } else {
            return end.y;
        }
    }

    public int getDownY() {
        if (start.y > end.y) {
            return start.y;
        } else {
            return end.y;
        }
    }
    
    public Point getLeftPoint() {
        if (start.x < end.x) {
            return start;
        } else {
            return end;
        }
    }
    
    public Point getRightPoint() {
        if (start.x > end.x) {
            return start;
        } else {
            return end;
        }
    }

    @Override
    public String toString() {
        return start.x + " " + start.y + " -> " + end.x + " " + end.y;
    }
    
}
