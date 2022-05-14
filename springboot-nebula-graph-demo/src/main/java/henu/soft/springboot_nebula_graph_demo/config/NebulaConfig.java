package henu.soft.springboot_nebula_graph_demo.config;


import com.vesoft.nebula.client.graph.NebulaPoolConfig;
import com.vesoft.nebula.client.graph.data.HostAddress;
import com.vesoft.nebula.client.graph.net.NebulaPool;
import com.vesoft.nebula.client.graph.net.Session;
import henu.soft.springboot_nebula_graph_demo.constant.NebulaConstant;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

import java.net.UnknownHostException;
import java.util.stream.Collectors;

@Slf4j
@Configuration
public class NebulaConfig {


    /**
     * 连接池
     * @param properties
     * @return
     * @throws UnknownHostException
     */
    @Bean
    public NebulaPool nebulaPool(NebulaProperties properties) throws UnknownHostException {
        NebulaPool pool = new NebulaPool();
        NebulaPoolConfig nebulaPoolConfig = new NebulaPoolConfig();
        nebulaPoolConfig.setMaxConnSize(1000);
        boolean init = pool.init(properties.getAddress().stream().map(d -> new HostAddress(d.getHost(), d.getPort())).collect(Collectors.toList()), nebulaPoolConfig);
        if (!init){
            throw new RuntimeException("NebulaGraph init err !");
        }else {
            log.info("NebulaGraph init Success ！");
        }
        return pool;
    }

    /**
     * 连接会话
     * @param nebulaPool
     * @param properties
     * @return
     */
    @Bean
    @Scope(scopeName = "prototype",proxyMode = ScopedProxyMode.TARGET_CLASS)
    public Session session(NebulaPool nebulaPool, NebulaProperties properties) {
        try {
            Session session = nebulaPool.getSession(properties.getUsername(), properties.getPassword(), properties.isReconnect());
            // 使用配置文件中的图空间
            session.execute(NebulaConstant.USE + properties.getSpace()+ NebulaConstant.SEMICOLON);
            return session;
        } catch (Exception e) {
            log.error("get nebula session err , {} ", e.toString());
        }
        return null;
    }
}
