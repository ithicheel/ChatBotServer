package com.example.chatbot.Components;

import com.example.chatbot.Module.UserModule;
import com.example.chatbot.section.Chat;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;

public class FriendListItem extends HBox {
    private Label username = null;
    private Label lastChat = null;
    private Label lastChatDate = null;
    private String friend_id = "";
    private String[] friend_info = null;
    public FriendListItem(String friend_id, Chat chat){
        this.friend_id = friend_id;
        this.friend_info = UserModule.getUserById(this.friend_id).split(",");
        CreateInterFace();
        setupInfo();

        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                chat.setUsername(friend_info[1]);
                chat.setFriend_id(friend_id);
            }
        });
    }
    public void setupInfo(){
        username.setText(this.friend_info[1]);
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
    }
}
