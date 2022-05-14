package study_cas;

import java.util.concurrent.TimeUnit;

public class Demo3 {

    public static void main(String[] args) {

        MyThread thread = new MyThread("xiao","si");

        new Thread(()->{
            // 线程1 对资源A 上锁，而且想得到 资源B的锁
            synchronized (thread.getA()){
                System.out.println("线程1锁住了A");

                // 延迟，保证 线程2 先拿到 B
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (thread.getB()){

                }
            }
        },"1").start();

        new Thread(()->{
            // 线程2 先对资源B上锁，而且想得到 资源A的锁
            synchronized (thread.getB()){
                System.out.println("线程A锁住了B");
                synchronized (thread.getA()){

                }
            }
        },"1").start();

    }
}

/**
 * 资源类
 */

class MyThread {


    private String A;
    private String B;

    public MyThread(String a, String b) {
        A = a;
        B = b;
    }

    public String getA() {
        return A;
    }

    public String getB() {
        return B;
    }
}


/**
 * 输出
 * 线程1锁住了A
 * 线程A锁住了B 进入死锁
 **/
