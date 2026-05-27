module FitConnect {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;
    requires org.json;
    requires java.net.http;
    requires java.logging;
    requires java.sql;

    exports view to javafx.graphics;
    exports model;
    exports controller;
    exports dao.authentication;

    opens view to javafx.fxml, javafx.graphics;
    opens controller to javafx.fxml;
    opens bean to javafx.fxml;
    opens model to javafx.fxml;
    opens bean.Enum to javafx.fxml;
}

