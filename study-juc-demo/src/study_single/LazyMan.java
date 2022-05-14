package study_single;


/**
 * 一般懒汉式
 * 单线程下没问题，但是多线程有可能出现问题
 */
public class LazyMan {



    private static LazyMan lazyMan;

    private LazyMan(){

        System.out.println(Thread.currentThread().getName() + " 线程拿到了实例！");
        /*输出 发现多线程下懒汉式 不能保证一个单例

        1 线程拿到了实例！
        2 线程拿到了实例！
        0 线程拿到了实例！

         */
    }

    public static LazyMan getInstance(){
        // 需要使用单例的情况下 且 实例还没有被创建 才创建单例，
        if(lazyMan == null){
            lazyMan = new LazyMan();
        }
        return lazyMan;
    }


    public static void main(String[] args) {
        // 模拟是个线程获取实例
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                LazyMan.getInstance();

            },String.valueOf(i)).start();
        }

    }

}


