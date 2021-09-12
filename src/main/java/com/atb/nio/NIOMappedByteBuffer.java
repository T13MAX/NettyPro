package com.atb.nio;

import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 可以让文件直接在内存(堆外内存)中修改,操作系统不需要拷贝一次
 *
 * @Author 呆呆
 * @Datetime 2021/9/12 13:18
 */
public class NIOMappedByteBuffer {
    public static void main(String[] args) throws Exception{
        RandomAccessFile randomAccessFile = new RandomAccessFile("d:/hdjy/file/1.txt","rw");
        //获取对应的通道
        FileChannel channel = randomAccessFile.getChannel();

        /**
         * FileChannel.MapMode.READ_WRITE读写模式
         * 0 可以直接修改的起始位置
         * 5 映射到内存的大小 将文件多少个字节映射到内存 可修改的范围就是5
         */
        MappedByteBuffer mappedByteBuffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, 10);
        mappedByteBuffer.put(0,(byte)'t');
        mappedByteBuffer.put(6,(byte)'x');

        randomAccessFile.close();
        System.out.println("修改成功");
    }
}
