package henu.soft.example.socket.example_bio;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * java 原生Socket API 实现简单的server、client通信
 *
 * client 端：
 * 创建Socket通信，设置通信服务器的IP和Port
 * 建立IO输出流向服务器发送数据消息
 * 建立IO输入流读取服务器发送来的数据消息
 *
 *
 * **/

public class SocketClient {
    public static void main(String[] args) {
        try {
            Socket s = new Socket("127.0.0.1",8888);

            //构建IO
            InputStream is = s.getInputStream();
            OutputStream os = s.getOutputStream();

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
            //向服务器端发送一条消息
            bw.write("测试客户端和服务器通信，服务器接收到消息返回到客户端\n");
            bw.flush();

            //读取服务器返回的消息
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String mess = br.readLine();
            System.out.println("服务器："+mess);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
