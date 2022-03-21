package com.gustyflows.patterns.structural.decorator.staticdecorator;

import java.util.function.Supplier;

public class StaticDecorator {

    public static void main(String[] args) {
        ColoredShape<Square> blueSquare = new ColoredShape<>(
                () -> new Square(20),
                "blue");
        System.out.println(blueSquare.info());

        TransparentShape<ColoredShape<Circle>> myCircle =
                new TransparentShape<>(() ->
                        new ColoredShape<>(() ->
                                new Circle(5), "green"), 50);

        System.out.println(myCircle.info());

        //thast how we determine the types at compile time as oppossed to runtime..
        //myCircle.resize() - still unavailable..

    }

}

class Circle implements Shape {

    private float radius;

    public Circle() {
    }

    public Circle(float radius) {
        this.radius = radius;
    }

    public void resize(float factor) {
        radius *= factor;
    }

    @Override
    public String info() {
        return "A circle of radius " + radius;
    }
}

class Square implements Shape {

    private float side;

    public Square(float side) {
        this.side = side;
    }

    public Square() {
    }

    public void resize(float factor) {
        side *= factor;
    }

    @Override
    public String info() {
        return "A square with side length of " + side;
    }
}

interface Shape {
    String info();
}

class ColoredShape<T extends Shape> implements Shape {

    private Shape shape;
    private String color;

    public ColoredShape(Supplier<? extends T> constructor, String color) {
//        shape = new T(); - cant co this this is java not c sharp and the type gets erased
        shape = constructor.get();
        this.color = color;
    }

    @Override
    public String info() {
        return shape.info() + " has the color " + color;
    }
}

class TransparentShape<T extends Shape> implements Shape {

    private Shape shape;
    private int transparency;

    public TransparentShape(Supplier<? extends T> constructor, int transparency) {
        shape = constructor.get();
        this.transparency = transparency;
    }

    @Override
    public String info() {
        return null;
    }
}
