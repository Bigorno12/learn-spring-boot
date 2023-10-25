package mu.tutorial.learnspringboot.java21.amber.instanceofpatternmatching;

import mu.tutorial.learnspringboot.java21.amber.City;
import mu.tutorial.learnspringboot.java21.amber.Department;
import mu.tutorial.learnspringboot.java21.amber.Populated;

import java.util.List;

public class BusinessCodeInstanceOfPatternMatching {
    public static int populationInstanceOfPatternMatching(Populated populated) {
        //Instance of Pattern Matching
        if (populated instanceof City city && city.population() > 1_000_000) {
            return city.population();
        } else if (populated instanceof Department department) {
            return 0;
        }
        throw new AssertionError();
    }

    public static int sum(List<Populated> populateds) {
        return populateds.stream()
                .mapToInt(BusinessCodeInstanceOfPatternMatching::populationInstanceOfPatternMatching)
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
