package com.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    /**
     * Return the singleton instance of RoomList.
     * @return RoomList instance
     */
    public static synchronized RoomList getInstance() {
        if (roomList == null)
            roomList = new RoomList();
        return roomList;
    }

    /**
     * Return a view of the rooms list.
     * @return  list of Room
     */
    public List<Room> getRooms() {
        return Collections.unmodifiableList(rooms);
    }

    /**
     * Add a room to the list if it's non-null and not already present.
     * @param room Room to add
     */
    public void addRoom(Room room) {
        if (room != null && !rooms.contains(room)) {
            rooms.add(room);
        }
    }
}