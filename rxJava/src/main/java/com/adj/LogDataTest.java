package com.adj;

import java.io.*;
import java.text.DecimalFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

public class LogDataTest {

    private static DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    public static void main(String[] args) throws IOException {
        File file = new File("C:\\Users\\adj\\Downloads\\eeg-data.txt");
        BufferedReader fileReader = new BufferedReader(new FileReader(file));
        List<WidthRecord> widthRecordList = new ArrayList<>();

        String line;

        long start = -1;

        long pre_long = -1;

        long cur_long = -1;

        long max_width = -1;

        long max_width_index = -1;

        int counter = 0;

        while ((line = fileReader.readLine()) != null) {
            counter++;
            String[] sr = line.split(" ");
            if (pre_long == -1) {
                pre_long = Long.parseLong(sr[1]);
                start = pre_long;
                continue;
            }
            cur_long = Long.parseLong(sr[1]);
            long width = cur_long - pre_long;

            if (width < 0) {
                System.out.println("ERROR: " + cur_long);
            }

            if (width > max_width) {
                System.out.println("find new max: " + " value: " + width + " index: " + cur_long);
                max_width = width;
                max_width_index = cur_long;
            }

            if (width > 2 * 1000) {// > 10S
                widthRecordList.add(new WidthRecord(pre_long, cur_long));
            }

            pre_long = cur_long;
        }
        System.out.println("max_width: " + max_width + " max_width_index: " + max_width_index);
        System.out.println("Counter: " + counter);

        LocalDateTime startTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(start), TimeZone.getDefault().toZoneId());
        LocalDateTime endTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(cur_long), TimeZone.getDefault().toZoneId());

        Duration testDuration = Duration.between(startTime, endTime);
        System.out.println("测试时长: " + formatDuration(testDuration) + " 开始时间：" + startTime.format(timeFormatter) + " 结束时间:" + endTime.format(timeFormatter));

        long numberOfTheory = testDuration.getSeconds() * 128;
        System.out.println("理论数据量： " + numberOfTheory + "条，" + " 实际数据量： " + counter + "条.");
        System.out.println("估算丢包率： " + new DecimalFormat("#0.00").format((1 - ((double) counter) / ((double) numberOfTheory)) * 100) + "%");
        System.out.println("最大无数据时长：" + max_width + "毫秒");
        System.out.println("不合格记录数(大于2秒无数据)： " + widthRecordList.size());

        for (WidthRecord record : widthRecordList) {
            System.out.println(record);
        }
    }


    public static String formatDuration(Duration duration) {
        long seconds = duration.getSeconds();
        long absSeconds = Math.abs(seconds);
        String positive = String.format(
                "%d:%02d:%02d",
                absSeconds / 3600,
                (absSeconds % 3600) / 60,
                absSeconds % 60);
        return seconds < 0 ? "-" + positive : positive;
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
                    ", startTime=" + startTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) +
                    ", endTime=" + endTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) +
                    '}';
        }
    }
}
