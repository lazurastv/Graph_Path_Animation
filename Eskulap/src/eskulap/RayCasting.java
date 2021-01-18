package eskulap;

import java.awt.Point;

public class RayCasting {

    private final Point[] polygon;
    private final int n;
    private boolean onEdge;

    public RayCasting(Point polygon[]) {
        this.polygon = polygon;
        this.n = polygon.length;
        onEdge = false;
    }

    public boolean isInside(Point p) {
        int count = 0;
        int i = 0;
        int next = -1;
        boolean onVertexHeight;
        while (next != 0) {
            next = (i + 1) % n;
            onVertexHeight = (polygon[i].y == p.y || polygon[next].y == p.y);
            if (isIntersecting(polygon[i], polygon[next], p)) {
                count++;
            }
            if (onEdge) {
                return false;
            }
            if (onVertexHeight && count == 2) {
                int c = vertexHeightIntersections(new Point(-1_000_000, p.y));
                return c != 2;
            }
            i = next;
        }
        return count % 2 == 1;
    }

    private int vertexHeightIntersections(Point p) {
        int count = 0;
        int i = 0;
        int next = -1;
        while (next != 0) {
            next = (i + 1) % n;
            if (isIntersecting(polygon[i], polygon[next], p)) {
                count++;
            }
            i = next;
        }
        return count;
    }

    private boolean isIntersecting(Point A, Point B, Point p) {
        if (A.y > B.y) {
            return isIntersecting(B, A, p);
        }
        double minX = Math.min(A.x, B.x);
        double maxX = Math.max(A.x, B.x);
        if (p.y > B.y || p.y < A.y || p.x > maxX) {
            return false;
        }
        if (p.x < minX) {
            return true;
        }
        
        double aEdge;
        if ((B.x - A.x) != 0) {
            aEdge = (B.y - A.y) / (B.x - A.x);
        } else if (p.x == A.x) {
            onEdge = true;
            return false;
        } else {
            return p.x < minX;
        }
        
        double aPoint;
        if (p.x == A.x) {
            if (A.y == p.y) {
                onEdge = true;
                return false;
            } else {
                return A.x < B.x;
            }
        } else {
            aPoint = (p.y - A.y) / (p.x - A.x);
        }
        if (aPoint == aEdge) {
            onEdge = true;
            return false;
        }
        return aPoint > aEdge;
    }

}
