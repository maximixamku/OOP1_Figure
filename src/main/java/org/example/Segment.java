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
    public boolean contains(Point P) {
        double distanceAP = Math.hypot(P.x() - A.x(), P.y() - A.y());
        double distancePB = Math.hypot(B.x() - P.x(), B.y() - P.y());
        double distanceAB = Math.hypot(B.x() - A.x(), B.y() - A.y());

        return Math.abs((distanceAP + distancePB) - distanceAB) < 1e-9;

    }
}
