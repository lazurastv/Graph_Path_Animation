package storage;

import java.awt.Point;
import java.util.ArrayList;

public class Line implements Comparable<Line> {

    private final double ratio;
    private final double a, b;
    private final Point start, end;

    public Line(Point s, Point e, double distance) {
        start = s;
        end = e;
        a = 1.0 * (end.y - start.y) / (end.x - start.x);
        b = start.y - a * start.x;
        ratio = distance / length();
    }

    public Line(Point s, Point e, Line l) {
        start = s;
        end = e;
        a = 1.0 * (end.y - start.y) / (end.x - start.x);
        b = start.y - a * start.x;
        ratio = l.ratio;
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
            lines.add(new Line(start[i], end[i], ros[i].getDistance()));
        }
        return lines;
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
            return (p.x >= getLeftPoint().x && p.x <= getRightPoint().x && p.y >= getUpY() && p.y <= getDownY());
        }
    }

    public Road toRoad(Hospital[] hos) {
        int s_i = -1;
        int e_i = -1;
        for (Hospital h : hos) {
            if (h.getWsp().equals(start)) {
                s_i = h.getId();
            } else if (h.getWsp().equals(end)) {
                e_i = h.getId();
            }
        }
        return new Road(0, s_i, e_i, getDistance());
    }

    private double length() {
        return Math.sqrt(Math.pow(end.x - start.x, 2) + Math.pow(end.y - start.y, 2));
    }

    private double getDistance() {
        return length() * ratio;
    }

    private int getUpY() {
        if (start.y < end.y) {
            return start.y;
        } else {
            return end.y;
        }
    }

    private int getDownY() {
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

    @Override
    public int compareTo(Line o) {
        return getLeftPoint().x - o.getLeftPoint().x;
    }

}
