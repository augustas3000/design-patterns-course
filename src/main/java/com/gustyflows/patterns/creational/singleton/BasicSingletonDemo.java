package com.gustyflows.patterns.creational.singleton;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class BasicSingletonDemo {
    public static void main(String[] args) throws Exception {
//        BasicSingleton instance = BasicSingleton.getInstance();
//        BasicSingleton inst = BasicSingleton.getInstance();
//        System.out.println(instance.hashCode());
//        System.out.println(instance.getValue());
//        System.out.println(inst.hashCode());
//        System.out.println(inst.getValue());

        //problems with basic singleton approach:
        //1. Reflection: make additional copies through reflection

        //2. Serialization: when we deserialize an object, the jvm doesn't care that constructor is private - it will
        //create an object anyways:

        BasicSingleton singleton = BasicSingleton.getInstance();
        singleton.setValue(111);

        String fileName = "singleton.bin";
        saveToFile(singleton, fileName);

        singleton.setValue(222);

        BasicSingleton singletonReadFromFile = readFromFile(fileName);
        System.out.println(singleton == singletonReadFromFile);
        System.out.println("value from singleton object at runtime:");
        System.out.println(singleton.getValue());
        System.out.println(singleton.hashCode());
        System.out.println("value from singleton object read from file:");
        System.out.println(singletonReadFromFile.getValue());
        System.out.println(singletonReadFromFile.hashCode());
    }

    //serialization code
    static void saveToFile(BasicSingleton basicSingleton, String fileName) throws Exception {
        try (
                FileOutputStream fileOutputStream = new FileOutputStream(fileName);
                ObjectOutputStream out = new ObjectOutputStream(fileOutputStream)
        ) {
            out.writeObject(basicSingleton);
        }
    }

    //deserialization code
    static BasicSingleton readFromFile(String fileName) throws Exception {
        try (
                FileInputStream fileIn = new FileInputStream(fileName);
                ObjectInputStream in = new ObjectInputStream(fileIn)
        ) {
            return (BasicSingleton) in.readObject();
        }
    }

}

class BasicSingleton implements Serializable {

    private int value = 0;

    private BasicSingleton() {

    }

    private static final BasicSingleton INSTANCE = new BasicSingleton();

    public static BasicSingleton getInstance() {
        return INSTANCE;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    //this resolves an issue were singletons are copied via serialization
    //we basically tell jvm, that whenever serialization happens, it has to happen
    //within the context of this instance
    protected Object readResolve() {
        return INSTANCE;
    }
}
