module com.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires json.simple;
    requires freetts;
    

    opens com.model to javafx.fxml;
    opens com.model.library to javafx.fxml;
    exports com.model;
    exports com.model.library to javafx.fxml;
     
}
