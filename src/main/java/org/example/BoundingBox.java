package org.example;

public record BoundingBox(double minX, double minY, double maxX, double maxY) {

    public double width()  { return maxX - minX; }

    public double height() { return maxY - minY; }

    public Point center() {
        return new Point((minX + maxX) / 2.0, (minY + maxY) / 2.0);
    }
}
