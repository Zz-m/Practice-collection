package com.adj;

import java.io.*;

public class LogDataTest {
    public static void main(String[] args) throws IOException {
        File file = new File("C:\\Users\\adj\\Downloads\\eeg-data-48h.txt");
        BufferedReader fileReader = new BufferedReader(new FileReader(file));

        String line;

        long pre_long = -1;

        long max_width = -1;

        long max_width_index = -1;

        int counter = 0;

        while ((line = fileReader.readLine()) != null) {
            counter++;
            String[] sr = line.split(" ");
            if (pre_long == -1) {
                pre_long = Long.parseLong(sr[1]);
                continue;
            }
            long cur_long = Long.parseLong(sr[1]);
            long width = cur_long - pre_long;

            if (width < 0) {
                System.out.println("ERROR: " + cur_long);
            }

            if (width > max_width) {
                System.out.println("find new max: " + " value: " + width + " index: " + cur_long);
                max_width = width;
                max_width_index = cur_long;
            }
            pre_long = cur_long;
        }
        System.out.println("max_width: " + max_width + " max_width_index: " + max_width_index);
        System.out.println("Counter: " + counter);
    }
}
