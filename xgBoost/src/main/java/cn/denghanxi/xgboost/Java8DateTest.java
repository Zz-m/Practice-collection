package cn.denghanxi.xgboost;

import java.time.LocalDate;

/**
 * Created by dhx on 2021/9/27.
 */
public class Java8DateTest {
    public static void main(String[] args) {
        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1L);
        System.out.println(today.toEpochDay() - yesterday.toEpochDay());
    }
}
