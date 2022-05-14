package study_future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class Demo1 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        test2();

    }

    // 1. 无返回值的 异步
    static void  test1() throws ExecutionException, InterruptedException {
        CompletableFuture<Void>  completableFuture =  CompletableFuture.runAsync(()->{
            try {
                TimeUnit.SECONDS.sleep(3);
                System.out.println("异步任务执行了！！");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
            }
        });

        System.out.println("xiaosi1");
        completableFuture.get();


    }

    // 2. 有返回值的 异步
    static void  test2() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer>  completableFuture =  CompletableFuture.supplyAsync(()->{
            int num = 10 / 0;
            try {


                TimeUnit.SECONDS.sleep(3);
                return 1;


            } catch (Exception e) {
                e.printStackTrace();
                return -1;
            }
        });


        System.out.println("xiaosi2");

        completableFuture.whenCompleteAsync((t,u)->{
            System.out.println(t+"=====> " + t); // 成功的时候返回值
            System.out.println(u+"=====> " + u); // 失败的信息
            System.out.println("有返回值的异步执行了！");

        }).exceptionally((e)->{ // 异常捕获
            e.printStackTrace();
            return 404;
        }).get();



    }
}
