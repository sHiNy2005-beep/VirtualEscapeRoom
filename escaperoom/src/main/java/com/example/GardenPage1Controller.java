package com.example;

import java.io.IOException;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
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
    void onBack(ActionEvent event) 
    {
        try {
            App.setRoot("gardenroom");
        } catch (IOException e) {
            e.printStackTrace();
        }
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
    void onEnter(ActionEvent event) 
    {
        messageLabel.setText("");
        String answer = (answerField.getText() == null) ? "" : answerField.getText().trim();
        if (answer.isEmpty()) {
            messageLabel.setText("Please enter an answer.");
            return;
        }

        if (CORRECT.equals(answer)) {
           
            javafx.scene.control.Alert ok = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
            ok.setTitle("Unlocked");
            ok.setHeaderText(null);
            ok.setContentText("Good job on solving this room! You found: ");
            ok.showAndWait();
            try {
                App.setRoot("gardenroom2");
            } catch (IOException e) {
                e.printStackTrace();
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

}
