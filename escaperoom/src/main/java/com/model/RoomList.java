package com.model;

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

   public Room findRoom(String title) {
        if (title == null) return null;
        for (Room r : rooms) if (title.equalsIgnoreCase(r.getTitle())) return r;
        return null;
    }

}
