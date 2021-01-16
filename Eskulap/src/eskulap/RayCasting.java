package eskulap;

class Point {

    double x, y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return this.x + " " + this.y;
    }
}

public class RayCasting {

    static boolean isIntersecting(Point A, Point B, Point p) {
        if (A.y > B.y) {
            Point tmp = new Point(A.x, A.y);
            A.x = B.x;
            A.y = B.y;
            B.x = tmp.x;
            B.y = tmp.y;
        }
        if (p.y > B.y || p.y < A.y || p.x > Math.max(A.x, B.x)) {
            return false;
        }
        if (p.x < Math.min(A.x, B.x)) {
            return true;
        }
        double a_edge;
        double a_point;
        if ((B.x - A.x) != 0) {
            a_edge = (B.y - A.y) / (B.x - A.x);
        } else {
            a_edge = Double.MAX_VALUE;
        }
        if ((p.x - A.x) != 0) {
            a_point = (p.y - A.y) / (p.x - A.x);
        } else {
            a_point = Double.MAX_VALUE;
        }
        return a_point >= a_edge;
    }

    static boolean isInside(Point polygon[], int n, Point p) {
        boolean inside = false;
        if (n < 3) {
            return inside;
        }
        int i = 0;
        int next = -1;
        while (next != 0) {
            if (polygon[i].x == p.x && polygon[i].y == p.y) {
                return false;
            }
            next = (i + 1) % n;
            boolean onEdge = false;
            if (polygon[i].y == p.y || polygon[next].y == p.y) {
                p.y = p.y + 0.0001;
                onEdge = true;
            }

            if (isIntersecting(polygon[i], polygon[next], p)) {
                inside = !inside;
                if (onEdge) {
                    p.y = p.y - 0.0002;
                    if (!isIntersecting(polygon[i], polygon[next], p)) {
                        return false;
                    }
                }
            } else {
                i = next;
                continue;
            }
            i = next;
        }
        return inside;
    }
}