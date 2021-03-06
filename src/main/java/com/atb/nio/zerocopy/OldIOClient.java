package com.atb.nio.zerocopy;

import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.net.Socket;

/**
 * @Author 呆呆
 * @Datetime 2022/1/9 12:42
 */
public class OldIOClient {

    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("127.0.0.1", 7001);
        String fileName = "pp.jpeg";
        FileInputStream inputStream = new FileInputStream(fileName);
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
        byte[] buffer = new byte[4096];
        long readCount;
        long total = 0;
        long startTime = System.currentTimeMillis();
        while ((readCount = inputStream.read(buffer)) >= 0) {
            total += readCount;
            dataOutputStream.write(buffer);
        }
        System.out.println("发送总字节数: " + total + " 耗时: " + (System.currentTimeMillis() - startTime));
        dataOutputStream.close();
        socket.close();
        inputStream.close();
    }
}
