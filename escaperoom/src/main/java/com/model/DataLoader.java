package com.model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class DataLoader extends DataConstants {
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final Logger LOGGER = Logger.getLogger(DataLoader.class.getName());

    public static List<User> getUsers() {
        return loadFromFile(USERS_FILE, new TypeReference<List<User>>() {}, "Users");
    }

    public static List<Room> getRooms() {
        return loadFromFile(ROOMS_FILE, new TypeReference<List<Room>>() {}, "Rooms");
    }

    public static List<GameSession> getGameSessions(User user) {
        if (user == null || user.getUserId() == null) {
            return new ArrayList<>();
        }
        
        List<GameSession> allSessions = loadFromFile(
            SESSIONS_FILE, 
            new TypeReference<List<GameSession>>() {}, 
            "GameSessions"
        );
        
        return allSessions.stream()
            .filter(session -> user.getUserId().equals(session.getUserId()))
            .collect(Collectors.toList());
    }

    public static List<GameSession> getAllGameSessions() {
        return loadFromFile(SESSIONS_FILE, new TypeReference<List<GameSession>>() {}, "GameSessions");
    }

    private static <T> List<T> loadFromFile(String filePath, TypeReference<List<T>> typeRef, String dataType) {
        try {
            File file = new File(filePath);
            if (file.exists() && file.length() > 0) {
                return mapper.readValue(file, typeRef);
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to load " + dataType + " from " + filePath, e);
        }
        return new ArrayList<>();
    }
}