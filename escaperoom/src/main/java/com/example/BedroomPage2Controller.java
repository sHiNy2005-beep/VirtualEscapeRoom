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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class BedroomPage2Controller {

    private final EscapeRoomFacade facade = App.getFacade();
    private Room bedroomRoom;
    private Puzzle currentPuzzle;
    private String puzzleTitle;
    private String correctAnswer = "secret"; // Default answer

    @FXML private AnchorPane rootPane;
    @FXML private Label puzzleDescLabel;
    @FXML private TextField answerField;
    @FXML private Button enterBtn;
    @FXML private Button hintBtn;
    @FXML private Button backButton;
    @FXML private Label messageLabel;
    @FXML private Pane hintOverlay;
    @FXML private Label hintText1;
    @FXML private Label hintText2;
    @FXML private Button closeHintBtn;
    @FXML private MenuItem menuHome;
    @FXML private MenuItem menuRooms;
    @FXML private MenuItem menuItems;

    @FXML
    private void initialize() {
        System.out.println("BedroomPage2Controller initialize() called");
        
        // Set background using resource URL
        URL imageUrl = getClass().getResource("/images/bedroom1.png");
        
        if (imageUrl != null) {
            rootPane.setStyle(
                "-fx-background-image: url('" + imageUrl.toExternalForm() + "'); " +
                "-fx-background-repeat: no-repeat; " +
                "-fx-background-size: cover; " +
                "-fx-background-position: center;"
            );
            System.out.println("✓ Background set with URL: " + imageUrl.toExternalForm());
        } else {
            System.out.println("✗ Image not found at: /images/bedroom1.png");
        }
        
        // Try to load puzzle data from backend
        bedroomRoom = App.getRoom("Bedroom");
        if (bedroomRoom != null) {
            App.ensureSession(bedroomRoom);
            
            ArrayList<Puzzle> puzzles = bedroomRoom.getPuzzles();
            if (!puzzles.isEmpty()) {
                currentPuzzle = puzzles.get(0);
                puzzleTitle = currentPuzzle.getTitle();
                correctAnswer = currentPuzzle.getSolution();
                
                if (puzzleDescLabel != null) {
                    puzzleDescLabel.setText(currentPuzzle.getDescription());
                }
                
                System.out.println("Loaded puzzle: " + puzzleTitle);
            } else {
                System.out.println("No puzzles found in Bedroom room, using default");
            }
        } else {
            System.out.println("Bedroom room not found, using default puzzle");
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
        
        boolean correct = correctAnswer.equalsIgnoreCase(answer) || 
                         answer.toLowerCase().equals("a secret");
        
        if (correct) {
            // Get score from facade and add to App.java variable
            if (bedroomRoom != null) {
                int score = facade.getCurrentRoomScore();
                App.addScore(score);
                System.out.println("Correct answer! Score added: " + score);
            }
            
            messageLabel.setText("Correct!");
            
            // Navigate to Bedroom3 after correct answer
            System.out.println("Navigating to Bedroom3...");
            loadSceneOnCurrentStage(rootPane, "Bedroom3.fxml");
        } else {
            messageLabel.setText("Incorrect — try again.");
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
            if (currentPuzzle != null) {
                ArrayList<String> hints = (ArrayList<String>) currentPuzzle.getHints();
                if (!hints.isEmpty()) {
                    hintText1.setText(hints.size() > 0 ? hints.get(0) : "Hint: It disappears when someone else knows it.");
                    hintText2.setText(hints.size() > 1 ? hints.get(1) : "");
                }
            } else {
                hintText1.setText("Hint: It disappears when someone else knows it.");
                hintText2.setText("");
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
                messageLabel.setText(hint != null ? hint : "Hint: It disappears when someone else knows it.");
            }
        }
    }

    @FXML
    private void onCloseHint() {
        hideHintOverlay();
    }

    @FXML
    private void onBack() {
        System.out.println("Back button clicked");
        loadSceneOnCurrentStage(backButton, "Bedroom1.fxml");
    }

    @FXML
    private void onNext() {
        System.out.println("Next button clicked");
        // Can be called after correct answer or by clicking a next button
        loadSceneOnCurrentStage(answerField, "Bedroom3.fxml");
    }

    @FXML
    private void onMenuHome() {
        loadSceneOnCurrentStage(rootPane, "primary.fxml");
    }

    @FXML
    private void onMenuRooms() {
        loadSceneOnCurrentStage(rootPane, "ExploreRooms.fxml");
    }

    @FXML
    private void onMenuItems() {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("Items");
        a.setHeaderText(null);
        a.setContentText("Items list not implemented yet.");
        a.showAndWait();
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

    /**
     * Load the provided FXML and set it as the active scene root on the same Stage.
     * If the fxmlResource cannot be found, shows an alert with the error.
     *
     * @param anyNode any Node that is part of the current scene (used to obtain Stage)
     * @param fxmlResource the FXML resource path to load
     */
    private void loadSceneOnCurrentStage(Node anyNode, String fxmlResource) {
        Stage stage = (Stage) anyNode.getScene().getWindow();
        if (stage == null) {
            showAlert("Error", "Unable to find the window to change scenes.");
            return;
        }

        URL fxmlUrl = getClass().getResource(fxmlResource);
        if (fxmlUrl == null) {
            fxmlUrl = getClass().getResource("/" + fxmlResource);
        }
        if (fxmlUrl == null) {
            showAlert("Missing FXML", "Can't find FXML: " + fxmlResource + "\nMake sure the path is correct.");
            return;
        }

        try {
            Parent root = FXMLLoader.load(fxmlUrl);
            Scene currentScene = stage.getScene();
            if (currentScene == null) {
                stage.setScene(new Scene(root));
            } else {
                currentScene.setRoot(root);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            showAlert("Load Error", "Failed to load: " + fxmlResource + "\n" + ex.getMessage());
        }
    }

    /**
     * Show an alert dialog with the given title and content
     */
    private void showAlert(String title, String content) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(content);
        a.showAndWait();
    }
}