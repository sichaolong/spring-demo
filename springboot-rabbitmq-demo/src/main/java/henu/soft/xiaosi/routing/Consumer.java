package henu.soft.xiaosi.routing;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer {


    public void consumer(String queueName) {
        /**
         * fanout原生方式使用rabbitmq
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

            connection = factory.newConnection("消费者");


            // 3. 通过连接获取管道channel

            channel = connection.createChannel();


            channel.basicConsume(
                    queueName,
                    true,
                    new DeliverCallback() {
                        public void handle(String consumerTag, Delivery message) throws IOException {
                            System.out.println(Thread.currentThread().getName() + " 收到的信息 ===》" + new String(message.getBody(), "UTF-8"));
                        }

                    },
                    new CancelCallback() {
                        public void handle(String consumerTag) throws IOException {
                            System.out.println("消费者接受消息失败！");

                        }
                    });


            System.out.println(Thread.currentThread().getName() + " 开始接受新消息！");
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

class test {
    public static void main(String[] args) {
        Consumer consumer = new Consumer();


        /**
         * 需要注意的是 可视化界面绑定了 my-fanout 交换机和 xiaosiQueue1、xiaosiQueue2、xiaosiQueue3三个队列
         */
        new Thread(() -> {
            consumer.consumer("xiaosiQueue1");
        }, "消费者1").start();

        new Thread(() -> {
            consumer.consumer("xiaosiQueue2");
        }, "消费者3").start();

        new Thread(() -> {
            consumer.consumer("xiaosiQueue3");
        }, "消费者2").start();


    }
}
