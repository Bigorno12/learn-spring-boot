package mu.tutorial.learnspringboot.java21.sequencecollection;

import java.util.List;

public class B_StreamListInReverse {
    static List<String> LIST = List.of("alfa", "bravo", "charlie", "delta", "echo");

    static String newStreamListInReverse() {
        return LIST.reversed()
                .stream()
                .map(String::toUpperCase)
                .filter(s -> s.contains("A"))
                .findFirst()
                .orElse("NONE");
    }

    public static void main(String[] args) {
        System.out.println(newStreamListInReverse());
    }
}
