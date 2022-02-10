package com.gustyflows.patterns.creational.singleton.enumBased;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public enum EnumBasedSingleton {
    INSTANCE;

    //private all the time
    EnumBasedSingleton() {
        value = 42;
    }

    private int value;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}

//cant be inherited, also problems with persistence
//enum fields are non serializable, the only thing that is serializable is the name of instance

class Demo {
    //serialization code
    static void saveToFile(EnumBasedSingleton enumBasedSingleton, String fileName) throws Exception {
        try (
                FileOutputStream fileOutputStream = new FileOutputStream(fileName);
                ObjectOutputStream out = new ObjectOutputStream(fileOutputStream)
        ) {
            out.writeObject(enumBasedSingleton);
        }
    }

    //deserialization code
    static EnumBasedSingleton readFromFile(String fileName) throws Exception {
        try (
                FileInputStream fileIn = new FileInputStream(fileName);
                ObjectInputStream in = new ObjectInputStream(fileIn)
        ) {
            return (EnumBasedSingleton) in.readObject();
        }
    }

    public static void main(String[] args) throws Exception {
        String fileName = "myfile.bin";
        /*
            the value of 111 is never saved to the file.
            we set the value field to 111 and because application process is still running we are getting
            the same Enum instance we passed to saveToFile, however, if we are only readingFrom file, a new
            instance will be created and default constructor triggered, giving 42
         */

        EnumBasedSingleton singleton = EnumBasedSingleton.INSTANCE;
        singleton.setValue(111);
        saveToFile(singleton, fileName);

        EnumBasedSingleton singleton2 = readFromFile(fileName);
        System.out.println(singleton2.getValue());
    }
}