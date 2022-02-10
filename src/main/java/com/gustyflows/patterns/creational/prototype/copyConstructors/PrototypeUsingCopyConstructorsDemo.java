package com.gustyflows.patterns.creational.prototype.copyConstructors;

public class PrototypeUsingCopyConstructorsDemo {
    public static void main(String[] args) {
        Employee john = new Employee(
                "John",
                new Address("123 Ldn Rd", "London", "UK")
        );
        Employee chris = new Employee(john);
        chris.name = "Chris";

        System.out.println(john);
        System.out.println(john.hashCode());
        System.out.println(chris);
        System.out.println(chris.hashCode());
    }
}

class Address {
    public String streetAddress;
    public String city;
    public String country;

    public Address(String streetAddress, String city, String country) {
        this.streetAddress = streetAddress;
        this.city = city;
        this.country = country;
    }

    //make a copy constructor
    public Address(Address other) {
        this(other.streetAddress, other.city, other.country);
    }

    @Override
    public String toString() {
        return "Address{" +
                "streetAddress='" + streetAddress + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}

class Employee {
    public String name;
    public Address address;

    public Employee(String name, Address address) {
        this.name = name;
        this.address = address;
    }

    //copy constructor
    public Employee(Employee other) {
        this(other.name, new Address(other.address));
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", address=" + address +
                '}';
    }
}

