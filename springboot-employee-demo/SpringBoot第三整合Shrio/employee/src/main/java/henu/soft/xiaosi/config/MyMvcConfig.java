package henu.soft.xiaosi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Locale;

@Configuration
public class MyMvcConfig implements WebMvcConfigurer {
    //自定义MVC跳转器组件
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/index.html").setViewName("index");
        registry.addViewController("/main.html").setViewName("dashboard");
    }

    //自定义国际化组件,装配组件
    @Bean
    public LocaleResolver localeResolver(){
        return new MyLocaleResolver();
    }


    /*
    //自定义拦截器组件，装配组件
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new MyLoginHandlerInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("index.html","/","/user/login","/assert/**","/assert/css/**","/assert/js/**");
    }

     */
}
