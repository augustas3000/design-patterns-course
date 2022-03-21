package com.gustyflows.patterns.structural.decorator;

import java.util.stream.IntStream;

public class DecoratorDemo {
    public static void main(String[] args) {
        MagicString hello = new MagicString("hello");
        System.out.println(hello + " has " + hello.getNumberOfVowels() + " vowels");

    }
}

/*
we wanna add additional fucntionality to objects from 'final' classes, such as string
, and we cannot inherit as the class is final:
public final class String
    implements java.io.Serializable, Comparable<String>, CharSequence,

BUT we can use decoration instead:
 */

class MagicString {
    private String string;

    public MagicString(String string) {
        this.string = string;
    }

    public long getNumberOfVowels() {
        return string.chars()
                .mapToObj(c -> (char) c)
                .filter(c -> "aeiou".contains(c.toString()))
                .count();
    }

    @Override
    public String toString() {
        return string;
    }

    //intellij idea - generate delegates

}
