package com.example;

import com.model.EscapeRoomFacade;
import com.model.Room;
import com.model.Puzzle;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.IOException;
import java.util.ArrayList;

public class LibraryPuzzleController {

    private final EscapeRoomFacade facade = App.getFacade();
    private Room libraryRoom;
    private Puzzle currentPuzzle;
    private String puzzleTitle;
    private String correctAnswer;

    @FXML private Label puzzleDescLabel;
    @FXML private TextField answerField;
    @FXML private Button enterBtn;
    @FXML private Button hintBtn;
    @FXML private Button backButton;
    @FXML private Label messageLabel;
    @FXML private ImageView bookPreview;
    @FXML private Pane hintOverlay;
    @FXML private Label hintText1;
    @FXML private Label hintText2;
    @FXML private Button closeHintBtn;
    @FXML private MenuItem menuHome;
    @FXML private MenuItem menuRooms;
    @FXML private MenuItem menuItems;

    @FXML
    private void initialize() {
        libraryRoom = App.getRoom("Library");
        App.ensureSession(libraryRoom);
        
        // Load puzzle data from backend
        ArrayList<Puzzle> puzzles = libraryRoom.getPuzzles();
        if (!puzzles.isEmpty()) {
            currentPuzzle = puzzles.get(0);
            puzzleTitle = currentPuzzle.getTitle();
            correctAnswer = currentPuzzle.getSolution();
            
            if (puzzleDescLabel != null) {
                puzzleDescLabel.setText(currentPuzzle.getDescription());
            }
            
            System.out.println("Loaded puzzle: " + puzzleTitle);
        } else {
            System.err.println("No puzzles found in Library room!");
        }
        
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
        boolean correct = correctAnswer.equalsIgnoreCase(answer);
        if (correct) {
            // Get score from facade and add to App.java variable
            int score = facade.getCurrentRoomScore();
            App.addScore(score);
            
            messageLabel.setText("");
            try {
                Stage stage = (Stage) answerField.getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("LibrarySuccess.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
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
        try { 
            hint = facade.useHint(puzzleTitle); 
        } catch (Exception ignored) {}
        
        if (hintOverlay != null && hintText1 != null && hintText2 != null) {
            ArrayList<String> hints = (ArrayList<String>) currentPuzzle.getHints();
            if (!hints.isEmpty()) {
                hintText1.setText(hints.size() > 0 ? hints.get(0) : "No hint available");
                hintText2.setText(hints.size() > 1 ? hints.get(1) : "");
            }
            
            hintOverlay.setManaged(true);
            hintOverlay.setVisible(true);
            FadeTransition ft = new FadeTransition(Duration.millis(200), hintOverlay);
            ft.setFromValue(0.0); 
            ft.setToValue(1.0); 
            ft.setInterpolator(Interpolator.EASE_OUT);
            ft.play();
        } else {
            if (messageLabel != null) {
                messageLabel.setText(hint != null ? hint : "Use A=1, B=2, C=3 to decode the numbers.");
            }
        }
    }

    @FXML
    private void onCloseHint() {
        hideHintOverlay();
    }

    @FXML
    private void onBack() {
        try {
            Stage stage = (Stage) backButton.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LibraryRoom.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            if (messageLabel != null) {
                messageLabel.setText("Can't navigate back.");
            }
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