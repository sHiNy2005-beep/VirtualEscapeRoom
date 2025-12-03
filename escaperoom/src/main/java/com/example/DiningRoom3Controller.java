package com.example;

import com.model.EscapeRoomFacade;
import com.model.Room;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import java.io.IOException;

public class DiningRoom3Controller {

    private final EscapeRoomFacade facade = App.getFacade();
    private Room dining;

    @FXML private Label titleLabel;
    @FXML private Label storyLabel;
    @FXML private Label scoreLabel;
    @FXML private Button backButton;
    @FXML private Button nextButton;
    @FXML private MenuItem menuHome;
    @FXML private MenuItem menuRooms;
    @FXML private MenuItem menuItems;

    @FXML
    private void initialize() {
        dining = App.getRoom("Dining");
        App.ensureSession(dining);
        if (scoreLabel != null) {
            int score = 0;
            try { score = facade.getCurrentRoomScore(); } catch (Exception ignored) { }
            scoreLabel.setText(String.valueOf(score));
        }
    }

    @FXML
    private void onBack() {
        try {
            App.setRoot("DiningRoom2");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onNext() {
         try {
        App.getFacade().endGame();         
        App.setRoot("certificate");          
    } catch (IOException e) {
        e.printStackTrace();
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
}
