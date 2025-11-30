package com.example;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Window;
import java.io.IOException;

public class StudyRoomController {

    @FXML private Button backButton;
    @FXML private ImageView safeImage;
    @FXML private Label titleLabel;
    @FXML private Label descLabel;
    @FXML private Label hintLabel;

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

    @FXML
    private void onSafeClicked() {
        try {
            
            App.setRoot("Studyroom2");
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Navigation error", "Could not open the safe. " + e.getMessage());
        } catch (Throwable t) {
            t.printStackTrace();
            showAlert("Unexpected error", t.getClass().getSimpleName() + ": " + t.getMessage());
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
}