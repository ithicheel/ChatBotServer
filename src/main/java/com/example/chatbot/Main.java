package com.example.chatbot;

import com.example.chatbot.Classes.Context;
import com.example.chatbot.Classes.Users;
import com.example.chatbot.section.*;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
//    private Users user = null;
    @Override
    public void start(Stage stage) throws IOException {
        HBox root = new HBox();
        Context context = new Context();
        // Create section
        Login login = new Login(stage, root, context);
        // Scene
        System.out.println("11");
        Scene scene = new Scene(root, 1100, 700);
        stage.setTitle("Chat with ChatBot!");
        stage.setScene(scene);
        stage.setOnCloseRequest(EventHandler -> {
            context.setMainStoped(true);
        });
//        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}