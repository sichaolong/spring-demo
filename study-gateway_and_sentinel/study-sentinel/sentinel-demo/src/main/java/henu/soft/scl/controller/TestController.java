package henu.soft.scl.controller;

import henu.soft.scl.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Time;

/**
 * @author sichaolong
 * @date 2022/8/27 10:15
 */
@RestController
public class TestController {
    @Autowired
    private TestService service;

    @GetMapping(value = "/hello/{name}")
    public String apiHello(@PathVariable String name) {
        return service.hello(name);
    }
    @GetMapping(value = "/sayHello/{num}")
    public String apiSayHello(@PathVariable String num) {
        return service.sayHello(num);
    }

    @GetMapping(value = "/test")
    public String test() {
        return service.test();
    }
}

/**
 * @SentinelResource 用于定义资源，并提供可选的异常处理和 fallback 配置项。 @SentinelResource 注解包含以下属性：
 *
 * value：资源名称，必需项（不能为空）
 * entryType：entry 类型，可选项（默认为 EntryType.OUT）
 * blockHandler / blockHandlerClass: blockHandler 对应处理 BlockException 的函数名称，可选项。blockHandler 函数访问范围需要是 public，返回类型需要与原方法相匹配，参数类型需要和原方法相匹配并且最后加一个额外的参数，类型为 BlockException。blockHandler 函数默认需要和原方法在同一个类中。若希望使用其他类的函数，则可以指定 blockHandlerClass 为对应的类的 Class 对象，注意对应的函数必需为 static 函数，否则无法解析。
 * fallback / fallbackClass：fallback 函数名称，可选项，用于在抛出异常的时候提供 fallback 处理逻辑。fallback 函数可以针对所有类型的异常（除了 exceptionsToIgnore 里面排除掉的异常类型）进行处理。fallback 函数签名和位置要求：
 * 返回值类型必须与原函数返回值类型一致；
 * 方法参数列表需要和原函数一致，或者可以额外多一个 Throwable 类型的参数用于接收对应的异常。
 * fallback 函数默认需要和原方法在同一个类中。若希望使用其他类的函数，则可以指定 fallbackClass 为对应的类的 Class 对象，注意对应的函数必需为 static 函数，否则无法解析。
 * defaultFallback（since 1.6.0）：默认的 fallback 函数名称，可选项，通常用于通用的 fallback 逻辑（即可以用于很多服务或方法）。默认 fallback 函数可以针对所有类型的异常（除了 exceptionsToIgnore 里面排除掉的异常类型）进行处理。若同时配置了 fallback 和 defaultFallback，则只有 fallback 会生效。defaultFallback 函数签名要求：
 * 返回值类型必须与原函数返回值类型一致；
 * 方法参数列表需要为空，或者可以额外多一个 Throwable 类型的参数用于接收对应的异常。
 * defaultFallback 函数默认需要和原方法在同一个类中。若希望使用其他类的函数，则可以指定 fallbackClass 为对应的类的 Class 对象，注意对应的函数必需为 static 函数，否则无法解析。
 * exceptionsToIgnore（since 1.6.0）：用于指定哪些异常被排除掉，不会计入异常统计中，也不会进入 fallback 逻辑中，而是会原样抛出。
 * 1.8.0 版本开始，defaultFallback 支持在类级别进行配置。
 *
 * 注：1.6.0 之前的版本 fallback 函数只针对降级异常（DegradeException）进行处理，不能针对业务异常进行处理。
 *
 * 特别地，若 blockHandler 和 fallback 都进行了配置，则被限流降级而抛出 BlockException 时只会进入 blockHandler 处理逻辑。若未配置 blockHandler、fallback 和 defaultFallback，则被限流降级时会将 BlockException 直接抛出（若方法本身未定义 throws BlockException 则会被 JVM 包装一层 UndeclaredThrowableException）。
 */
