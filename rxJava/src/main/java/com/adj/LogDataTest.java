package com.adj;

import java.io.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

public class LogDataTest {
    public static void main(String[] args) throws IOException {
        File file = new File("C:\\Users\\adj\\Downloads\\eeg-data.txt");
        BufferedReader fileReader = new BufferedReader(new FileReader(file));
        List<WidthRecord> widthRecordList = new ArrayList<>();

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

            if (width > 10 * 1000) {// > 10S
                widthRecordList.add(new WidthRecord(pre_long, cur_long));
            }

            pre_long = cur_long;
        }
        System.out.println("max_width: " + max_width + " max_width_index: " + max_width_index);
        System.out.println("Counter: " + counter);

        System.out.println("不合格记录数： " + widthRecordList.size());

        for (WidthRecord record : widthRecordList) {
            System.out.println(record);
        }
    }

    static class WidthRecord {

        private final long width;

        private final long start;
        private final long end;
        private final LocalDateTime startTime;
        private final LocalDateTime endTime;

        public WidthRecord(long start, long end) {
            width = end - start;
            this.start = start;
            this.end = end;
            this.startTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(start), TimeZone.getDefault().toZoneId());
            this.endTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(end), TimeZone.getDefault().toZoneId());
        }

        @Override
        public String toString() {
            return "WidthRecord{" +
                    "width=" + width +
                    ", start=" + start +
                    ", end=" + end +
                    ", startTime=" + startTime +
                    ", endTime=" + endTime +
                    '}';
        }
    }
}
