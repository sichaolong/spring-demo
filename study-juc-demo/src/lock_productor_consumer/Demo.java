package lock_productor_consumer;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Demo {
    public static void main(String[] args) {

        Data data = new Data();

        // 线程A,进行加1
        new Thread(()->{
            try {
                // 10 次操作
                for (int i = 0; i < 10; i++) {
                    data.increment();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        },"线程A").start();

        // 线程B,进行减1
        new Thread(()->{
            try {

                for (int i = 0; i < 10; i++) {
                    data.decrement();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        },"线程B").start();

        // 线程A,进行加1
        new Thread(()->{
            try {
                // 10 次操作
                for (int i = 0; i < 10; i++) {
                    data.increment();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        },"线程C").start();

        // 线程B,进行减1
        new Thread(()->{
            try {

                for (int i = 0; i < 10; i++) {
                    data.decrement();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        },"线程D").start();

    }
}


/**
 * 待操作的Data资源类，需要解耦
 */

class Data{
    private int num = 0;

    Lock lock = new ReentrantLock();
    Condition condition = lock.newCondition();

    public  void increment()  {

        // 加锁
        lock.lock();

        try {
            while(num != 0){
                // 等待
                condition.await();


            }
            System.out.println(Thread.currentThread().getName() + "=> " + num);
            num ++;
            // 唤醒
            condition.signalAll();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // 解锁
            lock.unlock();
        }


    }

    public  void decrement()  {
        // 加锁
        lock.lock();

        try {
            while(num == 0){
                // 等待
               condition.await();
            }
            System.out.println(Thread.currentThread().getName() + "=> " + num);
            num --;
            // 唤醒
            condition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // 解锁
            lock.unlock();
        }


    }


}
