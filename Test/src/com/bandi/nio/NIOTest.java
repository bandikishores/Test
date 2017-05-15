package com.bandi.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

public class NIOTest {

    public static void main(String[] args) throws IOException {
        RandomAccessFile aFile = new RandomAccessFile("/home/kishore/tmp/test.txt", "rw");
        FileChannel inChannel = aFile.getChannel();

        ByteBuffer buf = ByteBuffer.allocate(48);
        
        ByteBuffer buf1 = ByteBuffer.allocate(48);
        ByteBuffer buf2 = ByteBuffer.allocate(48);
        buf1.put((byte) 1);
        buf2.put((byte) 1);
        buf1.flip();
        buf2.flip();
        
        System.out.println(buf1.equals(buf2));
        
        buf1.put((byte) 2);
        buf2.put((byte) 3);
        System.out.println(buf1.equals(buf2));

        int bytesRead = inChannel.read(buf);
        SocketChannel.open().register(null, SelectionKey.OP_READ);
        for(int i = 0; i < 3; i++) {
            
            /*System.out.println("Pointer:" + aFile.getFilePointer());

            System.out.println("Read " + bytesRead);
            System.out.println("");
            System.out.println("");*/
            buf.flip();

            for(int j = 0; j < 3; j++) {
                System.out.print((char) buf.get());
            }
            System.out.println("");

            buf.compact();
            bytesRead = inChannel.read(buf);
        }
        aFile.close();
    }
}