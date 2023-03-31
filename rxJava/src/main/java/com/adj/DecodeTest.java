package com.adj;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class DecodeTest {

    public static void main(String[] args) {
        TransportEncoder encoder = new TransportEncoder();
        TransportDecoder decoder = new TransportDecoder();
        byte[] in = new byte[]{1, 2};
        System.out.println(ByteUtil.bytesToHexString(encoder.encode(in)));
        byte[] out = decoder.decode(encoder.encode(in));
        if (out != null) {
            System.out.println(ByteUtil.bytesToHexString(out));
        } else {
            System.out.println("null");
        }


    }

    private final static class TransportEncoder {
        private static final byte[] DIC = new byte[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

        byte[] encode(byte[] in) {
            byte[] result = new byte[in.length * 2 + 2];
            result[0] = 0x3A;
            result[in.length * 2 + 1] = 0x0A;
            for (int i = 0; i < in.length; i++) {
                result[i * 2 + 1] = DIC[in[i] >> 4 & 0xF];
                result[i * 2 + 2] = DIC[in[i] & 0xF];
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
            while (inBuffer.remaining() > 2) {
                byte b1 = inBuffer.get();
                byte b2 = inBuffer.get();
                if (b1 < 0 || b1 > 15 || b2 < 0 || b2 > 15) {
                    //TODO log
                    isBroken = true;
                    outList.clear();
                    inBuffer.clear();
                    break;
                }
                byte b = (byte) ((b1 << 4 & 0xF0) | (b2 & 0xF));
                if (isBroken && b != 0x3A) {
                    continue;
                }
                if (b == 0x3A) {
                    isBroken = false;
                    continue;
                }
                if (b == 0x0A) {
                    isBroken = true;
                    byte[] result = new byte[outList.size()];
                    for (int i = 0; i < outList.size(); i++) {
                        result[i] = outList.get(i);
                    }
                    outList.clear();
                    inBuffer.compact();
                    return result;
                }


            }
            inBuffer.compact();
            return null;
        }
    }
}
