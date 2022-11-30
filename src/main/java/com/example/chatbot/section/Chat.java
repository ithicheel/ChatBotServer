package com.example.chatbot.section;

import com.example.chatbot.Classes.Context;
import com.example.chatbot.Classes.Users;
import com.example.chatbot.Module.ChatProcess;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Chat extends VBox {
    private Label username = null;
    private ScrollPane sp = null;
    private String my_id = "";
    private String friend_id = "";
    private TextArea getSendText;
    private Button sendTextbtn;
    public Chat(Users user, Context context){
        this.my_id = user.get_user_id();
        CreateInterFace();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Socket socket = null;
                try {
//                    socket = new Socket("127.0.0.1", 3002);
                    socket = new Socket("192.168.1.112", 3002);
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
                        HBox showChat[] = new HBox[50];
                        Circle circle = null;
                        Label chatt = null;
                        for(String g: turshilt){
                            listOfLists.add(List.of(g.split(",")));
                        }
//                        System.out.println(listOfLists);
                        VBox vBox = new VBox();
                        vBox.setPrefSize(480, 600);
                        int counter = 0;
                        for(List s: listOfLists){
                            if(s.size() > 1){
                                if(s.get(4).equals(my_id)){
                                    chatt = new Label((String) s.get(0));
                                    chatt.setPadding(new Insets(5, 10, 5, 10));
                                    chatt.setStyle("-fx-background-color: #D9D9D9; -fx-background-radius: 10;");
                                    showChat[counter] = new HBox(chatt);
                                    showChat[counter].setAlignment(Pos.CENTER_RIGHT);
                                }else {
                                    circle = new Circle(0 ,0, 15);
                                    chatt = new Label((String) s.get(0));
                                    chatt.setPadding(new Insets(5, 10, 5, 10));
                                    chatt.setStyle("-fx-background-color: #794ADD; -fx-background-radius: 10; -fx-text-fill : #ffffff");
                                    showChat[counter] = new HBox(circle, chatt);
                                    showChat[counter].setAlignment(Pos.CENTER_LEFT);
                                    showChat[counter].setSpacing(5);
                                }
                                vBox.getChildren().add(showChat[counter]);
                                vBox.setPadding(new Insets(20));
                                vBox.setSpacing(5);
                                counter++;
                            }
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
        sendTextbtn.setOnAction(actionEvent -> {
            String text = getSendText.getText().replaceAll("\n", " ");
            if(text.equals("")){
                System.out.println("text hooson bna");
            }else {
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime dateF = LocalDateTime.now();
                String dateNow = dtf.format(dateF);
                String sendData = text + "=" +  "" + "=" + dateNow  + "=" + dateNow + "=" + getMy_id() + "=" + getFriend_id();
                System.out.println("Send text: " + sendData);
                ChatProcess.SendText(sendData);
//                String[] f = sendData.split("=");
//                System.out.println(f[0]);
                getSendText.clear();
            }
        });
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
        sp.setStyle("-fx-focus-color:  #d9d9d9;");
        /***
         * Settings
         */
        VBox sendText = new VBox();
        getSendText = new TextArea();
        getSendText.setPromptText("Enter your text here");
        getSendText.setPrefColumnCount(2);
        getSendText.setPrefHeight(70);
        sendTextbtn = new Button("Send");
        sendTextbtn.setAlignment(Pos.CENTER_RIGHT);
        sendText.getChildren().addAll(getSendText, sendTextbtn);
        this.setMinHeight(700);
        this.setMinWidth(500);
        this.setBorder(Border.stroke(Color.BLACK));
        this.getChildren().add(sendText);
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
