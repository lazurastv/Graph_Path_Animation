package eskulap;

import java.awt.Point;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RayCastingTest {

    private RayCasting rayCasting;

    @Test
    public void squarePointsOnEdgesAndVertices() {
        Point polygon[] = {new Point(0, 0),
            new Point(10, 0),
            new Point(10, 10),
            new Point(0, 10)};
        rayCasting = new RayCasting(polygon);
        Point[] vertex = {new Point(0, 0),
            new Point(10, 0),
            new Point(10, 10),
            new Point(0, 10)};
        Point[] onEdge = {new Point(0, 2),
            new Point(4, 10),
            new Point(5, 0),
            new Point(10, 4)};
        int expectedInsidePoints = 0;

        int insidePointsCount = 0;
        for (Point p : vertex) {
            if (rayCasting.isInside(p)) {
                insidePointsCount++;
            }
        }
        for (Point p : onEdge) {
            if (rayCasting.isInside(p)) {
                insidePointsCount++;
            }
        }
        assertEquals(insidePointsCount, expectedInsidePoints);
    }

    @Test
    public void rotatedSquarePointsOnEdges() {
        Point polygon[] = {new Point(-10, 0),
            new Point(0, 10),
            new Point(10, 0),
            new Point(0, -10)};
        rayCasting = new RayCasting(polygon);
        Point[] onEdge = {new Point(-5, 5),
            new Point(4, 6),
            new Point(-8, -2),
            new Point(9, -1)};
        int expectedInsidePoints = 0;

        int insidePointsCount = 0;
        for (Point p : onEdge) {
            if (rayCasting.isInside(p)) {
                insidePointsCount++;
            }
        }
        assertEquals(insidePointsCount, expectedInsidePoints);
    }

    @Test
    public void pointInsidePolygonOnSameHeightAsVertex() {
        Point polygon[] = {new Point(-10, 1),
            new Point(0, 10),
            new Point(10, 0),
            new Point(0, -10)};
        rayCasting = new RayCasting(polygon);

        Point p = new Point(0, 0);
        boolean isInside = rayCasting.isInside(p);

        assertTrue(isInside);
    }

    @Test
    public void pointInsidePolygonOnSameHeightAsVertices() {
        Point polygon[] = {new Point(-10, 0),
            new Point(0, 10),
            new Point(10, 0),
            new Point(0, -10)};
        rayCasting = new RayCasting(polygon);

        Point p = new Point(0, 0);
        boolean isInside = rayCasting.isInside(p);

        assertTrue(isInside);
    }

    @Test
    public void pointOutsidePolygonOnSameHeightAsVertices() {
        Point polygon[] = {new Point(-10, 0),
            new Point(0, 10),
            new Point(10, 0),
            new Point(0, -10)};
        rayCasting = new RayCasting(polygon);

        Point p = new Point(-1000, 0);
        boolean isInside = rayCasting.isInside(p);

        assertFalse(isInside);
    }

    @Test
    public void pointOutsidePolygonOnSameHeightAsVertex() {
        Point polygon[] = {new Point(-10, 1),
            new Point(0, 10),
            new Point(10, 0),
            new Point(0, -10)};
        rayCasting = new RayCasting(polygon);

        Point p = new Point(-1000, 0);

        boolean isInside = rayCasting.isInside(p);

        assertFalse(isInside);
    }

     @Test
    public void pointOutsidePolygonOnSameHeightMostUpVertex() {
        Point polygon[] = {new Point(-10, 1),
            new Point(0, 10),
            new Point(10, 0),
            new Point(0, -10)};
        rayCasting = new RayCasting(polygon);

        Point p = new Point(-1000, 10);

        boolean isInside = rayCasting.isInside(p);

        assertFalse(isInside);
    }
    
       @Test
    public void pointOutsidePolygonOnSameHeightMostDownVertex() {
        Point polygon[] = {new Point(-10, 1),
            new Point(0, 10),
            new Point(10, 0),
            new Point(0, -10)};
        rayCasting = new RayCasting(polygon);

        Point p = new Point(-1000, -10);

        boolean isInside = rayCasting.isInside(p);

        assertFalse(isInside);
    }
}
