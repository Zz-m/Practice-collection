package cn.denghanxi.xgboost;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by dhx on 2021/9/1.
 */
public class Java8TimeTest {
    public static void main(String[] args) {
        LocalTime localTime = LocalTime.of(23,55);
        localTime = localTime.plusMinutes(231230);
        System.out.println(localTime.toString());


        LocalDate today = LocalDate.now();

        String s = today.format(DateTimeFormatter.ofPattern("yyyy-MM月dd"));
        System.out.println(s);
    }
}
