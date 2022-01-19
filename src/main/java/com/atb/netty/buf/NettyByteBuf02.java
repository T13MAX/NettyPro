package com.atb.netty.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;

/**
 * @Author 呆呆
 * @Datetime 2022/1/16 15:43
 */
public class NettyByteBuf02 {

    public static void main(String[] args) {

        //创建ByteBuf
        ByteBuf byteBuf = Unpooled.copiedBuffer("hello,world!", CharsetUtil.UTF_8);

        //使用相关的方法
        if (byteBuf.hasArray()) {
            byte[] content = byteBuf.array();
            //转成字符串
            System.out.println(new String(content, Charset.forName("utf-8")));

            System.out.println("byteBuf=" + byteBuf);

            System.out.println(byteBuf.arrayOffset());//0
            System.out.println(byteBuf.readerIndex());//0
            System.out.println(byteBuf.writerIndex());//12
            System.out.println(byteBuf.capacity());//64

            int len = byteBuf.readableBytes();
            System.out.println(len);//12

        }
    }
}
