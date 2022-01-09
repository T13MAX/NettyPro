package com.atb.nio.zerocopy;

import java.io.FileInputStream;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

/**
 * @Author 呆呆
 * @Datetime 2022/1/9 12:19
 */
public class NewIOClient {

    public static void main(String[] args) throws Exception {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("127.0.0.1", 7001));
        String fileName = "pp.jpeg";

        FileChannel fileChannel = new FileInputStream(fileName).getChannel();

        long startTime = System.currentTimeMillis();

        //在linux下一个transferTo方法就可以完成传输
        //在windows下调用一次transferTo只能发送8M,需要分段传输文件
        //transferTo 底层使用零拷贝
        long transferCount = fileChannel.transferTo(0, fileChannel.size(), socketChannel);

        System.out.println("发送的总字节数 = " + transferCount + "耗时: " + (System.currentTimeMillis() - startTime));

        fileChannel.close();
    }
}
