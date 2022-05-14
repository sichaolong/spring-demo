package study_function;

import java.util.function.Function;


/**
 * Function函数式接口
 */
public class Demo1 {

    public static void main(String[] args) {
        // 一般形式
        Function function = new Function<String,String>() {
            @Override
            public String apply(String str) {
                return str;
            }
        };

        // 使用Lambda 简化
        Function function1 = (str)->{ return str;};
        Function function2 = str-> {return str;};

        System.out.println(function.apply("xiaosi")); // 输出 xiaosi
        System.out.println(function1.apply("xiaosi")); // 输出 xiaosi
        System.out.println(function2.apply("xiaosi")); // 输出 xiaosi

    }
}
