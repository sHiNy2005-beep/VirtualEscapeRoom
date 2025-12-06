package com.example;

import com.model.EscapeRoomFacade;
import com.model.Room;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.io.IOException;

public class DiningRoom3Controller {

    private final EscapeRoomFacade facade = App.getFacade();
    private Room dining;

    @FXML private Label titleLabel;
    @FXML private Label storyLabel;
    @FXML private Label scoreLabel;
    @FXML private Button backButton;
    @FXML private Button nextButton;
    @FXML private MenuItem menuHome;
    @FXML private MenuItem menuRooms;
    @FXML private MenuItem menuItems;

    @FXML
    private void initialize() {
        dining = App.getRoom("Dining");
        App.ensureSession(dining);
        if (scoreLabel != null) {
            int score = 0;
            try { score = facade.getCurrentRoomScore(); } catch (Exception ignored) { }
            scoreLabel.setText(String.valueOf(score));
        }
    }

    @FXML
    private void onBack() {
        try {
            App.setRoot("DiningRoom2");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onNext() {
        navigateTo("Leaderboard.fxml");
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
