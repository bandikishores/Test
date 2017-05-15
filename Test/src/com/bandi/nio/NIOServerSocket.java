package com.bandi.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class NIOServerSocket {

    public static void main(String[] args) throws IOException {
        try (ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()) {

            serverSocketChannel.socket().bind(new InetSocketAddress(1234));
            serverSocketChannel.configureBlocking(false);
            
            while (true) {
                SocketChannel socketChannel = serverSocketChannel.accept();

            }
        }
    }

}
