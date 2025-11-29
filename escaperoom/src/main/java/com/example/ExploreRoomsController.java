package com.example;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ExploreRoomsController implements Initializable {

    @FXML private ImageView imgLibrary;
    @FXML private ImageView imgGarden;
    @FXML private ImageView imgBedroom;
    @FXML private ImageView imgStudy;
    @FXML private ImageView imgDining;
    @FXML private Button backButton;
    @FXML private Label diningLock; 

    private static final String ROOM_FXML_LIBRARY = "LibraryRoom.fxml";
    private static final String ROOM_FXML_GARDEN   = "GardenRoom.fxml";
    private static final String ROOM_FXML_BEDROOM  = "BedroomRoom.fxml";  
    private static final String ROOM_FXML_STUDY    = "StudyRoom.fxml";
    private static final String ROOM_FXML_DINING   = "DiningRoom.fxml";

    private static final String BACK_FXML = "MainMenu.fxml";

    private static final String UPLOADED_IMAGE_PATH = "file:/mnt/data/c13e2156-1b73-4f5e-ba4d-1a3ecca66673.png";

    private static final double CARD_SIZE = 180.0;
    private static final double ARC_RADIUS = 18.0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setFallbackIfMissing(imgLibrary);
        setFallbackIfMissing(imgGarden);
        setFallbackIfMissing(imgBedroom);
        setFallbackIfMissing(imgStudy);
        setFallbackIfMissing(imgDining);

        applyRoundedClip(imgLibrary);
        applyRoundedClip(imgGarden);
        applyRoundedClip(imgBedroom);
        applyRoundedClip(imgStudy);
        applyRoundedClip(imgDining);

        imgLibrary.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> loadRoomFxml(e, ROOM_FXML_LIBRARY));
        imgGarden.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> loadRoomFxml(e, ROOM_FXML_GARDEN));
        imgBedroom.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> loadRoomFxml(e, ROOM_FXML_BEDROOM));
        imgStudy.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> loadRoomFxml(e, ROOM_FXML_STUDY));
        imgDining.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> loadRoomFxml(e, ROOM_FXML_DINING));

        if (diningLock != null) diningLock.setVisible(true);

        backButton.setOnAction(evt -> {
            Node src = backButton;
            loadSceneOnCurrentStage(src, BACK_FXML);
        });
    }

    /**
     * Load a room FXML and set it as the current scene root (keeps same stage).
     * @param clickEvent mouse event used to find stage
     * @param fxmlResource resource name/path for the room FXML
     */
    private void loadRoomFxml(MouseEvent clickEvent, String fxmlResource) {
        Node src = (Node) clickEvent.getSource();
        loadSceneOnCurrentStage(src, fxmlResource);
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

    
    private void setFallbackIfMissing(ImageView iv) {
        if (iv.getImage() == null) {
            try {
                Image fallback = new Image(UPLOADED_IMAGE_PATH, CARD_SIZE, CARD_SIZE, false, true);
                iv.setImage(fallback);
            } catch (Exception ex) {
                System.err.println("Failed to load fallback image: " + ex.getMessage());
            }
        }
        iv.setFitWidth(CARD_SIZE);
        iv.setFitHeight(CARD_SIZE);
        iv.setPreserveRatio(false);
    }

    
    private void applyRoundedClip(ImageView iv) {
        Rectangle clip = new Rectangle(CARD_SIZE, CARD_SIZE);
        clip.setArcWidth(ARC_RADIUS);
        clip.setArcHeight(ARC_RADIUS);
        iv.setClip(clip);
    }

    
    private void showAlert(String title, String content) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(content);
        a.showAndWait();
    }
}
