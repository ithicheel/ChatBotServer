package com.example.chatbot.section;

import com.example.chatbot.Classes.Context;
import com.example.chatbot.Classes.Users;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.Border;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Chat extends VBox {
    private Label username = null;
    private ScrollPane sp = null;
    private String my_id = "";
    private String friend_id = "";
    private String changeFriend_id = "";
    private TextField getSendText;
    private Button sendTextbtn;
    private boolean sendMsg = false;
    private boolean firstData = true;
    public Chat(Users user, Context context){
        this.my_id = user.get_user_id();
        CreateInterFace();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Client client = new Client();
                client.run();
            }
        }).start();
        System.out.println("fsdfsdfsdfkhsfskhfslkfhsf=" + getFriend_id());
        sendTextbtn.setOnAction(actionEvent -> {
            if(!getSendText.getText().equals("")){
                sendMsg = true;
            }
        });
    }
    private class Client implements Runnable {
        private Socket client;
        private BufferedReader in;
        private PrintWriter out;
        private boolean done;
        @Override
        public void run() {
            try {
                client = new Socket("127.0.0.1", 8081);
                out = new PrintWriter(client.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                InputHandler inputHandler = new InputHandler();
                Thread t = new Thread(inputHandler);
                t.start();
                String inMessage;
                while((inMessage = in.readLine()) != null){
                    System.out.println("Server to message: " + inMessage);
                }
            } catch (Exception e){
                // TODO: handle
                shutdown();
            }
        }
        public void shutdown(){
            done = true;
            try {
                in.close();
                out.close();
                if(!client.isClosed()){
                    client.close();
                }
            } catch (Exception e){
                // ignore
            }
        }
        class InputHandler implements Runnable {
            @Override
            public void run() {
                try {
//                    BufferedReader inReader = new BufferedReader(new InputStreamReader(System.in));
                    String id = getMy_id()+"="+ getFriend_id();
                    String info = "=dsdsds=2022-06-23 14:50:30=2022-06-23 14:50:30=zxcv1234zxcv1234=1234zsdlk32333";
                    if(getFriend_id().equals("")){
                        if(!getFriend_id().equals("ff")){
                            System.out.println("sdd=" + getFriend_id());
                        }
                    }else {
                        out.println(id);
                        firstData = false;
                    }
                    while(!done){
//                        System.out.println("t=" + getFriend_id() );
                        if(!getFriend_id().equals("")){
//                            System.out.println("DSds");
                            if(firstData){
                                System.out.println("sssssssssssss=" + getFriend_id());
                                out.println(getMy_id() + "=" + getFriend_id());
                                firstData = false;
                            }
//                            String message = inReader.readLine();
                            if(sendMsg){
                                if(changeFriend_id.startsWith("/quit")){
                                    out.println("exit=" +id);
//                                    inReader.close();
                                    shutdown();
                                }if(changeFriend_id.startsWith("/changeToId")){
                                    out.println("/changeToId" + getFriend_id());
                                } else {
                                    out.println(getSendText.getText() + info);
                                }
                                sendMsg = false;
                            }
                        }
                    }
                } catch (Exception e){
                    shutdown();
                }
            }
        }
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
        getSendText = new TextField();
        getSendText.setPromptText("Enter your text here");
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
