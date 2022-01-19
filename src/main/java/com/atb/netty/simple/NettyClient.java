package com.atb.netty.simple;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @Author 呆呆
 * @Datetime 2022/1/15 15:05
 */
public class NettyClient {

    public static void main(String[] args) throws Exception {

        //客户端需要一个时间循环组
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            //创建客户端启动对象
            //注意客户端使用的不是ServerBootstrap 而是Bootstrap
            Bootstrap bootstrap = new Bootstrap();

            //设置相关参数
            bootstrap.group(group)//设置线程
                    .channel(NioSocketChannel.class)//设置客户端通道的实现类(反射)
                    .handler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new NettyClientHandler());//加入自己的处理器
                        }
                    })
            ;
            System.out.println("client is ok");

            //启动客户端去连接
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 6668).sync();
            //给关闭通道进行监听
            channelFuture.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }
}
