package com.example.model;

import java.util.ArrayList;

public class RoomList {
    private static RoomList roomList;
    private ArrayList<Room> rooms;

    private RoomList() {
        rooms = new ArrayList<>();
    }

    public static RoomList getInstance() {
        return new RoomList();
    }

    public void addRoom(Room room) {
        rooms.add(room);
    }

    public Room getRoom(String roomId) {
        return null; 
    }

    public ArrayList<Room> getAllRooms() {
        return new ArrayList<>(); 
    }

    public void saveRooms() {
       
    }

    public void loadRooms() {
        
    }
}
