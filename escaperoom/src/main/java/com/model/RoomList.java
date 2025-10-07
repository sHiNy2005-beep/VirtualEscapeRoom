package com.model;

import java.util.ArrayList;
import com.model.Room;

public class RoomList {

    private ArrayList<Room> rooms;
    private static RoomList instance;

    private RoomList() {
        rooms = new ArrayList<>();
    }

    public static RoomList getInstance() {
        if (instance == null)
            instance = new RoomList();
        return instance;
    }

    public void addRoom(Room room) {
        rooms.add(room);
    }

    public Room getRoom(String roomId) {
       return null;
    }

    public ArrayList<Room> getAllRooms() { 
        return rooms;
    }

    public void saveRooms() {
        
    }

    public void loadRooms() {
        
    }
}
