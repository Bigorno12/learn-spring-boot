package mu.tutorial.learnspringboot.java21.amber.recordpattern;

import mu.tutorial.learnspringboot.java21.amber.City;
import mu.tutorial.learnspringboot.java21.amber.Department;
import mu.tutorial.learnspringboot.java21.amber.Populated;

import java.util.Arrays;
import java.util.List;

public class BusinessCodeRecordPattern {
    public static int populationRecordPattern(Populated populated) {
        // Deconstruction of Record
        // Only works on record for now but will work on regular classes in the future
        return switch (populated) {
            case City(var name, var population) -> population;
            case Department(String name, List<City> cities) -> {
                System.out.println("Name: " + name);
                // Cannot use return because of ambiguity
                // Existing the switch not the method
                yield cities.stream().mapToInt(BusinessCodeRecordPattern::populationRecordPattern).sum();
            }
        };
    }

    public static int sum(List<Populated> populateds) {
        return populateds.stream()
                .mapToInt(BusinessCodeRecordPattern::populationRecordPattern)
                .sum();
    }

    public static void main(String[] args) {
        var paris = new City("Paris", 2_000_000);
        var seine = new Department("La Seine", List.of(paris));
        var london = new City("London", 8_000_000);
        List<Populated> populateds = List.of(seine, london, paris);

        System.out.println(sum(populateds));
    }
}
