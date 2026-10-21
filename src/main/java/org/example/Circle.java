package org.example;

public record Circle(Point circleCenter, double radius) implements Figure {
    @Override
    public double perimeter() {
        return radius * 2 * Math.PI;
    }

    @Override
    public double area() {
        return radius * radius * Math.PI;
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
        return new Circle(circleCenter, radius * factor);
    }

    @Override
    public boolean contains(Point p) {
        double dx = p.x() - circleCenter.x();
        double dy = p.y() - circleCenter.y();
        return dx * dx + dy * dy <= radius * radius + 1e-9;
    }
}
