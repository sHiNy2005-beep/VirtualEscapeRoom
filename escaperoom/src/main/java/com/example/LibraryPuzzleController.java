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
        // Initialize facade
        facade = new EscapeRoomFacade();
        
        // Load library room and puzzle data
        loadLibraryPuzzle();
    }
    
    private void loadLibraryPuzzle() {
        // Get all rooms and find the Library room
        ArrayList<Room> rooms = facade.getAllRooms();
        for (Room room : rooms) {
            if (room.getTitle().equalsIgnoreCase("Library")) {
                libraryRoom = room;
                break;
            }
        }
        
        if (libraryRoom == null) {
            System.err.println("Library room not found!");
            feedbackLabel.setText("Error: Library room not found!");
            return;
        }
        
        // Start or continue the room session
        roomSession = facade.startGame(libraryRoom);
        
        // Load puzzle data from JSON
        ArrayList<Puzzle> puzzles = libraryRoom.getPuzzles();
        if (!puzzles.isEmpty()) {
            currentPuzzle = puzzles.get(0); // Get the first puzzle (Hidden Will Cipher)
            
            // Set puzzle description from JSON (uppercase for consistency)
            puzzleDescLabel.setText(currentPuzzle.getDescription().toUpperCase());
            
            // Load hints from JSON
            hints = currentPuzzle.getHints();
            
            // Log puzzle details for debugging
            System.out.println("Loaded puzzle: " + currentPuzzle.getTitle());
            System.out.println("Solution: " + currentPuzzle.getSolution());
            System.out.println("Hints available: " + hints.size());
        } else {
            feedbackLabel.setText("No puzzles available in this room.");
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
        
        // Submit answer through facade
        boolean correct = facade.submitAnswer(currentPuzzle.getTitle(), userAnswer);
        
        if (correct) {
            // Navigate to success screen
            navigateTo("LibrarySuccess.fxml");
        } else {
            feedbackLabel.setText("✗ INCORRECT. Try again or use a hint.");
            feedbackLabel.setStyle("-fx-text-fill: #f44336;");
            answerField.clear();
        }
    }
    
    @FXML
    private void onHintButton() {
        if (currentPuzzle == null) {
            feedbackLabel.setText("No puzzle loaded!");
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
        facade.useHint(currentPuzzle.getTitle());
        
        // Display current hint from JSON
        String hint = hints.get(currentHintIndex);
        feedbackLabel.setText("💡 HINT: " + hint);
        feedbackLabel.setStyle("-fx-text-fill: #f7d884;");
        
        currentHintIndex++;
        
        // Disable button if no more hints
        if (currentHintIndex >= hints.size()) {
            hintButton.setDisable(true);
        }
        
        System.out.println("Hint " + currentHintIndex + " used. Current score: " + facade.getCurrentRoomScore());
    }
    
    @FXML
    private void onBackButton() {
        // Return to library main view
        navigateTo("Library.fxml");
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