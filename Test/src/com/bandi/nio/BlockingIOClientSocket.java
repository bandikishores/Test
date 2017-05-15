package com.bandi.nio;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.stream.IntStream;

public class BlockingIOClientSocket {

    public static void main(String[] args) throws UnknownHostException, IOException {

        IntStream.range(1, 10).forEach(i -> {
            new Thread(() -> {
                Socket socket;
                try {
                    socket = new Socket("localhost", 1234);
                    socket.getOutputStream().write(("Client Socket" + i).getBytes());
                    System.out.println("Completed Write " + i);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }).start();
        });

    }

}
