package henu.soft.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ConfigBean {
    // 相当于spring的 applicationContext.xml配置文件，配置Bean

    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }


}
