package henu.sofi.xiaosi.springbootorderconsumer.service;


import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RabbitListener(queues = {"sms.direct.queue"})
public class SmsService {
    @RabbitHandler
    public void receiveSms(String id){
        System.out.println("sms direct 消费了订单信息是：--》" + id);
    }
}
