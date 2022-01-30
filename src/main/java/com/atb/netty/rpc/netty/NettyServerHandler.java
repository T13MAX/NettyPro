package com.atb.netty.rpc.netty;

import com.atb.netty.rpc.provider.HelloServiceImpl;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @Author 呆呆
 * @Datetime 2022/1/26 21:13
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //获取客户端发送的消息 并调用服务
        System.out.println("msg=" + msg);
        //调用时 需要定义一个协议
        //比如 每次发消息都必须以某个字符串开头 HelloService#hello#你好
        String str = msg.toString();
        if (str.startsWith("HelloService#hello")) {
            String result = new HelloServiceImpl().hello(str.substring(str.lastIndexOf("#") + 1));//下一个位置开始
            ctx.writeAndFlush(result);
        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
