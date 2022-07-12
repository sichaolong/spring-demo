package henu.soft.example.netty.example_tcp.server;

import henu.soft.example.netty.example_tcp.handler.DecoderHandler;
import henu.soft.example.netty.example_tcp.handler.EncoderHandler;
import henu.soft.example.netty.example_tcp.handler.MyBusinessHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 只要是基于netty的服务器，都会用到bootstrap 并用这个对象绑定
 * (1) 工作线程组，
 * (2) channel的Class,
 * (3) 以及用户DIV的各种pipeline的handler类
 *
 * 注意在添加自定义handler的时候，数据的流动顺序和pipeline中添加hanlder的顺序是一致的。也就是说，从上往下应该为：底层字节流的解码/编码handler、业务处理handler。

 */

public class MyTcpServer {
    private  int port;
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    public  void init(){
        logger.info("正在启动tcp服务器……");
        NioEventLoopGroup boss = new NioEventLoopGroup();//主线程组
        NioEventLoopGroup work = new NioEventLoopGroup();//工作线程组
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();//引导对象
            bootstrap.group(boss,work);//配置工作线程组
            bootstrap.channel(NioServerSocketChannel.class);//配置为NIO的socket通道
            bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                protected void initChannel(SocketChannel ch) throws Exception {//绑定通道参数
                    ch.pipeline().addLast("logging",new LoggingHandler("DEBUG"));//设置log监听器，并且日志级别为debug，方便观察运行流程
                    ch.pipeline().addLast("encode",new EncoderHandler());//编码器。发送消息时候用过
                    ch.pipeline().addLast("decode",new DecoderHandler());//解码器，接收消息时候用
                    ch.pipeline().addLast("handler",new MyBusinessHandler());//业务处理类，最终的消息会在这个handler中进行业务处理
                }
            });
            bootstrap.option(ChannelOption.SO_BACKLOG,1024);//缓冲区
            bootstrap.childOption(ChannelOption.SO_KEEPALIVE,true);//ChannelOption对象设置TCP套接字的参数，非必须步骤
            ChannelFuture future = bootstrap.bind(port).sync();//使用了Future来启动线程，并绑定了端口
            logger.info("启动tcp服务器启动成功，正在监听端口:"+port);
            future.channel().closeFuture().sync();//以异步的方式关闭端口

        }catch (InterruptedException e) {
            logger.info("启动出现异常："+e);
        }finally {
            work.shutdownGracefully();
            boss.shutdownGracefully();//出现异常后，关闭线程组
            logger.info("tcp服务器已经关闭");
        }

    }

    public static void main(String[] args) {
        new MyTcpServer(8777).init();
    }
    public MyTcpServer(int port) {
        this.port = port;
    }
}

