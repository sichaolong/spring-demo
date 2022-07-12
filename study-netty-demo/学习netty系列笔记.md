## 笔记

### 一、Java NIO 的简介



同步非阻塞IO

https://segmentfault.com/a/1190000006824091

注意：

组件Buffer是一块内核内存的抽象，这个是Linux中NIO的模型吧，数据从Channle读取到内核内存buffer中，再从buffer中拷贝到用户进程中，是有拷贝两次到操作。



#### 1、Java NIO Channel

https://segmentfault.com/a/1190000006824107



分类

- FileChannel：FileChannel 是操作文件的Channel, 我们可以通过 FileChannel 从一个文件中读取数据, 也可以将数据写入到文件中.**`注意`**, FileChannel 不能设置为非阻塞模式
- SocketChannel、ServerSocketChannel
- DatagramChannel：处理UDP连接



Socket、ServerSocketChannel 使用打开方式

```java

    1、客户端 SocketChannel 打开方式
        
    SocketChannel socketChannel = SocketChannel.open();
    socketChannel.connect(new InetSocketAddress("http://example.com", 80));
    
    2、服务端 ServerSocketChannel 打开方式
        
    ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
    
    serverSocketChannel.socket().bind(new InetSocketAddress(9999));
    serverSocketChannel.configureBlocking(false);
    
    while(true){
        SocketChannel socketChannel =
                serverSocketChannel.accept();
        // 在非阻塞模式下, accept()是非阻塞的, 因此如果此时没有连接到来, 那么 accept()方法会返回null:
        if(socketChannel != null){
            //do something with socketChannel...
            }
    }

```



DatagramChannel 使用方式

```
// 打开
DatagramChannel channel = DatagramChannel.open();
channel.socket().bind(new InetSocketAddress(9999));

// 读取数据
ByteBuffer buf = ByteBuffer.allocate(48);
buf.clear();

channel.receive(buf);

// 发送数据
String newData = "New String to write to file..."
                    + System.currentTimeMillis();
    
ByteBuffer buf = ByteBuffer.allocate(48);
buf.clear();
buf.put(newData.getBytes());
buf.flip();

// 因为 UDP 是非连接的, 因此这个的 connect 并不是向 TCP 一样真正意义上的连接, 而是它会将 DatagramChannel 锁住, 因此我们仅仅可以从指定的地址中读取或写入数据.
int bytesSent = channel.send(buf, new InetSocketAddress("example.com", 80));
```



#### 2、Java NIO Buffer



https://segmentfault.com/a/1190000006824155

实际上, 一个 Buffer 其实就是一块内存区域, 我们可以在这个内存区域中进行数据的读写. NIO Buffer 其实是这样的内存块的一个封装, 并提供了一些操作方法让我们能够方便地进行数据的读写.

使用 NIO Buffer 的步骤如下:

- 将数据写入到 Buffer 中.
- 调用 Buffer.flip()方法, 将 NIO Buffer 转换为读模式.
- 从 Buffer 中读取数据
- 调用 Buffer.clear() 或 Buffer.compact()方法, 将 Buffer 转换为写模式.



一个 Buffer 有三个属性:

- capacity
- position
- limit

其中 **position** 和 **limit** 的含义与 Buffer 处于读模式或写模式有关, 而 capacity 的含义与 Buffer 所处的模式无关。

`limit - position` 表示此时还可以写入/读取多少单位的数据.
当从写模式变为读模式时, 原先的 **写 position** 就变成了读模式的 **limit**.

- 写模式：从position开始为0，然后随着写入数据递增一

- 读模式：从position位置开始读数据，从写模式转为读模式的时候会被置为0，limit会变成position



#### 3、Java NIO Selector 



https://segmentfault.com/a/1190000006824196

基本使用流程

1. 通过 Selector.open() 打开一个 Selector.
2. 将 Channel 注册到 Selector 中, 并设置需要监听的事件(interest set)
3. 不断重复:
   - 调用 select() 方法
   - 调用 selector.selectedKeys() 获取 selected keys
   - 迭代每个 selected key:
     - *从 selected key 中获取 对应的 Channel 和附加信息(如果有的话)
     - *判断是哪些 IO 事件已经就绪了, 然后处理它们. `如果是 OP_ACCEPT 事件, 则调用 "SocketChannel clientChannel = ((ServerSocketChannel) key.channel()).accept()" 获取 SocketChannel, 并将它设置为 非阻塞的, 然后将这个 Channel 注册到 Selector 中.`
     - *根据需要更改 selected key 的监听事件.
     - *将已经处理过的 key 从 selected keys 集合中删除.



### 二、netty 3.x 、4.x 使用举栗

ps: 举栗 demo 代码说明说明请看 readme.md 文件


### 三、netty源码学习

![img](%E5%AD%A6%E4%B9%A0netty%E7%B3%BB%E5%88%97%E7%AC%94%E8%AE%B0.assets/13481385-7cd338a863d2737d)



#### 1、netty实现tcp服务怎么解决粘包，拆包问题？

参考：https://blog.csdn.net/qq_24874939/article/details/86475285

在举栗example_tcp中，编码解码的两个类在handler包下的DecoderHandler、EncoderHandler两个








