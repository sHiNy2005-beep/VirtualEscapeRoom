package com.example;

import com.model.User;
import com.model.UserList;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class LoginController {

    public enum Mode { LOGIN, SIGNUP }

    @FXML private TextField usernameField;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private Label messageLabel;
    @FXML private Button toggleModeBtn;
    @FXML private Button submitBtn;
    @FXML private ImageView imageView;

    private Mode mode = Mode.LOGIN;
    private final UserList users = UserList.getInstance();

     @FXML
    private void initialize() {
    //trying to fix image issues
        try { 
            URL url = getClass().getResource("/images/Mansion.png");
            System.out.println("Image resource URL: " + url);
            InputStream is = getClass().getResourceAsStream("/images/Mansion.png"); //had to add the images folder
            System.out.println("getImage-> " + (is != null ? "FOUND" : "NULL"));

            if (is != null && imageView != null) {
                imageView.setImage(new Image(is));
                imageView.toBack(); 
                System.out.println("Image from image folder.");
            } else if (imageView != null) {
                
                java.io.File f = new java.io.File("src/main/resources/images/Mansion.png");
                if (f.exists()) {
                    imageView.setImage(new Image(f.toURI().toString()));
                    System.out.println("Image set from local file: " + f.getAbsolutePath());
                } else {
                    System.err.println("Mansion.png not found on classpath or local file fallback.");
                }
            } else {
                System.err.println("imageView");
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
        messageLabel.setText("");
        String username = usernameField.getText() == null ? "" : usernameField.getText().trim();
        String password = passwordField.getText() == null ? "" : passwordField.getText();
        String email = emailField.getText() == null ? "" : emailField.getText().trim();

        if (username.isEmpty()) { messageLabel.setText("Username required."); return; }
        if (password.isEmpty()) { messageLabel.setText("Password required."); return; }

        if (mode == Mode.SIGNUP) {
            if (email.isEmpty()) { messageLabel.setText("Email required."); return; }
            boolean created = users.addUser(username, email, password);
            if (!created) { messageLabel.setText("Username or email already exists."); return; }
            messageLabel.setText("Account created.");
        } else {
            boolean authenticated = authenticate(username, password);
            messageLabel.setText(authenticated ? "Login successful." : "Invalid username/email or password.");
        }
    }

    private boolean authenticate(String identifier, String password) {
        List<User> list = users.getUsers();
        for (User u : list) {
            if (u == null) continue;
            String uname = u.getUserName();
            String mail = u.getEmail();
            if ((uname != null && uname.equalsIgnoreCase(identifier)) || (mail != null && mail.equalsIgnoreCase(identifier))) {
                return Objects.equals(u.getPassword(), password);
            }
        }
        return false;
    }
}