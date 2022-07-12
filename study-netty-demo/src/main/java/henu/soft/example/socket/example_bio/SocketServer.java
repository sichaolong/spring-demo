package henu.soft.example.socket.example_bio;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * java 原生Socket API 实现简单的server、client通信
 *
 * server端：
 *
 * 服务器建立通信ServerSocket
 * 服务器建立Socket接收客户端连接
 * 建立IO输入流读取客户端发送的数据
 * 建立IO输出流向客户端发送数据消息
 */
@Slf4j
public class SocketServer {

    private static final Integer serverPort = 8888;
    public static void main(String[] args) {


        try {
            ServerSocket ss = new ServerSocket(serverPort);
            log.info("启动服务器....端口号为：{}",serverPort);
            Socket s = ss.accept();
            log.info("客户端:"+s.getInetAddress().getLocalHost()+"已连接到服务器");

            BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
            //读取客户端发送来的消息
            String mess = br.readLine();
            log.info("客户端：{}",mess);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
            bw.write(mess+"\n");
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
