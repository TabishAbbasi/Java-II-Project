module Java.II.Project {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens main to javafx.fxml;
    exports main;
    opens controllers to javafx.fxml;
    exports controllers;
    opens model to javafx.fxml;
    exports model;
    opens database to javafx.fxml;
    exports database;
}