package org.example;

public record Circle(Point circleCenter, double radius) implements Figure {

    public Circle {
        if (radius <= 0) {
            throw new InvalidGeometryException("Radius must be positive, got " + radius);
        }
    }

    @Override
    public double perimeter() {
        return radius * 2 * Math.PI;
    }

    @Override
    public double area() {
        return radius * radius * Math.PI;
    }

    @Override
    public BoundingBox boundingBox() {
        return new BoundingBox(
                circleCenter.x() - radius,
                circleCenter.y() - radius,
                circleCenter.x() + radius,
                circleCenter.y() + radius);
    }

    @Override
    public Figure translate(double dx, double dy) {
        return new Circle(
                (Point) circleCenter.translate(dx, dy),
                radius);
    }

    @Override
    public Figure rotate(Point center, double angleRad) {
        return new Circle(
                (Point) circleCenter.rotate(center, angleRad),
                radius);
    }

    @Override
    public Figure scale(Point center, double factor) {
        return new Circle(circleCenter, radius * Math.abs(factor));
    }

    @Override
    public boolean contains(Point p) {
        double dx = p.x() - circleCenter.x();
        double dy = p.y() - circleCenter.y();
        return dx * dx + dy * dy <= radius * radius + 1e-9;
    }
}
