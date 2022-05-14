package study_cas;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class Demo2 {

    public static void main(String[] args) {
        Spinlock lock = new Spinlock();

        new Thread(()->{
            // 加锁
            lock.myLock();

            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                // 解锁
                lock.myUnLock();

            }
        },"T1").start();

        // 保证线程T1先拿到锁

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(()->{
            // 加锁
            lock.myLock();

            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                // 解锁
                lock.myUnLock();

            }

        },"T2").start();
    }




}

/**
 * 自定义自旋锁
 */

class Spinlock {
    AtomicReference<Thread> atomicReference = new AtomicReference<>();

    // 加锁，之后一直到自旋锁的循环中，不会释放锁，直到 手动释放锁
    public void myLock() {
        Thread thread = Thread.currentThread();
        System.out.println(Thread.currentThread().getName() + "==== > myLock");
        while (!atomicReference.compareAndSet(null, thread)) {

        }

    }


    // 解锁

    public void myUnLock() {
        Thread thread = Thread.currentThread();
        System.out.println(Thread.currentThread().getName() + "==== > myUnLock");
        atomicReference.compareAndSet(thread,null);



    }


}

/**
 * T1==== > myLock
 * T2==== > myLock
 * T1==== > myUnLock
 * T2==== > myUnLock
 */
