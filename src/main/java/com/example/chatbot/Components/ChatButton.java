package com.example.chatbot.Components;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;

public class ChatButton extends Button {
    private String var3;
    public ChatButton(){}
    public ChatButton(String var1){
        super(var1);
    }
    public ChatButton(String var1, Node var2){
        super(var1, var2);
    }
    public ChatButton(String var1, Node var2, String var3){
        super(var1, var2);
        this.var3 = var3;
        this.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Handler(var3,actionEvent);
            }
        });
    }
    public void Handler(String text, ActionEvent ae){
        System.out.println(text);
    }
}
