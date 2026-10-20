package main.java.org.example;

public enum ShapeKind {
    POINT("Точка"),
    SEGMENT("Отрезок"),
    CIRCLE("Окружность"),
    RECTANGLE("Прямоугольник"),
    POLYGON("Многоугольник"),
    GROUP("Группа фигур");

    private final String displayName;

    ShapeKind(String displayName) {
        this.displayName = displayName;
    }

    public String displayName() {
        return displayName;
    }
}
