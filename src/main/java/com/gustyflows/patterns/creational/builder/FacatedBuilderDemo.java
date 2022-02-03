package com.gustyflows.patterns.creational.builder;

public class FacatedBuilderDemo {

    public static void main(String[] args) {
        PersonBuilderF personBuilderF = new PersonBuilderF();
        PersonF personBuilt = personBuilderF
                .lives()
                    .at("Downling Street")
                    .in("London")
                .works()
                    .at("Some company")
                .lives()
                    .withPostcode("ZZ420")
                .works()
                    .asA("Donmctor")
                    .earning(200554)
                .build();

        System.out.println(personBuilt);

    }

}

class PersonF {
    public String streetAddress, postcode, city;
    public String companyName, position;
    public int annualIncome;

    @Override
    public String toString() {
        return "Person1{" +
                "streetAddress='" + streetAddress + '\'' +
                ", postcode='" + postcode + '\'' +
                ", city='" + city + '\'' +
                ", companyName='" + companyName + '\'' +
                ", position='" + position + '\'' +
                ", annualIncome=" + annualIncome +
                '}';
    }
}

class PersonBuilderF {
    protected PersonF person = new PersonF();

    public PersonAddressBuilder lives() {
        return new PersonAddressBuilder(person);
    }

    public PersonJobBuilder works() {
        return new PersonJobBuilder(person);
    }

    public PersonF build() {
        return person;
    }
}

class PersonJobBuilder extends PersonBuilderF {
    public PersonJobBuilder(PersonF person) {
        this.person = person;
    }

    public PersonJobBuilder at(String companyName) {
        person.companyName = companyName;
        return this;
    }

    public PersonJobBuilder asA(String position) {
        person.position = position;
        return this;
    }

    public PersonJobBuilder earning(int annualIncome) {
        person.annualIncome = annualIncome;
        return this;
    }

}

class PersonAddressBuilder extends PersonBuilderF {
    //need a reference to person being built in every sub-builder
    public PersonAddressBuilder(PersonF person) {
        this.person = person;
    }

    public PersonAddressBuilder at(String streetAddress) {
        person.streetAddress = streetAddress;
        return this;
    }

    public PersonAddressBuilder withPostcode(String postcode) {
        person.postcode = postcode;
        return this;
    }

    public PersonAddressBuilder in(String city) {
        person.city = city;
        return this;
    }
}
