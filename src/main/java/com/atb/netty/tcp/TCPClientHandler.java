package com.atb.netty.tcp;

import com.atb.netty.protocoltcp.MessageProtocol;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;

/**
 * @Author 呆呆
 * @Datetime 2022/1/22 17:18
 */
public class TCPClientHandler extends SimpleChannelInboundHandler<MessageProtocol> {//自定义的包 不用ByteBuf

    private int count;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {
       /* byte[] buffer = new byte[msg.readableBytes()];
        msg.readBytes(buffer);
        String message = new String(buffer, Charset.forName("utf-8"));
        System.out.println("客户端接收的消息为: " + message);
        System.out.println("客户端接收的消息数量=" + (++count));*/
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //使用客户端发送10条数据
        for (int i = 0; i < 10; i++) {
            String mes="庞庞大笨蛋";
            byte[] content = mes.getBytes(Charset.forName("utf-8"));
            int length = content.length;

            //创建协议包
            MessageProtocol messageProtocol=new MessageProtocol();
            messageProtocol.setContent(content);
            messageProtocol.setLen(length);
            ctx.writeAndFlush(messageProtocol);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("发生异常");
        ctx.close();
    }
}
