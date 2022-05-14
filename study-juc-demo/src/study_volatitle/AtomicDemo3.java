package study_volatitle;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicDemo3 {

    // 创建 可原子更新的int 值对象
    private static volatile AtomicInteger num = new AtomicInteger(0);

    public static void main(String[] args) {


        for (int i = 0; i < 10; i++) {

            new Thread(()->{

                for (int i1 = 0; i1 < 1000; i1++) {
                    // 该对象有方法+1
                    num.getAndIncrement();
                }
            }).start();
        }

        // 确保上面的线程执行完，只剩下 main 和 gc 线程
        while(Thread.activeCount() > 2){
            Thread.yield();//当前主线程 暂时停止执行
        }

        System.out.println("正常结果为10000，输出结果为===》" + num); // 正常结果为10000，输出结果为===》10000
    }


}
