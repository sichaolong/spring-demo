package henu.soft.scl.filter;

import com.alibaba.cloud.commons.lang.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.List;

/**
 * @author sichaolong
 * @date 2022/8/19 17:00
 */

/**
 * name : MyLoginGlobalFilter.java
 * creator : sichaolong
 * date : 2022/8/19 17:01
 * descript : gateway做登录验证
**/

@Component
@Slf4j
public class MyLoginGlobalFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        log.info("come in MyLogGatewayFilter: " + new Date());
//        String name = exchange.getRequest().getQueryParams().getFirst("name");
        String token = exchange.getRequest().getHeaders().getFirst("token");

        log.info("token is :{}",token );
        if (StringUtils.isBlank(token)) {
            log.info("用户未登录!");
            exchange.getResponse().setStatusCode(HttpStatus.NOT_ACCEPTABLE);
            return exchange.getResponse().setComplete();
        }
        // 继续下一个filter
        return chain.filter(exchange);
    }


    @Override
    public int getOrder() {
        return 0;
    }
}
