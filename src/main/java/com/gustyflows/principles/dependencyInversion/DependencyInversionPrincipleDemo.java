package com.gustyflows.principles.dependencyInversion;

import org.javatuples.Triplet;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


// A. High-level modules should not depend on low level modules
// Both should depend on abstractions
//abstraction - either an abstract class or an interface
//a signature of something that performs particular operation
//but you dont necesssarily work with concrete impl, instead you work with abstraction

// B. Abstractions should not depend on details.
// Details should depend on abstractions.
//if you can, use interfaces and abstract classes instead of concrete classes
//so we can substitute implementations without breaking anything


public class DependencyInversionPrincipleDemo {

    public static void main(String[] args) {

        Person parent = new Person("Sean");
        Person child = new Person("Judy");

        Relationships relationships = new Relationships();
        relationships.addParentAndChild(parent,child);
//        relationships.getRelations() we are exposing internal storage implementation of relationships as a public getter

        Research research = new Research(relationships);

        //implementation can change now without rewritting lots of code
        RelationshipsImproved relationshipsImproved = new RelationshipsImproved();
        relationshipsImproved.addParentAndChild(parent,child);
        Research researchImproved = new Research(relationshipsImproved);

    }

}

//say we wanna have an app that model relationships between different people,
//kinda like a family tree

enum Relationship {
    PARENT,
    CHILD,
    SIBLING
}

class Person {
    public String name;

    public Person(String name) {
        this.name = name;
    }
}

//low level module - because it is related to dada storage, simply keeps and provides a list of data
//doesnt have any business logic on its own, it simply aloows to modify a list and that is single responsibility
class Relationships {
    private List<Triplet<Person, Relationship, Person>> relations = new ArrayList<>();

    public void addParentAndChild(Person parent, Person child) {
        relations.add(new Triplet<>(parent, Relationship.PARENT, child));
        relations.add(new Triplet<>(child, Relationship.CHILD, parent));
    }

    public List<Triplet<Person, Relationship, Person>> getRelations() {
        return relations;
    }
}


//high level module - it allows us to perform operations on those low level  constructs
//such as Relationships, it kind of is HIGHER to the end user(they dont care about storage impl, but about actual research)

//Dependency Inversion principle states that high level modules should
//not depend on low level modules -both should debepn on abstarctions
class Research {

    //violation of principle here
    public Research(Relationships relationships) {

        List<Triplet<Person, Relationship, Person>> relations =
                relationships.getRelations();

        relations.stream()
                .filter(triplet -> triplet.getValue0().name.equals("Sean")
                        && triplet.getValue1() == Relationship.PARENT)
                .forEach(triplet -> System.out.println("Sean has a child called " + triplet.getValue2().name));
    }

    //depend on abstraction:
    public Research(RelationshipBrowser relationshipBrowser) {
        List<Person> children = relationshipBrowser.findAllChildrenOf("Sean");
        for (Person child : children) {
            System.out.println("Sean has a child called " + child.name);
        }
    }

}

//to adhere to this principle better - we inntroduce an abstraction:
//this is an abstraction you would want to introduce on low-level module
interface RelationshipBrowser {
    List<Person> findAllChildrenOf(String name);
}

class RelationshipsImproved implements RelationshipBrowser {
    private List<Triplet<Person, Relationship, Person>> relations = new ArrayList<>();

    public void addParentAndChild(Person parent, Person child) {
        relations.add(new Triplet<>(parent, Relationship.PARENT, child));
        relations.add(new Triplet<>(child, Relationship.CHILD, parent));
    }

    private List<Triplet<Person, Relationship, Person>> getRelations() {
        return relations;
    }

    @Override
    public List<Person> findAllChildrenOf(String name) {
        //the search will happen in low level module, because what if we decided to change implementation?

        List<Triplet<Person, Relationship, Person>> relations =
                getRelations();

        return relations.stream()
                .filter(triplet -> triplet.getValue0().name.equals(name)
                        && triplet.getValue1() == Relationship.PARENT)
                .map(Triplet::getValue2)
                .collect(Collectors.toList());

    }
}