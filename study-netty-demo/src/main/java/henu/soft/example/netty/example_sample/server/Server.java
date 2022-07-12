package henu.soft.example.netty.example_sample.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import org.springframework.boot.logging.LogLevel;


/**
 * netty 4.x demo
 */
public class Server {
    public static void main(String[] args) {
        // boss 线程用于服务器接收客户端连接
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);

        // worker 线程用于处理已经被接受的连接，一旦把信息注册到workerGroup上
        EventLoopGroup workGroup = new NioEventLoopGroup();

        try {
            // Netty启动辅助类
            ServerBootstrap serverBootstrap = new ServerBootstrap();

            serverBootstrap.group(bossGroup,workGroup)
                    .channel(NioServerSocketChannel.class) // 说明一个新的Channel如何接收进来的连接
                    .handler(new LoggingHandler(String.valueOf(LogLevel.INFO))) // 打印日志级别
                    .childHandler(new ServerInitializer());

            // 绑定端口
            ChannelFuture channelFuture = serverBootstrap.bind(8888);
            // 等待服务端口关闭
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }


    }
}
