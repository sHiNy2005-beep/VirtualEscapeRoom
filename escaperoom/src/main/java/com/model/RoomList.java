package com.model;

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

    public ArrayList<Room> getAllRooms() { 
        return rooms;
    }

    public void saveRooms() {
        
    }

    public void loadRooms() {
        
    }
}
