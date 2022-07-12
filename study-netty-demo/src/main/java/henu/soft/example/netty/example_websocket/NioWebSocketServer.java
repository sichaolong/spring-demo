package henu.soft.example.netty.example_websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 1、绑定主线程组和工作线程组，这部分对应架构图中的事件循环组
 *
 * 2、只有服务器才需要绑定端口，客户端是绑定一个地址
 *
 * 3、配置channel（数据通道）参数，重点就是ChannelInitializer的配置
 *
 * 4、以异步的方式启动，最后是结束关闭两个线程组
 *
 * 作者：rpf_siwash
 * 链接：https://www.jianshu.com/p/56216d1052d7
 * 来源：简书
 * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
 */

public class NioWebSocketServer {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private void init() {
        logger.info("正在启动websocket服务器");
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup work = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(boss, work);
            bootstrap.channel(NioServerSocketChannel.class);
            bootstrap.childHandler(new NioWebSocketChannelInitializer());

            // 以异步的方式启动，最后是结束关闭两个线程组

            Channel channel = bootstrap.bind(8081).sync().channel();
            logger.info("webSocket服务器启动成功：" + channel);
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
            logger.info("运行出错：" + e);
        } finally {
            boss.shutdownGracefully();
            work.shutdownGracefully();
            logger.info("websocket服务器已关闭");
        }
    }

    public static void main(String[] args) {
        new NioWebSocketServer().init();
    }
}

