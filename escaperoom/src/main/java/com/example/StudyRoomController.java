package com.example;

import com.model.EscapeRoomFacade;
import com.model.Room;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;

public class StudyRoomController {
    @FXML private Button backButton;
    @FXML private ImageView safeImage;
    @FXML private Label titleLabel;
    @FXML private Label descLabel;
    @FXML private Label hintLabel;
    @FXML private MenuItem menuHome;
    @FXML private MenuItem menuRooms;
    @FXML private MenuItem menuItems;

    private static final EscapeRoomFacade facade = App.getFacade();
    private Room studyRoom;
   
    @FXML
    private void initialize() {
        studyRoom = App.getRoom("Study");
        App.ensureSession(studyRoom);
        
        System.out.println("StudyRoomController initialize() called");
        
        if (safeImage != null) {
            System.out.println("✓ safeImage injected successfully");
        }
    }

    @FXML
    private void onSafeClicked(MouseEvent event) {
        System.out.println("Safe clicked");
        Node src = (Node) event.getSource();
        loadSceneOnCurrentStage(src, "StudyRoom2.fxml");
    }

    @FXML
    private void onBackButton() {
        loadSceneOnCurrentStage(backButton, "explorerooms.fxml");
    }

    @FXML
    private void onMenuHome() {
        loadSceneOnCurrentStage(backButton, "landing.fxml");
    }

    @FXML
    private void onMenuRooms() {
        loadSceneOnCurrentStage(backButton, "ExploreRooms.fxml");
    }

    @FXML
    private void onMenuItems() {
        showAlert("Items", "Items list not implemented yet.");
    }

    /**
     * Load the provided FXML and set it as the active scene root on the same Stage.
     * If the fxmlResource cannot be found, shows an alert with the error.
     *
     * @param anyNode any Node that is part of the current scene (used to obtain Stage)
     * @param fxmlResource the FXML resource path to load
     */
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
