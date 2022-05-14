package config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"service.impl"})
@ConditionalOnProperty(prefix = "study",name = "enable",havingValue = "true")
public class HelloServiceAutoConfiguration {
}
