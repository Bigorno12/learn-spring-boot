package mu.tutorial.learnspringboot.java21.amber.guard;

import mu.tutorial.learnspringboot.java21.amber.City;
import mu.tutorial.learnspringboot.java21.amber.Department;
import mu.tutorial.learnspringboot.java21.amber.Populated;

import java.util.List;

public class BusinessCodeSwitchPatternMatchingWithGuard {
    public static int populationSwitchPatternMatchingWithGuard(Populated populated) {
        // Guard
        return switch (populated) {
            case City city when city.population() > 0 -> city.population();
            case City city -> city.population();
            case Department department -> 0;
        };
    }

    public static int sum(List<Populated> populateds) {
        return populateds.stream()
                .mapToInt(BusinessCodeSwitchPatternMatchingWithGuard::populationSwitchPatternMatchingWithGuard)
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
