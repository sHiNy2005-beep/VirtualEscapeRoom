package com.example;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class BedroomPage2Controller {

    @FXML private ImageView bgImage;
    @FXML private Label bloodLabel;
    @FXML private Label bloodShadow;
    @FXML private Label clockLabel;
    @FXML private TextField hintField;
    @FXML private Button enterBtn;
    @FXML private Button backBtn;
    @FXML private Button nextBtn;

    private static final String CORRECT_ANSWER = "secret";

    @FXML
    public void initialize() {
        URL imgUrl = getClass().getResource("/images/bedroom2.png");
        if (imgUrl != null) {
            bgImage.setImage(new Image(imgUrl.toExternalForm()));
        } else {
            System.err.println("Warning: /images/bedroom2.png not found on classpath.");
        }
        FadeTransition ft = new FadeTransition(Duration.millis(700), bloodLabel);
        ft.setFromValue(0.0);
        ft.setToValue(1.0);
        ft.play();

        FadeTransition ft2 = new FadeTransition(Duration.millis(900), bloodShadow);
        ft2.setFromValue(0.0);
        ft2.setToValue(0.9);
        ft2.play(); 
    }


    @FXML
    private void onEnter() {
        String attempt = hintField.getText() == null ? "" : hintField.getText().trim().toLowerCase();

        if (attempt.isEmpty()) {
            showAlert(Alert.AlertType.INFORMATION, "Try an answer", "Type a guess into the box and press ENTER.");
            return;
        }

        if (attempt.equals(CORRECT_ANSWER)) {
            showAlert(Alert.AlertType.INFORMATION, "Correct", "You got it — the answer is \"" + CORRECT_ANSWER + "\".\nA small compartment clicks open somewhere.");
        
        } else {
            showAlert(Alert.AlertType.ERROR, "Not quite", "That doesn't look right. Think about what disappears when someone else knows it.");
        }
    }

    @FXML
    private void onBack() {
        loadScene("BedroomPage1.fxml");
    }

    @FXML
    private void onNext() {
        loadScene("BedroomPage3.fxml");
    }

    private void loadScene(String fxmlName) {
        try {
            URL resource = getClass().getResource(fxmlName);
            if (resource == null) {
                System.err.println("FXML not found: " + fxmlName);
                return;
            }
            Parent root = FXMLLoader.load(resource);
            Stage stage = (Stage) hintField.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType type, String title, String body) {
        Alert a = new Alert(type);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(body);
        a.showAndWait();
    }
}
