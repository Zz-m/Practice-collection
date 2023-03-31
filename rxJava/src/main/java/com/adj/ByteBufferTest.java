package com.adj;

import java.nio.ByteBuffer;

public class ByteBufferTest {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(10);

        System.out.println(buffer);

        buffer.put((byte) 0x01);
        buffer.put((byte) 0x02);
        buffer.put((byte) 0x03);
        buffer.put((byte) 0x04);
        buffer.put((byte) 0x05);
        System.out.println(buffer);

        buffer.flip();

        System.out.println("get: " + buffer.get());
        System.out.println("get: " + buffer.get());
        System.out.println("get: " + buffer.get());

        System.out.println(buffer);

        buffer.compact();
        System.out.println("compat");
        System.out.println(buffer);
        buffer.put((byte) 0xAA);

        buffer.flip();

        System.out.println("get: " + buffer.get());
        System.out.println("get: " + buffer.get());
        System.out.println("get: " + buffer.get());


        System.out.println(buffer);
        System.out.println("remaining: " + buffer.remaining());
    }
}
