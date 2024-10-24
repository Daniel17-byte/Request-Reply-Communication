package org.sd.client;

import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class View extends Scene {

    public View(Stage primaryStage) {
        super(new VBox(), 300, 200);
        initComponents();
    }

    public void initComponents() {
    }
}