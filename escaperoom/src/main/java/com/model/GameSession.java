package com.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a player's overall game session across multiple rooms.
 * Tracks progress in each room via RoomSession objects and maintains
 * aggregate statistics across all rooms.
 */
public class GameSession {

    private String sessionId;
    private User user;
    private long sessionStartTime;
    private long sessionEndTime;
    private boolean isSessionCompleted;
    private HashMap<String, RoomSession> roomSessionMap; // Maps roomId to RoomSession
    private RoomSession currentRoomSession; // The room currently being played
    
    /**
     * Create a new GameSession for the given user.
     * 
     * @param user the player who owns this session
     */
    public GameSession(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        this.user = user;
        this.sessionId = "session_" + System.currentTimeMillis() + "_" + user.getUserName().toLowerCase();
        this.sessionStartTime = System.currentTimeMillis();
        this.roomSessionMap = new HashMap<>();
        this.isSessionCompleted = false;
    }
    
    /**
     * Start or resume progress in a specific room.
     * If progress already exists for this room, it will be resumed.
     * 
     * @param room the room to enter
     * @return the RoomSession for this room
     */
    public RoomSession enterRoom(Room room) {
        if (room == null) {
            throw new IllegalArgumentException("Room cannot be null");
        }
        
        // Check if session already exists for this room
        RoomSession roomSession = roomSessionMap.get(room.getRoomId());
        if (roomSession == null) {
            // Create new session for this room
            roomSession = new RoomSession(room);
            roomSessionMap.put(room.getRoomId(), roomSession);
        }
        
        this.currentRoomSession = roomSession;
        return roomSession;
    }
    
    /**
     * Get the session for a specific room.
     * 
     * @param room the room to get session for
     * @return RoomSession or null if no session exists for this room
     */
    public RoomSession getRoomSession(Room room) {
        if (room == null) return null;
        return roomSessionMap.get(room.getRoomId());
    }
    
    /**
     * Get the session for the currently active room.
     * 
     * @return current RoomSession or null if no room is active
     */
    public RoomSession getCurrentRoomSession() {
        return currentRoomSession;
    }
    
    /**
     * Submit an answer for a puzzle in the current room.
     * 
     * @param puzzleTitle the title of the puzzle
     * @param answer the player's answer
     * @param room the room containing the puzzle
     * @return true if the answer is correct
     */
    public boolean submitAnswer(String puzzleTitle, String answer, Room room) {
        if (room == null || currentRoomSession == null) return false;
        
        for (Puzzle p : room.getPuzzles()) {
            if (p.getTitle().equalsIgnoreCase(puzzleTitle)) {
                boolean correct = p.checkAnswer(answer);
                PuzzleSession ps = currentRoomSession.getPuzzleSession(p);
                ps.setFinalAnswer(answer);
                
                if (correct) {
                    ps.setSolved(true);
                    System.out.println("Correct! The solution was: " + answer);
                } else {
                    System.out.println("Incorrect! Try again.");
                }
                return correct;
            }
        }
        return false;
    }
    
    /**
     * Use a hint for a puzzle in the current room.
     * 
     * @param puzzleTitle the title of the puzzle
     */
    public void useHint(String puzzleTitle) {
        if (currentRoomSession != null) {
            currentRoomSession.useHint(puzzleTitle);
        }
    }
    
    /**
     * Collect an item in the current room.
     * 
     * @param item the item to collect
     */
    public void collectItem(String item) {
        if (currentRoomSession != null) {
            currentRoomSession.collectItem(item);
        }
    }
    
    /**
     * Check if the current room's inventory has a specific item.
     * 
     * @param item the item to check for
     * @return true if the item is in the current room's inventory
     */
    public boolean hasItem(String item) {
        if (currentRoomSession != null) {
            return currentRoomSession.hasItem(item);
        }
        return false;
    }
    
    /**
     * Mark the current room as completed.
     */
    public void completeCurrentRoom() {
        if (currentRoomSession != null) {
            currentRoomSession.complete();
        }
    }
    
    /**
     * End the entire game session.
     */
    public void endSession() {
        this.sessionEndTime = System.currentTimeMillis();
        this.isSessionCompleted = true;
    }
    
    /**
     * Calculate the total score across all rooms in this session.
     * 
     * @return aggregate score
     */
    public int calculateTotalScore() {
        int totalScore = 0;
        for (RoomSession roomSession : roomSessionMap.values()) {
            // You'll need to pass the room difficulty somehow
            // This is a simplified version
            totalScore += roomSession.calculateScore("Medium");
        }
        return totalScore;
    }
    
    /**
     * Get the total number of puzzles solved across all rooms.
     * 
     * @return total puzzles solved
     */
    public int getTotalPuzzlesSolved() {
        int total = 0;
        for (RoomSession roomSession : roomSessionMap.values()) {
            total += roomSession.getSolvedCount();
        }
        return total;
    }
    
    /**
     * Get the total number of hints used across all rooms.
     * 
     * @return total hints used
     */
    public int getTotalHintsUsed() {
        int total = 0;
        for (RoomSession roomSession : roomSessionMap.values()) {
            total += roomSession.getHintsUsed();
        }
        return total;
    }
    
    /**
     * Get the number of rooms completed in this session.
     * 
     * @return count of completed rooms
     */
    public int getCompletedRoomsCount() {
        int count = 0;
        for (RoomSession roomSession : roomSessionMap.values()) {
            if (roomSession.isCompleted()) {
                count++;
            }
        }
        return count;
    }
    
    /**
     * Get the total number of rooms visited in this session.
     * 
     * @return count of visited rooms
     */
    public int getVisitedRoomsCount() {
        return roomSessionMap.size();
    }
    
    /**
     * Get the overall completion percentage across all rooms.
     * 
     * @return percentage (0-100)
     */
    public int getOverallCompletionPercent() {
        if (roomSessionMap.isEmpty()) return 0;
        
        int totalPercent = 0;
        for (RoomSession roomSession : roomSessionMap.values()) {
            totalPercent += roomSession.getCompletionPercent();
        }
        return totalPercent / roomSessionMap.size();
    }
    
    /**
     * Get all room session entries.
     * 
     * @return map of roomId to RoomSession
     */
    public Map<String, RoomSession> getAllRoomSessions() {
        return new HashMap<>(roomSessionMap);
    }
    
    /**
     * Get the session duration in seconds.
     * 
     * @return duration in seconds
     */
    public long getSessionDuration() {
        if (sessionEndTime == 0) {
            return (System.currentTimeMillis() - sessionStartTime) / 1000;
        }
        return (sessionEndTime - sessionStartTime) / 1000;
    }
    
    // Getters and Setters
    
    public String getSessionId() {
        return sessionId;
    }
    
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
    
    public User getUser() {
        return user;
    }
    
    public long getSessionStartTime() {
        return sessionStartTime;
    }
    
    public void setSessionStartTime(long sessionStartTime) {
        this.sessionStartTime = sessionStartTime;
    }
    
    public long getSessionEndTime() {
        return sessionEndTime;
    }
    
    public void setSessionEndTime(long sessionEndTime) {
        this.sessionEndTime = sessionEndTime;
    }
    
    public boolean isSessionCompleted() {
        return isSessionCompleted;
    }
    
    public void setSessionCompleted(boolean sessionCompleted) {
        this.isSessionCompleted = sessionCompleted;
    }
    
    public HashMap<String, RoomSession> getRoomSessionMap() {
        return roomSessionMap;
    }
    
    public void setRoomSessionMap(HashMap<String, RoomSession> roomSessionMap) {
        this.roomSessionMap = roomSessionMap;
    }
    
    @Override
    public String toString() {
        return "GameSession{" +
               "user=" + user.getUserName() +
               ", roomsVisited=" + getVisitedRoomsCount() +
               ", roomsCompleted=" + getCompletedRoomsCount() +
               ", overallProgress=" + getOverallCompletionPercent() + "%" +
               ", totalHints=" + getTotalHintsUsed() +
               '}';
    }
}
