package com.gustyflows.patterns.creational.singleton.testabilityissues;

import com.google.common.collect.Iterables;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class SingletonTestabilityIssuesDemo {
}


interface Database {
    int getPopulation(String name);
}

class SingletonDatabase implements Database {
    private Dictionary<String, Integer> capitals = new Hashtable<>();
    private static int instacneCount = 0;

    public static int getCount() {
        return instacneCount;
    }


    private SingletonDatabase() {
        instacneCount++;
        System.out.println("Initializing the database");

        try {
            File file = new File(
                    SingletonDatabase.class.getProtectionDomain().getCodeSource().getLocation().getPath()
            );

//            Path fullPath = Paths.get(file.getPath(), "capitals.txt");
            List<String> lines = Files.readAllLines(Paths.get("res/capitals.txt"));
            Iterables.partition(lines, 2)
                    .forEach(kv -> capitals.put(kv.get(0).trim(), Integer.parseInt(kv.get(1))));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static final SingletonDatabase INSTANCE = new SingletonDatabase();

    public static SingletonDatabase getInstance() {
        return INSTANCE;
    }

    @Override
    public int getPopulation(String city) {
        return capitals.get(city);
    }

}

class SingletonRecordFinder {
    public int getTotalPopulation(List<String> names) {
        int result = 0;
        for (String name : names) {
            result += SingletonDatabase.getInstance().getPopulation(name);
        }
        return result;
    }
}

class ConfigurableRecordFinder {
    private Database database;

    //dependency injection - we can either use a framework like spring,
    //or provide dependency by ourselves

    public ConfigurableRecordFinder(Database database) {
        this.database = database;
    }

    public int getTotalPopulation(List<String> names) {
        int result = 0;
        for (String name : names) {
            result += database.getPopulation(name);
        }
        return result;
    }
}

//make a dummy database for use in unit tests
class DummyDatabase implements Database {

    private Dictionary<String, Integer> data = new Hashtable<>();

    public DummyDatabase() {
        data.put("a", 1);
        data.put("b", 2);
        data.put("c", 3);
    }

    @Override
    public int getPopulation(String name) {
        return data.get(name);
    }

}

