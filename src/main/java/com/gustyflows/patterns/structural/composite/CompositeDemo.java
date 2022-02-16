package com.gustyflows.patterns.structural.composite;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CompositeDemo {

    public static void main(String[] args) {
        //make a top level object:
        GraphicObject drawing = new GraphicObject();
        drawing.setName("My drawing");
        //add children
        drawing.children.add(new Square("Red"));
        drawing.children.add(new Circle("Yellow"));

        //make anothert group
        GraphicObject group = new GraphicObject();
        group.children.add(new Circle("Blue"));
        group.children.add(new Square("Piiiink"));

        //add tha group to the "My Drawing" group
        drawing.children.add(group);
        System.out.println(drawing);
        //this works because in implementation of GraphicObject.toString(),
        //there is a recursive procedure through print() going through all the children
        //and treating them uniformely - remember that GraphicObject can be either a singular object OR
        //a group of objects

        Circle greenCircle = new Circle("Green");
        System.out.println(greenCircle);

        GraphicObject graphicObjectGroupOfCircles = new GraphicObject();
        graphicObjectGroupOfCircles.children.add(new Circle("Yellow"));
        graphicObjectGroupOfCircles.children.add(new Circle("Yellow"));
        graphicObjectGroupOfCircles.children.add(new Circle("Yellow"));
        graphicObjectGroupOfCircles.children.add(new Circle("Yellow"));
        System.out.println(graphicObjectGroupOfCircles);

        GraphicObject graphicObjectGroupOfCirclesOfSquares = new GraphicObject();
        Circle yellowCircle1 = new Circle("Yellow");
        yellowCircle1.children.add(new Square("Green"));
        graphicObjectGroupOfCirclesOfSquares.children.add(yellowCircle1);
        Circle yellowCircle2 = new Circle("Yellow");
        yellowCircle2.children.add(new Square("Green"));
        graphicObjectGroupOfCirclesOfSquares.children.add(yellowCircle2);
        Circle yellowCircle3 = new Circle("Yellow");
        yellowCircle3.children.add(new Square("Green"));
        graphicObjectGroupOfCirclesOfSquares.children.add(yellowCircle3);
        System.out.println(graphicObjectGroupOfCirclesOfSquares);
    }

}

/*
Think drawing application - in addition to having several different shapes separate,
u can also take several shapes together, i.e drag them as group
 */

class GraphicObject {
    protected String name = "Group";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GraphicObject() {
    }

    public String color;
    /*
    we consider a graphic object as a scalar object - 1. Something inherits from GraphicObject
    and they make a circle, or a square, so thats an individual SINGLE object...
    BUT, in addition to that, a graphic object can represent a grouping, in which case
    we have a bunch of children.
    So its either a group, or a single object - and the idea of composite pattern is being able to
    treat single objects or groups of objects in a uniform fashion
     */
    public List<GraphicObject> children = new ArrayList<>();

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        print(stringBuilder, 0);
        return stringBuilder.toString();
    }

    private void print(StringBuilder stringBuilder, int depth) {
        stringBuilder.append(String.join("", Collections.nCopies(depth, "*")))
                .append(depth > 0 ? " " : "")
                .append((color == null || color.isEmpty()) ? "" : color + " ")
                .append(getName())
                .append(System.lineSeparator());
        for (GraphicObject child : children) {
            child.print(stringBuilder, depth + 1);
        }
    }
}

//notice that GraphicObject is non abstract meaning it can also be instantiated to act a s a GROUP
class Circle extends GraphicObject {
    public Circle(String color) {
        name = "Circle";
        this.color = color;
    }
}

class Square extends GraphicObject {
    public Square(String color) {
        name = "Square";
        this.color = color;
    }
}