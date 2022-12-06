package com.example.chatbot.section;

import com.example.chatbot.Classes.Users;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class About extends VBox {
    Label about = null;
    Label username = null;
    Label desc = null;
    Label email = null;
    Label status = null;
    public About(Users user){
        creatInterFace();
        username.setText("Username: "+ user.get_username());
        desc.setText("Description: "+ user.get_desc());
        email.setText("Email: " + user.get_email());

    }
    private void creatInterFace(){
        about = new Label("About");
        about.setPadding(new Insets(10,0, 10, 20));
        username = new Label("Username: ");
        desc = new Label("Description");
        email = new Label("Email: ");
        status = new Label("Status: online");
        this.getChildren().add(about);
        this.getChildren().add(username);
        this.getChildren().add(desc);
        this.getChildren().add(email);
        this.getChildren().add(status);
        this.setSpacing(10);
        this.setPadding(new Insets(0,0, 0, 20));
    }
}
