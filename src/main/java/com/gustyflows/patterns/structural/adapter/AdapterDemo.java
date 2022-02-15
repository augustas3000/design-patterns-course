package com.gustyflows.patterns.structural.adapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Spliterator;
import java.util.function.Consumer;


/*
often when we implement adapter pattern, sometimes it will generate temporary objects
and if you want to avoid genrerating those extra objects, then you build a cache..
 */

public class AdapterDemo {

    private final static List<VectorObject> vectorObjects = new ArrayList<>(Arrays.asList(
            new VectorRectangle(1, 1, 10, 10),
            new VectorRectangle(3, 3, 6, 6)
    ));


    //suppose we only have an api that alllows drawing by points - we need a line-to-point adapter class
    public static void drawPoint(Point point) {
        System.out.println(".");
    }

    private static void draw() {
        for (VectorObject vo : vectorObjects) {
            for (Line line : vo) {
                LineToPointAdapter adapter = new LineToPointAdapter(line);
                adapter.forEach(point -> drawPoint(point));
            }
        }
    }

    public static void main(String[] args) {
        draw();
        //if we call the same method, we basically duplicate the work
        //because those points generated in first method could be reused
        draw();
    }
}

class LineToPointAdapter implements Iterable<Point> {

    private static int count = 0;
    //gonna cache objects by hashing

    private static Map<Integer, List<Point>> cachedPoints = new HashMap<>();
    private int currentHash;


    //gets a single line and turns it into array of corresponding points
    public LineToPointAdapter(Line line) {
        /*
        when you come to adapt a line by series of points, the first thing you do is calculate the hash
        of this line: hash = line.hashCode(), and we check if its already in the cache, we dont generate
        the points because we already have all info necessary, if not, we generate points, add these into
        an array, and put that into cache
         */
        currentHash = line.hashCode();
        if (cachedPoints.containsKey(currentHash)) {
            return;
        }

        //turns the line into array of corresponding points:
        System.out.println(
                String.format("%d: Generating points for line [%d,%d]-[%d,%d] (with chaching)",
                        ++count, line.start.x, line.start.y, line.end.x, line.end.y)
        );

        ArrayList<Point> points = new ArrayList<>();

        int left = Math.min(line.start.x, line.end.x);
        int right = Math.max(line.start.x, line.end.x);
        int top = Math.min(line.start.y, line.end.y);
        int bottom = Math.max(line.start.y, line.end.y);
        int dx = right - left;
        int dy = line.end.y - line.start.y;

        if (dx == 0) {
            for (int y = top; y <= bottom; ++y) {
                points.add(new Point(left, y));
            }
        } else if (dy == 0) {
            for (int x = left; x <= right; ++x) {
                points.add(new Point(x, top));
            }
        }

        cachedPoints.put(currentHash, points);
    }

    @Override
    public Iterator<Point> iterator() {
        return cachedPoints.get(currentHash).iterator();
    }

    @Override
    public void forEach(Consumer<? super Point> action) {
        cachedPoints.get(currentHash).forEach(action);
    }

    @Override
    public Spliterator<Point> spliterator() {
        return cachedPoints.get(currentHash).spliterator();
    }
}


//Adapter - a construct which adapts an existing
//interface X to conform to the required interface y

class Point {
    public int x, y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return x == point.x && y == point.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}

class Line {
    public Point start, end;

    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Line line = (Line) o;
        return Objects.equals(start, line.start) && Objects.equals(end, line.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end);
    }
}

class VectorObject extends ArrayList<Line> {

}

class VectorRectangle extends VectorObject {
    public VectorRectangle(int x, int y, int width, int height) {
        add(new Line(new Point(x, y), new Point(x + width, y)));
        add(new Line(new Point(x + width, y), new Point(x + width, y + height)));
        add(new Line(new Point(x, y), new Point(x, y + height)));
        add(new Line(new Point(x, y + height), new Point(x + width, y + height)));

    }
}

