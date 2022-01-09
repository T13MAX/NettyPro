package com.atb.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @Author 呆呆
 * @Datetime 2022/1/8 14:11
 */
public class NIOClient {
    public static void main(String[] args) throws Exception {

        //得到一个网络通道
        SocketChannel socketChannel = SocketChannel.open();

        //设置非阻塞
        socketChannel.configureBlocking(false);

        //提供服务器端的ip和端口
        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 6666);

        //连接服务器
        if (!socketChannel.connect(inetSocketAddress)) {
            while (!socketChannel.finishConnect()) {
                System.out.println("因为连接需要时间,客户端不会阻塞,可以做其他工作");
            }
        }

        //连接成功就发送数据
        String str = "庞庞大笨蛋";
        ByteBuffer buffer = ByteBuffer.wrap(str.getBytes());

        //发送数据 将buffer写入channel
        socketChannel.write(buffer);
        //停在这
        System.in.read();
    }
}
