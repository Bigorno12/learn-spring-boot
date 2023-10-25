package mu.tutorial.learnspringboot.java21.amber;

import java.util.List;
import java.util.Objects;

public record Department(String name, List<City> cities) implements Populated {

    public Department {
        Objects.requireNonNull(name);
    }
}
