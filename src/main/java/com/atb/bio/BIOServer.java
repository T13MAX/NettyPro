package com.atb.bio;


import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * BIO 传统的java io,blocking io
 * 一个线程对应一个连接
 * NIO有选择器(多路复用器) 一个线程通过选择器同时处理多个连接
 *
 * @Author 呆呆
 * @Datetime 2021/9/11 11:27
 */
public class BIOServer {
    public static void main(String[] args) throws Exception{

        //1线程池
        //2如果有客户端连接 就创建一个线程 与之通讯(写一个单独的方法
        //ExecutorService是一个线程池接口
        ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();

        //创建一个ServerSocket
        final ServerSocket serverSocket = new ServerSocket(6666);

        System.out.println("服务器启动了");

        while(true){
            //进行监听 等待客户端连接
            System.out.println("等待连接...");//服务器卡在这 啥也干不了 有连接以后 创建一个新线程和客户端交互 主线程回到这继续阻塞
            final Socket socket = serverSocket.accept();//会阻塞
            System.out.println("连接到一个客户端");

            //创建一个线程与之通讯
            newCachedThreadPool.execute(new Runnable() {
                public void run() {
                    //可以和客户端通讯
                    handler(socket);
                }
            });
        }

    }

    //handler方法 与客户端通讯
    public static void handler(Socket socket){
        try {
            //System.out.println("线程id="+Thread.currentThread().getId()+"  名字="+Thread.currentThread().getName());
            byte[] bytes = new byte[1024];
            //通过socket获取输入流
            InputStream inputStream = socket.getInputStream();
            while(true){
                //System.out.println("线程id="+Thread.currentThread().getId()+"  名字="+Thread.currentThread().getName());
                System.out.println("read...");
                int read = inputStream.read(bytes);//返回的是下标 //没读到会阻塞
                if(read!=-1){
                    System.out.println(new String(bytes,0,read));//输出客户端发送的数据
                }else{
                    break;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            System.out.println("关闭和client的连接");
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
