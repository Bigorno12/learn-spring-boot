package mu.tutorial.learnspringboot.java21.amber;

import java.util.Objects;

public record City(String name, int population) implements Populated {

    public City {
        Objects.requireNonNull(name);
        if (population < 0) {
            throw new IllegalArgumentException("Population cannot be negative");
        }
    }
}
