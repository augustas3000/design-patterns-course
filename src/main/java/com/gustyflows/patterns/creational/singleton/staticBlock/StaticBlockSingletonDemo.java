package com.gustyflows.patterns.creational.singleton.staticBlock;

import java.io.File;
import java.io.IOException;

public class StaticBlockSingletonDemo {
}

//we might have exceptions inside the singleton constructor, if that happens, we are in trouble,
//but with static block singletons this is different

class StaticBlockSingleton {

    private StaticBlockSingleton() throws IOException {
        System.out.println("Singleton is initializing...");
        File.createTempFile(".", ".");
    }

    private static StaticBlockSingleton instance;

    static {
        try {
            instance = new StaticBlockSingleton();
        } catch (Exception e) {
            System.err.println("Failed to create singleton");
        }
    }

    public static StaticBlockSingleton getInstance() {
        return instance;
    }
}
