package study_aba;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicStampedReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class AtomicsReferenceDemo2 {

    private static AtomicStampedReference<Integer> num = new AtomicStampedReference<Integer>(1, 1);

    public static void main(String[] args) {

        Lock lock = new ReentrantLock();
        // 线程2 期望的版本号
        int currentStamp = num.getStamp();


        new Thread(() -> {
            System.out.println("==========正常线程1==========");

            // 获得版本号
            System.out.println("正常线程1 拿到的版本号" + num.getStamp());

        }, "正常线程1").start();


        // 休眠2s
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 捣乱线程虽然不影响最终的结果，但是正常流程之间加了一个其他操作
        new Thread(() -> {
            System.out.println("==========捣乱线程==========");


            System.out.println(num.compareAndSet(1, 2, num.getStamp(), num.getStamp() + 1));

            System.out.println("捣乱线程第一次修改值后的 版本号===》" + num.getStamp());

            System.out.println(num.compareAndSet(2, 1, num.getStamp(), num.getStamp() + 1));

            System.out.println("捣乱线程第二次修改值后的 版本号===》" + num.getStamp());


        }, "捣乱线程").start();


        // 休眠2s
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(() -> {

            System.out.println("==========正常线程2==========");
            System.out.println("正常线程2执行前 当前版本号===》" + num.getStamp());
            System.out.println("正常线程2执行前 期望的版本号===》" + currentStamp);

            System.out.println(num.compareAndSet(1000, 3000, currentStamp, num.getStamp() + 1));

            System.out.println("正常线程2执行后 版本号===》" + num.getStamp());


        }, "正常线程2").start();


    }
}


/**输出：
 * ==========正常线程1==========
 * 正常线程1 拿到的版本号1
 * ==========捣乱线程==========
 * true
 * 捣乱线程第一次修改值后的 版本号===》2
 * true
 * 捣乱线程第二次修改值后的 版本号===》3
 * ==========正常线程2==========
 * 正常线程2执行前 当前版本号===》3
 * 正常线程2执行前 期望的版本号===》1
 * false
 * 正常线程2执行后 版本号===》3
 **/
