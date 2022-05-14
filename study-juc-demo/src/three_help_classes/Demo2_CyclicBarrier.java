package three_help_classes;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Demo2_CyclicBarrier {

    public static void main(String[] args)  {
        /**
         * 构造函数有两个
         * - public CyclicBarrier(int parties, Runnable barrierAction)  绑定达到技计数目标的任务
         * - public CyclicBarrier(int parties)
         *
         *
         */
        CyclicBarrier cyclicBarrier = new CyclicBarrier(6,()->{

            System.out.println("6个线程全部执行完才输出！！");
        });

        for (int i = 1; i <= 8; i++) {
            final int temp = i;
            new Thread(()->{
                System.out.println(Thread.currentThread().getName() + "===>执行完毕~~");
                try {
                    // 等待设置目标线程数达标，执行绑定的方法
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            },String.valueOf(temp)).start();

        }
    }

}
