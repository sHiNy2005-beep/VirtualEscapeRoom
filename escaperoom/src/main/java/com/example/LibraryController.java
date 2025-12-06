package com.example;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LibraryController implements Initializable {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private Label titleLabel;

    @FXML
    private Label descLabel;

    @FXML
    private Label hintLabel;

    @FXML
    private Button backButton;

    @FXML
    private ImageView bookImage;

    @FXML
    private MenuItem menuHome;

    @FXML
    private MenuItem menuRooms;

    @FXML
    private MenuItem menuItems;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("LibraryController initialize() called");
        
        // Set background using resource URL (now using .png)
        URL imageUrl = getClass().getResource("/images/LibraryRoom.png");
        
        if (imageUrl != null) {
            rootPane.setStyle(
                "-fx-background-image: url('" + imageUrl.toExternalForm() + "'); " +
                "-fx-background-repeat: no-repeat; " +
                "-fx-background-size: cover; " +
                "-fx-background-position: center;"
            );
            System.out.println("✓ Background set with URL: " + imageUrl.toExternalForm());
        } else {
            System.out.println("✗ Image not found at: /images/LibraryRoom.png");
            System.out.println("✗ No library background image found");
        }
        
        if (bookImage != null) {
            System.out.println("✓ bookImage injected successfully");
        }
    }

    @FXML
    private void onBookClicked(MouseEvent event) {
        System.out.println("Book clicked");
        Node src = (Node) event.getSource();
        loadSceneOnCurrentStage(src, "LibraryPuzzle.fxml");
    }

    @FXML
    private void onBackButton() {
        loadSceneOnCurrentStage(backButton, "ExploreRooms.fxml");
    }

    @FXML
    private void onMenuHome() {
        loadSceneOnCurrentStage(rootPane, "primary.fxml");
    }

    @FXML
    private void onMenuRooms() {
        loadSceneOnCurrentStage(rootPane, "ExploreRooms.fxml");
    }

    @FXML
    private void onMenuItems() {
        loadSceneOnCurrentStage(rootPane, "items.fxml");
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