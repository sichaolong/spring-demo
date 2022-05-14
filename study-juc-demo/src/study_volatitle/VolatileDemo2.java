package study_volatitle;

public class VolatileDemo2 {
    private static volatile int num = 0;

    public static void main(String[] args) {


        for (int i = 0; i < 10; i++) {

            new Thread(()->{

                for (int i1 = 0; i1 < 1000; i1++) {
                    add();
                }
            }).start();
        }

        // 确保上面的线程执行完，只剩下 main 和 gc 线程
        while(Thread.activeCount() > 2){
            Thread.yield();//当前主线程 暂时停止执行
        }

        System.out.println("正常结果为100，输出结果为===》" + num); // 正常结果为10000，输出结果为===》9991
    }

    public static void add(){
        num++;
    }
}
