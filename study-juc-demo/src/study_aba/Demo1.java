package study_aba;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Demo1 {

    private static AtomicInteger num = new AtomicInteger(1000);

    public static void main(String[] args) {


       new Thread(()->{
           num.compareAndSet(1000,2000);
           System.out.println(num.get());

       },"正常线程1").start();


       // 休眠2s
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 捣乱线程虽然不影响最终的结果，但是正常流程之间加了一个其他操作
        new Thread(()->{
            num.compareAndSet(2000,1000);
            System.out.println(num.get());

            num.compareAndSet(1000,2000);
            System.out.println(num.get());

        },"捣乱线程").start();


        // 休眠2s
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(()->{
            num.compareAndSet(2000,3000);
            System.out.println(num.get());


        },"正常线程2").start();




    }
}
