package eskulap;

import java.awt.Point;
import org.junit.Before;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class JarvisMarchTest {

    JarvisMarch jMarch;

    @Before
    public void setUp() {
        jMarch = new JarvisMarch();
    }

    @Test
    public void isodFile() {
        Point[] points = {new Point(10, 10),
            new Point(100, 120),
            new Point(120, 130),
            new Point(10, 140),
            new Point(140, 10),
            new Point(-1, 50),
            new Point(110, 55),
            new Point(40, 70)};

        Point[] polygonExpected = {new Point(-1, 50),
            new Point(10, 10),
            new Point(140, 10),
            new Point(120, 130),
            new Point(10, 140)};

        Point[] polygon = jMarch.calculateBorder(points, points.length).toArray(new Point[0]);

        assertArrayEquals(polygon, polygonExpected);
    }

    @Test
    public void wrapSquare() {
        Point[] points = {new Point(0, 0),
            new Point(10, 0),
            new Point(10, 10),
            new Point(0, 10),
            new Point(5, 5),
            new Point(5, 6),
            new Point(5, 7),
            new Point(4, 2),
            new Point(1, 6),
            new Point(2, 8)};
        Point[] polygonExpected = {new Point(0, 0),
            new Point(10, 0),
            new Point(10, 10),
            new Point(0, 10)};

        Point[] polygon = jMarch.calculateBorder(points, points.length).toArray(new Point[0]);

        assertArrayEquals(polygon, polygonExpected);
    }

    @Test
    public void wrapTriangleWithPointsOnEdges() {
        Point[] points = {new Point(-10, 0),
            new Point(-5, 5),
            new Point(0, 10),
            new Point(5, 0),
            new Point(0, 0)};
        Point[] polygonExpected = {new Point(-10, 0),
            new Point(5, 0),
            new Point(0, 10)};
        
        Point[] polygon = jMarch.calculateBorder(points, points.length).toArray(new Point[0]);

        assertArrayEquals(polygon, polygonExpected);
    }
}
