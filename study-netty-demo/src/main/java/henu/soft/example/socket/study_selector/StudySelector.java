package henu.soft.example.socket.study_selector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class StudySelector {
    private static final int BUF_SIZE = 256;
    private static final int TIMEOUT = 3000;


    public static void main(String[] args) {

        new StudySelector().test1();
    }
    public void test1(){
        try {
            ServerSocketChannel channel = ServerSocketChannel.open();

            Selector selector = Selector.open();
            channel.socket().bind(new InetSocketAddress(8080));
            channel.configureBlocking(false);

            // 将服务端 channel 的 accept 事件注册到 selector
            // 通常我们都是先注册一个 OP_ACCEPT 事件, 然后在 OP_ACCEPT 到来时, 再将这个 Channel 的 OP_READ
            // 注册到 Selector 中
            channel.register(selector, SelectionKey.OP_ACCEPT);


            // select
            while (true){
                if(selector.select(TIMEOUT) == 0){
                    System.out.println("服务正在监听....");
                    continue;
                }

                // 有可以执行的事件
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();

                while(iterator.hasNext()){
                    SelectionKey currentKey = iterator.next();

                    // 已处理的事件要删除
                    iterator.remove();

                    // 不同的事件处理逻辑

                    // 一、accept 事件
                    if(currentKey.isAcceptable()){
                        // 1、获取ServerSocketChannel
                        ServerSocketChannel serverChannel = (ServerSocketChannel) currentKey.channel();

                        // 2、获取SocketChannel
                        SocketChannel clientChannel = serverChannel.accept();

                        clientChannel.configureBlocking(false);

                        // 在 OP_ACCEPT 到来时, 再将这个 Channel 的 OP_READ 注册到 Selector 中.
                        // 注意, 这里我们如果没有设置 OP_READ 的话, 即 interest set 仍然是 OP_CONNECT 的话, 那么 select 方法会一直直接返回.
                        clientChannel.register(currentKey.selector(),SelectionKey.OP_READ, ByteBuffer.allocate(BUF_SIZE));
                    }

                    // 二、readable 事件
                    if(currentKey.isReadable()){
                        SocketChannel clientChannel = (SocketChannel) currentKey.channel();
                        ByteBuffer buf = (ByteBuffer) currentKey.attachment();
                        int bytesRead = clientChannel.read(buf);
                        if(bytesRead == -1){
                            clientChannel.close();
                        }
                        else if(bytesRead > 0){
                            currentKey.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
                            System.out.println("读取数据的长度：" + bytesRead);
                        }

                    }

                    // 三、writable 事件
                    if(currentKey.isWritable()){
                        ByteBuffer buf = (ByteBuffer) currentKey.attachment();
                        buf.flip();
                        SocketChannel clientChannel = (SocketChannel) currentKey.channel();
                        clientChannel.write(buf);

                        if(!buf.hasRemaining()){
                            currentKey.interestOps(SelectionKey.OP_READ);
                        }
                        buf.compact();
                    }
                }


            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
