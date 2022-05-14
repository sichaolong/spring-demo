package study_fokejoin;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.stream.LongStream;

public class Test {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 使用FokrJoin计算
        MyRecursiveTask task  = new MyRecursiveTask(1L,10_0000_0000L);
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinTask<Long> res = forkJoinPool.submit(task);
        System.out.println(res.get());

        // 使用并行Stream流
        System.out.println(LongStream.rangeClosed(1L, 10_0000_0000L).parallel().reduce(0, Long::sum));
    }
}
