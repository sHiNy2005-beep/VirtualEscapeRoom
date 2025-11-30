module com.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires json.simple;
    requires freetts;
    requires transitive javafx.graphics;


    opens com.model to javafx.fxml;
    exports com.model;
     
    opens com.example to javafx.fxml;
    exports com.example;
}
