package com.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.model.*;


public class DataLoader extends DataConstants {

    
    public ArrayList<Room> getRooms() {
        synchronized (Room.class) {
            ArrayList<Room> rooms = new ArrayList<>();
            List<String> lines = readAllLines(ROOMS_FILE);
            for (String line : lines) {
                Room room = Room.fromString(line);
                if (room != null) rooms.add(room);
            }
        return new ArrayList<Room>();
    }
    
    public  ArrayList<User> getUsers() {
        synchronized (User.class) {
            ArrayList<User> users = new ArrayList<>();
            List<String> lines = readAllLines(USERS_FILE);
            for (String line : lines) {
                User user = User.fromString(line);
                if (user != null) users.add(user);
            }
        return new ArrayList<User>();}
    }
    
    public ArrayList<GameSession> getGameSessions(User user) {
        return new ArrayList<GameSession>();
    }
}
    
}
