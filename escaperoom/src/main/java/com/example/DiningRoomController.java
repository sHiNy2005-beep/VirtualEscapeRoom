package com.example;

import java.io.IOException;

import com.model.EscapeRoomFacade;
import com.model.Room;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import javafx.stage.Window;

public class DiningRoomController {

    private final EscapeRoomFacade facade = App.getFacade();
    private Room dining;

    @FXML private Button backButton;
    @FXML private Label titleLabel;
    @FXML private Label descLabel;
    @FXML private MenuItem menuHome;
    @FXML private MenuItem menuRooms;
    @FXML private MenuItem menuItems;

    @FXML
    private void initialize() {
        dining = App.getRoom("Dining");
        App.ensureSession(dining);

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
        navigateTo("DiningRoom2.fxml");
    }
    
    private void navigateTo(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();
            Stage stage = (Stage) backButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.err.println("Error loading " + fxmlFile + ": " + e.getMessage());
            e.printStackTrace();
        }
    }




}
