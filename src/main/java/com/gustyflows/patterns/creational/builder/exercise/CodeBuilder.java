package com.gustyflows.patterns.creational.builder.exercise;


import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class CodeBuilderDemo {
    public static void main(String[] args) {
        CodeBuilder cb = new CodeBuilder("Person").addField("name", "String").addField("age", "int");
        System.out.println(cb);
        /*
        expected output:
            public class Person
            {
                public String name;
                public int age;
            }
         */
    }

}


class CodeBuilder {

    private String className;
    private final int indentSize = 2;
    private final String newLine = System.lineSeparator();
    private List<Pair<String, String>> fields = new ArrayList<>();


    public CodeBuilder(String className) {
        this.className = className;
    }

    public CodeBuilder addField(String name, String type) {
        fields.add(new Pair<>(name, type));
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("public class ");
        sb.append(className);
        sb.append(newLine);
        sb.append("{").append(newLine);

        String indentation = String.join("", Collections.nCopies(2 * indentSize, " "));

        fields.forEach(field ->
                sb.append(indentation + "public ")
                        .append(field.getValue1())
                        .append(" ")
                        .append(field.getValue0())
                        .append(";")
                        .append(newLine));

        sb.append("}");
        return sb.toString();
    }
}

