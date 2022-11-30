package com.example.chatbot.Components;

import com.example.chatbot.section.Chat;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class FriendListItem extends HBox {
    private Label username = null;
    private Label lastChat = null;
    private Label lastChatDate = null;
    private String friend_id = "";
    private ArrayList<String> friend_info = new ArrayList<>();
    public FriendListItem(String friend_id, Chat chat){
        this.friend_id = friend_id;
        CreateInterFace();
        String url = "http://localhost:8080/user/" + friend_id;
        try {
            HttpResponse<JsonNode> apiResponse = Unirest.get(url)
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
                friend_info.add(info.getString("user_id"));
                friend_info.add(info.getString("username"));
                friend_info.add(info.getString("desc"));
                friend_info.add(info.getString("phone"));
                friend_info.add(info.getString("email"));
                friend_info.add(info.getString("status"));
                setupInfo();
            }
        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }
        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                chat.setUsername(friend_info.get(1));
                chat.setFriend_id(friend_id);
            }
        });
    }
    public void setupInfo(){
        username.setText(this.friend_info.get(1));
    }
    private void CreateInterFace(){
        username = new Label("example");
        lastChat = new Label("You:example");
        VBox vBox = new VBox(username, lastChat);
        Circle circle = new Circle(0 ,0, 20);
        lastChatDate = new Label("Fri");
        HBox hBox = new HBox(circle, vBox);
        hBox.setSpacing(10);
        // FriendListItem interface setup
        this.getChildren().add(hBox);
        this.getChildren().add(lastChatDate);
        this.setMinHeight(40);
        this.setMinWidth(220);
        this.setSpacing(60);
        this.setAlignment(Pos.CENTER);
        this.setPadding(new Insets(10));
        this.setOnMouseEntered(e -> this.setStyle("-fx-background-color: #d9d9d9"));
        this.setOnMouseExited(e -> this.setStyle("-fx-background-color: transparent"));
    }
}
