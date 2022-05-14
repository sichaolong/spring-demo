package study_synchronionousqueue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

public class Demo1 {

    public static void main(String[] args) {

        BlockingQueue<String> queue = new SynchronousQueue<>();


        // 模拟一个线程 像同步队列插入三个元素

        new Thread(()->{
            try {
                System.out.println(Thread.currentThread().getName() + " 线程入队了 === 1");
                queue.put("1");

                System.out.println(Thread.currentThread().getName() + " 线程入队了 === 2");
                queue.put("2");

                System.out.println(Thread.currentThread().getName() + " 线程入队了 === 3");
                queue.put("3");


            } catch (InterruptedException e) {
                e.printStackTrace();
            }




        },"T1").start();



        // 模拟一个相乘 取出元素

        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(3);
                System.out.println(Thread.currentThread().getName() + " 线程出队了 === >" + queue.take() );

                TimeUnit.SECONDS.sleep(3);
                System.out.println(Thread.currentThread().getName() + " 线程出队了 === >" + queue.take());

                TimeUnit.SECONDS.sleep(3);
                System.out.println(Thread.currentThread().getName() + " 线程出队了 === >" + queue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }





        },"T2").start();
    }
}
