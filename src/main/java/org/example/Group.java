package org.example;

import java.util.ArrayList;
import java.util.List;

public record Group(List<Figure> children) implements Figure {

    public Group {
        if (children == null || children.isEmpty()) {
            throw new InvalidGeometryException("Group cannot be empty");
        }
        children = List.copyOf(children);
    }

    @Override
    public double perimeter() {
        double total = 0;
        for (Figure f : children) {
            total += f.perimeter();
        }
        return total;
    }

    @Override
    public double area() {
        double total = 0;
        for (Figure f : children) {
            total += f.area();
        }
        return total;
    }

    @Override
    public BoundingBox boundingBox() {
        BoundingBox result = children.get(0).boundingBox();
        for (int i = 1; i < children.size(); i++) {
            BoundingBox bb = children.get(i).boundingBox();
            result = new BoundingBox(
                    Math.min(result.minX(), bb.minX()),
                    Math.min(result.minY(), bb.minY()),
                    Math.max(result.maxX(), bb.maxX()),
                    Math.max(result.maxY(), bb.maxY())
            );
        }
        return result;
    }

    @Override
    public Figure translate(double dx, double dy) {
        List<Figure> newChildren = new ArrayList<>();
        for (Figure f : children) {
            newChildren.add(f.translate(dx, dy));
        }
        return new Group(newChildren);
    }

    @Override
    public Figure rotate(Point center, double angleRad) {
        List<Figure> newChildren = new ArrayList<>();
        for (Figure f : children) {
            newChildren.add(f.rotate(center, angleRad));
        }
        return new Group(newChildren);
    }

    @Override
    public Figure scale(Point center, double factor) {
        List<Figure> newChildren = new ArrayList<>();
        for (Figure f : children) {
            newChildren.add(f.scale(center, factor));
        }
        return new Group(newChildren);
    }

    @Override
    public boolean contains(Point p) {
        for (Figure f : children) {
            if (f.contains(p)) return true;
        }
        return false;
    }
}