package com.gustyflows.patterns.creational.singleton.testabilityissues;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class SingletonTestabilityTest {

    //bad idea.. to base unit test on the state of the database, we want dummy data instead
    //atm this is an integration test, not a unit test
    @Test
    public void singletonTotalPopulationTest() {
        final SingletonRecordFinder singletonRecordFinder = new SingletonRecordFinder();

        List<String> names = List.of("Tokyo", "New York");
        final int totalPopulation = singletonRecordFinder.getTotalPopulation(names);
        assertEquals(33200000 + 17800000, totalPopulation);
    }

    //proper unit test with dummy db
    @Test
    public void dependentPopulationTest() {
        DummyDatabase dummyDatabase = new DummyDatabase();
        final ConfigurableRecordFinder configurableRecordFinder = new ConfigurableRecordFinder(dummyDatabase);
        final int totalPopulation = configurableRecordFinder.getTotalPopulation(List.of("a", "b", "c"));
        assertEquals(6, totalPopulation);

    }
}
