package mu.tutorial.learnspringboot.java21.amber.switchpatternmatching;

import mu.tutorial.learnspringboot.java21.amber.City;
import mu.tutorial.learnspringboot.java21.amber.Department;
import mu.tutorial.learnspringboot.java21.amber.Populated;

import java.util.List;

public class BusinessCodeSwitchPatternMatching {
    public static int populationSwitchPatternMatching(Populated populated) {
        // Switch Expression Pattern Matching with Sealed Class
        // Check Exhaustiveness
        // Can Now have a null case
        return switch (populated) {
            case City city -> city.population();
            case Department department -> 0;
            case null -> throw new NullPointerException("dkmkldf");
        };
    }

    public static int sum(List<Populated> populateds) {
        return populateds.stream()
                .mapToInt(BusinessCodeSwitchPatternMatching::populationSwitchPatternMatching)
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
