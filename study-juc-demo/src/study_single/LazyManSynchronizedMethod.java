package study_single;


/**
 * 加synchronized懒汉式
 *
 * 在方法前面加上synchronized
 */
public class LazyManSynchronizedMethod {



    private static LazyManSynchronizedMethod lazyMan;

    private LazyManSynchronizedMethod(){

        System.out.println(Thread.currentThread().getName() + " 线程拿到了实例！");
        /*输出 发现多线程下懒汉式 不能保证一个单例

        0 线程拿到了实例！

         */
    }

    public static  synchronized LazyManSynchronizedMethod getInstance(){
        // 需要使用单例的情况下 且 实例还没有被创建 才创建单例，
        if(lazyMan == null){
            lazyMan = new LazyManSynchronizedMethod();
        }
        return lazyMan;
    }


    public static void main(String[] args) {
        // 模拟是个线程获取实例
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                LazyManSynchronizedMethod.getInstance();

            },String.valueOf(i)).start();
        }

    }

}


