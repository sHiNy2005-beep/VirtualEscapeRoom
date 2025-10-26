package com.model;
import java.util.ArrayList;

public class GameSession {

    private String sessionId;
    private User user;
    private Room room;
    private long startTime;
    private long endTime;
    private int score;
    private int hintsUsed;
    private boolean isCompleted;
    private long getDuration;
    private ArrayList<String> inventory;
    private ArrayList<PuzzleSession> puzzleSessions;
    

    public GameSession(User user, Room room) {
        this.user = user;
        this.room = room;
        this.inventory = new ArrayList<>();
        this.hintsUsed = 0;
        this.isCompleted = false;
        this.sessionId = "sessions"+ System.currentTimeMillis(); 
        this.puzzleSessions = new ArrayList<>();

        for (Puzzle p : room.getPuzzles()) {
        puzzleSessions.add(new PuzzleSession(p.getTitle()));
    }
    }

    

    public ArrayList<PuzzleSession> getPuzzleSessions() {
    return puzzleSessions;
   }

    public int getCompletionPercent() {
    long solved = puzzleSessions.stream().filter(PuzzleSession::isSolved).count();
    return (int) ((solved * 100) / puzzleSessions.size());
}
    
    public void startSession() {
        this.startTime = System.currentTimeMillis();
    }

    public void endSession() {
        this.endTime = System.currentTimeMillis();
        this.isCompleted = true;
    }

    public void useHint(String puzzleTitle) {
        hintsUsed++;
    for (PuzzleSession ps : puzzleSessions) {
        if (ps.getPuzzleTitle().equalsIgnoreCase(puzzleTitle)) {
            ps.useHint();
            break;
        }
    }
    }

    public boolean submitAnswer(String puzzleTitle, String answer) {
    for (PuzzleSession ps : puzzleSessions) {
        if (ps.getPuzzleTitle().equalsIgnoreCase(puzzleTitle)) {
            ps.markSolved(answer);
            return true;
        }
    }
    return false;
   }



    public void collectItem(String item) {
        inventory.add(item);
    }

    public boolean hasItem(String item) {
        return inventory.contains(item);
    }

    public ArrayList<String> getInventory() {
        return inventory;
    }

    public int calculateScore() {
        
        return 0;
    }

    //getters and setters
    
    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public int getHintsUsed() {
        return hintsUsed;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public int getScore() {
        return score;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public User getUser() {
        return user;
    }

    public Room getRoom() {
        return room;
    }

    public long getDuration() {
        if (endTime == 0) return 0;
        return (endTime - startTime) / 1000; 
    }

    public void setDuration(long duration) {
        this.getDuration = duration;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setHintsUsed(int hintsUsed) {
        this.hintsUsed = hintsUsed;
    }

    public void setCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    @Override
    public String toString() {
        return "Room: " + (room != null ? room.getTitle() : "Unknown") +
               " | Score: " + score +
               " | Hints Used: " + hintsUsed +
               " | Completed: " + isCompleted;
    }
}