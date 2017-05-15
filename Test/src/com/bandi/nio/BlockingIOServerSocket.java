package com.bandi.nio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BlockingIOServerSocket {

    public static void main(String[] args) throws IOException, InterruptedException {
        try (ServerSocket serverSocket = new ServerSocket(1234)) {
            ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(10);
            while (true) {
                System.out.println("Waiting ");
                Socket socket = serverSocket.accept();
                newFixedThreadPool.execute(() -> {
                    try {
                        System.out.println("Accepted ");
                        Thread.sleep(1000);
                        BufferedReader bufferedReader =
                                new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        System.out.println(bufferedReader.readLine());
                    } catch (IOException | InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        Thread.currentThread().interrupt();
                    }
                });

            }
        }
    }


}
