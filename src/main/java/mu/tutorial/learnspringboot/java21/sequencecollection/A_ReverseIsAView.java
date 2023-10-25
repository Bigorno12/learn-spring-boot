package mu.tutorial.learnspringboot.java21.sequencecollection;

import java.util.ArrayList;
import java.util.List;

public class A_ReverseIsAView {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>(List.of("alfa", "bravo", "charlie", "delta", "echo"));
        List<String> reversedList = list.reversed();

        System.out.println("list: " + list);
        System.out.println("reversedList: " + reversedList);
        System.out.println("list == reversedList: " + (list == reversedList.reversed()));

        System.out.println();
        System.out.println("list.set(2, \"XYZZY\")");
        list.set(2, "XYZZY"); // replace charlie with XYZZY
        System.out.println("list: " + list);
        System.out.println("reversedList: " + reversedList);

        // Change to the reversed view write through to the backing list
        System.out.println();
        System.out.println("reversedList.set(2, \"PLUGH\")");
        reversedList.set(2, "PLUGH"); // replace XYZZY with PLUGH
        System.out.println("list: " + list);
        System.out.println("reversedList: " + reversedList);
    }
}
