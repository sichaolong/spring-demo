package study_function;

import java.util.function.Supplier;

public class Demo4_supplier {
    public static void main(String[] args) {


        // 一般方式 生产字符串
        Supplier<String> supplier = new Supplier<String>() {
            @Override
            public String get() {

                System.out.println("生产了===》xiaosi");

                return "xiasoi";
            }
        };

        // 使用Lambda表达式简化

        Supplier<String> supplier1 = ()->{
            System.out.println("生产了===》si");
            return "si";
        };


        supplier.get();// 生产了===》xiaosi
        supplier1.get();// 生产了===》si


    }
}
