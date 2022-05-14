package study_condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Demo {

    public static void main(String[] args) {
        Data data = new Data();

        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                data.A();
            }
        },"1线程").start();

        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                data.B();
            }
        },"2线程").start();

        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                data.C();
            }
        },"3线程").start();

    }
}

class Data {
    private Lock lock = new ReentrantLock();
    private Condition condition1 = lock.newCondition();
    private Condition condition2 = lock.newCondition();
    private Condition condition3 = lock.newCondition();
    private int num = 1;

    public void A(){
        lock.lock();

        try {
            while(num != 1){
                // 等待
                condition1.await();
            }
            System.out.println(Thread.currentThread().getName() + "=> A 方法" );
            num  = 2;

            // 唤醒执行方法B的线程
            condition2.signal();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }


    }
    public void B(){
        lock.lock();

        try {
            while(num != 2){
                // 等待
                condition2.await();
            }
            System.out.println(Thread.currentThread().getName() + "=> B 方法" );
            num  = 3;

            // 唤醒执行方法B的线程
            condition3.signal();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }


    }
    public void C(){
        lock.lock();

        try {
            while(num != 3){
                // 等待
                condition3.await();
            }
            System.out.println(Thread.currentThread().getName() + "=> C 方法" );
            num  = 1;

            // 唤醒执行方法B的线程
            condition1.signal();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }


    }

}
