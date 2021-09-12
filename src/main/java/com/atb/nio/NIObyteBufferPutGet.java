package com.atb.nio;

import java.nio.ByteBuffer;

/**
 * buffer类型化和只读
 *
 * @Author 呆呆
 * @Datetime 2021/9/12 13:02
 */
public class NIObyteBufferPutGet {
    public static void main(String[] args) {

        ByteBuffer byteBuffer = ByteBuffer.allocate(64);

        //类型化方式放入数据
        byteBuffer.putInt(100);
        byteBuffer.putLong(9);
        byteBuffer.putChar('呆');
        byteBuffer.putShort((short)4);

        ByteBuffer readOnlyBuffer = byteBuffer.asReadOnlyBuffer();
        //取出
        byteBuffer.flip();
        System.out.println();

        System.out.println(byteBuffer.getInt());
    }
}
