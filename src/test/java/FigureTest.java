import org.example.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class FigureTest {

    private static final double EPS = 1e-9;

    @Test
    void pointHasZeroAreaAndPerimeter() {
        Point p = new Point(3, 4);
        assertEquals(0, p.area(), EPS);
        assertEquals(0, p.perimeter(), EPS);
        assertEquals(new BoundingBox(3, 4, 3, 4), p.boundingBox());
    }

    @Test
    void pointContains() {
        Point p = new Point(3, 4);
        assertTrue(p.contains(new Point(3, 4)));
        assertTrue(p.contains(new Point(3.0000001, 3.9999999)));
        assertFalse(p.contains(new Point(3.1, 4.1)));
    }

    @Test
    void pointEqualsAndHashCode() {
        Point p1 = new Point(3, 4);
        Point p2 = new Point(3, 4);
        Point p3 = new Point(4, 3);

        assertEquals(p1, p2);
        assertEquals(p1.hashCode(), p2.hashCode());
        assertNotEquals(p1, p3);
    }

    @Test
    void segmentLengthAndArea() {
        Segment s = new Segment(new Point(0, 0), new Point(3, 4));
        assertEquals(5, s.perimeter(), EPS); // 3-4-5 треугольник
        assertEquals(0, s.area(), EPS);
    }

    @Test
    void segmentContains() {
        Segment s = new Segment(new Point(0, 0), new Point(4, 0));
        assertTrue(s.contains(new Point(2, 0)));  // внутри
        assertTrue(s.contains(new Point(0, 0)));  // на конце
        assertTrue(s.contains(new Point(4, 0)));  // на другом конце
        assertFalse(s.contains(new Point(5, 0))); // снаружи (продолжение)
        assertFalse(s.contains(new Point(2, 1))); // мимо
    }

    @Test
    void segmentWithEqualEndpointsThrows() {
        assertThrows(InvalidGeometryException.class,
                () -> new Segment(new Point(1, 1), new Point(1, 1)));
    }

    @Test
    void circleAreaAndPerimeter() {
        Circle c = new Circle(new Point(0, 0), 2);
        assertEquals(Math.PI * 4, c.area(), EPS);
        assertEquals(2 * Math.PI * 2, c.perimeter(), EPS);
    }

    @Test
    void circleContains() {
        Circle c = new Circle(new Point(10, 10), 5);
        assertTrue(c.contains(new Point(10, 10)));  // центр
        assertTrue(c.contains(new Point(10, 15)));  // ровно на границе
        assertFalse(c.contains(new Point(10, 16))); // снаружи
    }

    @Test
    void circleZeroOrNegativeRadiusThrows() {
        assertThrows(InvalidGeometryException.class,
                () -> new Circle(new Point(0, 0), 0));
        assertThrows(InvalidGeometryException.class,
                () -> new Circle(new Point(0, 0), -1));
    }

    @Test
    void rectangleAreaAndPerimeter() {
        Rectangle r = new Rectangle(new Point(0, 0), 4, 3, 0);
        assertEquals(12, r.area(), EPS);
        assertEquals(14, r.perimeter(), EPS);
    }

    @Test
    void rectangleContainsWithRotation() {
        Rectangle r = new Rectangle(new Point(0, 0), 2, 4, Math.PI / 2);
        assertTrue(r.contains(new Point(-2, 1)));
        assertFalse(r.contains(new Point(1, 1)));
    }

    @Test
    void rectangleInvalidParamsThrows() {
        assertThrows(InvalidGeometryException.class,
                () -> new Rectangle(new Point(0, 0), 0, 3, 0));
        assertThrows(InvalidGeometryException.class,
                () -> new Rectangle(null, 2, 3, 0));
    }

    @Test
    void polygonSquareAreaAndPerimeter() {
        Polygon p = new Polygon(List.of(
                new Point(0, 0), new Point(2, 0), new Point(2, 2), new Point(0, 2)));
        assertEquals(4, p.area(), EPS);
        assertEquals(8, p.perimeter(), EPS);
    }

    @Test
    void polygonContains() {
        Polygon p = new Polygon(List.of(
                new Point(0, 0), new Point(4, 0), new Point(4, 4), new Point(0, 4)));
        assertTrue(p.contains(new Point(2, 2)));  // внутри
        assertFalse(p.contains(new Point(5, 5))); // снаружи
    }

    @Test
    void polygonTooFewVerticesThrows() {
        assertThrows(InvalidGeometryException.class,
                () -> new Polygon(List.of(new Point(0, 0), new Point(1, 1))));
    }

    @Test
    void groupAreaPerimeterAndBoundingBox() {
        Group g = new Group(List.of(
                new Rectangle(new Point(0, 0), 2, 2, 0), // в (0,0)..(2,2)
                new Circle(new Point(5, 5), 1)           // в (4,4)..(6,6)
        ));

        assertEquals(4 + Math.PI, g.area(), EPS);
        assertEquals(8 + 2 * Math.PI, g.perimeter(), EPS);

        BoundingBox bb = g.boundingBox();
        assertEquals(0, bb.minX(), EPS);
        assertEquals(0, bb.minY(), EPS);
        assertEquals(6, bb.maxX(), EPS);
        assertEquals(6, bb.maxY(), EPS);
    }

    @Test
    void emptyGroupThrows() {
        assertThrows(InvalidGeometryException.class, () -> new Group(List.of()));
    }

    @Test
    void polygonDoesNotLeakInternalList() {
        List<Point> mutableList = new ArrayList<>();
        mutableList.add(new Point(0, 0));
        mutableList.add(new Point(10, 0));
        mutableList.add(new Point(10, 10));

        Polygon p = new Polygon(mutableList);

        mutableList.clear();

        assertEquals(3, p.vertices().size());
        assertEquals(50, p.area(), EPS);
    }

    @Test
    void transformationsWorkCorrectly() {
        Rectangle r = new Rectangle(new Point(1, 1), 2, 2, 0);

        Rectangle shifted = (Rectangle) r.translate(10, 10);
        assertEquals(new Point(11, 11), shifted.A());

        Rectangle scaled = (Rectangle) r.scale(new Point(0, 0), 2);
        assertEquals(new Point(2, 2), scaled.A());
        assertEquals(4, scaled.width(), EPS);
        assertEquals(4, scaled.height(), EPS);
    }
}