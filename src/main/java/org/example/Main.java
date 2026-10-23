package org.example;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        System.out.println("==========================================================");
        System.out.println("  ДЕМОНСТРАЦИЯ: Модель «Фигуры на плоскости»");
        System.out.println("==========================================================\n");

        List<Figure> scene = buildScene();
        System.out.println("Создано фигур: " + scene.size() + " (требуется >= 30)\n");

        // --- 1. Обзор всех фигур ---
        printOverview(scene);

        // --- 2. Группировка (рекурсивная) ---
        demoGroup(scene);

        // --- 3. Трансформации (сдвиг, поворот, масштаб) ---
        demoTransformations();

        // --- 4. Принадлежность точки ---
        demoContains();

        // --- 5. Обработка собственного исключения ---
        demoExceptionHandling();

        System.out.println("\n==========================================================");
        System.out.println("  ДЕМОНСТРАЦИЯ ЗАВЕРШЕНА");
        System.out.println("==========================================================");
    }

    /**
     * Создаёт сцену из 31 объекта всех типов.
     */
    private static List<Figure> buildScene() {
        List<Figure> list = new ArrayList<>();

        // 10 точек
        for (int i = 0; i < 10; i++) {
            list.add(new Point(i, i * 2));
        }

        // 5 отрезков
        for (int i = 0; i < 5; i++) {
            list.add(new Segment(new Point(i, 0), new Point(i + 3, 4)));
        }

        // 5 окружностей
        for (int i = 0; i < 5; i++) {
            list.add(new Circle(new Point(i * 2, i), 1.0 + i * 0.5));
        }

        // 5 прямоугольников (с разными углами поворота)
        for (int i = 0; i < 5; i++) {
            list.add(new Rectangle(new Point(i, i), 3, 2, Math.toRadians(45 * i)));
        }

        // 5 многоугольников (треугольники)
        for (int i = 0; i < 5; i++) {
            list.add(new Polygon(List.of(
                    new Point(i, i),
                    new Point(i + 2, i),
                    new Point(i + 1, i + 2)
            )));
        }

        // 1 группа (содержит 3 фигуры) — 31-й объект
        list.add(new Group(List.of(
                new Circle(new Point(10, 10), 2),
                new Rectangle(new Point(0, 0), 5, 5, 0),
                new Point(7, 7)
        )));

        return list;
    }

    // =====================================================================
    //  1. ОБЗОР ВСЕХ ФИГУР
    // =====================================================================
    private static void printOverview(List<Figure> figures) {
        System.out.println("--- 1. ОБЗОР ВСЕХ ФИГУР ---");
        System.out.printf("%-3s | %-16s | %-10s | %-10s | %s%n",
                "#", "Тип", "Площадь", "Периметр", "Bounding Box");
        System.out.println("-".repeat(90));

        int index = 1;
        for (Figure f : figures) {
            BoundingBox bb = f.boundingBox();
            System.out.printf("%-3d | %-16s | %10.3f | %10.3f | [(%.1f;%.1f) .. (%.1f;%.1f)]%n",
                    index++,
                    f.kind().displayName(),
                    f.area(),
                    f.perimeter(),
                    bb.minX(), bb.minY(), bb.maxX(), bb.maxY());
        }
        System.out.println();
    }

    // =====================================================================
    //  2. ГРУППА (рекурсивная)
    // =====================================================================
    private static void demoGroup(List<Figure> scene) {
        System.out.println("--- 2. ГРУППА ФИГУР (рекурсивная) ---");

        // Берём первые 5 фигур из сцены
        Figure innerGroup = new Group(scene.subList(0, 5));

        // Берём ещё несколько и создаём вложенную группу
        Figure outerGroup = new Group(List.of(
                innerGroup,
                new Circle(new Point(0, 0), 5),
                new Rectangle(new Point(-2, -2), 4, 4, 0)
        ));

        System.out.println("Внутренняя группа (5 фигур):");
        System.out.printf("  Площадь:  %.3f%n", innerGroup.area());
        System.out.printf("  Периметр: %.3f%n", innerGroup.perimeter());

        System.out.println("Внешняя группа (внутренняя + круг + прямоугольник):");
        System.out.printf("  Площадь:  %.3f%n", outerGroup.area());
        System.out.printf("  Периметр: %.3f%n", outerGroup.perimeter());

        BoundingBox bb = outerGroup.boundingBox();
        System.out.printf("  Bounding box: [%.2f, %.2f] .. [%.2f, %.2f]%n",
                bb.minX(), bb.minY(), bb.maxX(), bb.maxY());
        System.out.println();
    }

    // =====================================================================
    //  3. ТРАНСФОРМАЦИИ
    // =====================================================================
    private static void demoTransformations() {
        System.out.println("--- 3. ТРАНСФОРМАЦИИ (сдвиг, поворот, масштаб) ---");

        Rectangle rect = new Rectangle(new Point(1, 1), 4, 2, 0);
        System.out.println("Исходный прямоугольник: " + rect);
        System.out.println("  BBox: " + rect.boundingBox());

        Figure shifted = rect.translate(10, 5);
        System.out.println("После сдвига (10, 5): " + shifted);
        System.out.println("  BBox: " + shifted.boundingBox());

        Figure rotated = rect.rotate(new Point(1, 1), Math.PI / 4);
        System.out.println("После поворота на PI/4 вокруг (1,1): " + rotated);
        System.out.println("  BBox: " + rotated.boundingBox());

        Figure scaled = rect.scale(new Point(1, 1), 2.0);
        System.out.println("После масштабирования x2 вокруг (1,1): " + scaled);
        System.out.printf("  Площадь: %.3f (было %.3f)%n", scaled.area(), rect.area());
        System.out.println();
    }

    // =====================================================================
    //  4. ПРИНАДЛЕЖНОСТЬ ТОЧКИ
    // =====================================================================
    private static void demoContains() {
        System.out.println("--- 4. ПРИНАДЛЕЖНОСТЬ ТОЧКИ (contains) ---");

        Circle circle = new Circle(new Point(0, 0), 5);
        System.out.println("Окружность: " + circle);
        System.out.println("  contains(0, 0):   " + circle.contains(new Point(0, 0)) + "  (внутри)");
        System.out.println("  contains(5, 0):   " + circle.contains(new Point(5, 0)) + "  (на границе)");
        System.out.println("  contains(6, 0):   " + circle.contains(new Point(6, 0)) + "  (снаружи)");

        Rectangle rect = new Rectangle(new Point(0, 0), 4, 4, 0);
        System.out.println("\nПрямоугольник: " + rect);
        System.out.println("  contains(2, 2):   " + rect.contains(new Point(2, 2)) + "  (внутри)");
        System.out.println("  contains(4, 4):   " + rect.contains(new Point(4, 4)) + "  (угол)");
        System.out.println("  contains(5, 5):   " + rect.contains(new Point(5, 5)) + "  (снаружи)");

        Segment seg = new Segment(new Point(0, 0), new Point(10, 0));
        System.out.println("\nОтрезок: " + seg);
        System.out.println("  contains(5, 0):   " + seg.contains(new Point(5, 0)) + "  (на отрезке)");
        System.out.println("  contains(5, 1):   " + seg.contains(new Point(5, 1)) + "  (не на отрезке)");
        System.out.println();
    }

    // =====================================================================
    //  5. ОБРАБОТКА ИСКЛЮЧЕНИЯ
    // =====================================================================
    private static void demoExceptionHandling() {
        System.out.println("--- 5. ОБРАБОТКА СОБСТВЕННОГО ИСКЛЮЧЕНИЯ ---");

        try {
            System.out.println("Пытаемся создать окружность с радиусом -1...");
            Circle bad = new Circle(new Point(0, 0), -1);
            System.out.println("Этого не должно было случиться: " + bad);
        } catch (InvalidGeometryException e) {
            System.out.println("  Поймали исключение: " + e.getMessage());
            System.out.println("  Программа продолжает работать.");
        }

        try {
            System.out.println("\nПытаемся создать отрезок из одинаковых точек...");
            Segment bad = new Segment(new Point(1, 1), new Point(1, 1));
            System.out.println("Этого не должно было случиться: " + bad);
        } catch (InvalidGeometryException e) {
            System.out.println("  Поймали исключение: " + e.getMessage());
        }
        System.out.println();
    }
}