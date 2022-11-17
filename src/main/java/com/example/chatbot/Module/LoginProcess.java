package com.example.chatbot.Module;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class LoginProcess {
    private String value;
    public LoginProcess(String value){
        this.value = value;
    }
    public String LoginServer(){
        try {
//            Socket socket = new Socket("127.0.0.1", 3001);
            Socket socket = new Socket("192.168.1.112", 3001);
            System.out.println("Connected.");
            // writing to server
            PrintWriter out = new PrintWriter(
                    socket.getOutputStream(), true);
            // reading from server
            BufferedReader in
                    = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()));
            String line = this.value;
            // String line = "null";
            out.println(line);
            String result = in.readLine();
//            System.out.println(in.readLine());
            // socket close
            out.close();
            in.close();
            socket.close();
            System.out.println(result);
            return result;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
