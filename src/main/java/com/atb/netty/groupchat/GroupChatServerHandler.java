package com.atb.netty.groupchat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import jdk.nashorn.internal.ir.CallNode;

import java.text.SimpleDateFormat;

/**
 * @Author 呆呆
 * @Datetime 2022/1/19 21:26
 */
public class GroupChatServerHandler extends SimpleChannelInboundHandler {

    //定义一个channel组 管理所有的channel 要静态 所有共享
    //GlobalEventExecutor.INSTANCE 是全局的时间执行器 是一个单例
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    //表示连接建立  一旦建立 第一个被调用
    //将当前channel加入到channelGroup
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        //不需要自己遍历
        channelGroup.writeAndFlush("[客户端]" + channel.remoteAddress() + " 加入聊天\n");
        //将该用户加入聊天的信息推送给其他在线客户端
        channelGroup.add(channel);
    }

    //断开连接 将xx客户离开信息推送给在线用户
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush("[客户端]" + channel.remoteAddress() + " 离开了\n");
    }

    //表示channel处于活动状态,提示xx上线
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + "上线了");
    }

    //channel处于非活跃状态
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + "离线了");
    }

    //读取数据
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {

        Channel channel = ctx.channel();
        //遍历channelGroup 根据不同情况 会送不同消息
        channelGroup.forEach(ch -> {
            if (channel != ch) {//不是当前channel 直接转发
                ch.writeAndFlush("[客户]" + channel.remoteAddress() + "发送消息:" + msg + "\n");
            } else {
                //回显给自己
                ch.writeAndFlush("[自己发送了消息]" + msg + "\n");
            }
        });
    }

    //异常
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
