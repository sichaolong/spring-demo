package study_threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class Demo1 {

    public static void main(String[] args) {
        ExecutorService pool1 = Executors.newSingleThreadExecutor();
        ExecutorService pool2 = Executors.newFixedThreadPool(6);
        ExecutorService pool3 = Executors.newCachedThreadPool();

        try {
            for (int i = 0; i < 10; i++) {
                pool1.execute(()->{
                    System.out.println(Thread.currentThread().getName() );
                });
            }
        } finally {

            // 关闭线程池
            pool1.shutdown();

        }





        try {
            for (int i = 0; i < 10; i++) {
                pool2.execute(()->{
                    System.out.println(Thread.currentThread().getName() );
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool2.shutdown();

        }




        try {
            for (int i = 0; i < 10; i++) {
                pool3.execute(()->{
                    System.out.println(Thread.currentThread().getName() );
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool3.shutdown();
        }

    }
}
