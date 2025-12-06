package com.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import com.model.EscapeRoomFacade;
import com.model.Room;
import com.model.RoomList;
import com.model.RoomSession;
import com.model.User;
import java.util.Map;

public class App extends Application {
    private static Scene scene;
    private static final EscapeRoomFacade FACADE = new EscapeRoomFacade();
    
    private static int score = 0;

    @Override
    public void start(Stage stage) throws IOException {
     
        scene = new Scene(loadFXML("Bedroom3"), 980, 640);
        stage.setScene(scene);
        stage.setTitle("Hamton Mansion Virtual Escape Room");
        stage.setResizable(true);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return loader.load();
    }

    public static void main(String[] args) {
        launch();
    }

    public static EscapeRoomFacade getFacade() {
        return FACADE;
    }

    public static Room getRoom(String title) {
        return RoomList.getInstance().getRoomByTitle(title);
    }

    public static RoomSession ensureSession(Room room) {
        if (room == null || FACADE.getCurrentUser() == null) return null;
        RoomSession rs = FACADE.getExistingRoomSession(room);
        return (rs != null) ? FACADE.continueRoom(room) : FACADE.startGame(room);
    }

    /**
     * Add score points to the total score
     * @param points the points to add
     */
    public static void addScore(int points) {
        score += points;
        System.out.println("Added " + points + " points. Total score: " + score);
    }

    /**
     * Get the current total score
     * @return the total score
     */
    public static int getScore() {
        return score;
    }

    /**
     * Reset the score (call when starting a new game/session)
     */
    public static void resetScore() {
        score = 0;
        System.out.println("Score reset to 0");
    }

    public static void endGame() {
        FACADE.endGame();
        saveAllRoomSessions();
    }

    public static Map<User, Integer> getLeaderboard(String roomTitle) {
        Room room = getRoom(roomTitle);
        if (room == null) {
            System.err.println("Room not found: " + roomTitle);
            return null;
        }
        return FACADE.getLeaderboard(room);
    }

    public static void saveAllRoomSessions() {
        User currentUser = FACADE.getCurrentUser();
        if (currentUser == null) {
            System.err.println("No user logged in!");
            return;
        }
        System.out.println("Saving all room sessions for user: " + currentUser.getUserName());
    }
}