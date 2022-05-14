package study_function;

import java.util.function.Consumer;


/**
 * Consumer 消费者接口
 */
public class Demo3_consumer {
    public static void main(String[] args) {
        // 一般方式生产一个字符串
        Consumer<String> consumer = new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println("消费了====》 " + s);

            }
        };

        // 使用Lambda表达式简化
        Consumer<String> consumer1 = (str)->{
            System.out.println("消费了===》" + str);
        };

        consumer.accept("xiaosi"); // 消费了====》 xiaosi
        consumer1.accept("si");// 消费了====》 si
    }
}
