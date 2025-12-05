package com.example;

import com.model.EscapeRoomFacade;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import java.io.IOException;

public class LibrarySuccessController {

    @FXML private Label scoreLabel;
    @FXML private Button backButton;
    @FXML private Button nextButton;
    @FXML private MenuItem menuHome;
    @FXML private MenuItem menuRooms;
    @FXML private MenuItem menuItems;

    @FXML
    public void initialize() {
        // Use the shared facade from App
        EscapeRoomFacade facade = App.getFacade();

        // Get and display the current room score
        int score = facade.getCurrentRoomScore();
        scoreLabel.setText("+" + score);
        System.out.println("Library puzzle completed! Score: " + score);


    }

    @FXML
    private void onBackButton() {
        navigateTo("ExploreRooms.fxml");
    }

    @FXML
    private void onNextButton() {
        navigateTo("ExploreRooms.fxml");
    }

    @FXML
    private void onMenuHome() {
        navigateTo("landing.fxml");
    }

    @FXML
    private void onMenuRooms() {
        navigateTo("ExploreRooms.fxml");
    }

    @FXML
    private void onMenuItems() {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
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
