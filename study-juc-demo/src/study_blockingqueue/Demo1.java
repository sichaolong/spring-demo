package study_blockingqueue;

import java.util.Collection;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class Demo1 {
    public static void main(String[] args) throws InterruptedException {
        //test1();
        //test2();
        //test3();
        test4();

    }

    /**
     * add、remove 不合理情况 抛异常
     */

    public static void test1(){
        ArrayBlockingQueue queue = new ArrayBlockingQueue(3);

        queue.add("xiao");
        queue.add("si");
        queue.add("xiaosi");

        System.out.println(queue);

        //queue.add("队列已满，入队会抛异常"); IllegalStateException


        System.out.println(queue.remove());
        // 获取队首元素
        System.out.println(queue.element());
        System.out.println(queue.remove());
        System.out.println(queue.remove());

        // System.out.println(queue.element()); 队列为空的时候，获取队首元素抛异常 NoSuchElementException


        System.out.println(queue);

        //System.out.println(queue.remove()); 队列已空，再次出队会抛异常 NoSuchElementException
    }

    /**
     * offer、poll 入队失败返回false,出队失败返回 null
     *
     */
    public static void test2(){
        ArrayBlockingQueue queue = new ArrayBlockingQueue(3);

        queue.offer("xiao");
        queue.offer("si");
        queue.offer("xiaosi");


        System.out.println(queue.offer("队列已满，入队会返回false"));


        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll());




        System.out.println(queue.poll()); // 队列为空，出队返回null
    }

    /**
     * put 、take 阻塞等待
     *
     */
    public static void test3() throws InterruptedException {
        ArrayBlockingQueue queue = new ArrayBlockingQueue(3);

        queue.put("xiao");
        queue.put("si");
        queue.put("xiaosi");


        queue.put("队列已满，入队会一直阻塞等待");


        System.out.println(queue.take());
        System.out.println(queue.take());
        System.out.println(queue.take());




        System.out.println(queue.take()); // 队列为空，出队一直阻塞等待
    }

    /**
     * put 、take 超时等待,超时自动结束
     *
     */
    public static void test4() throws InterruptedException {
        ArrayBlockingQueue queue = new ArrayBlockingQueue(3);

        queue.put("xiao");
        queue.put("si");
        queue.put("xiaosi");



        queue.offer("队列已满，入队会超时等待",3, TimeUnit.SECONDS);


        System.out.println(queue.take());
        System.out.println(queue.take());
        System.out.println(queue.take());




        System.out.println(queue.poll(1,TimeUnit.SECONDS)); // 队列为空，超时等待，过时间自动结束
    }
}
