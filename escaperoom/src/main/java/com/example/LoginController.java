package com.example;

import com.model.EscapeRoomFacade;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class LoginController {

    public enum Mode { LOGIN, SIGNUP }

    @FXML private TextField usernameField;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private Label messageLabel;
    @FXML private Button toggleModeBtn;
    @FXML private Button submitBtn;
    @FXML private ImageView imageView;
    @FXML private Pane rootPane;

    private final EscapeRoomFacade facade = App.getFacade();
    private Mode mode = Mode.LOGIN;

    @FXML
    private void initialize() {

       
        try {
            URL url = getClass().getResource("/images/Mansion.png");
            InputStream is = getClass().getResourceAsStream("/images/Mansion.png");

            System.out.println("Image resource URL: " + url);
            System.out.println("getImage-> " + (is != null ? "FOUND" : "NULL"));

            if (is != null && imageView != null) {
                imageView.setImage(new Image(is));
                imageView.toBack();
            } else {
                System.err.println("Failed to load Mansion.png");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        updateUi();
        Platform.runLater(() -> { if (messageLabel != null) messageLabel.setText(""); });
    }

    private void updateUi() {
        boolean isSignup = (mode == Mode.SIGNUP);
        emailField.setVisible(isSignup);
        emailField.setManaged(isSignup);

        toggleModeBtn.setText(isSignup ? "Log in" : "Sign up");
        submitBtn.setText(isSignup ? "Sign up" : "Log in");

        messageLabel.setText("");
    }

    @FXML
    private void onToggleMode() {
        mode = (mode == Mode.LOGIN) ? Mode.SIGNUP : Mode.LOGIN;
        updateUi();
    }

    @FXML
    private void onSubmit() {
        String username = usernameField.getText() == null ? "" : usernameField.getText().trim();
        String password = passwordField.getText() == null ? "" : passwordField.getText();
        String email = emailField.getText() == null ? "" : emailField.getText().trim();

        messageLabel.setText("");

        if (username.isEmpty()) {
            messageLabel.setText("Username required.");
            return;
        }

        if (password.isEmpty()) {
            messageLabel.setText("Password required.");
            return;
        }

        if (mode == Mode.SIGNUP) {
            if (email.isEmpty()) {
                messageLabel.setText("Email required.");
                return;
            }

            boolean created = facade.createAccount(username, email, password);
            if (!created) {
                messageLabel.setText("Username or email already exists.");
                return;
            }

            
            if (!facade.login(username, password)) {
                messageLabel.setText("Account created, but login failed.");
                return;
            }

            messageLabel.setText("Account created.");
            goToExplore();
            return;
        }

        
        boolean logged = facade.login(username, password);

        if (!logged) {
            messageLabel.setText("Invalid username/email or password.");
            return;
        }

        messageLabel.setText("Login successful.");
        goToExplore();
    }

    private void goToExplore() {
        try {
            App.setRoot("story");
        } catch (IOException e) {
            e.printStackTrace();
            messageLabel.setText("Failed to open explore screen.");
        }
    }
}