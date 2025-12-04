package com.example;

import com.model.EscapeRoomFacade;
import com.model.Room;
import com.model.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class LeaderboardController implements Initializable {
    
    @FXML
    private VBox leaderboardContainer;
    
    @FXML
    private VBox userScoreSection;
    
    @FXML
    private Label titleLabel;
    
    @FXML
    private ScrollPane scrollPane;
    
    private EscapeRoomFacade facade;
    private Room currentRoom;
    
    /**
     * Constructor - Facade should be injected before loading FXML
     */
    public LeaderboardController() {
        // Facade will be set via setter
    }
    
    /**
     * Set the facade and room for this leaderboard
     * Call this BEFORE loading the FXML or immediately after getting controller
     */
    public void setFacadeAndRoom(EscapeRoomFacade facade, Room room) {
        this.facade = facade;
        this.currentRoom = room;
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Setup scroll pane
        if (scrollPane != null) {
            scrollPane.setFitToWidth(true);
        }
        
        // Load leaderboard data if facade is already set
        if (facade != null && currentRoom != null) {
            loadLeaderboardData();
        }
    }
    
    /**
     * Loads leaderboard data from the Facade and updates the UI
     */
    public void loadLeaderboardData() {
        if (facade == null || currentRoom == null) {
            System.err.println("Facade or Room not set!");
            return;
        }
        
        // Get current user info
        User currentUser = facade.getCurrentUser();
        if (currentUser == null) {
            System.err.println("No user logged in!");
            return;
        }
        
        // Get sorted leaderboard for the current room
        Map<User, Integer> sortedLeaderboard = facade.getSortedLeaderboard(currentRoom);
        
        // Find current user's rank and score
        int userRank = 0;
        int userScore = 0;
        int rank = 1;
        
        for (Map.Entry<User, Integer> entry : sortedLeaderboard.entrySet()) {
            if (entry.getKey().getUserName().equals(currentUser.getUserName())) {
                userRank = rank;
                userScore = entry.getValue();
                break;
            }
            rank++;
        }
        
        // If user hasn't played this room yet, show current session score
        if (userRank == 0) {
            userScore = facade.getCurrentRoomScore();
            userRank = sortedLeaderboard.size() + 1;
        }
        
        // Update UI
        updateUserScoreSection(currentUser.getUserName(), userScore, userRank);
        updateLeaderboard(sortedLeaderboard);
        
        // Update title to show room name
        if (titleLabel != null) {
            titleLabel.setText(currentRoom.getTitle().toUpperCase() + " LEADERBOARD");
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
    private void updateLeaderboard(Map<User, Integer> leaderboard) {
        leaderboardContainer.getChildren().clear();
        
        // Get top 10 entries
        int rank = 1;
        int count = 0;
        
        for (Map.Entry<User, Integer> entry : leaderboard.entrySet()) {
            if (count >= 10) break;
            
            String username = entry.getKey().getUserName();
            int score = entry.getValue();
            
            HBox entryBox = createLeaderboardEntry(username, score, rank, false);
            leaderboardContainer.getChildren().add(entryBox);
            
            rank++;
            count++;
        }
        
        // If leaderboard has fewer than 10 entries, show message
        if (count == 0) {
            Label emptyLabel = new Label("No scores yet. Be the first to play!");
            emptyLabel.getStyleClass().add("section-label");
            emptyLabel.setStyle("-fx-text-fill: #888; -fx-font-size: 16px;");
            leaderboardContainer.getChildren().add(emptyLabel);
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
        
        // Rank
        Label rankLabel = new Label("#" + rank);
        rankLabel.getStyleClass().add("rank-label");
        rankLabel.setMinWidth(50);
        
        // Apply special styling for top 3
        if (rank == 1) {
            rankLabel.getStyleClass().add("rank-gold");
        } else if (rank == 2) {
            rankLabel.getStyleClass().add("rank-silver");
        } else if (rank == 3) {
            rankLabel.getStyleClass().add("rank-bronze");
        }
        
        // Player name
        Label nameLabel = new Label(playerName);
        nameLabel.getStyleClass().add("player-name");
        HBox.setHgrow(nameLabel, Priority.ALWAYS);
        
        // Score
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
    private void handleBackButton() {
        // TODO: Implement navigation back to room selection or main menu
        // Example: SceneManager.switchToRoomSelection();
        System.out.println("Back button pressed - implement navigation");
    }
    
    /**
     * Optional: Create a leaderboard for all rooms combined
     */
    public void loadGlobalLeaderboard() {
        if (facade == null) return;
        
        User currentUser = facade.getCurrentUser();
        if (currentUser == null) return;
        
        // Aggregate scores across all rooms
        Map<String, Integer> globalScores = new HashMap<>();
        
        for (Room room : facade.getAllRooms()) {
            Map<User, Integer> roomLeaderboard = facade.getSortedLeaderboard(room);
            for (Map.Entry<User, Integer> entry : roomLeaderboard.entrySet()) {
                String username = entry.getKey().getUserName();
                globalScores.put(username, 
                    globalScores.getOrDefault(username, 0) + entry.getValue());
            }
        }
        
        // Sort by total score
        Map<String, Integer> sortedGlobalScores = globalScores.entrySet().stream()
            .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue,
                (e1, e2) -> e1,
                LinkedHashMap::new
            ));
        
        // Find current user's rank
        int userRank = 1;
        int userScore = 0;
        for (Map.Entry<String, Integer> entry : sortedGlobalScores.entrySet()) {
            if (entry.getKey().equals(currentUser.getUserName())) {
                userScore = entry.getValue();
                break;
            }
            userRank++;
        }
        
        // Update UI
        updateUserScoreSection(currentUser.getUserName(), userScore, userRank);
        updateGlobalLeaderboard(sortedGlobalScores);
        
        if (titleLabel != null) {
            titleLabel.setText("GLOBAL LEADERBOARD");
        }
    }
    
    /**
     * Update leaderboard with global scores
     */
    private void updateGlobalLeaderboard(Map<String, Integer> globalScores) {
        leaderboardContainer.getChildren().clear();
        
        int rank = 1;
        int count = 0;
        
        for (Map.Entry<String, Integer> entry : globalScores.entrySet()) {
            if (count >= 10) break;
            
            HBox entryBox = createLeaderboardEntry(entry.getKey(), entry.getValue(), rank, false);
            leaderboardContainer.getChildren().add(entryBox);
            
            rank++;
            count++;
        }
    }
}