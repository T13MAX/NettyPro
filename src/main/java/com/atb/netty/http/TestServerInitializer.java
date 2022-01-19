package com.atb.netty.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

import java.nio.channels.ServerSocketChannel;

/**
 * @Author 呆呆
 * @Datetime 2022/1/16 11:52
 */
public class TestServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        //向管道加入处理器

        //得到管道
        ChannelPipeline pipeline = ch.pipeline();

        //加入一个netty 提供的httpServerCodec codec =>[coder -decoder]
        //HttpServerCodec 是netty提供的处理http的 编-解码器
        pipeline.addLast("MyHttpServerCodec", new HttpServerCodec());
        //增加一个自定义的Handler
        pipeline.addLast("MyTestHttpServerHandler", new TestHttpServerHandler());
    }
}
