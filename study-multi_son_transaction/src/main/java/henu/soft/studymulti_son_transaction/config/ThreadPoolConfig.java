package henu.soft.studymulti_son_transaction.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
public class ThreadPoolConfig {

    @Bean
    public static ThreadPoolExecutor getThreadPoolExecutor(){
        return new ThreadPoolExecutor(
                5,
                10,
                3,
                TimeUnit.HOURS,
                new ArrayBlockingQueue<>(100));
    }
}
