module com.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires json.simple;
    requires freetts;
    requires junit;
    


    opens com.model to javafx.fxml;
    exports com.model;
   
     
}
