package henu.soft.xiaosi.simple;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer {

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

            connection = factory.newConnection("消费者");


            // 3. 通过连接获取管道channel

            channel = connection.createChannel();


            // 4. 从哪个队列取


            String queueName = "xiaosi";


            channel.basicConsume(
                    queueName,
                    true,
                    new DeliverCallback() {
                        public void handle(String consumerTag, Delivery message) throws IOException {
                            System.out.println("消费者受到消息：" + new String(message.getBody(), "UTF-8"));
                        }

                    },
                    new CancelCallback() {
                        public void handle(String consumerTag) throws IOException {
                            System.out.println("消费者接受消息失败！");

                        }
                    });


            System.out.println("开始接受新消息！");
            System.in.read();


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
