package study_volatitle;

import java.util.concurrent.TimeUnit;


/**
 * 演示不可见性
 */
public class JMMDemo1 {
    private static volatile Boolean flag = true;
    public static void main(String[] args) {

        new Thread(()->{
            while(flag){

            }
        },"其他线程").start();

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("主线程修改了flag,但是另外一个线程工作空间的flag仍然是true.");
        flag = false;

    }
}
