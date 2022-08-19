package henu.soft.scl.router;



/**
 * @author sichaolong
 * @date 2022/8/19 15:30
 */


import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.gateway.support.ipresolver.RemoteAddressResolver;
import org.springframework.cloud.gateway.support.ipresolver.XForwardedRemoteAddressResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置路由有两种方式：1、通过配置文件。2、通过配置类(测试暂时不能使用）
 * 这里通过配置类配置路由
 */
@Configuration
public class MyRouter {
    @Bean
    public RouteLocator customerRouteLocator(RouteLocatorBuilder routeLocatorBuilder) {

        return routeLocatorBuilder.routes()
                .route("test-router2", r ->
                        r.path("/test2").uri("http://www.baidu.com"))
                .route("test-router3", r ->
                        r.path("/test3").uri("http://localhost:8080/test/hello"))
                .build();



        // 第一个参数是路由的唯一id
        // http://localhost:9527/guonei  =>  http://news.baidu.com/guonei

    }
}
