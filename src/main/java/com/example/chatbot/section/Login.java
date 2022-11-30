package com.example.chatbot.section;

import com.example.chatbot.Classes.Context;
import com.example.chatbot.Classes.Users;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.mashape.unirest.http.Unirest;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;


public class Login extends Stage {
    private Button loginBtn = null;
    private TextField utf = null;
    private TextField ptf = null;
    public Login(Stage stage, HBox root, Context context) throws IOException {
//        this.users = user;
        Scene loginScene = new Scene(CreateInterFace(), 300, 300);
        loginBtn.setOnAction(actionEvent -> {
            String url = "http://localhost:8080/user/login/" + utf.getText() + "/" + ptf.getText();
             try {
                HttpResponse<JsonNode> apiResponse = Unirest.post(url)
                        .header("accept", "application/json")
                        .asJson();
                JSONArray responseJsonAsString = apiResponse.getBody().getArray();
                if(responseJsonAsString.length() == 1) {
                    JSONObject msg = responseJsonAsString.getJSONObject(0);
                    if(msg.get("status").equals(false)){
                        System.out.println("Ddd");
                    }
                }else if(responseJsonAsString.length() == 2) {
                    JSONObject info = responseJsonAsString.getJSONObject(0);
                    Users user = new Users(
                            info.getString("user_id"),
                            info.getString("username"),
                            info.getString("desc"),
                            info.getString("phone"),
                            info.getString("email"),
                            info.getString("status"),
                            "dsfsdf");
                    About about = new About(user);
                    Controller controller = null;
                    Chat chat = null;
                    FriendList fl = null;
                    System.out.println("22");
                    try {
                        controller = new Controller();
                        chat = new Chat(user, context);
                        fl = new FriendList(chat, user);
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    // Add section
                    root.getChildren().addAll(controller, fl, chat,about);
                    this.close();
                    stage.show();
                }
            } catch (UnirestException e) {
                throw new RuntimeException(e);
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
