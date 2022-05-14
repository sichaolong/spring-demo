package study_single;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class EnumSingleDemo {

    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        // 懒汉单例
        EnumSingle instance = EnumSingle.getInstance();
        System.out.println(instance); // INSTANCE

        // 测试反射破坏枚举单例模式

        Constructor<EnumSingle> declaredConstructor = EnumSingle.class.getDeclaredConstructor(String.class, int.class);
        declaredConstructor.setAccessible(true);
        EnumSingle instance1 = declaredConstructor.newInstance();
        System.out.println(instance1); //java.lang.IllegalArgumentException: Cannot reflectively create enum objects


    }
}
