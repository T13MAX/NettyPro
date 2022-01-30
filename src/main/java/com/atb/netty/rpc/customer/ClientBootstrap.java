package com.atb.netty.rpc.customer;

import com.atb.netty.rpc.netty.NettyClient;
import com.atb.netty.rpc.publicinterface.HelloService;

/**
 * @Author 呆呆
 * @Datetime 2022/1/26 21:48
 */
public class ClientBootstrap {

    //定义协议头
    public static final String providerName = "HelloService#hello#";

    public static void main(String[] args) throws Exception {

        NettyClient customer = new NettyClient();

        //创建代理对象
        HelloService service = (HelloService) customer.getBean(HelloService.class, providerName);

        String result = service.hello("你好 dubbo~");

        System.out.println("调用的结果:" + result);

    }
}
