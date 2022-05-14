package henu.sofi.xiaosi.springbootorderconsumer.service;


import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RabbitListener(queues = {"message.direct.queue"})
public class MessageService {
    @RabbitHandler
    public void receiveMessage(String id){
        System.out.println("message direct 消费了订单信息是：--》" + id);
    }
}
