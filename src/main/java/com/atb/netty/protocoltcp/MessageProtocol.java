package com.atb.netty.protocoltcp;

/**
 * 协议包
 *
 * @Author 呆呆
 * @Datetime 2022/1/22 17:45
 */
public class MessageProtocol {

    private int len;

    private byte[] content;


    public int getLen() {
        return len;
    }

    public void setLen(int len) {
        this.len = len;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
