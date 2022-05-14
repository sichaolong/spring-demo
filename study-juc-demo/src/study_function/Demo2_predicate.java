package study_function;

import java.util.function.Predicate;


/**
 * Predicate 判定性接口
 */
public class Demo2_predicate {

    public static void main(String[] args) {
        // 一般方式
        Predicate<String> predicate = new Predicate<String>() {
            @Override
            public boolean test(String s) {

                if(!s.isEmpty() && s.length() >= 3){
                    return true;
                }
                return false;
            }
        };

        // 使用lambda简化
        Predicate<String> predicate1 = (s)->{
            if((!s.isEmpty()) && (s.length() >= 3)) {
                return true;
            }
            return false;
        };

        System.out.println(predicate.test("xiaosi")); // true
        System.out.println(predicate1.test("xiaosi")); // true
    }
}
