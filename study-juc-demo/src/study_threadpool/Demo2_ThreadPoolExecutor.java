package study_threadpool;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Demo2_ThreadPoolExecutor {

    public static void main(String[] args) {


        /**
         * 使用原生的方式创建 线程池
         */
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(2,
                5,
                10,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.DiscardOldestPolicy());

        System.out.println("查看本机核心数：" + Runtime.getRuntime().availableProcessors());




        try {
            /*

            核心线程 2
            最大线程 5
            阻塞队列 3

            ====测试1====
            执行任务 3
            输出:
                pool-1-thread-1
                pool-1-thread-2
                pool-1-thread-1,只开启了核心线程1,2

            ====测试2====
            执行任务 5
            输出:
                pool-1-thread-1
                pool-1-thread-2
                pool-1-thread-1
                pool-1-thread-1
                pool-1-thread-2，只开启了核心线程1,2

             ====测试3====
             执行任务 6
             输出：
                pool-1-thread-2
                pool-1-thread-3
                pool-1-thread-2
                pool-1-thread-1
                pool-1-thread-2
                pool-1-thread-3，除了开启了核心线程，还另外开了一个线程3


             ====测试4（拒绝策略是AbortPolicy)====
             执行任务 9
             输出：
                pool-1-thread-3
                pool-1-thread-1
                pool-1-thread-3
                pool-1-thread-2
                pool-1-thread-3
                pool-1-thread-4
                pool-1-thread-1
                pool-1-thread-5，超出最大线程数8，（阻塞队列 + 最大线程数),共 8 个输出，最后一个任务被拒绝，抛出异常.RejectedExecutionException

             */
            /*
                 ====测试5（拒绝策略是CallerRunsPolicy)====
                执行任务 9
                输出：
                pool-1-thread-1
                pool-1-thread-3
                pool-1-thread-2
                pool-1-thread-1
                pool-1-thread-3
                main
                pool-1-thread-4
                pool-1-thread-2
                pool-1-thread-5，超出最大线程数8，（阻塞队列 + 最大线程数),由main线程执行

                */

            /*
                 ====测试6（拒绝策略是DiscardPolicy)====
                执行任务 9
                输出：
               pool-1-thread-1
                pool-1-thread-3
                pool-1-thread-4
                pool-1-thread-2
                pool-1-thread-5
                pool-1-thread-4
                pool-1-thread-3
                pool-1-thread-1超出最大线程数8，（阻塞队列 + 最大线程数),不会抛出异常，丢掉任务

                */

            /*
                 ====测试6（拒绝策略是DiscardOldestPolicy)====
                执行任务 9
                输出：
               pool-1-thread-1
                pool-1-thread-3
                pool-1-thread-2
                pool-1-thread-4
                pool-1-thread-1
                pool-1-thread-5
                pool-1-thread-3
                pool-1-thread-2超出最大线程数8，（阻塞队列 + 最大线程数),不会抛出异常，会和最早的一个线程竞争，但是竞争不一定成功

                */
            for (int i = 1; i <= 9; i++) {
                threadPool.execute(()->{
                    System.out.println(Thread.currentThread().getName() );
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }

    }

}
