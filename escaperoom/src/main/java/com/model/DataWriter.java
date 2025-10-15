package com.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataWriter extends DataConstants {
    private static final ObjectMapper mapper = new ObjectMapper()
        .enable(SerializationFeature.INDENT_OUTPUT);
    private static final Logger LOGGER = Logger.getLogger(DataWriter.class.getName());

    public static boolean saveUsers() {
        UserList userList = UserList.getInstance();
        List<User> users = userList.getUsers();
        return writeToFile(users, USERS_FILE, "Users");
    }

    public static boolean saveRooms() {
        RoomList roomList = RoomList.getInstance();
        List<Room> rooms = roomList.getRooms();
        return writeToFile(rooms, ROOMS_FILE, "Rooms");
    }

    public static boolean saveGameSessions(List<GameSession> sessions) {
        return writeToFile(sessions, SESSIONS_FILE, "GameSessions");
    }

    private static <T> boolean writeToFile(List<T> data, String filePath, String dataType) {
        try {
            File file = new File(filePath);
            File parentDir = file.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs();
            }
            mapper.writeValue(file, data);
            return true;
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to save " + dataType + " to " + filePath, e);
            return false;
        }
    }

    public static void main(String[] args) {
        saveUsers();
        saveRooms();
    }
}