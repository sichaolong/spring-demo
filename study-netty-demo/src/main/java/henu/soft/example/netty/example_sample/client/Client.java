package henu.soft.example.netty.example_sample.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * netty 4.x demo
 */
public class Client {
    public static void main(String[] args) {
        EventLoopGroup bossGroup = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();

        try {
            bootstrap.group(bossGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ClientInitializer());

            Channel channel = bootstrap.connect("127.0.0.1", 8888).sync().channel();
            ChannelFuture lastWriteFuture = null;
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

            while(true){
                String line = in.readLine();
                if(line == null){
                    break;
                }
                lastWriteFuture = channel.writeAndFlush(line + "\r\n" );
                if ("bye".equals(line.toLowerCase())) {
                    // Client端输入bye后关闭listener
                    channel.closeFuture().sync();
                    break;
                }
                if (lastWriteFuture != null) {
                    lastWriteFuture.sync();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
