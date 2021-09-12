package com.atb.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

/**
 * 从一个通道复制到另一个通道
 *
 * @Author 呆呆
 * @Datetime 2021/9/12 11:59
 */
public class NIOFileChannelTransfer {
    public static void main(String[] args) throws Exception {
        FileInputStream fileInputStream = new FileInputStream("d:/hdjy/file/a.jpg");
        FileOutputStream fileOutputStream = new FileOutputStream("d:/hdjy/file/b.jpg");

        //获取两个流对应的通道
        FileChannel sourceChannel = fileInputStream.getChannel();
        FileChannel destChannel = fileOutputStream.getChannel();

        //使用transferFrom拷贝
        destChannel.transferFrom(sourceChannel,0,sourceChannel.size());

        //关闭流 也可先关通道 在关流
        fileInputStream.close();
        fileOutputStream.close();

    }
}
