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
    private ArrayList<String> inventory;

    public GameSession(User user, Room room) {
        this.user = user;
        this.room = room;
        this.inventory = new ArrayList<>();
        this.hintsUsed = 0;
        this.isCompleted = false;
        this.sessionId = "sessions"+ System.currentTimeMillis(); 
    }

    public void startSession() {
        this.startTime = System.currentTimeMillis();
    }

    public void endSession() {
        this.endTime = System.currentTimeMillis();
        this.isCompleted = true;
    }

    public void useHint() {
        hintsUsed++;
    }

    public void submitAnswer(String answer) {
        
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

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}