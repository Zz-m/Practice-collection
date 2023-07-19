package com.adj.util;

/**
 * 助眠仪蓝牙原始数据转换工具类
 * Created by dhx on 2021/12/9.
 */
public class SleepAiderDataUtil {

    private static final float ADC = 32768F; //adc位数
    private static final int CIRCUIT_GAIN = 1000; //增益
    private static final float VOLTAGE_BASELINE = 4.096F; //电压基线

    /**
     * 解析助眠仪传来的脑电数据
     *
     * @param data 数据格式由嵌入式定义
     * @return 以伏表示的脑电 电压值
     */
    public static float[] parseRawEEGData(byte[] data) {
        if (data.length == 10) {
            int data1 = (data[2] << 8 & 0xFF00) | (data[3] & 0xFF);
            int data2 = (data[4] << 8 & 0xFF00) | (data[5] & 0xFF);
            int data3 = (data[6] << 8 & 0xFF00) | (data[7] & 0xFF);
            int data4 = (data[8] << 8 & 0xFF00) | (data[9] & 0xFF);
            float[] result = new float[4];
            result[0] = eegDataTransform(data1);
            result[1] = eegDataTransform(data2);
            result[2] = eegDataTransform(data3);
            result[3] = eegDataTransform(data4);
            return result;
        } else {
            return new float[0];
        }
    }

    /**
     * 32768是adc位数  1000是增益  4.096是电压基线  算出来的单位是V
     *
     * @param i raw data
     * @return data volt
     */
    private static float eegDataTransform(int i) {
        return (((float) i) - ADC) / ADC * VOLTAGE_BASELINE / CIRCUIT_GAIN;
    }

    private static float eegDataTransform2(int i) {
        float adc =32768F;
        float voltage_baseline = 4.096F;
        int circuit_gain = 1000;

        float data = i - adc;
        data = data / adc * voltage_baseline / circuit_gain;

        return data;
    }

    /**
     * 解析电刺激刺激电流强度
     *
     * @param in 原始数据
     * @return 刺激电流强度，单位μA
     */
    public static float parseStimulateCurrency(byte[] in) {
        if (in.length != 2) {
            return 0;
        }
        //高位在前
        int v = (in[0] << 8 & 0xFF00) | (in[1] & 0xFF);
        return v * 3;
    }

    public static void main(String[] args) {
        byte[] data = new byte[10];
        for (int i = 0; i < data.length; i++) {
            data[i] = (byte) i;
        }
        long before = System.currentTimeMillis();
        for (long i = 0; i < 10000 * 10000L; i++) {
            float[] a = parseRawEEGData(data);
        }
        long after = System.currentTimeMillis();

        System.out.println("用时：" + (after - before) + " ms");
    }
}
