package com.atb.netty.rpc.provider;

import com.atb.netty.rpc.netty.NettyServer;

/**
 * 启动一个服务提供者 就是NettyServer
 *
 * @Author 呆呆
 * @Datetime 2022/1/26 21:07
 */
public class ServerBootstrap {

    public static void main(String[] args) {

        //
        NettyServer.startServer("127.0.0.1", 7000);
    }
}
