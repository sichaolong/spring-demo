package kindslocks;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Demo1 {


    public static void main(String[] args) {

        Phone1 p = new Phone1();

        // 发短信
        new Thread(()->{
            System.out.println("线程1开始执行");
            // 获得一个锁，内部执行 调用 call()另外一个加锁的方法，因此内部锁自动获取
            p.sendMessage();

        },"线程1").start();

        // JUC下的线程延时方法
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        // 打电话
        new Thread(()->{
            System.out.println("线程2开始执行");
            p.call();
        },"线程2").start();


    }
}


class Phone1 {

    public  void sendMessage(){

        Lock lock = new ReentrantLock();
        lock.lock();

        try {
            System.out.println("发短信");
            // 继续获得内部的锁,又是一对新的 lock 锁
            call();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

    public  void call(){

        Lock lock = new ReentrantLock();
        lock.lock();


        try {
            System.out.println("打电话");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}


/**
 * 输出：
 * 线程1开始执行
 * 发短信
 * 打电话
 * 线程2开始执行
 * 打电话
 **/
