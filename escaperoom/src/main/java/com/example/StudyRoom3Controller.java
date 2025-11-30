package com.example;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import java.io.IOException;
import javafx.scene.image.Image;

public class StudyRoom3Controller {

    @FXML private Label titleLabel;
    @FXML private Label storyLabel;
    @FXML private ImageView ledgerImage;
    @FXML private ImageView keyImage;
    @FXML private Label itemsLabel;
    @FXML private Label scoreLabel;
    @FXML private Button backButton;
    @FXML private Button nextButton;
    @FXML private MenuItem menuHome;
    @FXML private MenuItem menuRooms;
    @FXML private MenuItem menuItems;

    @FXML
    private void initialize() {
        
        try {
            Image ledger = new Image(getClass().getResourceAsStream("/images/ledger.png"));
            if (ledger != null && ledgerImage != null) ledgerImage.setImage(ledger);
        } catch (Exception ignored) { }

        try {
            Image key = new Image(getClass().getResourceAsStream("/images/key.png"));
            if (key != null && keyImage != null) keyImage.setImage(key);
        } catch (Exception ignored) { }

        if (itemsLabel != null) itemsLabel.setText("ITEMS ACQUIRED: SAFE KEY & LEDGER");
        if (scoreLabel != null) scoreLabel.setText("+100");
    }

    @FXML
    private void onBack() {
        try {
            App.setRoot("studyroom2");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onNext() {
        try {
            
            App.setRoot("explorerooms");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onMenuHome() {
        try {
            App.setRoot("landing");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onMenuRooms() {
        try {
            App.setRoot("explorerooms");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onMenuItems() {
        javafx.scene.control.Alert a = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        a.setTitle("Items");
        a.setHeaderText(null);
        a.setContentText("Items list not implemented yet.");
        a.showAndWait();
    }
}