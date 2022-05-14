package henu.soft.xiaosi.routing;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer {

    public static void main(String[] args) {

        // 所有的中间件技术都是基于TCP\IP协议之上构建新型的协议规范，主不过rabbitmq遵循amqp协议 ip、port


        /**
         * 原生方式fanout使用rabbitmq
         */
        // 1. 创建连接工厂

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        factory.setPort(5672);
        factory.setUsername("admin");
        factory.setPassword("admin");
        factory.setVirtualHost("/xiaosi");

        Connection connection = null;
        Channel channel = null;

        try {
            // 2. 创建连接Connection

            connection = factory.newConnection("生产者");


            // 3. 通过连接获取管道channel

            channel = connection.createChannel();


            // 4. 创建交换机，声明队列、绑定关系、路由key、发送消息、接受信息

            // 创建交换机,因为图形化界面已经创建过了，这里不需要再次创建

            String exchangeName = "my-fanout";

            String type = "fanout";

            // RouteKey
            String routeKey = "";



            // 5. 准备消息内容
            String msg = "fanout 下的 xiaosi 的 RabbitMQ~~";


            // 6. 发送消息到queue

            /**
             * 参数
             *
             * fanout交换机
             * 路由key
             * 消息的状态控制
             * 消息主题
             */

            channel.basicPublish(exchangeName, routeKey, null, msg.getBytes());

            System.out.println("消息发送成功！");


        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } finally {
            try {
                // 7. 关闭通道
                if (channel != null && channel.isOpen()) {
                    channel.close();
                }
                // 8. 关闭连接
                if (connection != null && channel.isOpen()) {
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
