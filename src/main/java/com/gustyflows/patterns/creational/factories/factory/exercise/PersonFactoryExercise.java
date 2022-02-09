package com.gustyflows.patterns.creational.factories.factory.exercise;

import java.util.Arrays;
import java.util.List;

public class PersonFactoryExercise {
    public static void main(String[] args) {

        PersonFactory personFactory = new PersonFactory();
        List<Person> people = Arrays.asList(personFactory.createperson("Jon"),
                personFactory.createperson("Anna"),
                personFactory.createperson("Lucyyy"));
        people.forEach(p -> System.out.println(p.id));
    }
}


class Person {
    public int id;
    public String name;

    public Person(int id, String name) {
        this.id = id;
        this.name = name;
    }
}

class PersonFactory {

    private int personsCreated = 0;

    public Person createperson(String name) {
        int id = personsCreated;
        personsCreated++;
        return new Person(id, name);
    }
}

