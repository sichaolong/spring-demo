package safe_map;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class Demo1_safe_ConcurrentHashMap {

    public static void main(String[] args) {
        Map<String,String> map = new ConcurrentHashMap<>();


        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                map.put(Thread.currentThread().getName(), UUID.randomUUID().toString().substring(0,3));
                System.out.println(map);
            },String.valueOf(i)).start();
        }
    }
}
