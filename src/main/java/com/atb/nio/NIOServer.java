package com.atb.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @Author 呆呆
 * @Datetime 2022/1/8 13:53
 */
public class NIOServer {
    public static void main(String[] args) throws Exception {
        //差UN关键ServerSocketChannel -> ServerSocket
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        //得到一个Selector对象
        Selector selector = Selector.open();

        //绑定一个端口
        serverSocketChannel.socket().bind(new InetSocketAddress(6666));

        //非阻塞
        serverSocketChannel.configureBlocking(false);

        //把serverSocketChannel注册到selector 关心事件为OP_ACCEPT
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        //循环等待客户端的连接
        while (true) {
            //等待了1秒 如果没有事件发生,返回
            if (selector.select(1000) == 0) {//没有事件发生
                System.out.println("服务器等待了1秒,无连接");
                continue;
            }

            //如果返回的不是0 获取到SelectionKey集合
            //1.返回>0 表示已经获取到关注的事件
            //2.返回关注的事件的集合
            // 通过selectionKeys反向获取通道
            Set<SelectionKey> selectionKeys = selector.selectedKeys();

            //遍历 Set<SelectionKey> 使用迭代器
            Iterator<SelectionKey> keyIterator = selectionKeys.iterator();

            while (keyIterator.hasNext()) {
                //获取SelectionKey
                SelectionKey key = keyIterator.next();
                //根据key对应的通道发生的事件做相应的处理
                if (key.isAcceptable()) {//如果是OP_ACCEPT
                    //给该客户端生成一个SocketChannel
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    System.out.println("客户端连接成功 生成了一个socketChannel" + socketChannel.hashCode());
                    //将socketChannel设置为非阻塞
                    socketChannel.configureBlocking(false);
                    //将socketChannel注册到selector,关注事件为OP_READ 同时给socketChannel关联一个BUffer
                    socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                }
                if (key.isReadable()) {//发生了OP_READ
                    //通过该key反向获取到对应的Channel
                    SocketChannel channel = (SocketChannel) key.channel();
                    //获取到该Channel关联的buffer
                    ByteBuffer buffer = (ByteBuffer) key.attachment();
                    channel.read(buffer);
                    System.out.println("from client" + new String(buffer.array()));
                }

                //手动从集合中移除当前的selectKey
                keyIterator.remove();
            }
        }
    }
}
