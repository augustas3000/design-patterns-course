package com.gustyflows.patterns.creational.singleton.multiton;

import java.util.HashMap;

public class MultitonDemo {
    public static void main(String[] args) {
        final Printer main = Printer.get(Subsystem.PRIMARY);
        final Printer aux = Printer.get(Subsystem.AUXILIARY);
        final Printer aux2 = Printer.get(Subsystem.AUXILIARY);
        System.out.println(aux.hashCode() == aux2.hashCode());
    }
}

//pattern - defines a finite number of instances, restrict the number of instances

enum Subsystem {
    PRIMARY,
    AUXILIARY,
    FALLBACK
}

class Printer {
    private Printer() {

    }

    private static HashMap<Subsystem, Printer> instances = new HashMap<>();

    public static Printer get(Subsystem ss) {
        if (instances.containsKey(ss)) {
            return instances.get(ss);
        }
        Printer instance = new Printer();
        instances.put(ss, instance);
        return instance;
    }
}