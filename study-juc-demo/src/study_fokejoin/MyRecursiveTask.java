package study_fokejoin;

import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;


/**
 * 计算 1 - 10_0000_0000的累和
 * 使用分支合并
 */
public class MyRecursiveTask extends RecursiveTask<Long> {

    private Long start = 1L;
    private Long end;

    // 临界值,判断选择分支合并 还是 一般方法
    private Long temp = 10000L;

    long sum = 0L;

    public MyRecursiveTask(Long start, Long end) {
        this.start = start;
        this.end = end;
    }

    // 分支合并计算方法
    @Override
    protected Long compute() {
        // 一般方法
        if((end - start) < temp){

            for (long i = start; i <= end; i++) {
                sum += i;
            }
            return sum;
        } // ForkJoin方法
        else{

            long mid = (start + end) /2;


            // 拆分任务，将任务压入线程队列

            MyRecursiveTask task1 = new MyRecursiveTask(start,mid);
            task1.fork();

            MyRecursiveTask task2 = new MyRecursiveTask(mid+1,end);
            task2.fork();


            // 合并子任务结果
            long sum = task1.join() + task2.join();
            return sum;
        }

    }
}
