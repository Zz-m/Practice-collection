package cn.denghanxi.s4;

/**
 * Created by 邓晗熙 on 2020/2/17
 */
public class Sample {
    static {
        System.out.println("static method.");
    }

    public Sample() {
        System.out.println("constructor");
    }

    public static void main(String[] args) {
        Sample sample = new Sample();
    }
}
