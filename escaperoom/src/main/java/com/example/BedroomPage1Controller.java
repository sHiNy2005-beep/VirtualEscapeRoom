package com.example;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class BedroomPage1Controller {

    @FXML private ImageView bgImage;
    @FXML private Label descriptionLabel;
    @FXML private Label clockLabel;
    @FXML private Button pageBtn1;
    @FXML private Button pageBtn2;
    @FXML private Button pageBtn3;
    @FXML private Button backBtn;
    @FXML private Button nextBtn;

    private static final String PAGE_TEXT =
            "As soon as you enter... the smell of the sheets catches your attention. "
            + "The room feels still. A bedside table glints faintly to your right.";

    private static final String FALLBACK_FILE_URL = "file:/mnt/data/36190bae-8639-459c-a1b7-ff78adf4439b.png";

    @FXML
    public void initialize() {
        try {
            URL imgUrl = getClass().getResource("/images/bedroom.png");
            if (imgUrl != null) {
                bgImage.setImage(new Image(imgUrl.toExternalForm()));
            } else {
                bgImage.setImage(new Image(FALLBACK_FILE_URL));
                System.err.println("Warning: /images/bedroom.png not found on classpath; using fallback file URL.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        descriptionLabel.setOpacity(0);
        descriptionLabel.setText(PAGE_TEXT);
        FadeTransition ft = new FadeTransition(Duration.millis(300), descriptionLabel);
        ft.setFromValue(0.0);
        ft.setToValue(1.0);
        ft.play();

        clockLabel.setText("08:45");

        updateNavButtons();
    }

    private void updateNavButtons() {
        backBtn.setDisable(false);    
        nextBtn.setDisable(false);
    }

    @FXML
    private void onPage1() {
    }

    @FXML
    private void onPage2() {
        loadScene("BedroomPage2.fxml");
    }

    @FXML
    private void onPage3() {
        loadScene("BedroomPage3.fxml");
    }

    @FXML
    private void onNext() {
        onPage2();
    }

    @FXML
    private void onBack() {
        loadScene("Rooms.fxml");
    }

    private void loadScene(String fxmlName) {
        try {
            URL resource = getClass().getResource(fxmlName);
            if (resource == null) {
                System.err.println("FXML not found: " + fxmlName + " (checked getResource)");
                return;
            }
            Parent root = FXMLLoader.load(resource);
            Stage stage = (Stage) descriptionLabel.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
