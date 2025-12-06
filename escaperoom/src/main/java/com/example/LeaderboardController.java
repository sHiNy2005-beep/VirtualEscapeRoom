package com.example;

import com.model.EscapeRoomFacade;
import com.model.Room;
import com.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class LeaderboardController implements Initializable {

    @FXML private VBox leaderboardContainer;
    @FXML private VBox userScoreSection;
    @FXML private Label titleLabel;
    @FXML private ScrollPane scrollPane;
    @FXML private Button CertificateButton;

    private EscapeRoomFacade facade;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (scrollPane != null) {
            scrollPane.setFitToWidth(true);
        }
        
        // Use facade from App
        this.facade = App.getFacade();
        App.endGame();
        loadLeaderboardData();
    }

    /**
     * Loads leaderboard data from App.java and updates the UI
     */
    public void loadLeaderboardData() {
        if (facade == null) {
            System.err.println("Facade not set!");
            return;
        }

        User currentUser = facade.getCurrentUser();
        if (currentUser == null) {
            System.err.println("No user logged in!");
            return;
        }

        // Get the sorted leaderboard from Dining room
        Map<User, Integer> sortedLeaderboard = App.getLeaderboard("Dining");
        
        if (sortedLeaderboard == null) {
            System.err.println("Could not load leaderboard!");
            return;
        }

        System.out.println("Leaderboard entries: " + sortedLeaderboard.size());

        // Find current user's rank and score
        int userRank = 0;
        int userScore = App.getScore(); // Get current score from App
        int rank = 1;

        for (Map.Entry<User, Integer> entry : sortedLeaderboard.entrySet()) {
            if (entry.getKey().getUserName().equals(currentUser.getUserName())) {
                userRank = rank;
                userScore = entry.getValue(); // Get saved score from leaderboard
                break;
            }
            rank++;
        }

        // show current session score
        if (userRank == 0) {
            userScore = App.getScore();
            userRank = sortedLeaderboard.size() + 1;
        }

        // Update UI
        updateUserScoreSection(currentUser.getUserName(), userScore, userRank);
        updateLeaderboard(sortedLeaderboard, currentUser.getUserName());

        if (titleLabel != null) {
            titleLabel.setText("OVERALL LEADERBOARD");
        }
    }

    /**
     * Updates the user's score section at the top
     */
    private void updateUserScoreSection(String username, int score, int rank) {
        userScoreSection.getChildren().clear();

        Label yourRankLabel = new Label("Your Rank");
        yourRankLabel.getStyleClass().add("section-label");

        HBox userEntry = createLeaderboardEntry(username, score, rank, true);

        userScoreSection.getChildren().addAll(yourRankLabel, userEntry);
    }

    /**
     * Updates the top 10 leaderboard
     */
    private void updateLeaderboard(Map<User, Integer> leaderboard, String currentUsername) {
        leaderboardContainer.getChildren().clear();

        if (leaderboard.isEmpty()) {
            Label emptyLabel = new Label("No scores yet. Be the first to complete the game!");
            emptyLabel.getStyleClass().add("section-label");
            emptyLabel.setStyle("-fx-text-fill: #888; -fx-font-size: 16px;");
            leaderboardContainer.getChildren().add(emptyLabel);
            return;
        }

        int rank = 1;
        for (Map.Entry<User, Integer> entry : leaderboard.entrySet()) {
            if (rank > 10) break; // Show only top 10

            String username = entry.getKey().getUserName();
            int score = entry.getValue();
            boolean isCurrentUser = username.equals(currentUsername);

            HBox entryBox = createLeaderboardEntry(username, score, rank, isCurrentUser);
            leaderboardContainer.getChildren().add(entryBox);

            rank++;
        }
    }

    /**
     * Creates a single leaderboard entry (row)
     */
    private HBox createLeaderboardEntry(String playerName, int score, int rank, boolean isCurrentUser) {
        HBox entryBox = new HBox(20);
        entryBox.getStyleClass().add("leaderboard-entry");
        if (isCurrentUser) {
            entryBox.getStyleClass().add("current-user");
        }
        entryBox.setAlignment(Pos.CENTER_LEFT);
        entryBox.setPadding(new Insets(15, 20, 15, 20));

        Label rankLabel = new Label("#" + rank);
        rankLabel.getStyleClass().add("rank-label");
        rankLabel.setMinWidth(50);

        if (rank == 1) {
            rankLabel.getStyleClass().add("rank-gold");
        } else if (rank == 2) {
            rankLabel.getStyleClass().add("rank-silver");
        } else if (rank == 3) {
            rankLabel.getStyleClass().add("rank-bronze");
        }

        Label nameLabel = new Label(playerName);
        nameLabel.getStyleClass().add("player-name");
        HBox.setHgrow(nameLabel, Priority.ALWAYS);

        Label scoreLabel = new Label(String.format("%,d", score));
        scoreLabel.getStyleClass().add("score-label");

        entryBox.getChildren().addAll(rankLabel, nameLabel, scoreLabel);
        return entryBox;
    }

    /**
     * Refreshes the leaderboard (call this after game ends, etc.)
     */
    public void refreshLeaderboard() {
        loadLeaderboardData();
    }

    /**
     * Handle back button action
     */
    @FXML
    private void ViewCertificateButton() {
        loadSceneOnCurrentStage(CertificateButton, " Certificate.fxml");
    }

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
