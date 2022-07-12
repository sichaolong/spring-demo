## use netty demo

1、在pom文件引入netty相关依赖。

```

<!--        netty-->

<!-- 在Netty 3.x中，软件包来自org.jboss.netty。* http://netty.io/3.10/api/index.html.
但是启动了Netty 4.x的包裹来自Io.netty。*请参阅： http://netty.io/4.0/api/index.html.-->
        <dependency>
            <groupId>io.netty</groupId>
            <artifactId>netty</artifactId>
            <version>3.10.6.Final</version>
        </dependency>

        <dependency>
            <groupId>io.netty</groupId>
            <artifactId>netty-transport</artifactId>
            <version>4.1.77.Final</version>
        </dependency>
        <dependency>
            <groupId>io.netty</groupId>
            <artifactId>netty-handler</artifactId>
            <version>4.1.77.Final</version>
        </dependency>
```
        
2、example package :some demo example

```xml
关于netty的依赖，在下面的举栗中，使用了不同版本的netty依赖，
在Netty 3.x中，软件包来自org.jboss.netty。* http://netty.io/3.10/api/index.html.
但是启动了Netty 4.x的包裹来自Io.netty。*请参阅： http://netty.io/4.0/api/index.html.
 
```

- 2.1 netty:netty 使用举栗
    - example_simple : 简单的netty 3.x 的server案例，启动main之后，使用cmd、powershell 测试telnet 127.0.0.1 8080 观察控制台输出。
    - example_echo : 简单的netty 4.x 的 输出 举栗
    - example_sample : 简单netty 4.x 的server 、 client 案例
    - example_http : 简单netty 4.x 的 http server、http client, 参考：https://blog.csdn.net/wangshuang1631/article/details/73251180/
    - example_websocket : 简单 netty 4.x 的 WebSocket ,参考：https://www.jianshu.com/p/56216d1052d7。其中http和websocket的区别为：https://www.php.cn/faq/465597.html
    - example_tcp : 简单 netty 4.x 实现tcp服务，参考：https://blog.csdn.net/qq_24874939/article/details/86475285
- 2.2 socket:原生socket 使用举栗
    - example_bio: java socket 阻塞io
    - example_nio: java NIO 非阻塞io
