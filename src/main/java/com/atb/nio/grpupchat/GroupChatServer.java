package com.atb.nio.grpupchat;

import jdk.nashorn.internal.ir.CallNode;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * @Author 呆呆
 * @Datetime 2022/1/8 15:21
 */
public class GroupChatServer {
    private Selector selector;

    private ServerSocketChannel listenChannel;

    private static final int PORT = 6666;

    public GroupChatServer() {
        try {
            //得到选择器
            selector = Selector.open();
            //ServerSocketChannel
            listenChannel = ServerSocketChannel.open();
            //绑定端口
            listenChannel.socket().bind(new InetSocketAddress(PORT));
            //设置非阻塞
            listenChannel.configureBlocking(false);
            //注册到selector
            listenChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void lesten() {

        //循环处理
        try {
            while (true) {
                int count = selector.select(2000);
                if (count > 0) {//有事件处理
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        //监听到了ACCEPT
                        if (key.isAcceptable()) {
                            SocketChannel sc = listenChannel.accept();
                            sc.configureBlocking(false);
                            //将sc注册到selector
                            sc.register(selector, SelectionKey.OP_READ);
                            //提示 XXX上线了
                            System.out.println(sc.getRemoteAddress().toString().substring(1) + " 上线了");
                        }

                        if (key.isReadable()) {//通道发生READ,通道是可读状态
                            //处理读
                            readData(key);
                        }

                        //当前的key删除 防止重复处理
                        iterator.remove();
                    }
                } else {

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
    }

    //读取客户端消息
    private void readData(SelectionKey key) {
        //定义一个SocketChannel
        SocketChannel channel = null;
        try {
            //取到关联的channel
            channel = (SocketChannel) key.channel();
            //创建buffer
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            int count = channel.read(buffer);
            //根据count的值做处理
            if (count > 0) {
                //把缓冲区的数据转为字符串
                String msg = new String(buffer.array());
                System.out.println("from client:" + msg);
                //向其他客户端转发想消息(去掉自己)
                sendInfoToOtherClients(msg, channel);
            }
        } catch (IOException e) {
            try {
                System.out.println(channel.getRemoteAddress() + "离线了");
                //取消注册
                key.channel();
                //关闭通道
                channel.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    //转发消息给其他客户端
    private void sendInfoToOtherClients(String msg, SocketChannel self) throws IOException {
        System.out.println("服务器转发消息中...");
        //遍历 所有注册到selector上的SocketChannel,兵排除自己
        for (SelectionKey key : selector.keys()) {
            //通过key 取出对应SocketChannel
            Channel targetChannel = key.channel();
            //排除自己
            if (targetChannel instanceof SocketChannel && targetChannel != self) {
                //转型
                SocketChannel dest = (SocketChannel) targetChannel;
                //将msg存储到Buffer
                ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
                //将buffer的数据写入到Channel
                dest.write(buffer);
            }
        }
    }

    public static void main(String[] args) {
        GroupChatServer groupChatServer = new GroupChatServer();
        groupChatServer.lesten();
    }
}
