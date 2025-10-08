package com.example.model;

import com.example.model.Room;
import com.example.model.User;
import com.example.model.GameSession;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;


public class DataLoader {

    private static final String ROOMS_FILE = "rooms.txt";
    private static final String USERS_FILE = "users.txt";
    private static final String SESSIONS_FILE = "sessions.txt";

    public static ArrayList<Room> getRooms() {
        synchronized (Room.class) {
            ArrayList<Room> rooms = new ArrayList<>();
            List<String> lines = readAllLines(ROOMS_FILE);
            for (String line : lines) {
                Room room = Room.fromString(line);
                if (room != null) rooms.add(room);
            }
            return rooms;
        }
    }

    public static ArrayList<User> getUsers() {
        synchronized (User.class) {
            ArrayList<User> users = new ArrayList<>();
            List<String> lines = readAllLines(USERS_FILE);
            for (String line : lines) {
                User user = User.fromString(line);
                if (user != null) users.add(user);
            }
            return users;
        }
    }

    public static ArrayList<GameSession> getGameSessions(User user) {
        ArrayList<GameSession> sessions = new ArrayList<>();
        List<String> lines = readAllLines(SESSIONS_FILE);
        for (String line : lines) {
            GameSession gs = GameSession.fromString(line);
            if (gs == null) continue;
            if (user == null) {
                sessions.add(gs);
            } else {
                try {
                    if (gs.belongsTo(user)) sessions.add(gs);
                } catch (Throwable t) {
                    sessions.add(gs);
                }
            }
        }
        return sessions;
    }

    private static List<String> readAllLines(String fileName) {
        try {
            Path p = Paths.get(fileName);
            if (!Files.exists(p)) return new ArrayList<String>();
            return Files.readAllLines(p);
        } catch (IOException e) {
            System.err.println("DataLoader: failed to read " + fileName + ": " + e.getMessage());
            return new ArrayList<String>();
        }
    }
}
