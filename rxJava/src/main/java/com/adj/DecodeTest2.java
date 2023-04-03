package com.adj;

import com.adj.terminal.TerminalMessage;
import com.adj.terminal.TerminalMessageCodec;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DecodeTest2 {

    private static byte[] testData = {16, 0, 0, 0, 1, 0, 1, 0, 3, 1, 14, 0, 0, 13, 8, 0, 2, 5, 7, 0, 0, 2, 13, 0, 2, 11, 11, 0, 0, 0, 9, 0, 0, 0, 0, 0, 0, 2, 14, 0, 0, 2, 6, 0, 2, 3, 14, 0, 3, 11, 0, 0, 0, 0, 0, 0, 0, 1, 2, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 14, 0, 9, 9, 17};

    private static byte[] testData2 = {16, 0, 0, 0, 1, 0, 1, 0, 3, 1, 14, 0, 0, 13, 11, 0, 2, 4, 15, 0, 0, 2, 10, 0, 2, 10, 1, 0, 0, 0, 9, 0, 0, 0, 0, 0, 0, 2, 14, 0, 0, 2, 5, 0, 1, 13, 14, 0, 3, 11, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    private static byte[] testData3 = {0, 2, 0, 0, 0, 0, 0, 3, 4, 11, 12, 17};

    public static void main(String[] args) {
        TransportEncoder encoder = new TransportEncoder();
        TransportDecoder decoder = new TransportDecoder();

        TerminalMessageCodec codec = new TerminalMessageCodec();
//        byte[] in = new byte[10];
//        for (int i= 0; i < 10; i++) {
//            in[i] = (byte) (Math.random() * 100);
//        }
//        for (byte b : in) {
//            System.out.print(" " + b);
//        }
//        System.out.println();
//        byte[] out = decoder.decode(encoder.encode(in));
//        for (byte b : out) {
//            System.out.print(" " + b);
//        }
//        System.out.println();
        byte[] out = decoder.decode(testData2);
        if (out != null) {
            for (byte b : out) {
                System.out.print(" " + b);
            }
        } else {
            System.out.println("data1 out null");
        }
        byte[] out2 = decoder.decode(testData3);
        if (out2 != null) {
            for (byte b : out2) {
                System.out.print(" " + b);
            }
        } else {
            System.out.println("data2 out null");
        }

//        System.out.println();
//
//        TerminalMessage message = codec.bytesToMessage(out);
//
//        System.out.println(message);
    }

    private final static class TransportEncoder {

        byte[] encode(byte[] in) {
            byte[] result = new byte[in.length * 2 + 2];
            result[0] = 0x10;
            result[in.length * 2 + 1] = 0x11;
            for (int i = 0; i < in.length; i++) {
                result[i * 2 + 1] = (byte) (in[i] >> 4 & 0xF);
                result[i * 2 + 2] = (byte) (in[i] & 0xF);
            }
            return result;
        }
    }

    //有状态，线程不安全
    private final static class TransportDecoder {

        private final List<Byte> outList = new ArrayList<>();
        private final ByteBuffer inBuffer = ByteBuffer.allocate(512);

        private boolean isBroken = true;


        byte[] decode(byte[] in) {
            if (in == null) return null;
            if (inBuffer.remaining() < in.length) {
                //TODO logger.error
                inBuffer.clear();
                isBroken = true;
                outList.clear();
                return null;
            }

            inBuffer.put(in);
            inBuffer.flip();
            while (inBuffer.remaining() > 0) {
                byte b1 = inBuffer.get();
                if (b1 < 0 || b1 > 0x11) {
                    //TODO log
                    isBroken = true;
                    outList.clear();
                    continue;
                }
                if (isBroken && b1 != 0x10) continue;
                if (b1 == 0x10) {
                    isBroken = false;
                    outList.clear();
                    continue;
                }
                if (b1 == 0x11) {
                    isBroken = true;
                    inBuffer.compact();
                    if (outList.size() > 0) {
                        byte[] result = new byte[outList.size()];
                        for (int i = 0; i < outList.size(); i++) {
                            result[i] = outList.get(i);
                        }
                        outList.clear();
                        return result;
                    } else {
                        return null;
                    }
                }

                if (inBuffer.remaining() == 0) {
                    System.out.println(inBuffer);
                    inBuffer.compact();
                    inBuffer.put(b1);
                    return null;
                }

                byte b2 = inBuffer.get();
                if (b2 < 0 || b2 > 0x0F) {
                    //TODO log
                    isBroken = true;
                    outList.clear();
                    inBuffer.clear();
                    continue;
                }
                byte b = (byte) ((b1 << 4 & 0xF0) | (b2 & 0xF));
                outList.add(b);
            }
            inBuffer.compact();
            return null;
        }
    }
}
