module com.example.chatbot {
    requires javafx.controls;
    requires javafx.fxml;
    requires unirest.java;
    requires json;


    opens com.example.chatbot to javafx.fxml;
    exports com.example.chatbot;
}