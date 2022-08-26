package henu.soft.scl.router;



/**
 * @author sichaolong
 * @date 2022/8/19 15:30
 */

import static org.springframework.cloud.gateway.support.RouteMetadataUtils.CONNECT_TIMEOUT_ATTR;
import static org.springframework.cloud.gateway.support.RouteMetadataUtils.RESPONSE_TIMEOUT_ATTR;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.gateway.support.ipresolver.RemoteAddressResolver;
import org.springframework.cloud.gateway.support.ipresolver.XForwardedRemoteAddressResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置路由有两种方式：1、通过配置文件。2、通过配置类(测试test_router2、test_router3暂时不能使用）
 * 这里通过配置类配置路由
 */
@Configuration
public class MyRouter {
    @Bean
    public RouteLocator customerRouteLocator(RouteLocatorBuilder routeLocatorBuilder) {

        return routeLocatorBuilder.routes()
                .route("test_router2", r ->
                        r.path("/test2").uri("http://www.baidu.com"))
                .route("test_router3", r ->
                        r.path("/test3").uri("http://localhost:80/test/hello"))
                .route(
                        // 第一个参数是路由的唯一身份
                        "path_route_lb",
                        // 第二个参数是个lambda实现，
                        // 设置了配套条件是按照请求路径匹配，以及转发地址，
                        // 注意lb://表示这是个服务名，要从
                        r -> r.path("/lbtest/**").uri("lb://provider-demo")
                )
//                .route("test1", r -> {
//                    return r.host("*.somehost.org").and().path("/somepath")
//                            .filters(f -> f.addRequestHeader("header1", "header-value-1"))
//                            .uri("http://someuri")
//                            .metadata(RESPONSE_TIMEOUT_ATTR, 200)
//                            .metadata(CONNECT_TIMEOUT_ATTR, 200);
//                })
                .build();





    }
}
