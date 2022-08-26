package henu.soft.scl.ratelimit;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author sichaolong
 * @date 2022/8/26 20:27
 */

/**
 * name : MyRequestRateLimit.java
 * creator : sichaolong
 * date : 2022/8/26 20:28
 * descript : gateway限流，根据指定的request参数限流
**/

@Configuration
public class MyRequestRateLimit implements KeyResolver {
    @Override
    public Mono<String> resolve(ServerWebExchange exchange) {
        // 根据请求url限流
        // return Mono.just(exchange.getRequest().getURI().getPath());

        // 根据远程客户端地址限流
        return Mono.just(exchange.getRequest().getRemoteAddress().getAddress().getHostAddress());

    }
}
