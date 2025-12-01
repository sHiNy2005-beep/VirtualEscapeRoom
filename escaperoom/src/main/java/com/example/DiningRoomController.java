package com.example;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.stage.Window;

public class DiningRoomController {
    

    @FXML private Button backButton;
    @FXML private Label titleLabel;
    @FXML private Label descLabel;
    @FXML private MenuItem menuHome;
    @FXML private MenuItem menuRooms;
    @FXML private MenuItem menuItems;

    @FXML
    private void initialize() {
       
        if (backButton != null) {
            backButton.setOnAction(evt -> {
                try {
                    App.setRoot("explorerooms");
                } catch (IOException e) {
                    e.printStackTrace();
                    showAlert("Navigation error", "Could not return to explore screen.");
                }
            });
        }
    }

     private void showAlert(String title, String message) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(message);
        Window w = (backButton != null && backButton.getScene() != null) ? backButton.getScene().getWindow() : null;
        if (w != null) a.initOwner(w);
        a.showAndWait();
    }

     @FXML
    private void onMenuHome() {
        try {
            App.setRoot("landing");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onMenuRooms() {
        try {
            App.setRoot("explorerooms");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onMenuItems() {
        javafx.scene.control.Alert a = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        a.setTitle("Items");
        a.setHeaderText(null);
        a.setContentText("Items list not implemented yet.");
        a.showAndWait();
    }

    @FXML
    private void onNext() {
        try {
            
            App.setRoot("DiningRoom2");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }





}
