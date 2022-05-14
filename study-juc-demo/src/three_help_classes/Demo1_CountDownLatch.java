package three_help_classes;

import java.util.concurrent.CountDownLatch;

public class Demo1_CountDownLatch {

    public static void main(String[] args) throws InterruptedException {
        // 创建计数器类
        CountDownLatch countDownLatch = new CountDownLatch(4);

        for (int i = 0; i < 4; i++) {
            new Thread(()-> {
                System.out.println(Thread.currentThread().getName() +"===>执行完毕");
                countDownLatch.countDown();
            },String.valueOf(i)).start();
        }

        // 等待上面线程全部执行完毕，才执行性下面代码
        countDownLatch.await();
        System.out.println("我是最后执行的~");
    }
}
