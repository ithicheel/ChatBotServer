package com.example.chatbot.section;

import com.example.chatbot.Classes.Context;
import com.example.chatbot.Classes.Users;
import com.example.chatbot.Module.ChatProcess;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Chat extends VBox {
    private Label username = null;
    private ScrollPane sp = null;
    private String my_id = "";
    private String friend_id = "1234zsdlk32333";
    private boolean stoped = true;
    public Chat(Users user, Context context){
        this.my_id = user.get_user_id();
        CreateInterFace();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Socket socket = null;
                try {
                    socket = new Socket("127.0.0.1", 3002);
                    //Socket socket = new Socket("192.168.1.112", 3002);
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
                    while (!context.getMainStoped()) {
                        line = getMy_id() + "=" + getFriend_id();
                        out.println(line);
                        result = in.readLine();
                        String[] turshilt = result.split("=");
                        List<List<String>> listOfLists = new ArrayList<>();
                        HBox gg[] = new HBox[50];
                        Circle circle = null;
                        Label chatt = null;
                        chatt = new Label("Hi");
                        for(String g: turshilt){
                            listOfLists.add(List.of(g.split(",")));
                        }
//                        System.out.println(listOfLists);
                        VBox vBox = new VBox();
                        vBox.setPrefSize(480, 600);
                        int counter = 0;
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
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                sp.setContent(vBox);
                                try {
                                    Thread.sleep(10);
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        });
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    out.close();
                    in.close();
                    socket.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }
    private void CreateInterFace(){
        HBox hb = new HBox();
        Circle circle = new Circle(0 ,0, 20);
        username = new Label("example");
        hb.getChildren().add(circle);
        hb.getChildren().add(username);
        hb.setAlignment(Pos.CENTER_LEFT);
        hb.setPadding(new Insets(12, 20, 13, 20));
        hb.setSpacing(10);
        this.getChildren().add(hb);
        // Scroll panel
        sp = new ScrollPane();
        sp.setPadding(new Insets(20, 0, 0 ,0));
        sp.setPrefSize(360, 500);
        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        this.getChildren().add(sp);
        /***
         * Settings
         */
        this.setMinHeight(700);
        this.setMinWidth(500);
        this.setBorder(Border.stroke(Color.BLACK));
    }
    public String getUsername() {
        return username.getText();
    }
    public String getFriend_id() {
        return friend_id;
    }
    public String getMy_id() {
        return my_id;
    }
    public void setUsername(String name) {
        username.setText(name);
    }
    public void setFriend_id(String friend_id) {
        this.friend_id = friend_id;
    }
    public void setMy_id(String my_id) {
        this.my_id = my_id;
    }
}
