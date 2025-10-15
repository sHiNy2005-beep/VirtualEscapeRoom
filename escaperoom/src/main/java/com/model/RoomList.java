package com.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoomList {
    private static RoomList roomList;
    
    @JsonProperty("rooms")
    private Map<String, Room> roomsByTitle;
    
    private RoomList() {
        roomsByTitle = new HashMap<>();
        List<Room> loadedRooms = DataLoader.getRooms();
        for (Room room : loadedRooms) {
            if (room.getTitle() != null) {
                roomsByTitle.put(room.getTitle().toLowerCase(), room);
            }
        }
    }
    
    public static RoomList getInstance() {
        if (roomList == null) {
            roomList = new RoomList();
        }
        return roomList;
    }
    
    public void addRoom(Room room) {
        if (room != null && room.getTitle() != null) {
            roomsByTitle.put(room.getTitle().toLowerCase(), room);
        }
    }
    
    public ArrayList<Room> getRooms() {
        return new ArrayList<>(roomsByTitle.values());
    }
    
    public void setRooms(ArrayList<Room> rooms) {
        roomsByTitle.clear();
        for (Room room : rooms) {
            if (room != null && room.getTitle() != null) {
                roomsByTitle.put(room.getTitle().toLowerCase(), room);
            }
        }
    }
    
    public Map<String, Room> getRoomsByTitle() {
        return roomsByTitle;
    }
    
    public void setRoomsByTitle(Map<String, Room> roomsByTitle) {
        this.roomsByTitle = roomsByTitle;
    }
    
    public Room findRoom(String title) {
        if (title == null) return null;
        return roomsByTitle.get(title.toLowerCase());
    }
    
    public boolean roomExists(String title) {
        return title != null && roomsByTitle.containsKey(title.toLowerCase());
    }
    
    public boolean removeRoom(String title) {
        if (title == null) return false;
        return roomsByTitle.remove(title.toLowerCase()) != null;
    }
    
    public int getRoomCount() {
        return roomsByTitle.size();
    }
    
    public void clear() {
        roomsByTitle.clear();
    }
    
    public static void resetInstance() {
        roomList = null;
    }
}