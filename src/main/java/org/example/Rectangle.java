package org.example;

public record Rectangle(Point A, double width, double height, double angle) implements Figure {
    @Override
    public double perimeter() {
        return (height + width) * 2;
    }

    @Override
    public double area() {
        return height * width;
    }

    @Override
    public Figure translate(double dx, double dy) {
        return new Rectangle(
                (Point) A.translate(dx, dy),
                width,
                height,
                angle);
    }

    @Override
    public Figure rotate(Point center, double angleRad) {
        return new Rectangle(
                (Point) A.rotate(center, angleRad),
                width,
                height,
                angle + angleRad);
    }

    @Override
    public Figure scale(Point center, double factor) {
        return new Rectangle(
                (Point) A.scale(center, factor),
                width * factor,
                height * factor,
                angle);
    }

    @Override
    public boolean contains(Point p) {
        double px = p.x() - A.x();
        double py = p.y() - A.y();
        double cos = Math.cos(-angle);
        double sin = Math.sin(-angle);
        double lx = px * cos - py * sin;
        double ly = px * sin + py * cos;
        return lx >= -1e-9 && lx <= width + 1e-9
                && ly >= -1e-9 && ly <= height + 1e-9;
    }
}
