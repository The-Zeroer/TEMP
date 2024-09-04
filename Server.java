package Server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    public static void main(String[] args) {
        int port = 10086;
        ExecutorService pool = Executors.newCachedThreadPool();
        ServerSocket serverSocket = null;
        Socket socket = null;
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("服务端启动成功，等待客户端连接");
            System.out.println("服务端地址" + InetAddress.getLocalHost().getHostAddress() + ":" + port);
            while (true) {
                socket = serverSocket.accept();
                pool.submit(new Chat(socket));
            }
        } catch (IOException e) {
            System.out.println("该端口被占用");
        }finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            pool.shutdown();
        }
    }
}
