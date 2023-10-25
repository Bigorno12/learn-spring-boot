package mu.tutorial.learnspringboot.amber;

import java.util.Arrays;
import java.util.Objects;

public record Department(String name, City... cities) implements Populated {

    public Department {
        Objects.requireNonNull(name);
        cities = Arrays.copyOf(cities, cities.length);
    }

    public City[] cities() {
        return Arrays.copyOf(cities, cities.length);
    }
}
