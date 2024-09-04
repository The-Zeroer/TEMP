package Client;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        //System.out.println("请输入服务端地址");
        //String input = sc.nextLine();
        String input = "192.168.8.32:10086";
        String[] address = input.split(":");
        Socket socket = null;
        BufferedOutputStream bos = null;
        while (true) {
            try {
                socket = new Socket(InetAddress.getByName(address[0]),Integer.parseInt(address[1]));
                bos = new BufferedOutputStream(socket.getOutputStream());
                System.out.println("连接成功");
                new Thread(new Receive(socket)).start();
                while (true) {
                    String msg = sc.nextLine();
                    bos.write(msg.getBytes());
                    bos.flush();
                }
            } catch (IOException e) {
                System.out.println("连接失败，正在尝试重新连接");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                continue;
            } finally {
                if (socket != null) {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (bos != null) {
                    try {
                        bos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
