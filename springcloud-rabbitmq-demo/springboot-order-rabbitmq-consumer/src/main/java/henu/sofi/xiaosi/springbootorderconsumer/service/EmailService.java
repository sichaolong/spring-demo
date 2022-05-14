package henu.sofi.xiaosi.springbootorderconsumer.service;


import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Service;

@Service
@RabbitListener(bindings = @QueueBinding(
        value = @Queue(value = "email.topic.queue",durable = "true",autoDelete = "false"),
        exchange = @Exchange(value = "topic_order_exchange",type = ExchangeTypes.TOPIC),
        key = "#.email.#"
))
public class EmailService {


    @RabbitHandler
    public void receiveEmail(String id){
        System.out.println("email topic 消费了订单信息是：--》" + id);
    }
}
