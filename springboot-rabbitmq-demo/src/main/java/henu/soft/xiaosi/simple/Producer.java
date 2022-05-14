package henu.soft.xiaosi.simple;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer {

    public static void main(String[] args) {

        // 所有的中间件技术都是基于TCP\IP协议之上构建新型的协议规范，主不过rabbitmq遵循amqp协议 ip、port


        /**
         * 原生方式使用rabbitmq
         */
        // 1. 创建连接工厂

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        factory.setPort(5672);
        factory.setUsername("admin");
        factory.setPassword("admin");
//        factory.setVirtualHost("/");

        Connection connection = null;
        Channel channel = null;

        try {
            // 2. 创建连接Connection

            connection = factory.newConnection("生产者");


            // 3. 通过连接获取管道channel

            channel = connection.createChannel();


            // 4. 创建交换机，声明队列、绑定关系、路由key、发送消息、接受信息

            String exchangeName = "direct_message_exchange";
            String exchangeType = "direct";

            // 1. 声明交换机
            channel.exchangeDeclare(exchangeName,exchangeType,true);

            // 2. 声明队列


            String queueName1 = "xiaoxiaosi1";
            String queueName2 = "xiaoxiaosi2";
            String queueName3 = "xiaoxiaosi3";
            /**
             * 参数：
             * 队列名称
             * 是否需要持久化
             * 排他性
             * 是否自动删除（最后一个消费者消费完是否删除队列
             * 携带附加参数
             */

            channel.queueDeclare(queueName1,false,false,false,null);
            channel.queueDeclare(queueName2,false,false,false,null);
            channel.queueDeclare(queueName3,false,false,false,null);

            // 3. 准备消息内容
            String msg = "xiaoxiaosiRabbitMQ~~";

            // 4. 绑定交换机和队列
            channel.queueBind("xiaoxiaosi1",exchangeName,"xiaoxiao");
            channel.queueBind("xiaoxiaosi2",exchangeName,"xiaoxiao");
            channel.queueBind("xiaoxiaosi3",exchangeName,"xiaoxiaosi");


            // 5. 发送消息到queue

            /**
             * 参数
             *
             * 交换机
             * 队列、路由key
             * 消息的状态控制
             * 消息主题
             */

            String routeKey = "xiaoxiao";
            channel.basicPublish(exchangeName,routeKey,null,msg.getBytes());

            System.out.println("消息发送成功！");


        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } finally {
            try {
                // 7. 关闭通道
                if(channel != null && channel.isOpen()){
                    channel.close();
                }
                // 8. 关闭连接
                if(connection != null && channel.isOpen()){
                    connection.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }


        }


    }
}
