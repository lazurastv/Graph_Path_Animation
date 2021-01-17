package eskulap;

import java.awt.Point;
import java.awt.geom.Point2D;

class PointOnEdgeException extends Exception {
}

public class RayCasting {

    private static boolean isIntersecting(Point2D A, Point2D B, Point2D p) throws PointOnEdgeException {
        if (A.getY() > B.getY()) {
            return isIntersecting(B, A, p);
        }
        double minX = Math.min(A.getX(), B.getX());
        double maxX = Math.max(A.getX(), B.getX());
        
        if (p.getY() > B.getY() || p.getY() < A.getY() || p.getX() > maxX) {
            return false;
        }
        if (p.getX() < minX) {
            return true;
        }
        
        double aEdge;
        if ((B.getX() - A.getX()) != 0) {
            aEdge = (B.getY() - A.getY()) / (B.getX() - A.getX());
        } else if (p.getX() == A.getX()) {
            throw new PointOnEdgeException();
        } else {
            return p.getX() < minX;
        }
        
        double aPoint;
        if (p.getX() == A.getX()) {
            if (A.getY() == p.getY()) {
                throw new PointOnEdgeException();
            } else {
                return A.getX() < B.getX();
            }
        } else {
            aPoint = (p.getY() - A.getY()) / (p.getX() - A.getX());
        }
        
        if (aPoint == aEdge) {
            throw new PointOnEdgeException();
        }
        return aPoint > aEdge;
    }

    public boolean isInside(Point polygon[], int n, Point point) {
        Point2D p = (Point2D) point;
        boolean inside = false;
        int i = 0;
        int next = -1;
        while (next != 0) {
            next = (i + 1) % n;
            Point2D vertex1 = new Point2D.Double(polygon[i].x, polygon[i].y);
            Point2D vertex2 = new Point2D.Double(polygon[next].x, polygon[next].y);
            try {
                if (vertex1.getY() == p.getY() || vertex2.getY() == p.getY()) {
                    if (isIntersecting(vertex1, vertex2, new Point2D.Double(p.getX(), p.getY() + 0.001))) {
                        inside = !inside;
                        if (!isIntersecting(vertex1, vertex2, new Point2D.Double(p.getX(), p.getY() - 0.001))) {
                            throw new PointOnEdgeException();
                        }
                    } else {
                        i = next;
                        continue;
                    }
                } else if (isIntersecting(vertex1, vertex2, p)) {
                    inside = !inside;
                }
            } catch (PointOnEdgeException ex) {
                return false;
            }
            i = next;
        }
        return inside;
    }
}
