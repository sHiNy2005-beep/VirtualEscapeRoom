package com.model;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class RoomList {
    private ArrayList<Room> rooms;
    private static RoomList roomList;

    private RoomList() {
        this.rooms = new ArrayList<>();
        ArrayList<Room> loadedRooms = DataLoader.getRooms();
        if (loadedRooms != null && !loadedRooms.isEmpty()) {
            this.rooms.addAll(loadedRooms);
        }
    }

    public static RoomList getInstance() {
        if (roomList == null)
            roomList = new RoomList();
        return roomList;
    }

    public ArrayList<Room> getRooms() {
        return rooms;
    }

    public void addRoom(Room room) {
        if (room != null && !rooms.contains(room)) {
            rooms.add(room);
        }
    }
}