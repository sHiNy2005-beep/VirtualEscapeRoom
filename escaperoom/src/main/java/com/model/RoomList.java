package com.model;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class RoomList {

    ArrayList<Room> rooms;
    private static RoomList roomList;

    private RoomList() {
       this.rooms = DataLoader.getRooms();
    }

    public static RoomList getInstance() {
        if (roomList == null)
            roomList = new RoomList();
        return roomList;
    }

    public void addRoom(Room room) {
        if (room != null) rooms.add(room);
    }

    public ArrayList<Room> getRooms() {
    return rooms;
   }

    public void saveRooms() {
        DataWriter.saveRooms();
    }

}
