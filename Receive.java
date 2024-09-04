package Client;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.Socket;

public class Receive implements Runnable {
    private Socket socket;
    public Receive(Socket socket) {
        this.socket = socket;
    }
    @Override
    public void run() {
        BufferedInputStream bis = null;
        try {
            bis = new BufferedInputStream(socket.getInputStream());
            int b= 0;
            byte[] buffer = new byte[1024];
            while (((b = bis.read(buffer)) != -1)) {
                System.out.println(new String(buffer, 0, b));
            }
        } catch (IOException e) {
            System.out.println("服务端断开连接");
        }
    }
}
