package com.example.chatbot.section;

import com.example.chatbot.Classes.Context;
import com.example.chatbot.Classes.Users;
import com.example.chatbot.Module.LoginProcess;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Login extends Stage {
    private Button loginBtn = null;
    private TextField utf = null;
    private TextField ptf = null;
    private String[] userInfo;
    public Login(Stage stage, HBox root, Context context) throws IOException {
//        this.users = user;

        Scene loginScene = new Scene(CreateInterFace(), 300, 300);
        loginBtn.setOnAction(actionEvent -> {
            String ff = utf.getText() + "=" + ptf.getText();
            LoginProcess loginProcess = new LoginProcess(ff);
            String result = loginProcess.LoginServer();
            if(result == null){
                System.out.println("Aldaa garlaa login hiihed null irsen");
            }else if (result.equals("tanii password esvel email buruu bna.,")){
                System.out.println("tanii password esvel email buruu bna.");
            }else if (result.equals("")) {
                System.out.println("tanii password esvel email buruu bna..");
            }else {
                userInfo = result.split(",");
                Users user = new Users(userInfo[0], userInfo[1], userInfo[2], userInfo[3], userInfo[4], userInfo[5], userInfo[6]);
                About about = new About(user);
                Controller controller = null;
                Chat chat = null;
                FriendList fl = null;
                System.out.println("22");
                try {
                    controller = new Controller();
                    chat = new Chat(user, context);
                    fl = new FriendList(chat);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                // Add section
                root.getChildren().addAll(controller, fl, chat,about);
                this.close();
                stage.show();
            }
        });
        // Setup
        this.setTitle("Login");
        this.setScene(loginScene);
        this.initOwner(stage);
        this.show();
        this.setOnCloseRequest(EventHandler -> {
            stage.close();
        });
    }

    public String[] getUserInfo() {
        return userInfo;
    }

    private HBox CreateInterFace(){
        Label username = new Label("Email");
        Label password = new Label("Password");
         utf = new TextField();
         ptf = new TextField();
        utf.setText("bat44512@gmail.com");
        ptf.setText("12345678");
        loginBtn  = new Button("Login");
        VBox loginChat = new VBox(username, utf, password, ptf, loginBtn);
        loginChat.setSpacing(10);
        loginChat.setAlignment(Pos.CENTER);
        HBox loginRoot = new HBox(loginChat);
        loginRoot.setAlignment(Pos.CENTER);
        return loginRoot;
    }
}
