package eskulap;

import java.util.ArrayList;
import java.awt.Point;

public class JarvisMarch {

    public boolean isClockwise(Point a, Point b, Point c) {
        int crossProductAngleSign = (b.y - a.y) * (c.x - b.x) - (b.x - a.x) * (c.y - b.y);
        if (crossProductAngleSign < 0) {
            return true;
        }
        return false;
    }

    public ArrayList<Point> calculateBorder(Point points[], int n) {
        if (n < 3) {
            System.out.println("Zbyt mala ilosc obiektow, aby stworzyc kraj");
            return null;
        }
        int minxId = 0;
        for (int i = 1; i < n; i++) {
            if (points[i].x < points[minxId].x) {
                minxId = i;
            }
        }
        ArrayList<Point> hull = new ArrayList<>();
        int actual = minxId;
        int next = minxId + 1;
        while (next != minxId) {
            hull.add(points[actual]);
            next = (actual + 1) % n;
            for (int i = 0; i < n; i++) {
                if (isClockwise(points[actual], points[i], points[next])) {
                    next = i;
                }
            }
            actual = next;
        }
        return hull;
    }
}
