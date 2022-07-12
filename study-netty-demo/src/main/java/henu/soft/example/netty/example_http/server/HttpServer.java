package henu.soft.example.netty.example_http.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

/**
 * netty 服务端 http 服务
 *
 * 其中 http 服务 的4个类是Netty提供的，它们其实也是一种Handler，其中Encoder继承自ChannelOutboundHandler，Decoder继承自ChannelInboundHandler，它们的作用是：
 * 1、HttpRequestEncoder：对httpRequest进行编码。
 * 2、HttpRequestDecoder：把流数据解析为httpRequest。
 * 3、HttpResponsetEncoder：对httpResponset进行编码。
 * 4、HttpResponseEncoder：把流数据解析为httpResponse。
 */
public class HttpServer {

    public void start(int port) throws Exception {
        // 处理连接的线程
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        // 接受来自 bossGroup 线程组的 连接
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            // 辅助启动器
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class) // 匿名类
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch)
                                throws Exception {
                            // server端发送的是httpResponse，所以要使用HttpResponseEncoder进行编码
                            ch.pipeline().addLast(
                                    new HttpResponseEncoder());
                            // server端接收到的是httpRequest，所以要使用HttpRequestDecoder进行解码
                            ch.pipeline().addLast(
                                    new HttpRequestDecoder());
                            ch.pipeline().addLast(
                                    new HttpServerHandlerOfClient()); // 修改这里的 handler 决定是浏览器 还是 客户端
                        }
                    }).option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture f = b.bind(port).sync();

            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        HttpServer server = new HttpServer();
        server.start(8000);
    }
}
