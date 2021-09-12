package com.atb.nio;

import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 写入本地文件
 *
 * @Author 呆呆
 * @Datetime 2021/9/12 10:55
 */
public class NIOFileChannelWrite {
    public static void main(String[] args) throws Exception{

        String str="傻呆呆";

        //创建一个输出流 -/channel
        FileOutputStream fileOutputStream = new FileOutputStream("d:/hdjy/file/file01.txt");

        //通过fileOutputStream获取对应的FileChannel
        //这个fileChannel 真实类型是FileChannelImpl
        FileChannel channel = fileOutputStream.getChannel();

        //创建一个缓冲区 ByteBuffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        //将str放入ByteBuffer里
        byteBuffer.put(str.getBytes());

        //byteBuffer进行反转
        byteBuffer.flip();

        //将byteBuffer写入到channel
        channel.write(byteBuffer);

        //关闭流
        fileOutputStream.close();


    }
}
