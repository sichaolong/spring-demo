package safe_list;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.Vector;

public class Demo3_safe_Vector {
    public static void main(String[] args) {
        List<String> list = new Vector<>();
        /*
        注意 这里的i 从0 开始就不会有 java.util.ConcurrentModificationException（并发修改异常)
        JDK版本是1.8.0_251
         */
        for (int i = 1; i <= 10; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0, 5));
                System.out.println(list);
            }, String.valueOf(i)).start();
        }
    }
}
