package mu.tutorial.learnspringboot.java21.amber.unpatternmatching;

import mu.tutorial.learnspringboot.java21.amber.City;
import mu.tutorial.learnspringboot.java21.amber.Department;
import mu.tutorial.learnspringboot.java21.amber.Populated;

import java.util.Arrays;
import java.util.List;

public class BusinessCodeUnNamePatternMatching {
    public static int populationUnNamePatternMatching(Populated populated) {
        // Preview feature
        return switch (populated) {
            case City(var x , int population) -> population;
            case Department(String name, List<City> cities) -> {
                System.out.println("Name: " + name);
                // Cannot use return because of ambiguity
                // Existing the switch not the method
                yield cities.stream()
                        .mapToInt(BusinessCodeUnNamePatternMatching::populationUnNamePatternMatching)
                        .sum();
            }
        };
    }

    public static int sum(List<Populated> populateds) {
        return populateds.stream()
                .mapToInt(BusinessCodeUnNamePatternMatching::populationUnNamePatternMatching)
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
