package com.example;

import com.model.EscapeRoomFacade;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LibrarySuccessController {
    
    @FXML
    private Label scoreLabel;
    
    @FXML
    private Button backButton;
    
    @FXML
    private Button nextButton;
    
    private EscapeRoomFacade facade;
    
    @FXML
    public void initialize() {
        facade = new EscapeRoomFacade();
        
        // Get and display the score
        int score = facade.getCurrentRoomScore();
        scoreLabel.setText("+" + score);
        
        System.out.println("Library puzzle completed! Score: " + score);
    }
    
    @FXML
    private void onBackButton() {
        // Return to explore rooms
        navigateTo("ExploreRooms.fxml");
    }
    
    @FXML
    private void onNextButton() {
        // Navigate to next room or explore rooms
        navigateTo("ExploreRooms.fxml");
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
}