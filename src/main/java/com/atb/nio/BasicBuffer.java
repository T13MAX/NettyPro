package com.atb.nio;

import java.nio.IntBuffer;

/**
 * Selector对应一个而县城 一个线程对应多个channel(连接)
 * 多个通道注册到选择器
 * 切换到哪个channel由事件决定
 * Buffer就是一个内存块 底层有一个数组
 * 数据读取写入通过buffer BIO中要么是输入流,要么是输出流 NIO可读可写 用flip切换
 * channel是双向的 可以返回底层操作系统的情况 比如linux 底层的操作系统通道就是双向的
 *
 *
 * @Author 呆呆
 * @Datetime 2021/9/11 12:35
 */
public class BasicBuffer {
    public static void main(String[] args) {
        //举例说明buffer的使用(简单说明)
        //创建一个buffer 大小为5 可已存放5个int 还有float double等
        IntBuffer intBuffer = IntBuffer.allocate(5);
        //向buffer中存放数据
        for(int i=0;i<intBuffer.capacity();i++){ //capacity 大小
            intBuffer.put(i*2);
        }
        //从buffer读取数据
        //将intbuffer转换 读写切换
        intBuffer.flip();

        while(intBuffer.hasRemaining()){
            System.out.println(intBuffer.get());//get里面有个索引
        }
    }
}
