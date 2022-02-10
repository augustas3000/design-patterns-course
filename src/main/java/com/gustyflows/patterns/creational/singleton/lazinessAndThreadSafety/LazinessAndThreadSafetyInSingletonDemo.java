package com.gustyflows.patterns.creational.singleton.lazinessAndThreadSafety;

public class LazinessAndThreadSafetyInSingletonDemo {
    public static void main(String[] args) {
        InnerStaticSingleton.getInstance();
    }
}

/*
sometimes we want a singleton to be initialized only when getInstance() is called,
and so you dont want it initialized in a static block - essentially make singleton
 only when it is needed
 */

class LazySingleton {
    private static LazySingleton instance;

    private LazySingleton() {
        System.out.println("initializing a lazy singleton");
    }

//    public static synchronized LazySingleton getInstance() {
//        if (instance == null) {
//            instance = new LazySingleton();
//        }
//        return instance;
//    }

    /*
    what about race conditions, if two threads invoke getInstance() at the same time.. make the method synchronized
    public static synchronized LazySingleton getInstance()....
    but this will have implications on performance.

    also there is a double-checked locking approach (outdated)
     */

    //double-checked locking
    public LazySingleton getInstance() {
        if (instance == null) {
            synchronized (LazySingleton.class) {
                if (instance == null) {
                    instance = new LazySingleton();
                }
            }
        }
        return instance;
    }
}

//another way to achieve thread safe singleton is inner static singleton:
class InnerStaticSingleton {
    private InnerStaticSingleton() {

    }

    private static class Impl {
        //inner classes can access outer classes constructor..
        //java guarantees that a class is only loaded when its used the first time, so inner class
        //won't be loaded twice and, consequently, the singleton will never get constructed twice
        private static final InnerStaticSingleton INSTANCE = new InnerStaticSingleton();
    }

    public static InnerStaticSingleton getInstance() {
        return Impl.INSTANCE;
    }
}