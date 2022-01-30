package com.atb.netty.rpc.provider;

import com.atb.netty.rpc.publicinterface.HelloService;

/**
 * @Author 呆呆
 * @Datetime 2022/1/26 21:04
 */
public class HelloServiceImpl implements HelloService {

    private int count = 0;

    //当有消费方调用该方法时 就返回一个结果
    @Override
    public String hello(String msg) {
        System.out.println("收到客户端消息=" + msg);
        //根据msg返回不同结果
        if (msg != null) {
            return "你好客户端, 我已经收到你得消息 [" + msg + "]" + " 第" + (++count) + "次";
        } else {
            return "你好客户端, 我已经收到你得消息";
        }

    }
}
