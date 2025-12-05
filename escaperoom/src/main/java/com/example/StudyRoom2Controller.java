package com.example;

import com.model.EscapeRoomFacade;
import com.model.Room;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import java.io.IOException;

public class StudyRoom2Controller {

     private final EscapeRoomFacade facade = App.getFacade();
     private Room studyRoom;

    @FXML private TextField answerField;
    @FXML private Button enterBtn;
    @FXML private Button hintBtn;
    @FXML private Button backButton;
    @FXML private Label messageLabel;
    @FXML private ImageView safePreview;
    @FXML private Pane hintOverlay;
    @FXML private Button closeHintBtn;
    @FXML private MenuItem menuHome;
    @FXML private MenuItem menuRooms;
    @FXML private MenuItem menuItems;

    private static final String CORRECT = "112";
    private static final String PUZZLE_TITLE= "Safe Equation";

    @FXML
    private void initialize() {
        studyRoom = App.getRoom("Study");
        App.ensureSession(studyRoom);
        
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

       boolean correct = false;
        try {
            correct = facade.submitAnswer(PUZZLE_TITLE, answer);
        } catch (Exception ignored) { 

        }

        if (!correct) {
          
            correct = CORRECT.equals(answer);
        }

        if (correct) {
            messageLabel.setText("");
            try {
                App.setRoot("Studyroom3");
                int score = facade.getCurrentRoomScore();
                App.addScore(score);
            } catch (IOException e) {
                e.printStackTrace();
                messageLabel.setText("Can't open next screen.");
            }
        } else {
            messageLabel.setText("Try again.");
            shake(answerField);
        }
    }

    @FXML
    private void onHint() {
        String hint = null;
       try { hint = facade.useHint(PUZZLE_TITLE); } catch (Exception ignored) {}
        if (hintOverlay != null) {
            hintOverlay.setManaged(true);
            hintOverlay.setVisible(true);
            FadeTransition ft = new FadeTransition(Duration.millis(200), hintOverlay);
            ft.setFromValue(0.0); ft.setToValue(1.0); ft.setInterpolator(Interpolator.EASE_OUT);
            ft.play();
        } else {
          
            if (messageLabel != null) messageLabel.setText(hint != null ? hint : "Follow the order of operations (PEMDAS). Try simplifying each step -- division first.");
        }
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

