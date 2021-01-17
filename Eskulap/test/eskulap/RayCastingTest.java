package eskulap;

import java.awt.Point;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RayCastingTest {

    private RayCasting rayCasting;

    @Test
    public void square1() {
        Point polygon[] = {new Point(0, 0),
            new Point(10, 0),
            new Point(10, 10),
            new Point(0, 10)};
        rayCasting = new RayCasting(polygon);
        Point[] vertex = {new Point(0, 0),
            new Point(10, 0),
            new Point(10, 10),
            new Point(0, 10)};
        Point[] edge = {new Point(0, 2),
            new Point(4, 10),
            new Point(5, 0),
            new Point(10, 4)};

        int insidePointsCount = 0;
        for (Point p : vertex) {
            if (rayCasting.isInside(p)) {
                insidePointsCount++;
            }
        }
        for (Point p : edge) {
            if (rayCasting.isInside(p)) {
                insidePointsCount++;
            }
        }
        int expectedInsidePoints = 0;
        assertEquals(insidePointsCount, expectedInsidePoints);
    }
}
