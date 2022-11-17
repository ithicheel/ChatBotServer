package com.example.chatbot.section;

import com.example.chatbot.Components.FriendListItem;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class FriendList extends VBox {
    public FriendList(Chat chat) throws FileNotFoundException {
        // create interface
        ScrollPane sp = CreateInterFace();
        // create friend list interface
        VBox lists = new VBox();
        lists.setSpacing(0);
        String list[] = {"1234zsdlk32333", "1234zsdlk32334"};
        FriendListItem[] fli = new FriendListItem[list.length];
        int fl = 0;
        for(String i : list){
            fli[fl] = new FriendListItem(i, chat);
            lists.getChildren().add(fli[fl]);
            fl++;
        }
        sp.setContent(lists);
        this.getChildren().add(sp);
    }
    private ScrollPane CreateInterFace() throws FileNotFoundException {
        /***
         * Search Icon oruulj ireh heseg
         */
        FileInputStream searchInput = new FileInputStream("src/main/resources/image/loupe.png");
        Image searchimg = new Image(searchInput);
        ImageView searchView = new ImageView(searchimg);
        searchView.setFitWidth(15);
        searchView.setFitHeight(15);
        /***
         * Text field heseg
         */
        TextField search = new TextField();
        search.setBorder(null);
        /***
         * Hbox controller uusgeh
         */
        HBox hbox = new HBox();
        hbox.getChildren().add(searchView);
        hbox.getChildren().add(search);
        hbox.setMinHeight(30);
        hbox.setMinWidth(200);
        hbox.setAlignment(Pos.CENTER);
        hbox.setPadding(new Insets(20,0,0,0));
        hbox.setSpacing(10);
        this.getChildren().add(hbox);
        /***
         * Controller Settings
         */
        this.setSpacing(20);
        this.setBorder(Border.stroke(Color.BLACK));
        // Scroll panel
        ScrollPane sp = new ScrollPane();
        sp.setPadding(new Insets(20, 0, 0 ,0));
        sp.setPrefSize(240, 630);
        sp.setContent(null);
        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        // Style sheet
        sp.setStyle("-fx-focus-color: #d9d9d9;");

        return sp;
    }
}
