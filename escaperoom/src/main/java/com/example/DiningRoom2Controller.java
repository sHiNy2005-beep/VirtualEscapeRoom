package com.example;

import com.model.EscapeRoomFacade;
import com.model.Room;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.StringJoiner;

public class DiningRoom2Controller {

    private final EscapeRoomFacade facade = App.getFacade();
    private Room dining;

    private static final String PUZZLE_TITLE = "The Murderer Revealed";

    @FXML private ChoiceBox<String> choiceLoveLetter;
    @FXML private ChoiceBox<String> choiceWillPage;
    @FXML private ChoiceBox<String> choiceLedger;
    @FXML private ChoiceBox<String> choiceStatue;
    @FXML private Label labelLoveLetter;
    @FXML private Label labelWillPage;
    @FXML private Label labelLedger;
    @FXML private Label labelStatue;
    @FXML private Label overallMatchMessage;
    @FXML private TextField gardenInput;
    @FXML private Label hintMessage;
    @FXML private MenuItem menuHome;
    @FXML private MenuItem menuRooms;
    @FXML private MenuItem menuItems;
    @FXML private Button backButton;
    @FXML private Pane hintOverlay;
    @FXML private Button closeHintBtn;

    private final String[] suspects = {
            "Lilly Hamton",
            "Thomas Hamton",
            "Mr. Barner",
            "Ms. Louise"
    };

    private final Map<String, String> correctMapping = new LinkedHashMap<>();

    public DiningRoom2Controller() {
        correctMapping.put("love letter", "lilly hamton");
        correctMapping.put("will page", "thomas hamton");
        correctMapping.put("ledger", "mr. barner");
        correctMapping.put("bloody statue", "ms. louise");
    }

    @FXML
    private void initialize() {
        dining = App.getRoom("Dining");
        App.ensureSession(dining);

        for (String s : suspects) {
            choiceLoveLetter.getItems().add(s);
            choiceWillPage.getItems().add(s);
            choiceLedger.getItems().add(s);
            choiceStatue.getItems().add(s);
        }

        choiceLoveLetter.setValue(null);
        choiceWillPage.setValue(null);
        choiceLedger.setValue(null);
        choiceStatue.setValue(null);

        clearMatchFeedback();
        hintMessage.setText("");
    }

    private void clearMatchFeedback() {
        labelLoveLetter.setText("");
        labelWillPage.setText("");
        labelLedger.setText("");
        labelStatue.setText("");
        overallMatchMessage.setText("");
    }

    @FXML
    private void onCheckMatches(ActionEvent event) {
        clearMatchFeedback();
        boolean allCorrect = true;

        allCorrect &= checkSingle(choiceLoveLetter, labelLoveLetter, "love letter");
        allCorrect &= checkSingle(choiceWillPage, labelWillPage, "will page");
        allCorrect &= checkSingle(choiceLedger, labelLedger, "ledger");
        allCorrect &= checkSingle(choiceStatue, labelStatue, "bloody statue");

        if (allCorrect) {
            overallMatchMessage.setText("All correct ");
            overallMatchMessage.setStyle("-fx-text-fill: #2e7d32; -fx-font-weight: bold;");
            try {
                facade.submitAnswer(PUZZLE_TITLE, buildAnswerString());
            } catch (Exception ignored) { }
        } else {
            overallMatchMessage.setText("Some matches are incorrect — fix the highlighted items.");
            overallMatchMessage.setStyle("-fx-text-fill: #c0392b; -fx-font-weight: bold;");
        }
    }

    private boolean checkSingle(ChoiceBox<String> choice, Label feedbackLabel, String itemKey) {
        String selected = choice.getValue();
        if (selected == null || selected.trim().isEmpty()) {
            feedbackLabel.setText("Please choose a suspect.");
            feedbackLabel.setStyle("-fx-text-fill: #e67e22; -fx-font-weight: bold;");
            return false;
        }
        String selectedNorm = selected.trim().toLowerCase();
        String correct = correctMapping.get(itemKey);
        if (correct == null) {
            feedbackLabel.setText("Config error (no answer).");
            feedbackLabel.setStyle("-fx-text-fill: #c0392b; -fx-font-weight: bold;");
            return false;
        }
        if (selectedNorm.equals(correct)) {
            feedbackLabel.setText("Correct");
            feedbackLabel.setStyle("-fx-text-fill: #2e7d32; -fx-font-weight: bold;");
            return true;
        } else {
            feedbackLabel.setText("Wrong");
            feedbackLabel.setStyle("-fx-text-fill: #c0392b; -fx-font-weight: bold;");
            return false;
        }
    }

    @FXML
    private void onHintEnter(ActionEvent event) {
        String typed = (gardenInput.getText() == null) ? "" : gardenInput.getText().trim().toLowerCase();
        final String CORRECT = "ms. louise";

        if (typed.isEmpty()) {
            hintMessage.setText("Please enter an answer.");
            hintMessage.setStyle("-fx-text-fill: #e67e22; -fx-font-weight: bold;");
            return;
        }

        if (typed.equals(CORRECT)) {
            hintMessage.setText("Correct — proceeding to next room.");
            hintMessage.setStyle("-fx-text-fill: #2e7d32; -fx-font-weight: bold;");

            try {
                facade.submitAnswer(PUZZLE_TITLE, buildAnswerString());
            } catch (Exception ignored) { }

            PauseTransition pt = new PauseTransition(Duration.seconds(0.6));
            pt.setOnFinished(e -> loadScene((Node) event.getSource(), "DiningRoom3.fxml"));
            int score = facade.getCurrentRoomScore();
            App.addScore(score);
            pt.play();
        } else {
            hintMessage.setText("Incorrect — try again.");
            hintMessage.setStyle("-fx-text-fill: #c0392b; -fx-font-weight: bold;");
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

    @FXML
    private void onBack() {
        try {
            App.setRoot("DiningRoom");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadScene(Node sourceNode, String fxmlFileName) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlFileName));
            Stage stage = (Stage) sourceNode.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            overallMatchMessage.setText("Could not load " + fxmlFileName + ": " + e.getMessage());
            overallMatchMessage.setStyle("-fx-text-fill: #c0392b; -fx-font-weight: bold;");
            e.printStackTrace();
        } catch (NullPointerException npe) {
            overallMatchMessage.setText("Navigation failed (resource not found): " + fxmlFileName);
            overallMatchMessage.setStyle("-fx-text-fill: #c0392b; -fx-font-weight: bold;");
            npe.printStackTrace();
        }
    }

    @FXML
    private void onHint() {
        String hint = null;
        try { hint = facade.useHint(PUZZLE_TITLE); } catch (Exception ignored) {}
        if (hint != null && !hint.isBlank()) {
            hintMessage.setText(hint);
            hintMessage.setStyle("-fx-text-fill: #e67e22; -fx-font-weight: bold;");
        }
        showHintOverlay();
    }

    @FXML
    private void onCloseHint() {
        hideHintOverlay();
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

    
    private String buildAnswerString() {
        StringJoiner sj = new StringJoiner(",");
        appendPair(sj, choiceLoveLetter != null ? choiceLoveLetter.getValue() : null, "love letter");
        appendPair(sj, choiceWillPage != null ? choiceWillPage.getValue() : null, "will page");
        appendPair(sj, choiceLedger != null ? choiceLedger.getValue() : null, "ledger");
        appendPair(sj, choiceStatue != null ? choiceStatue.getValue() : null, "bloody statue");
        return sj.toString();
    }

    private void appendPair(StringJoiner sj, String suspect, String evidence) {
        if (suspect != null && !suspect.trim().isEmpty()) {
            sj.add(suspect.trim().toLowerCase() + "=" + evidence);
        }
    }
}
