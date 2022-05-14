package study_single;


/**
 * DCL双重锁 懒汉式
 *
 */
public class LazyManDCL {

    // 加上 volatile 关键字 解决创建对象指令重排（非原子性）问题
    private static  volatile LazyManDCL lazyMan;

    private LazyManDCL(){

        System.out.println(Thread.currentThread().getName() + " 线程拿到了实例！");
        /*输出 : 0 线程拿到了实例！
         发现多线程下DCL懒汉式 能保证一个单例


         */
    }

    public static LazyManDCL getInstance(){
        // DCL 双重校验判断

        // 第一次判断，没有实例的时候给类加锁
        if(lazyMan == null){
            synchronized (LazyManDCL.class){

                // 第二次判断，没有实例的时候 获得单例
                if(lazyMan == null){
                    lazyMan = new LazyManDCL();
                }
            }
        }

        return lazyMan;
    }


    public static void main(String[] args) {
        // 模拟10个线程获取实例
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                LazyManDCL.getInstance();

            },String.valueOf(i)).start();
        }

    }

}


