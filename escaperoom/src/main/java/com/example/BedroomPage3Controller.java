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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class BedroomPage3Controller implements Initializable {

    @FXML private AnchorPane rootPane;
    @FXML private Label titleLabel;
    @FXML private Label mainTitle;
    @FXML private ImageView img1;
    @FXML private ImageView img2;
    @FXML private Label itemsText;
    @FXML private Label scoreLabel;
    @FXML private Button backButton;
    @FXML private Button nextButton;
    @FXML private MenuItem menuHome;
    @FXML private MenuItem menuRooms;
    @FXML private MenuItem menuItems;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("BedroomPage3Controller initialize() called");
        
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
        }

        // Load item images
        loadImagesForPage();
    }

    private void loadImagesForPage() {
        try {
            setImageSafe(img1, "/images/locket.jpg");
            setImageSafe(img2, "/images/loveletter.jpg");
            setLabelSafe(itemsText, "Locket & Love Letter");
            setLabelSafe(scoreLabel, "+100");
        } catch (Exception ex) {
            if (img1 != null) img1.setImage(null);
            if (img2 != null) img2.setImage(null);
            setLabelSafe(itemsText, "No images available");
            setLabelSafe(scoreLabel, "+0");
            System.err.println("Failed to load images: " + ex.getMessage());
        }
    }

    private void setImageSafe(ImageView iv, String resourcePath) {
        if (iv == null) return;
        try {
            URL url = getClass().getResource(resourcePath);
            if (url != null) {
                iv.setImage(new Image(url.toExternalForm()));
                System.out.println("✓ Loaded image: " + resourcePath);
            } else {
                // try classloader with trimmed path
                String trimmed = resourcePath.startsWith("/") ? resourcePath.substring(1) : resourcePath;
                URL url2 = Thread.currentThread().getContextClassLoader().getResource(trimmed);
                if (url2 != null) {
                    iv.setImage(new Image(url2.toExternalForm()));
                    System.out.println("✓ Loaded image (via classloader): " + resourcePath);
                } else {
                    iv.setImage(null);
                    System.err.println("✗ Resource not found: " + resourcePath);
                }
            }
        } catch (Exception e) {
            iv.setImage(null);
            System.err.println("✗ Error loading image " + resourcePath + ": " + e.getMessage());
        }
    }

    private void setLabelSafe(Label lbl, String text) {
        if (lbl != null) lbl.setText(text);
    }

    @FXML
    private void onBack() {
        System.out.println("Back button clicked");
        loadSceneOnCurrentStage(backButton, "Bedroom2.fxml");
    }

    @FXML
    private void onNext() {
        System.out.println("Next button clicked - returning to Explore Rooms");
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
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("Items");
        a.setHeaderText(null);
        a.setContentText("Items list not implemented yet.");
        a.showAndWait();
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