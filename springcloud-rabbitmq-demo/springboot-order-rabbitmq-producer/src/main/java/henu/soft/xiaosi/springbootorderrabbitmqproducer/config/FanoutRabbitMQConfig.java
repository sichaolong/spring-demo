package henu.soft.xiaosi.springbootorderrabbitmqproducer.config;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FanoutRabbitMQConfig {

    // 1. 声明注册fanout模式的交换机

    @Bean
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange("fanout_order_exchange",true,false);
    }

    // 2. 声明队列 sms.fanout.queue 、email.fanout.queue 、message.fanout.queue

    @Bean
    public Queue smsQueue(){
        return new Queue("sms.fanout.queue",true);
    }

    @Bean
    public Queue emailQueue(){
        return new Queue("email.fanout.queue",true);
    }

    @Bean
    public Queue messageQueue(){
        return new Queue("message.fanout.queue",true);
    }


    // 3. 完成绑定关系

    @Bean
    public Binding smsBinding(){
        return BindingBuilder.bind(smsQueue()).to(fanoutExchange());
    }

    @Bean
    public Binding emailBinding(){
        return BindingBuilder.bind(emailQueue()).to(fanoutExchange());
    }

    @Bean
    public Binding messageBinding(){
        return BindingBuilder.bind(messageQueue()).to(fanoutExchange());
    }


}
