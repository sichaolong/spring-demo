package henu.soft.study_nebula_graph_demo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;


@Data
@Configuration
@ConfigurationProperties(prefix = "nebula")
public class NebulaProperties {
    private List<NebulaAddress> address;
    private String username;
    private String password;
    private boolean reconnect;
    private String space;
}
@Data
class NebulaAddress {
    private String host;
    private Integer port;
}
