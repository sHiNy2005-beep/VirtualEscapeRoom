package com.example;

import com.speech.Speek;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StoryReaderController {
    
    @FXML
    private Label titleLabel;
    
    @FXML
    private Label storyTextLabel;
    
    @FXML
    private ImageView storyImage;
    
    @FXML
    private Button skipButton;
    
    @FXML
    private Button replayButton;
    
    @FXML
    private MenuItem menuHome;
    
    @FXML
    private MenuItem menuRooms;
    
    @FXML
    private MenuItem menuItems;
    
    private int currentSegmentIndex = 0;
    private List<StorySegment> storySegments;
    private Task<Void> currentSpeakTask;
    private Thread currentSpeakThread;
    
    @FXML
    public void initialize() {
        initializeStory();
        displayAndReadSegment(currentSegmentIndex);
    }
    
    private void initializeStory() {
        storySegments = new ArrayList<>();

        storySegments.add(new StorySegment(
            "Welcome to the Hampton Mansion.",
            null
        ));

        storySegments.add(new StorySegment(
            "Reginald Hampton has mysteriously died and suspision fills every room.",
            null
        ));
        
        storySegments.add(new StorySegment(
            "Inside only four remain ...",
            null
        ));

        storySegments.add(new StorySegment(
            "Lily Hampton, the greiving widow.",
            "images/HamtonsWife.png"
        ));

        storySegments.add(new StorySegment(
            "Thomas Hampton, the estranged son.",
            "images/ThomasHampton.png"
        ));
        
        storySegments.add(new StorySegment(
            "Mr. Barner, the loyal butler who sees everything.",
            "images/Butler.png"
        ));

        storySegments.add(new StorySegment(
            "Ms. Louis, the gardener with secrets buried deep",
            "images/Louise.png"
        ));
        
        storySegments.add(new StorySegment(
            "Each Room hides a clue, and each person hides the truth.",
            null
        ));
        
        storySegments.add(new StorySegment(
            "Search the mansion, find the clues, uncover what happened.",
            null
        ));
        
        storySegments.add(new StorySegment(
            "The investigation starts now. Let the search begin",
            null
        ));
    }
    
    private void displayAndReadSegment(int index) {
        if (index >= storySegments.size()) {
            showRestartOption();
            return;
        }
        
        StorySegment segment = storySegments.get(index);
        
        storyTextLabel.setText(segment.getText());
        
        if (segment.getImagePath() != null && !segment.getImagePath().isEmpty()) {
            try {
                Image image = new Image(getClass().getResourceAsStream("/" + segment.getImagePath()));
                storyImage.setImage(image);
                storyImage.setVisible(true);
                storyTextLabel.setMaxWidth(500.0);
            } catch (Exception e) {
                System.err.println("Error loading image: " + segment.getImagePath());
                storyImage.setVisible(false);
                storyTextLabel.setMaxWidth(800.0);
            }
        } else {
            storyImage.setVisible(false);
            storyTextLabel.setMaxWidth(800.0);
        }
        
        speakCurrentSegment(segment.getText());
    }
    
    private void speakCurrentSegment(String text) {
        stopCurrentSpeech();
        
        currentSpeakTask = new Task<Void>() {
            @Override
            protected Void call() {
                Speek.speak(text);
                return null;
            }
        };
        
        currentSpeakTask.setOnSucceeded(e -> {
            Platform.runLater(() -> {
                if (currentSegmentIndex < storySegments.size() - 1) {
                    currentSegmentIndex++;
                    displayAndReadSegment(currentSegmentIndex);
                } else {
                    showRestartOption();
                }
            });
        });
        
        currentSpeakThread = new Thread(currentSpeakTask);
        currentSpeakThread.setDaemon(true);
        currentSpeakThread.start();
    }
    
    @FXML
    private void onSkipButtonClicked() {
        if (currentSegmentIndex >= storySegments.size() - 1) {
            // At the end, NEXT button navigates to explore rooms
            navigateTo("explorerooms.fxml");
        } else {
            // During story, SKIP button advances to next segment
            skipToNext();
        }
    }
    
    @FXML
    private void onReplayButtonClicked() {
        restartStory();
    }
    
    private void skipToNext() {
        stopCurrentSpeech();
        
        if (currentSegmentIndex < storySegments.size() - 1) {
            currentSegmentIndex++;
            displayAndReadSegment(currentSegmentIndex);
        } else {
            showRestartOption();
        }
    }
    
    private void stopCurrentSpeech() {
        if (currentSpeakThread != null && currentSpeakThread.isAlive()) {
            currentSpeakThread.interrupt();
        }
        if (currentSpeakTask != null) {
            currentSpeakTask.cancel();
        }
    }
    
    private void showRestartOption() {
        skipButton.setText("NEXT");
        replayButton.setVisible(true);
        storyTextLabel.setText("The investigation starts now. Let the search begin");
        storyImage.setVisible(false);
    }
    
    private void restartStory() {
        currentSegmentIndex = 0;
        skipButton.setText("SKIP >");
        replayButton.setVisible(false);
        displayAndReadSegment(currentSegmentIndex);
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
            stopCurrentSpeech();
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();
            
            Stage stage = (Stage) titleLabel.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.err.println("Error loading " + fxmlFile + ": " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static class StorySegment {
        private final String text;
        private final String imagePath;
        
        public StorySegment(String text, String imagePath) {
            this.text = text;
            this.imagePath = imagePath;
        }
        
        public String getText() {
            return text;
        }
        
        public String getImagePath() {
            return imagePath;
        }
    }
}