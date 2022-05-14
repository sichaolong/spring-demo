package study_cas;

import java.util.concurrent.atomic.AtomicInteger;


/*
简单cas举栗
 */
public class Demo1 {

    public static void main(String[] args) {
        AtomicInteger num = new AtomicInteger(100);
        // 参数为 (期望值 ,更新后的值)
        num.compareAndSet(100,200);
        System.out.println(num.compareAndSet(200, 300)); // true 更新成功
        System.out.println(num.compareAndSet(200, 300)); // false 更新失败
        System.out.println(num.get());


    }
}
