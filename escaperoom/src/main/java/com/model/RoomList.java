package com.model;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import com.model.Room;

public class RoomList {

    ArrayList<Room> rooms;
    private static RoomList roomList;

    private RoomList() {
        rooms = new ArrayList<>();
    }

    public static RoomList getInstance() {
        if (roomList == null)
            roomList = new RoomList();
        return roomList;
    }

    public void addRoom(Room room) {
        rooms.add(room);
    }

    public ArrayList<Room> getRooms() {
    return rooms;
   }

  
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


    public void loadRooms() {
        
    }
}
