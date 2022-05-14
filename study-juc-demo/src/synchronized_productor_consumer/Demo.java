package synchronized_productor_consumer;

public class Demo {
    public static void main(String[] args) {

        Data data = new Data();

        // 线程A,进行加1
        new Thread(()->{
            try {
                // 10 次操作
                for (int i = 0; i < 10; i++) {
                    data.increment();
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"线程A").start();

        // 线程B,进行减1
        new Thread(()->{
            try {

                for (int i = 0; i < 10; i++) {
                    data.decrement();
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"线程B").start();

        // 线程A,进行加1
        new Thread(()->{
            try {
                // 10 次操作
                for (int i = 0; i < 10; i++) {
                    data.increment();
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"线程C").start();

        // 线程B,进行减1
        new Thread(()->{
            try {

                for (int i = 0; i < 10; i++) {
                    data.decrement();
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"线程D").start();

    }
}


/**
 * 待操作的Data资源类，需要解耦
 */

class Data{
    private int num = 0;

    public synchronized void increment() throws InterruptedException {
        while(num != 0){
            // 等待
            this.wait();
        }
        System.out.println(Thread.currentThread().getName() + "=> " + num);
        num ++;
        this.notifyAll();
    }

    public synchronized void decrement() throws InterruptedException {
        while(num == 0){
            // 等待
            this.wait();
        }
        System.out.println(Thread.currentThread().getName() + "=> " + num);
        num --;

        this.notifyAll();
    }


}
