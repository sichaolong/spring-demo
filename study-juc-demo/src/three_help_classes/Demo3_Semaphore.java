package three_help_classes;

import java.util.Timer;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Demo3_Semaphore {

    public static void main(String[] args) {
        // 可以理解为只有4个车位
        Semaphore semaphore = new Semaphore(4);

        for (int i = 1; i <= 10; i++) {
            new Thread(()->{

                try {
                    // 占位
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName() + "===>拿到了车位！");
                    // 停车时间
                    TimeUnit.SECONDS.sleep(3);
                    // 离开车位
                    System.out.println(Thread.currentThread().getName() + "====>离开了！");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    // 最后告诉等待的人有1个空位了
                    semaphore.release();
                }





            },String.valueOf(i)).start();
        }
    }
}
