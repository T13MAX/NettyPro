package com.atb.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 用一个buffer实现复制
 *
 * @Author 呆呆
 * @Datetime 2021/9/12 11:34
 */
public class NIOFileChannelCopy {
    public static void main(String[] args) throws Exception {
        FileInputStream fileInputStream = new FileInputStream("d:/hdjy/file/1.txt");
        FileChannel channelRead = fileInputStream.getChannel();
        FileOutputStream fileOutputStream = new FileOutputStream("D:/hdjy/file/2.txt");
        FileChannel channelWrite = fileOutputStream.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(512);
        //循环读取
        while (true) {
            //超级重要 复位
            byteBuffer.clear();
            int read = channelRead.read(byteBuffer);
            if (read == -1) {
                break;
            }
            //将buffer中的数据写入到channelWrite里
            byteBuffer.flip();
            channelWrite.write(byteBuffer);
            //byteBuffer.flip();//不是必须上面clear 这里在转回去就ok了
        }

        //关闭流
        fileInputStream.close();
        fileOutputStream.close();
    }
}
