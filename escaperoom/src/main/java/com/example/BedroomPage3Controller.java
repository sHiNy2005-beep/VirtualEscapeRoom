package com.example;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class BedroomPage3Controller implements Initializable {

    @FXML private Label timeLabel;
    @FXML private Label mainTitle;
    @FXML private ToggleButton page1;
    @FXML private ToggleButton page2;
    @FXML private ToggleButton page3;
    @FXML private ImageView img1;
    @FXML private ImageView img2;
    @FXML private Label itemsText;
    @FXML private Label scoreLabel;
    @FXML private Button backButton;
    @FXML private Button nextButton;

    private ToggleGroup pageGroup;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // set clock time (static for demo; could be animated)
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("HH:mm");
        timeLabel.setText(LocalTime.now().format(fmt));

        // Toggle group for pages (so only one is selected)
        pageGroup = new ToggleGroup();
        page1.setToggleGroup(pageGroup);
        page2.setToggleGroup(pageGroup);
        page3.setToggleGroup(pageGroup);
        page1.setSelected(true);

        // load default images (replace with your resource paths)
        loadImagesForPage(1);

        // handlers for page change
        page1.setOnAction(e -> loadImagesForPage(1));
        page2.setOnAction(e -> loadImagesForPage(2));
        page3.setOnAction(e -> loadImagesForPage(3));

        // navigation handlers
        backButton.setOnAction(e -> onBack());
        nextButton.setOnAction(e -> onNext());
    }

    private void loadImagesForPage(int page) {
        // Example: load images from resources folder /images/
        // Replace with your actual image paths or URLs
        try {
            if (page == 1) {
                img1.setImage(new Image(getClass().getResourceAsStream("/images/locket.jpg")));
                img2.setImage(new Image(getClass().getResourceAsStream("/images/loveletter.jpg")));
                itemsText.setText("Locket & Love Letter");
                scoreLabel.setText("+100");
            } else if (page == 2) {
                img1.setImage(new Image(getClass().getResourceAsStream("/images/room2a.jpg")));
                img2.setImage(new Image(getClass().getResourceAsStream("/images/room2b.jpg")));
                itemsText.setText("Found: Candle & Note");
                scoreLabel.setText("+20");
            } else {
                img1.setImage(new Image(getClass().getResourceAsStream("/images/room3a.jpg")));
                img2.setImage(new Image(getClass().getResourceAsStream("/images/room3b.jpg")));
                itemsText.setText("Empty");
                scoreLabel.setText("+0");
            }
        } catch (Exception ex) {
            // fallback: clear or use placeholders
            img1.setImage(null);
            img2.setImage(null);
            itemsText.setText("No images available (check resource paths)");
            scoreLabel.setText("+0");
            System.err.println("Failed to load images: " + ex.getMessage());
        }
    }

    private void onBack() {
        // implement navigation logic; here we just print and toggle pages cyclically
        ToggleButton selected = (ToggleButton) pageGroup.getSelectedToggle();
        if (selected == page1) {
            page3.fire();
        } else if (selected == page2) {
            page1.fire();
        } else {
            page2.fire();
        }
        System.out.println("Back pressed");
    }

    private void onNext() {
        ToggleButton selected = (ToggleButton) pageGroup.getSelectedToggle();
        if (selected == page1) {
            page2.fire();
        } else if (selected == page2) {
            page3.fire();
        } else {
            page1.fire();
        }
        System.out.println("Next pressed");
    }
}
