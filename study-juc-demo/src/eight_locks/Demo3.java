package eight_locks;

import java.util.concurrent.TimeUnit;

public class Demo3 {


    public static void main(String[] args) {

        Phone3 p = new Phone3();

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
            p.watchMovie();
        }).start();

    }
}


class Phone3 {

    public synchronized void sendMessage(){
        // JUC下的线程延时方法
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("发短信");
    }

    public synchronized void call(){
        System.out.println("打电话");
    }

    public void watchMovie(){
        System.out.println("看电影");
    }
}
