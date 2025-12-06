package com.example;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("HH:mm");
        if (timeLabel != null) {
            timeLabel.setText(LocalTime.now().format(fmt));
        }

        pageGroup = new ToggleGroup();
        if (page1 != null && page2 != null && page3 != null) {
            page1.setToggleGroup(pageGroup);
            page2.setToggleGroup(pageGroup);
            page3.setToggleGroup(pageGroup);
            page1.setSelected(true);
            page1.setOnAction(e -> loadImagesForPage(1));
            page2.setOnAction(e -> loadImagesForPage(2));
            page3.setOnAction(e -> loadImagesForPage(3));
        }

        loadImagesForPage(1);

        if (backButton != null) backButton.setOnAction(e -> onBack());
        if (nextButton != null) nextButton.setOnAction(e -> onNext());
    }

    private void loadImagesForPage(int page) {
        try {
            if (page == 1) {
                setImageSafe(img1, "/images/locket.jpg");
                setImageSafe(img2, "/images/loveletter.jpg");
                setLabelSafe(itemsText, "Locket & Love Letter");
                setLabelSafe(scoreLabel, "+100");
            }
        } catch (Exception ex) {
            if (img1 != null) img1.setImage(null);
            if (img2 != null) img2.setImage(null);
            setLabelSafe(itemsText, "No images available (check resource paths)");
            setLabelSafe(scoreLabel, "+0");
            System.err.println("Failed to load images: " + ex.getMessage());
        }
    }

    private void setImageSafe(ImageView iv, String resourcePath) {
        if (iv == null) return;
        try {
            var stream = getClass().getResourceAsStream(resourcePath);
            if (stream != null) {
                iv.setImage(new Image(stream));
            } else {
                iv.setImage(null);
                System.err.println("Resource not found: " + resourcePath);
            }
        } catch (Exception e) {
            iv.setImage(null);
            System.err.println("Error loading image " + resourcePath + ": " + e.getMessage());
        }
    }

    private void setLabelSafe(Label lbl, String text) {
        if (lbl != null) lbl.setText(text);
    }

    @FXML
    private void onBack() {
        if (pageGroup == null) return;
        var selected = (ToggleButton) pageGroup.getSelectedToggle();
        if (selected == page1) {
            page3.fire();
        } else if (selected == page2) {
            page1.fire();
        } else {
            page2.fire();
        }
        System.out.println("Back pressed");
    }

    @FXML
    private void onNext() {
        if (pageGroup == null) return;
        var selected = (ToggleButton) pageGroup.getSelectedToggle();
        if (selected == page1) {
            page2.fire();
        } else if (selected == page2) {
            page3.fire();
        } else {
            goToExploreRooms();
        }
        System.out.println("Next pressed");
    }

    private void goToExploreRooms() {
        try {
            Class<?> appClass = Class.forName("com.example.App");
            var method = appClass.getMethod("setRoot", String.class);
            method.invoke(null, "explorerooms");
            return;
        } catch (Exception ignored) {}

        Scene scene = (nextButton != null) ? nextButton.getScene() : null;
        if (scene == null && backButton != null) scene = backButton.getScene();
        if (scene == null) {
            System.err.println("No scene available for fallback navigation to Explore Rooms.");
            return;
        }

        String[] candidates = {
                "/com/example/ExploreRooms.fxml",
                "/com/example/explorerooms.fxml"
        };

        for (String candidate : candidates) {
            URL url = getClass().getResource(candidate);
            if (url == null) continue;
            try {
                FXMLLoader loader = new FXMLLoader(url);
                Parent root = loader.load();
                scene.setRoot(root);
                return;
            } catch (Exception ex) {
                // try next candidate
            }
        }

        System.err.println("Fallback navigation failed: could not load Explore Rooms FXML.");
    }
}
