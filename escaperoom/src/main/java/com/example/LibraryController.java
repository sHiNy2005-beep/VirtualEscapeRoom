package com.example;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LibraryController {
    
    @FXML
    private AnchorPane rootPane;
    
    @FXML
    private Label titleLabel;
    
    @FXML
    private Label descLabel;
    
    @FXML
    private Label hintLabel;
    
    @FXML
    private Button backButton;
    
    @FXML
    private ImageView bookImage;
    
    @FXML
    private MenuItem menuHome;
    
    @FXML
    private MenuItem menuRooms;
    
    @FXML
    private MenuItem menuItems;
    
    @FXML
    public void initialize() {
        System.out.println("LibraryController initialize() called");
        
        // Set up click handler for book
        if (bookImage != null) {
            System.out.println("✓ bookImage injected successfully");
            bookImage.setOnMouseClicked(event -> {
                System.out.println("Book clicked!");
                onBookClicked();
            });
        } else {
            System.err.println("✗ bookImage is NULL - check fx:id in FXML");
        }
    }
    
    @FXML
    private void onBookClicked() {
        System.out.println("=== BOOK CLICKED - Navigating to puzzle ===");
        navigateTo("LibraryPuzzle.fxml");
    }
    
    @FXML
    private void onBackButton() {
        System.out.println("Back button clicked");
        navigateTo("ExploreRooms.fxml");
    }
    
    @FXML
    private void onMenuHome() {
        navigateTo("Home.fxml");
    }
    
    @FXML
    private void onMenuRooms() {
        navigateTo("ExploreRooms.fxml");
    }
    
    @FXML
    private void onMenuItems() {
        navigateTo("Items.fxml");
    }
    
    private void navigateTo(String fxmlFile) {
        try {
            System.out.println("Navigating to: " + fxmlFile);
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();
            
            Stage stage = (Stage) titleLabel.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            System.out.println("✓ Navigation successful");
        } catch (IOException e) {
            System.err.println("✗ Error loading " + fxmlFile + ": " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("✗ Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}