package com.example;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import com.model.EscapeRoomFacade;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
//import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

public class CertificateController {

    private final EscapeRoomFacade facade = App.getFacade();

    @FXML
    private Button homeButton;

    @FXML
    private Label dateLabel;

    public void initialize(URL location, ResourceBundle resources) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy HH:mm");
        dateLabel.setText("DATE : " + now.format(formatter));
    }


    @FXML
    private void onCloseButton(ActionEvent event) 
    {
        loadSceneOnCurrentStage(homeButton, "ExploreRooms.fxml");
    }

    @FXML
    private void onView(ActionEvent event) 
    {
        System.out.println(facade.getCurrentUser());
    }

    private void loadSceneOnCurrentStage(Node anyNode, String fxmlResource) {
        Stage stage = (Stage) anyNode.getScene().getWindow();
        if (stage == null) {
            showAlert("Error", "Unable to find the window to change scenes.");
            return;
        }

        URL fxmlUrl = getClass().getResource(fxmlResource);
        if (fxmlUrl == null) {
            fxmlUrl = getClass().getResource("/" + fxmlResource);
        }
        if (fxmlUrl == null) {
            showAlert("Missing FXML", "Can't find FXML: " + fxmlResource + "\nMake sure the path is correct.");
            return;
        }

        try {
            Parent root = FXMLLoader.load(fxmlUrl);
            Scene currentScene = stage.getScene();
            if (currentScene == null) {
                stage.setScene(new Scene(root));
            } else {
                currentScene.setRoot(root);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            showAlert("Load Error", "Failed to load: " + fxmlResource + "\n" + ex.getMessage());
        }
    }

    /**
     * Show an alert dialog with the given title and content
     */
    private void showAlert(String title, String content) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(content);
        a.showAndWait();
    }
}