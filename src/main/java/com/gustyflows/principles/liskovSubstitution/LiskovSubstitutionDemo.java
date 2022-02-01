package com.gustyflows.principles.liskovSubstitution;

//You should be able to substitute a sub-class for a base-class
//if u got an api that takes in a base class, you should be able to substitute that with sub-class

import org.w3c.dom.css.Rect;

import java.awt.*;

public class LiskovSubstitutionDemo {

    static void useIt(Rectangle rectangle) {
        int width = rectangle.getWidth();
        rectangle.setHeight(10);
        //area = width * 10
        System.out.println("Expect an area of " + (width * 10) + ", got " + rectangle.getArea());
    }

    public static void main(String[] args) {
        //ok
        Rectangle rectangle = new Rectangle(2, 3);
        useIt(rectangle);

        //not ok
        Rectangle square = new Square();
        square.setWidth(5);
        useIt(square);

        //this basically violates liskov substitution principle when used with suqare
        //all about useIt method working correctly on both rectangle and the derived class
    }
}

class Rectangle {
    protected int width, height;

    public Rectangle() {
    }

    public Rectangle(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return "Rectangle{" +
                "width=" + width +
                ", height=" + height +
                '}';
    }

    public int getArea() {
        return width * height;
    }

    //one way to avoid principle violation is to add this method to base class
    public boolean isSquare() {
        return width == height;
    }
}

//suppose we use inheritence to define a special type of rectangle - a square
class Square extends Rectangle {

    public Square() {
    }

    public Square(int size) {
        width = height = size;
    }

    @Override
    public void setWidth(int width) {
        super.setWidth(width);
        super.setHeight(width);
    }

    @Override
    public void setHeight(int height) {
        super.setHeight(height);
        super.setWidth(height);
    }
}

//or use a factory pattern:
class RectangleFactory {

    public static Rectangle newRectangle(int width, int height) {
        return new Rectangle(width, height);
    }

    public static Rectangle newSquare(int side) {
        return new Rectangle(side, side);
    }
}
