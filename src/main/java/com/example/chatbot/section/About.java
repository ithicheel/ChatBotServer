package com.example.chatbot.section;

import com.example.chatbot.Classes.Users;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class About extends VBox {
    public About(Users user){
        Label about = new Label("About");
        about.setPadding(new Insets(10,0, 10, 20));
        Label username = new Label("Username: "+ user.get_username());
        Label desc = new Label("Description"+ user.get_desc());
        Label email = new Label("Email: " + user.get_email());
        Label status = new Label("Status: online");
        this.getChildren().add(about);
        this.getChildren().add(username);
        this.getChildren().add(desc);
        this.getChildren().add(email);
        this.getChildren().add(status);
        this.setSpacing(10);
        this.setPadding(new Insets(0,0, 0, 20));
    }
}
