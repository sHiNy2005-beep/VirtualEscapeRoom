package com.example;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.shape.Rectangle;
import javafx.scene.image.ImageView;

public class GardenPage2Controller {

    @FXML
    private Button backButton;

    @FXML
    private Button nextButton;

    @FXML
    private Label descLabel;

    @FXML
    private MenuItem menuHome;

    @FXML
    private MenuItem menuItems;

    @FXML
    private MenuItem menuRooms;

    @FXML
    private Rectangle overlay;

    @FXML
    private Label titleLabel;
    
    @FXML
    private ImageView gardenImageView;

    @FXML
    void onBack(ActionEvent event) 
    {
        try {
            App.setRoot("gardenroom1");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onNext(ActionEvent event) {
        try 
        {
            App.setRoot("explorerooms");
        } catch (IOException e) 
        {
            e.printStackTrace();
        }
    }

    @FXML
    void onMenuHome(ActionEvent event) 
    {
         try {
            App.setRoot("landing");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onMenuItems(ActionEvent event) 
    {
        javafx.scene.control.Alert a = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        a.setTitle("Items");
        a.setHeaderText(null);
        a.setContentText("Items list not implemented yet.");
        a.showAndWait();
    }

    @FXML
    void onMenuRooms(ActionEvent event) 
    {
        try {
            App.setRoot("explorerooms");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
