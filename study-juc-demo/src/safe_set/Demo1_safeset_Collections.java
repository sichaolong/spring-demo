package safe_set;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArraySet;

public class Demo1_safeset_Collections {

    public static void main(String[] args) {
        // Set<String> set = Collections.synchronizedSet(new HashSet<>());
        Set<String> set = new CopyOnWriteArraySet<>();

        Set<String> set1 = new HashSet<>();


        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                set.add(UUID.randomUUID().toString().substring(0, 3));
                System.out.println(set);
            },String.valueOf(i)).start();
        }


    }
}
