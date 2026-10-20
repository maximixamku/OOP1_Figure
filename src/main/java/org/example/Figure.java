package main.java.org.example;

public sealed interface Figure
        permits Point, Segment, Circle, Rectangle, Polygon, Group {

    double perimeter();
    double area();
    //BoundingBox boundingBox();
    Figure translate(double dx, double dy);
    Figure rotate(Point center, double angleRad);
    Figure scale(Point center, double factor);
    boolean contains(Point p);

    default ShapeKind kind() {
        return switch (this) {
            case Point p -> ShapeKind.POINT;
            case Segment s -> ShapeKind.SEGMENT;
            case Circle c -> ShapeKind.CIRCLE;
            case Rectangle r -> ShapeKind.RECTANGLE;
            case Polygon poly -> ShapeKind.POLYGON;
            case Group g -> ShapeKind.GROUP;
        };
    }
}