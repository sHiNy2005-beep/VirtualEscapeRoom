package com.model;
import java.util.ArrayList;

public class GameSession {
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
        // To be implemented: delegate to current puzzle
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
        // placeholder
        return 0;
    }

    public long getElapsedTime() {
        return endTime - startTime;
    }
}