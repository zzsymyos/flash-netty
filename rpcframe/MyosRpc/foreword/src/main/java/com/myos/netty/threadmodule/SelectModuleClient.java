package com.myos.netty.threadmodule;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

/**
 * @Author: wu sir
 * @Date: 2020/5/21 2:55 下午
 */
public class SelectModuleClient {

    public static void main(String[] args) {
        try {
            SocketChannel sc = SocketChannel.open(new InetSocketAddress("127.0.0.1",9001));
            Scanner scanner = new Scanner(System.in);
            sc.configureBlocking(false);
            Selector selector = Selector.open();
            sc.register(selector,SelectionKey.OP_READ);
            while (true) {
                ByteBuffer writeBuffer = ByteBuffer.wrap(scanner.next().getBytes("UTF-8"));
                sc.write(writeBuffer);

                // 读取响应
                ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                int num;
                if ((num = sc.read(readBuffer)) > 0) {
                    readBuffer.flip();

                    byte[] re = new byte[num];
                    readBuffer.get(re);

                    String result = new String(re, "UTF-8");
                    System.out.println("返回值: " + result);
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
