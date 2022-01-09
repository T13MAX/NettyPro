package com.atb.nio.zerocopy;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Author 呆呆
 * @Datetime 2022/1/9 12:39
 */
public class OldIOServer {
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(7001);

        while (true) {
            Socket socket = serverSocket.accept();
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            try {
                byte[] bytes = new byte[4096];
                while (true) {
                    int readCount = dataInputStream.read(bytes, 0, bytes.length);
                    if (-1 == readCount) {
                        break;
                    }
                }
            } catch (IOException e) {
                //e.printStackTrace();
                break;
            }
        }
    }
}
