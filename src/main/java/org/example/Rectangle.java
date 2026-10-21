package org.example;

public record Rectangle(Point A, double width, double height, double angle) implements Figure {

    public Rectangle {
        if (A == null) {
            throw new InvalidGeometryException("A must not be null");
        }
        if (width <= 0 || height <= 0) {
            throw new InvalidGeometryException("width and height must be positive");
        }
    }

    @Override
    public double perimeter() {
        return (height + width) * 2;
    }

    @Override
    public double area() {
        return height * width;
    }

    @Override
    public BoundingBox boundingBox() {
        double[][] localCorners = {
                {0, 0}, {width, 0}, {width, height}, {0, height}
        };
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);
        double minX = Double.POSITIVE_INFINITY, minY = Double.POSITIVE_INFINITY;
        double maxX = Double.NEGATIVE_INFINITY, maxY = Double.NEGATIVE_INFINITY;
        for (double[] c : localCorners) {
            double wx = A.x() + c[0] * cos - c[1] * sin;
            double wy = A.y() + c[0] * sin + c[1] * cos;
            minX = Math.min(minX, wx);
            minY = Math.min(minY, wy);
            maxX = Math.max(maxX, wx);
            maxY = Math.max(maxY, wy);
        }
        return new BoundingBox(minX, minY, maxX, maxY);
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
        if (factor > 0) {
            return new Rectangle(
                    (Point) A.scale(center, factor),
                    width * factor,
                    height * factor,
                    angle);
        }
        return new Rectangle(
                (Point) A.scale(center, factor),
                width * -factor,
                height * -factor,
                angle + Math.PI);
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
