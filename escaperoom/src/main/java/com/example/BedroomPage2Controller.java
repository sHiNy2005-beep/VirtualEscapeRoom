package com.example;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.IOException;
import java.net.URL;
import java.util.Set;

public class BedroomPage2Controller {

    private static final String ROOMS_ROUTE = "explorerooms";
    private static final String NEXT_ROUTE = "Bedroom3";
    private static final String[] NEXT_CANDIDATES = { "/com/example/Bedroom3.fxml", "/com/example/bedroom3.fxml" };
    private static final String[] PREV_CANDIDATES = { "/com/example/ExploreRooms.fxml", "/com/example/explorerooms.fxml" };

    @FXML private ImageView bgImage;
    @FXML private Button backBtn1;
    @FXML private Button nextBtn1;

    private TextField answerField;
    private Button enterButton;
    private Button hintButton;

    @FXML
    private void initialize() {
        if (backBtn1 != null) backBtn1.setOnAction(this::onBack);
        if (nextBtn1 != null) nextBtn1.setOnAction(this::onNext);

        Platform.runLater(() -> {
            Scene s = bgImage != null ? bgImage.getScene() : null;
            if (s != null) {
                s.addEventHandler(KeyEvent.KEY_PRESSED, e -> {
                    if (e.getCode() == KeyCode.LEFT) { onBack(null); e.consume(); }
                    else if (e.getCode() == KeyCode.RIGHT) { onNext(null); e.consume(); }
                    else if (e.getCode() == KeyCode.ENTER) {
                        if (answerField != null && answerField.isFocused()) onEnter(null);
                    }
                });
                locateRuntimeControls(s);
                wireRuntimeControlHandlers();
            }
        });
    }

    private void locateRuntimeControls(Scene scene) {
        Set<javafx.scene.Node> tfs = scene.getRoot().lookupAll(".text-field");
        if (!tfs.isEmpty()) for (javafx.scene.Node n : tfs) if (n instanceof TextField) { answerField = (TextField) n; break; }

        Set<javafx.scene.Node> btns = scene.getRoot().lookupAll(".button");
        for (javafx.scene.Node n : btns) {
            if (!(n instanceof Button)) continue;
            Button b = (Button) n;
            String txt = b.getText() == null ? "" : b.getText().trim();
            if ("ENTER".equalsIgnoreCase(txt) && enterButton == null) enterButton = b;
            else if ("HINT ?".equalsIgnoreCase(txt) && hintButton == null) hintButton = b;
            if (enterButton != null && hintButton != null) break;
        }
    }

    private void wireRuntimeControlHandlers() {
        if (enterButton != null) enterButton.setOnAction(this::onEnter);
        if (hintButton != null) hintButton.setOnAction(this::onHint);
        if (answerField != null) answerField.setOnAction(this::onEnter);
    }

    @FXML
    private void onBack(ActionEvent event) {
        if (!navigateTo(ROOMS_ROUTE)) fallbackLoad(PREV_CANDIDATES);
    }

    @FXML
    private void onNext(ActionEvent event) {
        if (!navigateTo(NEXT_ROUTE)) fallbackLoad(NEXT_CANDIDATES);
    }

    @FXML
    private void onEnter(ActionEvent event) {
        String ans = (answerField == null) ? "" : answerField.getText();
        if (ans == null || ans.trim().isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please enter an answer.").showAndWait();
            return;
        }
        String n = ans.trim().toLowerCase();
        if (n.equals("secret") || n.equals("a secret")) {
            new Alert(Alert.AlertType.INFORMATION, "Correct!").showAndWait();
            if (!navigateTo(NEXT_ROUTE)) fallbackLoad(NEXT_CANDIDATES);
        } else {
            new Alert(Alert.AlertType.ERROR, "Incorrect — try again.").showAndWait();
        }
    }

    @FXML
    private void onHint(ActionEvent event) {
        new Alert(Alert.AlertType.INFORMATION, "Hint: It disappears when someone else knows it.").showAndWait();
    }

    private boolean navigateTo(String route) {
        try {
            com.example.App.setRoot(route);
            return true;
        } catch (Exception ignored) {}

        try {
            Class<?> app = Class.forName("com.example.App");
            var m = app.getDeclaredMethod("setRoot", String.class);
            m.setAccessible(true);
            m.invoke(null, route);
            return true;
        } catch (Exception ignored) {}

        return false;
    }

    private void fallbackLoad(String[] candidates) {
        Scene scene = bgImage != null ? bgImage.getScene() : null;
        if (scene == null) return;
        for (String c : candidates) {
            URL url = getClass().getResource(c);
            if (url == null) continue;
            try {
                Parent root = FXMLLoader.load(url);
                scene.setRoot(root);
                return;
            } catch (IOException ignored) {}
        }
    }
}