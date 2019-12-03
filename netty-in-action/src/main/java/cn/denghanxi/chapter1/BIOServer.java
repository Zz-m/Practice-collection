package cn.denghanxi.chapter1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class BIOServer {
    public static final int PORT_NUM = 8848;
    private static int counter = 0;

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT_NUM);
            System.out.println("System start and accept in " + PORT_NUM);
            Socket clientSocket = serverSocket.accept();
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            String request, response;
            while ((request = in.readLine()) != null) {
                if ("Done".equals(request)) {
                    System.out.println("get \"Done\", quit.");
                    break;
                }
                response = processRequest(request);
                out.println(response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String processRequest(String request) {
        counter++;
        return String.format("We receive %s, and everything is OK, counter : %d", request, counter);
    }
}
