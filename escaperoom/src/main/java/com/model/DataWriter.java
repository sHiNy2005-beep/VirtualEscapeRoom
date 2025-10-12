package com.model;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class DataWriter extends DataConstants {

    /**
     * Saves all users from the UserList singleton into USERS_FILE.
     */
    public static void saveUsers() {
        UserList userList = UserList.getInstance();
        ArrayList<User> users = userList.getUsers();

        if (users == null || users.isEmpty()) {
            System.out.println("No users to save.");
            return;
        }

        try (FileWriter writer = new FileWriter(USERS_FILE)) {
            for (User user : users) {
                writer.write(user.toLine());
                writer.write(System.lineSeparator());
            }
            writer.flush();
            System.out.println("Users successfully saved to: " + USERS_FILE);
        } catch (IOException e) {
            System.err.println("Error while saving users: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Saves all rooms from the RoomList singleton into ROOMS_FILE.
     */
    public static void saveRooms() {
        RoomList roomList = RoomList.getInstance();
        ArrayList<Room> rooms = roomList.getRooms();

        if (rooms == null || rooms.isEmpty()) {
            System.out.println("No rooms to save.");
            return;
        }

        try (FileWriter writer = new FileWriter(ROOMS_FILE)) {
            for (Room room : rooms) {
                writer.write(room.toLine());
                writer.write(System.lineSeparator());
            }
            writer.flush();
            System.out.println("Rooms successfully saved to: " + ROOMS_FILE);
        } catch (IOException e) {
            System.err.println("Error while saving rooms: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
