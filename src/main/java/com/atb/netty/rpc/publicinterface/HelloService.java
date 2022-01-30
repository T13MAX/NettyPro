package com.atb.netty.rpc.publicinterface;

/**
 * 服务提供方和消费方都需要的接口
 *
 * @Author 呆呆
 * @Datetime 2022/1/26 21:02
 */
public interface HelloService {

    String hello(String msg);
}
