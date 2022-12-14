package com.example.chatbot.section;

import com.example.chatbot.Classes.Users;
import com.example.chatbot.Components.FriendListItem;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
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
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class FriendList extends VBox {
    public FriendList(Chat chat, Users users) throws FileNotFoundException {
        // create interface
        ScrollPane sp = CreateInterFace();
        // get friend list
        ArrayList<String> list = new ArrayList<>();
        String url = "http://localhost:8080/user/friendlist/" + users.get_user_id();
//        String url = "http://192.168.1.112:8080/user/friendlist/" + users.get_user_id();
        try {
            HttpResponse<JsonNode> apiResponse = Unirest.get(url)
                    .header("accept", "application/json")
                    .asJson();
            JSONArray responseJsonAsString = apiResponse.getBody().getArray();
            if(responseJsonAsString.length() > 1){
                for(int i = 0; i<responseJsonAsString.length() - 1; i++){
                    list.add("fr=" + responseJsonAsString.get(i).toString());
                }
            }else {
                System.out.println("friend list avahad aldaa garlaa.");
            }
        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }
        String url1 = "http://127.0.0.1:8080/group/groups/" + users.get_user_id();
//        String url = "http://192.168.1.112:8080/user/friendlist/" + users.get_user_id();
        try {
            HttpResponse<JsonNode> apiResponse1 = Unirest.get(url1)
                    .header("accept", "application/json")
                    .asJson();
            JSONArray responseJsonAsString1 = apiResponse1.getBody().getArray();
            if(responseJsonAsString1.length() > 1){
                for(int i = 0; i<responseJsonAsString1.length() - 1; i++){
                    list.add("gp=" + responseJsonAsString1.get(i).toString());
                }
            }else {
                System.out.println("friend list avahad aldaa garlaa.");
            }
        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }
//        chat.setFriend_id(list.get(0));
        // create friend list interface
        VBox lists = new VBox();
        lists.setSpacing(0);
        FriendListItem[] fli = new FriendListItem[list.size()];
        int fl = 0;
        for(String i : list){
            fli[fl] = new FriendListItem(i, chat);
            lists.getChildren().add(fli[fl]);
            fl++;
        }
        System.out.println(list);




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
