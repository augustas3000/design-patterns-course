package com.gustyflows.patterns.structural.decorator;

public class DynamicDecoratorComposition {

    public static void main(String[] args) {
        Circle circle = new Circle(10);
        System.out.println(circle.info());
        ColoredShape redSquare = new ColoredShape(new Square(20), "Red");
        System.out.println(redSquare.info());

        TransparentShape colouredShapeWithTransparency = new TransparentShape(
                new ColoredShape(new Square(50), "Green"), 50
        );

        System.out.println(colouredShapeWithTransparency.info());
        //but we have to realise that colouredShapeWithTransparency is TransparentShape but not a Square -
        //we cannot say - resize it because it is not part of api...
        //colouredShapeWithTransparency.resize(2653) ... wont work

    }

}

interface Shape {
    String info();
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

/*
say we got these two above implementations of Shape, and we dont want to modify these classes
any further... we need a decorator ;) - assume we cant modify existing classes - OCP
 */

class ColoredShape implements Shape {

    private Shape shape;
    private String color;

    public ColoredShape(Shape shape, String color) {
        this.shape = shape;
        this.color = color;
    }

    @Override
    public String info() {
        return shape.info() + " has the color " + color;
    }
}

class TransparentShape implements Shape {

    private Shape shape;
    private int transparency;

    public TransparentShape(Shape shape, int transparency) {
        this.shape = shape;
        this.transparency = transparency;
    }

    @Override
    public String info() {
        return shape.info() + " has transparency of " + transparency;
    }
}



