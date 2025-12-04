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


public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
     
        scene = new Scene(loadFXML("login"), 900, 640);
       // scene=new Scene(loadFXML("landing"), 980, 640);
        stage.setScene(scene);
        stage.setTitle("Hamton Mansion Virtual Escape Room");
        stage.setResizable(true);      
        //stage.setMaximized(true);      
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

    
   private static final EscapeRoomFacade FACADE = new EscapeRoomFacade();

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
}
