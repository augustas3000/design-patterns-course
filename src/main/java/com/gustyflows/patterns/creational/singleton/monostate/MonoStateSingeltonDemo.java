package com.gustyflows.patterns.creational.singleton.monostate;

public class MonoStateSingeltonDemo {
    public static void main(String[] args) {

        ChiefExecutiveOfficer chiefExecutiveOfficer = new ChiefExecutiveOfficer();
        chiefExecutiveOfficer.setAge(20);
        chiefExecutiveOfficer.setName("Kaka");

        ChiefExecutiveOfficer chiefExecutiveOfficer2 = new ChiefExecutiveOfficer();
        System.out.println(chiefExecutiveOfficer.getAge());
        System.out.println(chiefExecutiveOfficer2.getAge());
    }
}

class ChiefExecutiveOfficer {
    //achieving singleton-like effect by making  storage element static:
    //so one can go heade and make many instances but as soon as one creates an instance, all the state
    //will map to the same fields
    private static String name;
    private static int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        ChiefExecutiveOfficer.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        ChiefExecutiveOfficer.age = age;
    }

    @Override
    public String toString() {
        return "ChiefExecutiveOfficer{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
