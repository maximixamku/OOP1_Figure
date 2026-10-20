package org.example;

public record Circle(Point center, double radius) implements Figure {
    @Override
    public double perimeter() {
        return 0;
    }

    @Override
    public double area() {
        return 0;
    }

    @Override
    public Figure translate(double dx, double dy) {
        return null;
    }

    @Override
    public Figure rotate(Point center, double angleRad) {
        return null;
    }

    @Override
    public Figure scale(Point center, double factor) {
        return null;
    }

    @Override
    public boolean contains(Point p) {
        return false;
    }
}
