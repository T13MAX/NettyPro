package com.atb.netty.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * @Author 呆呆
 * @Datetime 2022/1/16 15:28
 */
public class NettyByteBuf01 {

    public static void main(String[] args) {
        //创建一个ByteBuf
        //创建一个对象 该对象包含一个数组arr 是一个byte[10]
        //在netty的buffer不需要使用flip进行反转
        //底层维护了readerIndex和writeIndex
        //readerIndex writeIndex capacity将buffer分成三个区域
        //0---readerIndex已经读取的
        //readerIndex---writeIndex可读区域
        //writeIndex---capacity 可写区域
        ByteBuf buffer = Unpooled.buffer(10);
        for (int i = 0; i < 10; i++) {
            buffer.writeByte(i);
        }

        System.out.println("capacity=" + buffer.capacity());

        for (int i = 0; i < buffer.capacity(); i++) {
            System.out.println(buffer.getByte(i));
        }
    }
}
