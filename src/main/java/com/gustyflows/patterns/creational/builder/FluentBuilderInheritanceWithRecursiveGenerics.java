package com.gustyflows.patterns.creational.builder;

public class FluentBuilderInheritanceWithRecursiveGenerics {
    public static void main(String[] args) {
        EmployeeBuilder employeeBuilder = new EmployeeBuilder();
        Person augustas = employeeBuilder.withName("Augustas").worksAt("Factory worker").build();
        System.out.println(augustas);

    }
}

class Person {
    public String name;
    public String position;


    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", position='" + position + '\'' +
                '}';
    }
}

//person builder is gonna take a type argument but that type has to be some
//inheritor of PersonBuilder
class PersonBuilder<SELF extends PersonBuilder<SELF>> {
    protected Person person = new Person();

    public SELF withName(String name) {
        person.name = name;
        return self();
    }

    public Person build() {
        return person;
    }

    protected SELF self() {
        return (SELF) this;
    }
}

class EmployeeBuilder extends PersonBuilder<EmployeeBuilder> {
    public EmployeeBuilder worksAt(String position) {
        person.position = position;
        return this;
    }
}

