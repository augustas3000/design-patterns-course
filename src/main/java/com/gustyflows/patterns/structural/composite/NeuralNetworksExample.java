package com.gustyflows.patterns.structural.composite;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.stream.Stream;

public class NeuralNetworksExample {
}

//we got a Neuron, which has incomming and outgoing connections
class Neuron {
    public ArrayList<Neuron> in, out;

    public void connectTo(Neuron other) {
        out.add(other);
        other.in.add(this);
    }
}

//we also wann be able to make entire layers of neurons
class NeuronLayer extends ArrayList<Neuron> {

}

//class Demo {
//    public static void main(String[] args) {
//        Neuron neuron = new Neuron();
//        Neuron neuron2 = new Neuron();
//
//        NeuronLayer neuronLayer = new NeuronLayer();
//        NeuronLayer neuronLayer2 = new NeuronLayer();
//
//        //this ok
//        neuron.connectTo(neuron2);
//        //this problematic - not gonna implement 4 different connectTo methods..
////        neuron.connectTo(neuronLayer);
////        neuronLayer.conectTo(neuron);
////        neuronLayer.connectTo(neuronLayer2);
//    }
//}

/*
instead lets build an interface thats as general as possible for the purposes
of connecting elements to one another,
we gotta treat both, individual Neurons as well as NeuronLayers in uniform fashion
 */

class Neuron1 implements OneOrMoreNeurons {
    public ArrayList<Neuron1> in = new ArrayList<>();
    public ArrayList<Neuron1> out = new ArrayList<>();

    @Override
    public Iterator<Neuron1> iterator() {
        return Collections.singleton(this).iterator();
    }

    @Override
    public void forEach(Consumer<? super Neuron1> action) {
        action.accept(this);
    }

    @Override
    public Spliterator<Neuron1> spliterator() {
        return Collections.singleton(this).spliterator();
    }
}

class NeuronLayer1 extends ArrayList<Neuron1> implements OneOrMoreNeurons {

}

interface OneOrMoreNeurons extends Iterable<Neuron1> {
    //make a default impl right inside the interface, or just replace interface with an abstract class
    default void connectTo(OneOrMoreNeurons oneOrMoreNeuronsOther) {
        //ensure we do not connect to ourselves
        if (this == oneOrMoreNeuronsOther) return;
        //we iterate our own neurons, and iterate otehr neurons, and interconenct them
        for (Neuron1 from : this) {
            for (Neuron1 to : oneOrMoreNeuronsOther) {
                from.out.add(to);
                to.in.add(from);
            }
        }
    }
}

class Demo1 {
    public static void main(String[] args) {
        Neuron1 neuron = new Neuron1();
        Neuron1 neuron2 = new Neuron1();

        NeuronLayer1 neuronLayer = new NeuronLayer1();
        NeuronLayer1 neuronLayer2 = new NeuronLayer1();

        //this ok
        neuron.connectTo(neuron2);
        //this problematic - not gonna implement 4 different connectTo methods..
        neuron.connectTo(neuronLayer);
        neuronLayer.connectTo(neuron);
        neuronLayer.connectTo(neuronLayer2);
    }
}

