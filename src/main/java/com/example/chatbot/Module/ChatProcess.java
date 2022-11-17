package com.example.chatbot.Module;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatProcess {
    public static void ChatServer(String my_id, String friend_id, ScrollPane sp){
        int counter = 0;
        try {
            Socket socket = new Socket("127.0.0.1", 3002);
//            Socket socket = new Socket("192.168.1.112", 3002);
            System.out.println("Connected.");
            // writing to server
            PrintWriter out = new PrintWriter(
                    socket.getOutputStream(), true);
            // reading from server
            BufferedReader in
                    = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()));
            String line = "";
            String result = "";
            while (!line.equals("Over")) {
                try {
                    line = my_id + "=" + friend_id;
                    out.println(line);
                    result = in.readLine();
                    sp.setContent(null);
                    String[] turshilt = result.split("=");
                    List<List<String>> listOfLists = new ArrayList<>();
                    HBox gg[] = new HBox[50];
                    Circle circle = null;
                    Label chatt = null;
                    chatt = new Label("Hi");
                    for(String i: turshilt){
                        listOfLists.add(List.of(i.split(",")));
                    }
                    System.out.println(listOfLists);
                    VBox vBox = new VBox();
                    vBox.setPrefSize(480, 600);
                    for(List s: listOfLists){
                        if(s.get(4).equals(my_id)){
                            chatt = new Label((String) s.get(0));
                            gg[counter] = new HBox(chatt);
                            gg[counter].setAlignment(Pos.CENTER_RIGHT);
                        }else {
                            circle = new Circle(0 ,0, 10);
                            chatt = new Label((String) s.get(0));
                            gg[counter] = new HBox(circle, chatt);
                            gg[counter].setAlignment(Pos.CENTER_LEFT);
                        }
                        vBox.getChildren().add(gg[counter]);
                        vBox.setPadding(new Insets(20));
                        counter++;
                    }
                    sp.setContent(vBox);
                    Thread.sleep(1000);
                    counter = 0;
                } catch (IOException i) {
                    System.out.println(i);
                }
            }
            out.close();
            in.close();
            socket.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public static void SendText(String value){
        try {
//            Socket socket = new Socket("127.0.0.1", 3002);
            Socket socket = new Socket("192.168.1.112", 3002);
            System.out.println("Connected.");
            // writing to server
            PrintWriter out = new PrintWriter(
                    socket.getOutputStream(), true);
            // reading from server
            BufferedReader in
                    = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()));
//            String line = "";
            String result = "";
            out.println(value);
            result = in.readLine();
            System.out.println("chatproces Send data: " + result);
            out.close();
            in.close();
            socket.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
