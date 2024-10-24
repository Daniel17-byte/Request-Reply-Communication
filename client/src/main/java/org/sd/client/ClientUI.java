package org.sd.client;


import javafx.application.Application;
import javafx.stage.Stage;

public class ClientUI extends Application {
    @Override
    public void start(Stage stage) {
        View view = new View(stage);

        stage.setScene(view);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}