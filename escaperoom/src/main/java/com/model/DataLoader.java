package com.model;

import java.util.ArrayList;
import java.util.List;

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
        return new ArrayList<User>();
    }
    
    public ArrayList<GameSession> getGameSessions(User user) {
        return new ArrayList<GameSession>();
    }
}
