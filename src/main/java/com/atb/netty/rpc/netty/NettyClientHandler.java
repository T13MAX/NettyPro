package com.atb.netty.rpc.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.Callable;

/**
 * @Author 呆呆
 * @Datetime 2022/1/26 21:24
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter implements Callable {

    private ChannelHandlerContext context;

    private String result;

    private String para;//客户端调用方式传入的参数

    //与服务器创建连接后就会调用
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        context = ctx;//其他方法会使用到
    }

    //收到服务器的数据后
    @Override
    public synchronized void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        result = msg.toString();
        notify();//唤醒等待线程
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    //被代理对象调用 发送数据给服务器 ->wait ->等待被唤醒 ->返回结果
    @Override
    public synchronized Object call() throws Exception {
        context.writeAndFlush(para);
        wait();//等待channelRead 方法获取到服务器结果后 唤醒
        return result;
    }

    void setPara(String para) {
        this.para = para;
    }
}
