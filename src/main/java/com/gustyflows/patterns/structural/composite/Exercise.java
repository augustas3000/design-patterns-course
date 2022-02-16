package com.gustyflows.patterns.structural.composite;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

public class Exercise {
}

interface ValueContainer extends Iterable<Integer> {

}

class SingleValue implements ValueContainer {
    public int value;

    public SingleValue(int value) {
        this.value = value;
    }

    @Override
    public Iterator<Integer> iterator() {
        return Collections.singletonList(value).iterator();
    }

    @Override
    public void forEach(Consumer<? super Integer> action) {
        action.accept(value);
    }

    @Override
    public Spliterator<Integer> spliterator() {
        return Collections.singletonList(value).spliterator();
    }
}

class ManyValues extends ArrayList<Integer> implements ValueContainer {

}

class MyList extends ArrayList<ValueContainer> {
    public MyList(Collection<? extends ValueContainer> c) {
        super(c);
    }

    public int sum() {
        int tot = 0;

        for (ValueContainer col : this) {
            for (int v : col) {
                tot += v;
            }
        }
        return tot;
    }
}

class Demo {
    public static void main(String[] args) {
        SingleValue singleVal1 = new SingleValue(5);
        SingleValue singleVal2 = new SingleValue(10);

        ManyValues manyValues = new ManyValues();
        manyValues.add(5);
        manyValues.add(80);

        MyList valueContainers = new MyList(List.of(singleVal1, singleVal2, manyValues));
        System.out.println(valueContainers.sum());
    }
}
