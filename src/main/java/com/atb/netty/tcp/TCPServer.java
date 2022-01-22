package com.atb.netty.tcp;

import com.atb.netty.simple.NettyServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @Author 呆呆
 * @Datetime 2022/1/22 17:23
 */
public class TCPServer {
    public static void main(String[] args) throws Exception {

        //创建bossGroup和workerGroup 两个线程组
        //bossGroup值处理连接请求 workerGroup处理真正的和客户端的业务处理
        //两个都是无限循环
        //bossGroup和workerGroup含有的子线程(NioEventLoop)的个数 默认为cpu核数X2
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            //创建服务器端的启动对象,配置参数
            ServerBootstrap bootstrap = new ServerBootstrap();

            //使用链式编程的方式来进行设置
            bootstrap.group(bossGroup, workerGroup)  //设置两个线程组
                    .channel(NioServerSocketChannel.class)//使用NioServerSocketChannel作为服务器的通道实现
                    .option(ChannelOption.SO_BACKLOG, 128)//设置线程队列得到连接个数
                    .childOption(ChannelOption.SO_KEEPALIVE, true)//设置保持活动连接状态
                    .childHandler(new TCPServerInitializer())//给我们的workerGroup的EventLoop对应的管道设置对应的处理器
            ;
            System.out.println("server is ready!!!");

            //绑定一个端口并且同步 生成了一个ChannelFuture对象
            //启动服务器(兵绑定端口)
            ChannelFuture cf = bootstrap.bind(6668).sync();

            //给cf注册监听器 监控我们关心的事件
            cf.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (cf.isSuccess()) {
                        System.out.println("监听6668成功");
                    } else {
                        System.out.println("监听6668失败");
                    }
                }
            });

            //对关闭通道进行监听
            cf.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
