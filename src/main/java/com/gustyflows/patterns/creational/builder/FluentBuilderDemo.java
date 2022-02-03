package com.gustyflows.patterns.creational.builder;

public class FluentBuilderDemo {
    public static void main(String[] args) {
        // .append returns itself - fluent interface

        StringBuilder sb = new StringBuilder();
        sb.append("foo").append("smth");

        HtmlBuilderFluent builder = new HtmlBuilderFluent("ul");
        builder
                .addChild("li", "hello")
                .addChild("li", "world");
        System.out.println(builder);
    }
}

class HtmlBuilderFluent {
    private String rootName;
    private HtmlElement root = new HtmlElement();

    public HtmlBuilderFluent(String rootName) {
        this.rootName = rootName;
        root.name = rootName;
    }

    public HtmlBuilderFluent addChild(String childName, String childText) {
        HtmlElement htmlElement = new HtmlElement(childName, childText);
        root.elements.add(htmlElement);
        return this;
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
