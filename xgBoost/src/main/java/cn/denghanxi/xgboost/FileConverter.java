package cn.denghanxi.xgboost;

import java.io.*;

/**
 * Created by dhx on 2021/7/16.
 */
public class FileConverter {
    public static void main(String[] args) throws IOException {
        PrintWriter dataWriter = new PrintWriter(new BufferedWriter(new FileWriter("C:\\Users\\dell\\Downloads\\log_s_c.txt", true)));
        BufferedReader reader = new BufferedReader(new FileReader(new File("C:\\Users\\dell\\Downloads\\log_s.txt")));
        String line;
        int counter = 0;
        while ((line = reader.readLine()) != null) {
            counter++;
            // process the line.
            System.out.println(line);

            String[] sp = line.split(" +");
            System.out.println(sp[0]);
            System.out.println(sp[1]);
            System.out.println(sp[2]);

            dataWriter.println(sp[1] + "    " + sp[2]);
        }
        dataWriter.flush();
        dataWriter.close();
        System.out.println("line: " + counter);
    }
}
