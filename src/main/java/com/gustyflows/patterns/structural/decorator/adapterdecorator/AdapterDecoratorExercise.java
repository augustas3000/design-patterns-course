package com.gustyflows.patterns.structural.decorator.adapterdecorator;


class Bird {
public int age;

    public String fly() {
        return age < 10 ? "flying" : "tooo old";
    }
}

class Lizard {
    public int age;

    public String crawl() {
        return (age < 1) ? "crawling" : "too young";
    }
}

class Dragon {
    private Lizard lizard;
    private Bird bird;

    public Dragon() {
        lizard = new Lizard();
        bird = new Bird();
    }

    private int age;

    public void setAge(int age) {
        lizard.age = age;
        bird.age = age;
    }


    public String fly() {
        return bird.fly();
    }

    public String crawl() {
        return lizard.crawl();
    }
}


public class AdapterDecoratorExercise {
    public static void main(String[] args) {
        Dragon dragon = new Dragon();
        dragon.setAge(8);
        System.out.println(dragon.fly());
        System.out.println(dragon.crawl());
    }
}
