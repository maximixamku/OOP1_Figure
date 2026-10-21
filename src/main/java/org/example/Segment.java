package org.example;

public record Segment(Point A, Point B) implements Figure {

    private double length() {
        double dx = A.x() - B.x();
        double dy = A.y() - B.y();
        return Math.hypot(dx, dy);
    }

    @Override
    public double perimeter() {
        return length();
    }

    @Override
    public double area() {
        return 0;
    }

    @Override
    public BoundingBox boundingBox() {
        return new BoundingBox(
                Math.min(A.x(), B.x()),
                Math.min(A.y(), B.y()),
                Math.max(A.x(), B.x()),
                Math.max(A.y(), B.y())
        );
    }

    @Override
    public Figure translate(double dx, double dy) {
        return new Segment(
                (Point) A.translate(dx, dy),
                (Point) B.translate(dx, dy));
    }

    @Override
    public Figure rotate(Point center, double angleRad) {
        return new Segment(
                (Point) A.rotate(center, angleRad),
                (Point) B.rotate(center, angleRad));
    }

    @Override
    public Figure scale(Point center, double factor) {
        return new Segment(
                (Point) A.scale(center, factor),
                (Point) B.scale(center, factor));
    }

    @Override
    public boolean contains(Point p) {
        double distanceAP = Math.hypot(p.x() - A.x(), p.y() - A.y());
        double distancePB = Math.hypot(B.x() - p.x(), B.y() - p.y());
        double distanceAB = Math.hypot(B.x() - A.x(), B.y() - A.y());

        return Math.abs((distanceAP + distancePB) - distanceAB) < 1e-9;

    }
}
