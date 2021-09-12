package com.atb.nio;

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Author 呆呆
 * @Datetime 2021/9/12 11:23
 */
public class NIOFileChannelRead {
    public static void main(String[] args) throws Exception{

        //创建一个文件输入流
        File file = new File("d:/hdjy/file/file01.txt");
        FileInputStream fileInputStream = new FileInputStream(file);

        //通过fileInputStream获取对应的FileChannle 实际类型为FileChannelImpl
        FileChannel channel = fileInputStream.getChannel();

        //创建缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate((int) file.length());

        //将通道的数据读到缓冲区
        int read = channel.read(byteBuffer);

        //将字节转成字符串 array:转字节数组
        System.out.println(new String(byteBuffer.array()));
        //关闭流
        fileInputStream.close();

    }
}
