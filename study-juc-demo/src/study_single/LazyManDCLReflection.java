package study_single;


import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * DCL双重锁
 *
 * 使用 红绿灯标志位 防止单例模式被 反射破坏
 */
public class LazyManDCLReflection {

    // 设置标志，阻止反射绕过
    private static  Boolean xiaosi = false;

    // 加上 volatile 关键字 解决创建对象指令重排（非原子性）问题
    private static volatile LazyManDCLReflection lazyMan;

    private LazyManDCLReflection() {

        System.out.println(Thread.currentThread().getName() + " 线程拿到了实例！");
        /*输出 : 0 线程拿到了实例！
         发现多线程下DCL懒汉式 能保证一个单例
         */

        // 再加一层判断，防止通过反射绕过 单例模式

        if (xiaosi == false) {
            xiaosi = true;
        } else {
            throw new RuntimeException("使用 红绿灯标志 防止反射绕过单例模式！");
        }


    }

    public static LazyManDCLReflection getInstance() {
        // DCL 双重校验判断
        // 第一次判断，没有实例的时候给类加锁
        if (lazyMan == null) {
            synchronized (LazyManDCLReflection.class) {

                // 第二次判断，没有实例的时候 获得单例
                if (lazyMan == null) {
                    lazyMan = new LazyManDCLReflection();
                }
            }
        }


        return lazyMan;
    }


    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {


        // 单例模式获取
        LazyManDCLReflection instance = LazyManDCLReflection.getInstance();
        System.out.println(instance);

        // 反射获取

        // 获取无参构造器
        Constructor<LazyManDCLReflection> declaredConstructor = LazyManDCLReflection.class.getDeclaredConstructor(null);
        // 忽略私有设置
        declaredConstructor.setAccessible(true);
        // 创建实例
        LazyManDCLReflection instance2 = declaredConstructor.newInstance();
        System.out.println(instance2);

        /*
        通过反射可以绕过 单例模式

        main 线程拿到了实例！
        study_single.LazyManDCLReflection@1b6d3586
        main 线程拿到了实例！
        study_single.LazyManDCLReflection@4554617c
         */


    }

}


