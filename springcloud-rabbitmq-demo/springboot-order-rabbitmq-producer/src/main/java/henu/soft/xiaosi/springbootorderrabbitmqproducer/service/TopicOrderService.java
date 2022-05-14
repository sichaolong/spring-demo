package henu.soft.xiaosi.springbootorderrabbitmqproducer.service;


import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TopicOrderService {

    @Autowired
    private RabbitTemplate rabbitTemplate;


    /**
     * 模拟用户下单
     * @param userId
     * @param productId
     * @param num
     */

    public void makeOrder(String userId,String productId,int num, String routingKey){

        // 1. 根据商品订单id查询库存是否充足

        // 2. 保存订单
        String orderId = UUID.randomUUID().toString();

        System.out.println("订单生成成功" + orderId);


        // 3. 通过消息队列完成消息的分发

        // 除了使用配置类声明交换机、队列，还可以在consumer 通过注解的方式来绑定 交换机 和 队列
        String exchangeName = "topic_order_exchange";


        /**
         * 参数
         * 交换机
         * 路由key、queue队列名称
         * 消息内容
         */

        // 关于交换机的生命、队列的声明、绑定需要写一个配置类
        rabbitTemplate.convertAndSend(exchangeName,routingKey,orderId);

    }


}
