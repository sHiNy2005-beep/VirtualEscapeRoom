package com.example;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import java.io.IOException;

public class StudyRoom2Controller {

    @FXML private TextField answerField;
    @FXML private Button enterBtn;
    @FXML private Button hintBtn;
    @FXML private Button backButton;
    @FXML private Label messageLabel;
    @FXML private ImageView safePreview;

    @FXML private Pane hintOverlay;
    @FXML private Button closeHintBtn;

    private static final String CORRECT = "104";

    @FXML
    private void initialize() {
        if (messageLabel != null) messageLabel.setText("");
        
        if (hintOverlay != null) {
            hintOverlay.setVisible(false);
            hintOverlay.setManaged(false);
        }
    }

    @FXML
    private void onEnter() {
        messageLabel.setText("");
        String answer = (answerField.getText() == null) ? "" : answerField.getText().trim();
        if (answer.isEmpty()) {
            messageLabel.setText("Please enter an answer.");
            shake(answerField);
            return;
        }

        if (CORRECT.equals(answer)) {
           
            javafx.scene.control.Alert ok = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
            ok.setTitle("Unlocked");
            ok.setHeaderText(null);
            ok.setContentText("Correct! The safe unlocked and you found a clue.");
            ok.showAndWait();
            try {
                App.setRoot("StudyRoom3");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            messageLabel.setText("Try again.");
            shake(answerField);
        }
    }

    @FXML
    private void onHint() {
        showHintOverlay();
    }

    @FXML
    private void onCloseHint() {
        hideHintOverlay();
    }

    @FXML
    private void onBack() {
        try {
            App.setRoot("Studyroom");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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

    
    private void shake(Node node) {
        if (node == null) return;
        double distance = 10;
        Duration d = Duration.millis(60);

        TranslateTransition t1 = new TranslateTransition(d, node);
        t1.setByX(-distance);
        t1.setInterpolator(Interpolator.EASE_OUT);

        TranslateTransition t2 = new TranslateTransition(d, node);
        t2.setByX(distance * 2);
        t2.setInterpolator(Interpolator.EASE_IN);

        TranslateTransition t3 = new TranslateTransition(d, node);
        t3.setByX(-distance * 2);
        t3.setInterpolator(Interpolator.EASE_IN);

        TranslateTransition t4 = new TranslateTransition(d, node);
        t4.setByX(distance);
        t4.setInterpolator(Interpolator.EASE_OUT);

        TranslateTransition reset = new TranslateTransition(Duration.millis(30), node);
        reset.setToX(0);

        SequentialTransition seq = new SequentialTransition(t1, t2, t3, t4, reset);
        seq.play();
    }
}
