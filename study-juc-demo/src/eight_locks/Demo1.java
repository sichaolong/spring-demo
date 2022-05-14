package eight_locks;

import java.util.concurrent.TimeUnit;

public class Demo1 {


    public static void main(String[] args) {

        Phone1 p = new Phone1();

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


class Phone1 {

    public synchronized void sendMessage(){

        System.out.println("发短信");
    }

    public synchronized void call(){
        System.out.println("打电话");
    }
}
