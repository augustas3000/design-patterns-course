package com.gustyflows.patterns.structural.bridge;

public class BridgePatternDemo {
    public static void main(String[] args) {
        //this would be less clunky if using proper DI framework like spring
        final RasterRenderer rasterRenderer = new RasterRenderer();
        final VectorRenderer vectorRenderer = new VectorRenderer();

        final Circle circleV = new Circle(vectorRenderer, 5);
        circleV.draw();
        final Circle circleR = new Circle(rasterRenderer, 10);
        circleR.draw();
    }
}

//a mechanism that decouples an interface (hierarchy) from an implementation (hierarchy)

//why it exists:
/*
Shape -> Circle, Square
Rendering -> Vector, Raster

gonna end up with lots of renderers:
VectorCircleRenderer, VectorSquareRenderer, RasterCircleRenderer, RasterSquareRenderer

if its 2x2, end up with 4 classes, if say 5 shapes and 3 types of rendering = 15 classes.. not really a viable solution
 */

interface Renderer {
    void renderCircle(float radius);
}

class VectorRenderer implements Renderer {
    @Override
    public void renderCircle(float radius) {
        System.out.println("Drawing a circle of radius " + radius);
    }
}

class RasterRenderer implements Renderer {
    @Override
    public void renderCircle(float radius) {
        System.out.println("Drawing pixels for a circle of radius " + radius);
    }
}

abstract class Shape {
    protected Renderer renderer;

    public Shape(Renderer renderer) {
        this.renderer = renderer;
    }

    public abstract void draw();

    public abstract void resize(float factor);
}

class Circle extends Shape {

    public float radius;

    public Circle(Renderer renderer) {
        super(renderer);
    }

    public Circle(Renderer renderer, float radius) {
        super(renderer);
        this.radius = radius;
    }

    @Override
    public void draw() {
        renderer.renderCircle(radius);
    }

    @Override
    public void resize(float factor) {
        radius *= factor;
    }
}
