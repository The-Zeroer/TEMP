package Server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Chat implements Runnable {
    private static final ArrayList<Socket> userList = new ArrayList<>();
    private final Socket socket;
    public  Chat(Socket socket){
        this.socket = socket;
        userList.add(socket);
        Date date = new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println();
        System.out.println(sdf.format(date));
        System.out.println("客户端(" + socket.getInetAddress().getHostName() +'/' + socket.getInetAddress().getHostAddress() + ")已连接");
        System.out.println(socket.toString());
        System.out.println("当前在线客户端数量" + userList.size());
    }
    @Override
    public void run() {
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            bis = new BufferedInputStream(socket.getInputStream());
            int b= 0;
            byte[] buffer = new byte[1024];
            while (((b = bis.read(buffer)) != -1)) {
                for (Socket s : userList) {
                    if(s != socket){
                        bos = new BufferedOutputStream(s.getOutputStream());
                        bos.write((s.getInetAddress().getHostName() + '\n').getBytes());
                        bos.write(buffer, 0, b);
                        bos.flush();
                    }
                }
            }
        } catch (IOException e) {
            userList.remove(socket);
            Date date = new Date();
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            System.out.println();
            System.out.println(sdf.format(date));
            System.out.println("客户端(" + socket.getInetAddress().getHostName() +'/' + socket.getInetAddress().getHostAddress() + ")已断开");
            System.out.println(socket.toString());
            System.out.println("当前在线客户端数量" + userList.size());
        } finally {
            if(socket != null){
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bis != null) {
                try {
                    bis.close();
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
