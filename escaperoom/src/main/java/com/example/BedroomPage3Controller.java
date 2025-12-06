package com.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.control.Alert;

import java.io.IOException;
import java.lang.reflect.Method;
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

    private static final String ROOMS_ROUTE = "explorerooms";

    private static final String[] PREV_CANDIDATES = {
            "/com/example/ExploreRooms.fxml",
            "/com/example/explorerooms.fxml",
            "/com/example/ExploreRoomsPage.fxml",
            "/com/example/exploreRooms.fxml"
    };

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
        if (nextButton != null) nextButton.setOnAction(this::onNext);
    }

    private void loadImagesForPage(int page) {
        try {
            if (page == 1) {
                setImageSafe(img1, "/images/locket.jpg");
                setImageSafe(img2, "/images/loveletter.jpg");
                setLabelSafe(itemsText, "Locket & Love Letter");
                setLabelSafe(scoreLabel, "+100");
            } else if (page == 2) {
                setImageSafe(img1, "/images/itemA.png");
                setImageSafe(img2, "/images/itemB.png");
                setLabelSafe(itemsText, "Item A & Item B");
                setLabelSafe(scoreLabel, "+50");
            } else {
                setImageSafe(img1, "/images/itemC.png");
                setImageSafe(img2, "/images/itemD.png");
                setLabelSafe(itemsText, "Item C & Item D");
                setLabelSafe(scoreLabel, "+25");
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
            URL url = getClass().getResource(resourcePath);
            if (url != null) {
                iv.setImage(new Image(url.toExternalForm()));
            } else {
                // try classloader with trimmed path
                String trimmed = resourcePath.startsWith("/") ? resourcePath.substring(1) : resourcePath;
                URL url2 = Thread.currentThread().getContextClassLoader().getResource(trimmed);
                if (url2 != null) {
                    iv.setImage(new Image(url2.toExternalForm()));
                } else {
                    iv.setImage(null);
                    System.err.println("Resource not found: " + resourcePath);
                }
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
       navigateTo("Bedroom2.fxml");
    }

    @FXML
    private void onNext(ActionEvent event) {
        // navigate to Explore Rooms
        //if (tryAppSetRoot(ROOMS_ROUTE)) return;
       // navigateToFirstAvailable(PREV_CANDIDATES, "Explore Rooms");
        navigateTo("ExploreRooms.fxml");

    }

    private boolean tryAppSetRoot(String routeName) {
        try {
            // try direct call first
            com.example.App.setRoot(routeName);
            return true;
        } catch (Exception directEx) {
            // try declared method (handles package-private)
            try {
                Class<?> appClass = Class.forName("com.example.App");
                Method m = appClass.getDeclaredMethod("setRoot", String.class);
                m.setAccessible(true);
                m.invoke(null, routeName);
                return true;
            } catch (Exception e) {
                System.err.println("tryAppSetRoot failed: " + e.getClass().getSimpleName() + " - " + e.getMessage());
                return false;
            }
        }
    }

     private void navigateTo(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();
            Stage stage = (Stage) backButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.err.println("Error loading " + fxmlFile + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void navigateToFirstAvailable(String[] candidates, String name) {
        Scene scene = (nextButton != null) ? nextButton.getScene() : (backButton != null ? backButton.getScene() : null);
        if (scene == null) return;

        for (String candidate : candidates) {
            URL url = getClass().getResource(candidate);
            if (url == null) continue;
            try {
                FXMLLoader loader = new FXMLLoader(url);
                Parent root = loader.load();
                scene.setRoot(root);
                return;
            } catch (IOException ignored) {}
        }

        Alert a = new Alert(Alert.AlertType.WARNING);
        a.setTitle("Navigation Error");
        a.setHeaderText(null);
        a.setContentText("Could not load: " + name);
        a.showAndWait();
    }
}
