package com.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class DataLoader extends DataConstants {

    /**
     * Returns rooms from the RoomList singleton (in-memory).
     * Use loadRooms() to populate from disk first.
     */
    public ArrayList<Room> getRooms() {
        return RoomList.getInstance().getRooms();
    }

    /**
     * Returns users from the UserList singleton (in-memory).
     * Use loadUsers() to populate from disk first.
     */
    public ArrayList<User> getUsers() {
        return UserList.getInstance().getUsers();
    }

    /**
     * Loads rooms from ROOMS_FILE and populates the RoomList singleton.
     * Each line is parsed by Room.fromString().
     */
    public void loadRooms() {
        ArrayList<Room> loaded = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(ROOMS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Room r = Room.fromString(line);
                if (r != null) loaded.add(r);
            }
            // Replace contents of singleton with loaded rooms
            RoomList list = RoomList.getInstance();
            list.getRooms().clear();
            list.getRooms().addAll(loaded);

            System.out.println("Loaded " + loaded.size() + " rooms from " + ROOMS_FILE);
        } catch (IOException e) {
            // If file doesn't exist or can't be read, just report and keep current in-memory rooms
            System.err.println("Could not load rooms from " + ROOMS_FILE + ": " + e.getMessage());
        }
    }

    /**
     * Loads users from USERS_FILE and populates the UserList singleton.
     * Each line is parsed by User.fromString().
     */
    public void loadUsers() {
        ArrayList<User> loaded = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                User u = User.fromString(line);
                if (u != null) loaded.add(u);
            }
            // Replace contents of singleton with loaded users
            UserList list = UserList.getInstance();
            list.getUsers().clear();
            list.getUsers().addAll(loaded);

            System.out.println("Loaded " + loaded.size() + " users from " + USERS_FILE);
        } catch (IOException e) {
            System.err.println("Could not load users from " + USERS_FILE + ": " + e.getMessage());
        }
    }

    /**
     * Placeholder - returns persisted game sessions for a user if you later implement them.
     * For now returns an empty list.
     */
    public ArrayList<GameSession> getGameSessions(User user) {
        return new ArrayList<GameSession>();
    }
}
