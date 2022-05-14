package callable;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Demo1_Callable {
    public static void main(String[] args) throws InterruptedException, ExecutionException {

        /**
         * 开启线程需要 中间类 FutureTask 类
         * 该类的·构造函数 可以 将 Callable 实例绑定，并且 该类实现 Runnable 接口
         */

        MyThread mt = new MyThread();
        for (int i = 0; i < 10; i++) {
            FutureTask target = new FutureTask(mt);
            new Thread(target).start();
            // 返回值
            System.out.println(target.get()); // 这个get方法可能会产生阻塞
        }



    }
}

/**
 * 未解耦，资源类实现Callable接口
 */

class MyThread implements Callable<String> {

    Lock lock = new ReentrantLock();



    @Override
    public String call() {
        //lock.lock();


        try {
            System.out.println(Thread.currentThread().getName() + "====> test" );

            return "带返回值的Callable接口";
            // 唤醒
            //Condition condition = lock.newCondition();
            //condition.signal();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            //lock.unlock();

        }

    }
}
