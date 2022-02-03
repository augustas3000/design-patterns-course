package com.gustyflows.patterns.creational.builder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HtmlBuilderDemo {

    public static void main(String[] args) {
        HtmlBuilderFluent builder = new HtmlBuilderFluent("ul");
        builder.addChild("li", "hello");
        builder.addChild("li", "world");
        System.out.println(builder);

    }


}

class HtmlElement {
    public String name, text; //tag name, txt providing it is text not a tag
    public List<HtmlElement> elements = new ArrayList<>(); //other tags

    private final int indentSize = 2;
    private final String newLine = System.lineSeparator();

    public HtmlElement() {
    }

    public HtmlElement(String name, String text) {
        this.name = name;
        this.text = text;
    }

    @Override
    public String toString() {
        //want a recursive impl:
        return toStringImpl(0);
    }

    private String toStringImpl(int indent) {
        StringBuilder sb = new StringBuilder();
        String i = String.join("", Collections.nCopies(indent * indentSize, " "));
        sb.append(String.format("%s<%s>%s", i, name, newLine));
        if (text != null && !text.isEmpty()) {
            sb.append(String.join("", Collections.nCopies(indentSize * (indent + 1), " ")))
                    .append(text)
                    .append(newLine);
        }

        for (HtmlElement e : elements) {
            sb.append(e.toStringImpl(indent + 1));
        }

        sb.append(String.format("%s</%s>%s", i, name, newLine));
        return sb.toString();
    }
}

class HtmlBuilder {
    private String rootName;
    private HtmlElement root = new HtmlElement();

    public HtmlBuilder(String rootName) {
        this.rootName = rootName;
        root.name = rootName;
    }

    public void addChild(String childName, String childText) {
        HtmlElement htmlElement = new HtmlElement(childName, childText);
        root.elements.add(htmlElement);
    }

    public void clear() {
        root = new HtmlElement();
        root.name = rootName;
    }

    @Override
    public String toString() {
        return root.toString();
    }
}

