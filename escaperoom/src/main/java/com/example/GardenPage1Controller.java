package com.example;

import java.io.IOException;
import java.net.URL;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GardenPage1Controller {

    @FXML
    private ImageView RopePreview;

    @FXML
    private TextField answerField;

    @FXML
    private Button backButton;

    @FXML
    private Button closeHintBtn;

    @FXML
    private Button enterBtn;

    @FXML
    private Button hintBtn;

    @FXML
    private Pane hintOverlay;

    @FXML
    private MenuItem menuHome;

    @FXML
    private MenuItem menuItems;

    @FXML
    private MenuItem menuRooms;

    @FXML
    private Label messageLabel;

    @FXML
    private Rectangle overlay;

    @FXML
    private ImageView shovelPreview;

    @FXML
    private Label titleLabel;

    private static final String CORRECT = "STATUE";

    @FXML
    private void onBackButton() {
        loadSceneOnCurrentStage(backButton, "GardenRoom.fxml");
    }

    @FXML
    private void onNextButton() {
        loadSceneOnCurrentStage(backButton, "GardenRoom2.fxml");
    }

    @FXML
    void onCloseHint(ActionEvent event) 
    {
        hideHintOverlay();
    }

     @FXML
    void onHint(ActionEvent event) 
    {
        showHintOverlay();
    }

    @FXML
    private void showHintOverlay() {
        if (hintOverlay == null) return;
        hintOverlay.setManaged(true);
        hintOverlay.setVisible(true);
        FadeTransition ft = new FadeTransition(Duration.millis(200), hintOverlay);
        ft.setFromValue(0.0);
        ft.setToValue(1.0);
        ft.setInterpolator(Interpolator.EASE_OUT);
        ft.play();
    }

    @FXML
    private void hideHintOverlay() {
        if (hintOverlay == null) return;
        FadeTransition ft = new FadeTransition(Duration.millis(180), hintOverlay);
        ft.setFromValue(1.0);
        ft.setToValue(0.0);
        ft.setInterpolator(Interpolator.EASE_IN);
        ft.setOnFinished(e -> {
            hintOverlay.setVisible(false);
            hintOverlay.setManaged(false);
        });
        ft.play();
    }

    @FXML
    private void onEnter() {
        messageLabel.setText("");
        String answer = (answerField.getText() == null) ? "" : answerField.getText().trim();
        if (answer.isEmpty()) {
            messageLabel.setText("Please enter an answer.");
            return;
        }
        boolean correct = CORRECT.equalsIgnoreCase(answer);
        if (correct) {
            // Get score from facade and add to App.java variable
            //int score = facade.getCurrentRoomScore();
            //App.addScore(score);
            
            messageLabel.setText("");
            try {
                Stage stage = (Stage) answerField.getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("GardenRoom2.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
                messageLabel.setText("Can't open next screen.");
            }
        } else {
            messageLabel.setText("Try again.");
        }
    }
    @FXML
    void onMenuHome(ActionEvent event) 
    {
        try {
            App.setRoot("landing");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onMenuItems(ActionEvent event) 
    {
        javafx.scene.control.Alert a = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        a.setTitle("Items");
        a.setHeaderText(null);
        a.setContentText("Items list not implemented yet.");
        a.showAndWait();
    }

    @FXML
    void onMenuRooms(ActionEvent event) 
    {
        try {
            App.setRoot("explorerooms");
        } catch (IOException e) {
            e.printStackTrace();
        }
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
