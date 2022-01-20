package com.atb.netty.heartbeat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * @Author 呆呆
 * @Datetime 2022/1/20 20:59
 */
public class MyServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * @return void
     * @Author 呆呆
     * @Date 2022/1/20 20:59
     * @Param [ctx, evt] 上下文,事件
     **/
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

        if (evt instanceof IdleStateEvent) {
            //将evt向下转型
            IdleStateEvent event = (IdleStateEvent) evt;
            String eventType = null;
            switch (event.state()) {
                case READER_IDLE:
                    eventType="读空闲";
                    break;
                case WRITER_IDLE:
                    eventType="写空闲";
                    break;
                case ALL_IDLE:
                    eventType="读写空闲";
                    break;
            }
            System.out.println(ctx.channel().remoteAddress()+"--超时事件--"+eventType);
        }
    }
}
