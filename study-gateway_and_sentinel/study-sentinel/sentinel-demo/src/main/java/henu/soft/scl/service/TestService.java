package henu.soft.scl.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import henu.soft.scl.utils.ExceptionUtil;
import org.springframework.stereotype.Service;

/**
 * @author sichaolong
 * @date 2022/8/27 10:15
 */
@Service
public class TestService {
    @SentinelResource(value = "hello")
    public String hello(String name) {
        return "Hello, " + name;
    }


    // 原函数
    @SentinelResource(value = "sayHello", blockHandler = "exceptionHandler", fallback = "sayHelloFallback")
    public String sayHello(String num) {
        return String.format("Hello at %d", Integer.valueOf(num));
    }

    // Fallback 函数，函数签名与原函数一致或加一个 Throwable 类型的参数.
    public String sayHelloFallback(Integer s) {
        return String.format("sayHelloFallback %d", s);
    }

    // Block 异常处理函数，参数最后多一个 BlockException，其余与原函数一致.
    public String exceptionHandler(Integer s, BlockException ex) {
        // Do some log here.
        ex.printStackTrace();
        return "exceptionHandler, error occurred at " + s;
    }

    // 这里单独演示 blockHandlerClass 的配置.
    // 对应的 `handleException` 函数需要位于 `ExceptionUtil` 类中，并且必须为 public static 函数.
    @SentinelResource(value = "test", blockHandler = "handleException", blockHandlerClass = {ExceptionUtil.class})
    public String test() {
        System.out.println("Test");
        return "sichaolong-test";

    }
}
