package com.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Room {
    @JsonProperty("roomId")
    private String roomId;
    
    @JsonProperty("title")
    private String title;
    
    @JsonProperty("items")
    private ArrayList<String> items;
    
    @JsonProperty("difficulty")
    private String difficulty;
    
    @JsonProperty("isLocked")
    private boolean isLocked;
    
    @JsonProperty("puzzles")
    private ArrayList<Puzzle> puzzles;
    
    // @JsonProperty("gameSessions")
    // private Map<UUID, GameSession> gameSessions;
    
    public Room() {
        this.roomId = UUID.randomUUID().toString();
        this.puzzles = new ArrayList<>();
        this.items = new ArrayList<>();
        // this.gameSessions = new HashMap<>();
    }
    
    public Room(String title, String difficulty, boolean isLocked) {
        this.roomId = UUID.randomUUID().toString();
        this.title = title;
        this.difficulty = difficulty;
        this.puzzles = new ArrayList<>();
        this.items = new ArrayList<>();
        this.isLocked = isLocked;
        // this.gameSessions = new HashMap<>();
    }
    
    public String getRoomId() {
        return roomId;
    }
    
    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public ArrayList<String> getItems() {
        return items;
    }
    
    public void setItems(ArrayList<String> items) {
        this.items = items;
    }
    
    public String getDifficulty() {
        return difficulty;
    }
    
    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }
    
    public boolean isLocked() {
        return isLocked;
    }
    
    public void setLocked(boolean locked) {
        this.isLocked = locked;
    }
    
    public ArrayList<Puzzle> getPuzzles() {
        return puzzles;
    }
    
    public void setPuzzles(ArrayList<Puzzle> puzzles) {
        this.puzzles = puzzles;
    }
    
    // public Map<UUID, GameSession> getGameSessions() {
    //     return gameSessions;
    // }
    
    // public void setGameSessions(Map<UUID, GameSession> gameSessions) {
    //     this.gameSessions = gameSessions;
    // }
    
    public void addPuzzle(Puzzle puzzle) {
        if (puzzle != null) {
            puzzles.add(puzzle);
        }
    }
    
    public void addItem(String item) {
        if (item != null) {
            items.add(item);
        }
    }
    
    // public void addGameSession(GameSession session) {
    //     if (session != null) {
    //         gameSessions.put(session.getSessionId(), session);
    //     }
    // }
    
    // public GameSession getGameSession(UUID sessionId) {
    //     return gameSessions.get(sessionId);
    // }
    
    // public void removeGameSession(UUID sessionId) {
    //     gameSessions.remove(sessionId);
    // }
    
    @Override
    public String toString() {
        return "Room:" + "title='" + title + '\'' + ", difficulty='" + difficulty + '\'' + ", items=" + items;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return roomId != null && roomId.equals(room.roomId);
    }
    
    @Override
    public int hashCode() {
        return roomId != null ? roomId.hashCode() : 0;
    }
}