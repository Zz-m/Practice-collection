package com.adj;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

public class BadiDateTest {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            try {
                System.out.println("start date:");
                String startInputString = sc.nextLine();
                String[] firstInputs = startInputString.split(" ");
                LocalDate firstDay = LocalDate.of(Integer.parseInt(firstInputs[0]), Integer.parseInt(firstInputs[1]), Integer.parseInt(firstInputs[2]));


                System.out.println("target date:");
                String targetInputString = sc.nextLine();
                String[] targetInputs = targetInputString.split(" ");
                LocalDate targetDay = LocalDate.of(Integer.parseInt(targetInputs[0]), Integer.parseInt(targetInputs[1]), Integer.parseInt(targetInputs[2]));

                long days = ChronoUnit.DAYS.between(firstDay, targetDay) + 1;

                System.out.println("From: " + firstDay + " to: " + targetDay);
                System.out.println("total days: " + days);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
