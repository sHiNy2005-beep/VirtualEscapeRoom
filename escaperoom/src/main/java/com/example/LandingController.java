package com.example;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.io.IOException;

public class LandingController {

    @FXML
    private ImageView heroImage;

    @FXML
    private Button signInButton;

    @FXML
    private void handleSignIn(ActionEvent event) {
     try {
        App.setRoot("login");  
    } catch (IOException e) {
        e.printStackTrace();
        System.err.println("Could not load login.fxml");
    }
    }
}
