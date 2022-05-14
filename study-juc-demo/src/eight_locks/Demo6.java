package eight_locks;

import java.util.concurrent.TimeUnit;

public class Demo6 {


    public static void main(String[] args) {

        Phone8 one = new Phone8();
        Phone8 two = new Phone8();


        // 发短信
        new Thread(()->{
            one.sendMessage();
        }).start();

        // JUC下的线程延时方法
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 打电话
        new Thread(()->{
            two.call();
        }).start();

    }
}


class Phone6 {

    public synchronized static void sendMessage(){
        // JUC下的线程延时方法
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("发短信");
    }

    public synchronized static void call(){
        System.out.println("打电话");
    }


}
