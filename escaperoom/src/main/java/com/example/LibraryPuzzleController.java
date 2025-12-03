package com.example;

import com.model.EscapeRoomFacade;
import com.model.Room;
import com.model.RoomSession;
import com.model.Puzzle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LibraryPuzzleController {
    
    @FXML
    private Label puzzleDescLabel;
    
    @FXML
    private TextField answerField;
    
    @FXML
    private Button enterButton;
    
    @FXML
    private Button hintButton;
    
    @FXML
    private Label feedbackLabel;
    
    @FXML
    private Button backButton;
    
    @FXML
    private ImageView bookImage;
    
    private EscapeRoomFacade facade;
    private Room libraryRoom;
    private RoomSession roomSession;
    private Puzzle currentPuzzle;
    private List<String> hints;
    private int currentHintIndex = 0;
    
    @FXML
    public void initialize() {
        System.out.println("LibraryPuzzleController initialize() called");
        
        // Initialize facade
        facade = new EscapeRoomFacade();
        
        // Load library room and puzzle data
        loadLibraryPuzzle();
    }
    
    private void loadLibraryPuzzle() {
        // Get all rooms and find the Library room
        ArrayList<Room> rooms = facade.getAllRooms();
        
        if (rooms == null || rooms.isEmpty()) {
            System.err.println("No rooms found!");
            feedbackLabel.setText("Error: No rooms available!");
            feedbackLabel.setStyle("-fx-text-fill: #f44336;");
            return;
        }
        
        for (Room room : rooms) {
            if (room.getTitle().equalsIgnoreCase("Library")) {
                libraryRoom = room;
                break;
            }
        }
        
        if (libraryRoom == null) {
            System.err.println("Library room not found!");
            feedbackLabel.setText("Error: Library room not found!");
            feedbackLabel.setStyle("-fx-text-fill: #f44336;");
            return;
        }
        
        // Start or continue the room session only if user is logged in
        if (facade.getCurrentUser() != null) {
            roomSession = facade.startGame(libraryRoom);
            System.out.println("✓ Room session started for Library");
        } else {
            System.out.println("⚠ No user logged in - session not started");
        }
        
        // Load puzzle data from JSON
        ArrayList<Puzzle> puzzles = libraryRoom.getPuzzles();
        if (puzzles != null && !puzzles.isEmpty()) {
            currentPuzzle = puzzles.get(0); // Get the first puzzle (Hidden Will Cipher)
            
            // Set puzzle description from JSON
            if (currentPuzzle.getDescription() != null) {
                puzzleDescLabel.setText(currentPuzzle.getDescription().toUpperCase());
            } else {
                puzzleDescLabel.setText("DECODE THE SECRET MESSAGE IN THE ANCIENT TEXT");
            }
            
            // Load hints from JSON with null check
            hints = currentPuzzle.getHints();
            if (hints == null) {
                hints = new ArrayList<>();
            }
            
            // Log puzzle details for debugging
            System.out.println("✓ Loaded puzzle: " + currentPuzzle.getTitle());
            System.out.println("  Solution: " + currentPuzzle.getSolution());
            System.out.println("  Hints available: " + hints.size());
        } else {
            feedbackLabel.setText("No puzzles available in this room.");
            feedbackLabel.setStyle("-fx-text-fill: #f7d884;");
        }
    }
    
    @FXML
    private void onEnterButton() {
        if (currentPuzzle == null) {
            feedbackLabel.setText("No puzzle loaded!");
            feedbackLabel.setStyle("-fx-text-fill: #f44336;");
            return;
        }
        
        String userAnswer = answerField.getText().trim();
        
        if (userAnswer.isEmpty()) {
            feedbackLabel.setText("Please enter an answer.");
            feedbackLabel.setStyle("-fx-text-fill: #f7d884;");
            return;
        }
        
        // Normalize the answer: remove all spaces and convert to uppercase
        String normalizedAnswer = userAnswer.replaceAll("\\s+", "").toUpperCase();
        
        System.out.println("User entered: '" + normalizedAnswer + "'");
        System.out.println("Expected solution: '" + currentPuzzle.getSolution() + "'");
        
        // Submit answer through facade
        boolean correct = facade.submitAnswer(currentPuzzle.getTitle(), normalizedAnswer);
        
        if (correct) {
            System.out.println("✓ Correct answer!");
            feedbackLabel.setText("✓ CORRECT! The secret is revealed: Thomas is disowned...");
            feedbackLabel.setStyle("-fx-text-fill: #4CAF50; -fx-font-weight: bold;");
            
            // Disable input after success
            answerField.setDisable(true);
            enterButton.setDisable(true);
            hintButton.setDisable(true);
            
            // Navigate to success screen after a short delay
            new Thread(() -> {
                try {
                    Thread.sleep(2000);
                    javafx.application.Platform.runLater(() -> {
                        navigateTo("LibrarySuccess.fxml");
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        } else {
            System.out.println("✗ Incorrect answer");
            feedbackLabel.setText("✗ INCORRECT. Try again or use a hint.");
            feedbackLabel.setStyle("-fx-text-fill: #f44336; -fx-font-weight: bold;");
            answerField.clear();
            answerField.requestFocus();
        }
    }
    
    @FXML
    private void onHintButton() {
        if (currentPuzzle == null) {
            feedbackLabel.setText("No puzzle loaded!");
            feedbackLabel.setStyle("-fx-text-fill: #f7d884;");
            return;
        }
        
        // Check if there are hints available
        if (hints == null || hints.isEmpty()) {
            feedbackLabel.setText("No hints available for this puzzle.");
            feedbackLabel.setStyle("-fx-text-fill: #f7d884;");
            hintButton.setDisable(true);
            return;
        }
        
        // Check if all hints have been used
        if (currentHintIndex >= hints.size()) {
            feedbackLabel.setText("No more hints available!");
            feedbackLabel.setStyle("-fx-text-fill: #f7d884;");
            hintButton.setDisable(true);
            return;
        }
        
        // Use hint through facade (for scoring penalty)
        if (facade != null) {
            facade.useHint(currentPuzzle.getTitle());
        }
        
        // Display current hint
        String hint = hints.get(currentHintIndex);
        feedbackLabel.setText("💡 HINT " + (currentHintIndex + 1) + "/" + hints.size() + ": " + hint);
        feedbackLabel.setStyle("-fx-text-fill: #f7d884; -fx-font-weight: bold;");
        
        currentHintIndex++;
        
        // Update button text to show remaining hints
        if (currentHintIndex < hints.size()) {
            hintButton.setText("HINT? (" + (hints.size() - currentHintIndex) + " left)");
        } else {
            hintButton.setDisable(true);
            hintButton.setText("NO MORE HINTS");
        }
        
        System.out.println("Hint " + currentHintIndex + " used. Remaining: " + (hints.size() - currentHintIndex));
    }
    
    @FXML
    private void onBackButton() {
        System.out.println("Back button clicked - returning to Library");
        // Save progress before leaving
        if (facade != null && roomSession != null) {
            facade.endGame();
        }
        // Return to library main view
        navigateTo("Library.fxml");
    }
    
    private void navigateTo(String fxmlFile) {
        try {
            System.out.println("Navigating to: " + fxmlFile);
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();
            
            Stage stage = (Stage) backButton.getScene().getWindow();
            if (stage != null) {
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
                System.out.println("✓ Navigation successful");
            }
        } catch (IOException e) {
            System.err.println("✗ Error loading " + fxmlFile + ": " + e.getMessage());
            e.printStackTrace();
            if (feedbackLabel != null) {
                feedbackLabel.setText("Navigation error. Please try again.");
                feedbackLabel.setStyle("-fx-text-fill: #f44336;");
            }
        } catch (Exception e) {
            System.err.println("✗ Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Get the current puzzle
     * @return the current Puzzle
     */
    public Puzzle getCurrentPuzzle() {
        return currentPuzzle;
    }
    
    /**
     * Get the facade
     * @return the EscapeRoomFacade
     */
    public EscapeRoomFacade getFacade() {
        return facade;
    }
}