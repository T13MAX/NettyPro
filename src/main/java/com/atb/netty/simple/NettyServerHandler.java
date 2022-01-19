package com.atb.netty.simple;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.buffer.ByteBuf;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

/**
 * 自定义一个Handler 需要继承netty规定好的某个HandlerAdapter
 *
 * @Author 呆呆
 * @Datetime 2022/1/15 14:41
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    //读取数据(读取客户端发送的消息
    //ChannelHandlerContext 上下文对象 含有管道pipeline, 通道channel, 地址
    //Object msg 客户端发送的数据 默认Object
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        //用户自定义的普通任务 taskQueue 10+20 因为是同一线程 我感觉是因为Queue 队列啊!
        ctx.channel().eventLoop().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10*1000);
                    ctx.writeAndFlush(Unpooled.copiedBuffer("hello client 喵喵10", CharsetUtil.UTF_8));
                } catch (InterruptedException e) {
                    System.out.println("发生异常:"+e.getMessage());
                }
            }
        });
        ctx.channel().eventLoop().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(20*1000);
                    ctx.writeAndFlush(Unpooled.copiedBuffer("hello client 喵喵20", CharsetUtil.UTF_8));
                } catch (InterruptedException e) {
                    System.out.println("发生异常:"+e.getMessage());
                }
            }
        });

        //用户自定义定时任务 scheduleTaskQueue
        ctx.channel().eventLoop().schedule(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5*1000);
                    ctx.writeAndFlush(Unpooled.copiedBuffer("hello client 定时喵喵", CharsetUtil.UTF_8));
                } catch (InterruptedException e) {
                    System.out.println("发生异常:"+e.getMessage());
                }
            }
        },5, TimeUnit.SECONDS);

        //Thread.sleep(10*1000);
        //ctx.writeAndFlush(Unpooled.copiedBuffer("hello client 喵喵", CharsetUtil.UTF_8));

        System.out.println("go on");

        /*System.out.println("server ctx =" + ctx);

        //将msg转成一个ByteBuffer
        //ByteBuf 是Netty提供的 不是NIO的ByteBuffer
        ByteBuf buf = (ByteBuf) msg;
        System.out.println("client msg: " + buf.toString(CharsetUtil.UTF_8));
        System.out.println("client address: " + ctx.channel().remoteAddress());*/
    }

    //数据读取完毕
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {

        //是wirte+flush
        //将数据写入到缓存 并刷新
        //对发送的数据进行编码
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello client", CharsetUtil.UTF_8));
    }

    //处理异常 一般是关闭通道
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

        ctx.close();
    }
}
