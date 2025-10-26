package com.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class RoomList {
    private ArrayList<Room> rooms;
    private HashMap<String, Room> roomCache; // Cache rooms by ID
    private static RoomList roomList;

    private RoomList() {
        this.rooms = new ArrayList<>();
        this.roomCache = new HashMap<>();
        ArrayList<Room> loadedRooms = DataLoader.getRooms();
        if (loadedRooms != null && !loadedRooms.isEmpty()) {
            this.rooms.addAll(loadedRooms);
            // Build cache
            for (Room r : this.rooms) {
                roomCache.put(r.getRoomId(), r);
            }
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
     * Returns the SAME room instances, not copies.
     * @return list of Room
     */
    public List<Room> getRooms() {
        return Collections.unmodifiableList(rooms);
    }

    /**
     * Get a room by its ID from the cache.
     * @param roomId the room ID
     * @return the Room or null if not found
     */
    public Room getRoomById(String roomId) {
        return roomCache.get(roomId);
    }

    /**
     * Get a room by its title.
     * @param title the room title
     * @return the Room or null if not found
     */
    public Room getRoomByTitle(String title) {
        for (Room r : rooms) {
            if (r.getTitle().equalsIgnoreCase(title)) {
                return r;
            }
        }
        return null;
    }

    /**
     * Add a room to the list if it's non-null and not already present.
     * @param room Room to add
     */
    public void addRoom(Room room) {
        if (room != null && !rooms.contains(room)) {
            rooms.add(room);
            roomCache.put(room.getRoomId(), room);
        }
    }
}
