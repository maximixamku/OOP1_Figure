package org.example;

import java.util.ArrayList;
import java.util.List;

public record Polygon(List<Point> vertices) implements Figure {

    public Polygon {
        if (vertices == null || vertices.size() < 3) {
            throw new InvalidGeometryException("Polygon needs at least 3 vertices");
        }
        vertices = List.copyOf(vertices);
    }

    @Override
    public double perimeter() {
        double perimeter = 0;
        for (int i = 0; i < vertices.size() - 1; i++) {
            Point curr = vertices.get(i);
            Point next = vertices.get(i + 1);
            perimeter += Math.hypot(curr.x() - next.x(), curr.y() - next.y());
        }
        perimeter += Math.hypot(vertices.getLast().x() - vertices.get(0).x(), vertices.getLast().y() - vertices.get(0).y());
        return perimeter;
    }

    @Override
    public double area() {
        if (vertices == null || vertices.size() < 3) {
            return 0;
        }

        double sum = 0;
        int n = vertices.size();

        for (int i = 0; i < n; i++) {
            Point current = vertices.get(i);
            Point next = vertices.get((i + 1) % n);

            sum += current.x() * next.y() - next.x() * current.y();
        }

        return Math.abs(sum) / 2.0;
    }

    @Override
    public BoundingBox boundingBox() {
        double minX = Double.POSITIVE_INFINITY, minY = Double.POSITIVE_INFINITY;
        double maxX = Double.NEGATIVE_INFINITY, maxY = Double.NEGATIVE_INFINITY;
        for (Point p : vertices) {
            minX = Math.min(minX, p.x());
            minY = Math.min(minY, p.y());
            maxX = Math.max(maxX, p.x());
            maxY = Math.max(maxY, p.y());
        }
        return new BoundingBox(minX, minY, maxX, maxY);
    }

    @Override
    public Figure translate(double dx, double dy) {
        List<Point> newVertices = new ArrayList<>();
        for (int i = 0; i < vertices.size(); i++) {
            newVertices.add((Point) vertices.get(i).translate(dx, dy));
        }
        return new Polygon(newVertices);
    }

    @Override
    public Figure rotate(Point center, double angleRad) {
        List<Point> newVertices = new ArrayList<>();
        for (int i = 0; i < vertices.size(); i++) {
            newVertices.add((Point) vertices.get(i).rotate(center, angleRad));
        }
        return new Polygon(newVertices);
    }

    @Override
    public Figure scale(Point center, double factor) {
        List<Point> newVertices = new ArrayList<>();
        for (int i = 0; i < vertices.size(); i++) {
            newVertices.add((Point) vertices.get(i).scale(center, factor));
        }
        return new Polygon(newVertices);
    }

    @Override
    public boolean contains(Point p) {
        if (vertices == null || vertices.size() < 3) {
            return false;
        }

        boolean result = false;
        int n = vertices.size();

        for (int i = 0; i < n; i++) {
            Point current = vertices.get(i);
            Point next = vertices.get((i + 1) % n);

            if ((current.y() > p.y()) != (next.y() > p.y())) {
                double intersectX = (next.x() - current.x()) * (p.y() - current.y())
                        / (next.y() - current.y()) + current.x();

                if (p.x() < intersectX) {
                    result = !result;
                }
            }
        }

        return result;
    }
}
