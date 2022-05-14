package eight_locks;

import java.util.concurrent.TimeUnit;

public class Demo2 {


    public static void main(String[] args) {

        Phone8 p = new Phone8();

        // 发短信
        new Thread(()->{
            p.sendMessage();
        }).start();

        // JUC下的线程延时方法
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 打电话
        new Thread(()->{
            p.call();
        }).start();

    }
}


class Phone2 {

    public synchronized void sendMessage(){
        // JUC下的线程延时方法
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("发短信");
    }

    public synchronized void call(){
        System.out.println("打电话");
    }


}
