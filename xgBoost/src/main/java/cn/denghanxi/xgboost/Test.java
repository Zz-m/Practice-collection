package cn.denghanxi.xgboost;

import java.nio.charset.StandardCharsets;

/**
 * Created by dhx on 2021/7/16.
 */
public class Test {
    public static void main(String[] args) {
        int a = 0xa2;

        byte b = (byte) a;


        byte[] bytes = {0, 0x00, b, 0x00, 0x00, 0x54};

        byte[] aa = new byte[10];
//        aa[0]= -127;
        aa[0]= -127;
        System.out.println(aa[0] | 0x00);
        int c = aa[0]&0xff;
        System.out.println(c);

        int ii = 0xFFFFFF81;
        System.out.println(ii);
    }

    private static final byte[] HEX_ARRAY = "0123456789ABCDEF".getBytes(StandardCharsets.UTF_8);

    private static String bytesToHex(byte[] bytes) {
        byte[] hexChars = new byte[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars, StandardCharsets.UTF_8);
    }

    private static void change(byte[] data) {
        int red = 0, i_red = 0;
        red += data[0] << 16 & 0xFF0000;
        red += data[1] << 8 & 0xFF00;
        red += data[2] & 0xFF;
        red = red & 0x03ffff;
        i_red |= data[3] << 16;
        i_red |= data[4] << 8;
        i_red |= data[5];
        i_red = i_red & 0x03ffff;
        System.out.println("" + red + "    " + i_red);
    }

    private static void toBinary(int number, int base) {
        final boolean[] ret = new boolean[base];
        for (int i = 0; i < base; i++) {
            ret[base - 1 - i] = (1 << i & number) != 0;
            if (ret[base - 1 - i]) {
                System.out.print(1);
            } else {
                System.out.print(0);
            }
        }
        System.out.println();
    }
}
