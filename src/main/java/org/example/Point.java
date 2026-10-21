package org.example;

public record Point(double x, double y) implements Figure {

    @Override
    public double perimeter() {
        return 0;
    }

    @Override
    public double area() {
        return 0;
    }

    @Override
    public BoundingBox boundingBox() {
        return new BoundingBox(x, y, x, y);
    }

    @Override
    public Figure translate(double dx, double dy) {
        return new Point(x + dx, y + dy);
    }

    @Override
    public Figure rotate(Point center, double angleRad) {
        double cos = Math.cos(angleRad);
        double sin = Math.sin(angleRad);
        double dx = x - center.x();
        double dy = y - center.y();
        return new Point(
                center.x() + dx * cos - dy * sin,
                center.y() + dx * sin + dy * cos
        );
    }

    @Override
    public Figure scale(Point center, double factor) {
        return new Point(
                center.x() + (x - center.x()) * factor,
                center.y() + (y - center.y()) * factor);
    }

    @Override
    public boolean contains(Point p) {
        if ((Math.abs(p.x() - x) < 1e-6) && (Math.abs(p.y() - y) < 1e-6)) {
            return true;
        }
        return false;
    }
}
