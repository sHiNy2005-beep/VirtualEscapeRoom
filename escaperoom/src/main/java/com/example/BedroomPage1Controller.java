package com.example;

import javafx.animation.FadeTransition;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class BedroomPage1Controller implements Initializable {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private Label titleLabel;

    @FXML
    private Label descriptionLabel;

    @FXML
    private Button backBtn;

    @FXML
    private Button nextBtn;

    @FXML
    private MenuItem menuHome;

    @FXML
    private MenuItem menuRooms;

    @FXML
    private MenuItem menuItems;

    private static final String PAGE_TEXT =
            "As soon as you enter... the smell of the sheets catches your attention. "
            + "The room feels still. A bedside table glints faintly to your right.";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("BedroomPage1Controller initialize() called");
        
        // Set background using resource URL
        URL imageUrl = getClass().getResource("/images/bedroom1.png");
        
        if (imageUrl != null) {
            rootPane.setStyle(
                "-fx-background-image: url('" + imageUrl.toExternalForm() + "'); " +
                "-fx-background-repeat: no-repeat; " +
                "-fx-background-size: cover; " +
                "-fx-background-position: center;"
            );
            System.out.println("✓ Background set with URL: " + imageUrl.toExternalForm());
        } else {
            System.out.println("✗ Image not found at: /images/bedroom1.png");
            System.out.println("✗ No bedroom background image found");
        }
        
        // Fade in description
        descriptionLabel.setOpacity(0);
        descriptionLabel.setText(PAGE_TEXT);
        FadeTransition ft = new FadeTransition(Duration.millis(300), descriptionLabel);
        ft.setFromValue(0.0);
        ft.setToValue(1.0);
        ft.play();

        updateNavButtons();
    }

    private void updateNavButtons() {
        backBtn.setDisable(false);    
        nextBtn.setDisable(false);
    }

    @FXML
    private void onNext() {
        // Use rootPane which is always in the scene
        loadSceneOnCurrentStage(rootPane, "Bedroom2.fxml");
    }

    @FXML
    private void onBack() {
        // Use rootPane which is always in the scene
        loadSceneOnCurrentStage(rootPane, "ExploreRooms.fxml");
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
        if (anyNode == null || anyNode.getScene() == null) {
            showAlert("Error", "Node is not attached to a scene yet.");
            return;
        }
        
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