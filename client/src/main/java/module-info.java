module org.sd.client {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;


    opens org.sd.client to javafx.fxml;
    exports org.sd.client;
}