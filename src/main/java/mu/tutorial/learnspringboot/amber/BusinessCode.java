package mu.tutorial.learnspringboot.amber;

import java.util.Arrays;
import java.util.List;

public class BusinessCode {

    public static int populationInstanceOfPatternMatching(Populated populated) {
        //Instance of Pattern Matching
        if (populated instanceof City city && city.population() > 1_000_000) {
            return city.population();
        } else if (populated instanceof Department department) {
            return 0;
        }
        throw new AssertionError();
    }

    public static int populationSwitchPatternMatching(Populated populated) {
        // Switch Expression Pattern Matching with Sealed Class
        // Check Exhaustiveness
        return switch (populated) {
            case City city -> city.population();
            case Department department -> 0;
        };
    }

    public static int populationSwitchPatternMatchingWithGuard(Populated populated) {
        // Guard
        return switch (populated) {
            case City city when city.population() > 1_000_000 -> city.population();
            case City city -> city.population();
            case Department department -> 0;
        };
    }

    public static int populationRecordPattern(Populated populated) {
        // Deconstruction of Record
        // Only works on record for now but will work on regular classes in the future
        return switch (populated) {
            case City(var name, var population) -> population;
            case Department(String name, City[] cities) -> {
                System.out.println("Name: " + name);
                // Cannot use return because of ambiguity
                // Existing the switch not the method
                yield Arrays.stream(cities).mapToInt(BusinessCode::populationRecordPattern).sum();
            }
        };
    }

    public static int populationUnNamePatternMatching(Populated populated) {
        // Preview feature
        return switch (populated) {
            case City(_, int population) -> population;
            case Department(String name, City[] cities) -> {
                System.out.println("Name: " + name);
                // Cannot use return because of ambiguity
                // Existing the switch not the method
                yield Arrays.stream(cities).mapToInt(BusinessCode::populationRecordPattern).sum();
            }
        };
    }

    public static int sum(List<Populated> populateds) {
        return populateds.stream()
                .mapToInt(BusinessCode::populationInstanceOfPatternMatching)
                .sum();
    }

    public static void main(String[] args) {
        var paris = new City("Paris", 2_000_000);
        var seine = new Department("La Seine", paris);
        var london = new City("London", 8_000_000);
        List<Populated> populateds = List.of(seine, london, paris);

        System.out.println(sum(populateds));
    }
}
