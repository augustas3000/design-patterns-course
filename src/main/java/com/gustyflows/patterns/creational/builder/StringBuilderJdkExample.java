package com.gustyflows.patterns.creational.builder;

import jdk.swing.interop.SwingInterOpUtils;

public class StringBuilderJdkExample {
    public static void main(String[] args) {
        /*
        //say we wann output a paragraph, inm wa website or in simplified case a console
        String hello = "hello";
        System.out.println("<p>" + hello + "<p>");

        //how about a list of words
        String[] words = {"hello", "world"};
        System.out.println("<ul>\n" + "<li>" + words[0]); //quickly gets complicated..
        */


        //better use builder
        //construction of object happens through several functions
        String[] words = {"hello", "world"};
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<ul>\n");
        for (String word : words) {
            stringBuilder.append(String.format("  <li>%s</li>\n", word));
        }
        stringBuilder.append("</ul>");
        System.out.println(stringBuilder);

    }
}
