package com.example.chatbot.section;

import com.example.chatbot.Classes.Context;
import com.example.chatbot.Classes.Users;
import javafx.application.Platform;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

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
    private Context context;
    public Chat(Users user, Context context1){
        this.my_id = user.get_user_id();
        CreateInterFace();
        this.context = context1;
        new Thread(new Runnable() {
            @Override
            public void run() {
                Client client = new Client();
                client.run();
            }
        }).start();
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
//                client = new Socket("192.168.1.112", 8081);
                out = new PrintWriter(client.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                InputHandler inputHandler = new InputHandler();
                Thread t = new Thread(inputHandler);
                t.start();
                String inMessage;
                while((inMessage = in.readLine()) != null){
                    System.out.println("Server to message: " + inMessage);
//                    ArrayList<ArrayList> chats = new ArrayList<>();
                    String[] chats = inMessage.split("=");
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            VBox showChat = new VBox();
                            for(String chat : chats){
                                String[] chatInfos = chat.split("#");

                                if(chatInfos.length > 1){
                                    HBox stf = new HBox();
                                    Label l_chat = new Label();

                                    if(chatInfos[4].equals(getMy_id())){
                                        l_chat.setText(chatInfos[0]);
                                        l_chat.setStyle(
                                                "-fx-text-fill: white;" +
                                                "-fx-background-color: #9516b8;" +
                                                "-fx-padding: 5 10 5 10;" +
                                                "-fx-background-radius: 15;"
                                        );
                                        stf.setAlignment(Pos.CENTER_RIGHT);
                                    }else {
                                        if(getFriend_id().startsWith("gp")){
                                            l_chat.setText(chatInfos[4] + " : " + chatInfos[0]);
                                        }else {
                                            l_chat.setText(chatInfos[0]);
                                        }
                                        l_chat.setStyle(
                                                        "-fx-text-fill: white;" +
                                                        "-fx-background-color: #0b2eb8;" +
                                                        "-fx-padding: 5 10 5 10;" +
                                                        "-fx-background-radius: 15;"

                                        );
                                        stf.setAlignment(Pos.CENTER_LEFT);
                                    }
                                    stf.getChildren().add(l_chat);
                                    showChat.getChildren().add(stf);
                                }
                            }
                            showChat.setPrefWidth(435);
                            showChat.setSpacing(5);
                            sp.setContent(showChat);
                        }
                    });
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
                    if(getFriend_id().equals("")){
                        if(!getFriend_id().equals("ff")){
                            System.out.println("sdd=" + getFriend_id());
                        }
                    }else {
                        out.println(id);
                        firstData = false;
                    }
                    while(!done){
                        if(context.getMainStoped()){
                            out.println("/quit");
                            shutdown();
                        }else {
                            if(!getFriend_id().equals("")) {
                                if (firstData) {
                                    System.out.println("sssssssssssss=" + getFriend_id());
                                    out.println(getMy_id() + "=" + getFriend_id());
                                    firstData = false;
                                }
                                if (sendMsg) {
                                    System.out.println("send message:" + getSendText.getText());
                                    String message = getSendText.getText();
                                    String content = "";
                                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                                    LocalDateTime dateF = LocalDateTime.now();
                                    String dateNow = dtf.format(dateF);
                                    String seenDate = dateNow;
                                    String sendDate = dateNow;
                                    String my_id = getMy_id();
                                    String friend_id = getFriend_id();
                                    String info =
                                            message +"="+
                                            content +"="+
                                            seenDate +"="+
                                            sendDate+"="+
                                            my_id +"="+
                                            friend_id;
                                    out.println(info);
                                    sendMsg = false;
                                    getSendText.setText("");
                                }else if (getChangeFriend_id().startsWith("/changeToId")) {
                                    System.out.println("change id");
                                    out.println("/changeToId=" + getFriend_id());
                                    setChangeFriend_id("");
                                }
                            }
                        }
                        Thread.sleep(50);
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
        sp.setPadding(new Insets(20, 20, 20,20));
        sp.setPrefSize(500, 500);
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
        if(!firstData){
            System.out.println("ccc: " + getFriend_id());
            setChangeFriend_id("/changeToId");
        }
    }

    public String getChangeFriend_id() {
        return changeFriend_id;
    }

    public void setChangeFriend_id(String changeFriend_id) {
        this.changeFriend_id = changeFriend_id;
    }

    public void setMy_id(String my_id) {
        this.my_id = my_id;
    }
}
