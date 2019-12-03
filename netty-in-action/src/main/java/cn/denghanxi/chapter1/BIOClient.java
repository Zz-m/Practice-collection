package cn.denghanxi.chapter1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class BIOClient {
    public static void main(String[] args) {
        try {
            Socket socket  = new Socket("127.0.0.1", 8848);
            PrintWriter out  = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out.println("hello");
            out.println("who are you");
            out.println("over");
            out.println("Done");
            String request;
            while (socket.isClosed() && (request = in.readLine()) != null) {
                System.out.println(request);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
