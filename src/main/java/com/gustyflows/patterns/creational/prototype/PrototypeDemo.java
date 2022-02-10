package com.gustyflows.patterns.creational.prototype;

import java.util.Arrays;

/*
complicated objects are not designed from scratch - the yreiterate existing designs,
an existing(partially or fully constructed) design is a prototype,
make a deep copy (clone) of prototype and customize it,
make cloning convenient - e.x a factory
 */
public class PrototypeDemo {
    public static void main(String[] args) {

    }
}

class Address implements Cloneable {
    public String streetName;
    public Integer houseNumber;

    public Address(String streetName, Integer houseNumber) {
        this.streetName = streetName;
        this.houseNumber = houseNumber;
    }

    @Override
    public String toString() {
        return "Address{" +
                "streetName='" + streetName + '\'' +
                ", houseNumber=" + houseNumber +
                '}';
    }

    //deep copy
    @Override
    public Object clone() throws CloneNotSupportedException {
        return new Address(streetName, houseNumber);
    }
}

class Person implements Cloneable {
    public String[] names;
    public Address address;

    public Person(String[] names, Address address) {
        this.names = names;
        this.address = address;
    }

    @Override
    public String toString() {
        return "Person{" +
                "names=" + Arrays.toString(names) +
                ", address=" + address +
                '}';
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        //fundamentally wrong to use references..
        //return new Person(names, address);
        return new Person(names.clone(), (Address) address.clone());
    }
}

class CloningDemo {
    public static void main(String[] args) throws CloneNotSupportedException {
        Person john = new Person(new String[]{"John", "Smith"}, new Address("Ldn road", 123));

        /*
        //suppose we want to create another object but not invoke complex constructor
        //Person john = new Person(new String[]{"John", "Smith"}, new Address("Ldn road", 123));

        //this will result in overwrite of john object
        //variable is just a remote control when pointing to an object
        Person jane = john;
        jane.names[0] = "jane";
        jane.address.houseNumber = 124;

        System.out.println(john);
        System.out.println(john.hashCode());
        System.out.println(jane);
        System.out.println(jane.hashCode());
         */

        Person jane = (Person) john.clone();
        jane.names[0] = "Jane";
        jane.address.houseNumber = 124;

        System.out.println(john);
        System.out.println(john.hashCode());
        System.out.println(jane);
        System.out.println(jane.hashCode());

    }
}

//using Cloneable interface is not recommended, as it defaults to shallow-copying
//most cases we want a deep copy and other mechanisms are better suited to that

