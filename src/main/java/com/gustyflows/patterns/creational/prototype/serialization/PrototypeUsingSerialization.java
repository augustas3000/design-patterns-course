package com.gustyflows.patterns.creational.prototype.serialization;

import org.apache.commons.lang3.SerializationUtils;

import java.io.Serializable;

public class PrototypeUsingSerialization {
    public static void main(String[] args) {
        Foo foo = new Foo(42, "life");
        //serialize and deserialize - basically make a new copy
        Foo foo2 = SerializationUtils.roundtrip(foo);
        foo2.whateva = "smsadkhjuasdk";
        System.out.println(foo);
        System.out.println(foo.hashCode());
        System.out.println(foo2);
        System.out.println(foo2.hashCode());
    }
}

class Foo implements Serializable {
    public int stuff;
    public String whateva;

    public Foo(int stuff, String whateva) {
        this.stuff = stuff;
        this.whateva = whateva;
    }

    @Override
    public String toString() {
        return "Foo{" +
                "stuff=" + stuff +
                ", whateva='" + whateva + '\'' +
                '}';
    }
}
