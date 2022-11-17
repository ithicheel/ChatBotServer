package com.example.chatbot.section;

import com.example.chatbot.Components.ChatButton;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Controller extends VBox {
    public Controller() throws FileNotFoundException {
        CreateInterFace();
        // Controller setup
        this.setSpacing(30);
        this.setMinHeight(700);
        this.setMinWidth(70);
        this.setAlignment(Pos.TOP_CENTER);
        this.setPadding(new Insets(30, 0,0,0));
    }
    private void CreateInterFace() throws FileNotFoundException {
        // File inputSteam endees shaardlagatai icon oruulj irej bna
        FileInputStream homeInput = new FileInputStream("src/main/resources/image/house.png");
        FileInputStream notInput  = new FileInputStream("src/main/resources/image/notification.png");
        FileInputStream addUserInput = new FileInputStream("src/main/resources/image/add-user.png");
        FileInputStream settingsInput = new FileInputStream("src/main/resources/image/settings.png");
        FileInputStream exitInput = new FileInputStream("src/main/resources/image/exit.png");
        // image uusgej bna
        Image homeimg = new Image(homeInput);
        Image notimg = new Image(notInput);
        Image addUserimg = new Image(addUserInput);
        Image settingsimg = new Image(settingsInput);
        Image exitimg = new Image(exitInput);
        // image haruulah
        ImageView homeView = new ImageView(homeimg);
        ImageView notView = new ImageView(notimg);
        ImageView addUserView = new ImageView(addUserimg);
        ImageView settingView = new ImageView(settingsimg);
        ImageView exitView = new ImageView(exitimg);
        // image size setup
        homeView.setFitHeight(25);
        homeView.setFitWidth(25);
        notView.setFitHeight(25);
        notView.setFitWidth(25);
        addUserView.setFitHeight(25);
        addUserView.setFitWidth(25);
        settingView.setFitHeight(25);
        settingView.setFitWidth(25);
        exitView.setFitHeight(25);
        exitView.setFitWidth(25);
        // Icon Button uusgej
        ChatButton homeBtn = new ChatButton("", homeView, "home");
        ChatButton notBtn= new ChatButton("", notView, "notification");
        ChatButton addUserBtn = new ChatButton("", addUserView, "add-user");
        ChatButton settingsBtn = new ChatButton("", settingView, "settings");
        ChatButton exitBtn = new ChatButton("", exitView, "exit");
        // button background arilgah
        homeBtn.setBackground(null);
        notBtn.setBackground(null);
        addUserBtn.setBackground(null);
        settingsBtn.setBackground(null);
        exitBtn.setBackground(null);
        // Controller deeree nemeh
        this.getChildren().add(homeBtn);
        this.getChildren().add(notBtn);
        this.getChildren().add(addUserBtn);
        this.getChildren().add(settingsBtn);
        this.getChildren().add(exitBtn);
    }
}
