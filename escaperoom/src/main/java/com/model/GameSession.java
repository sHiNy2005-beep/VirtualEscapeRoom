package com.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;

public class GameSession {
    @JsonProperty("user")
    private User user;
    
    @JsonProperty("room")
    private Room room;
    
    @JsonProperty("startTime")
    private long startTime;
    
    @JsonProperty("endTime")
    private long endTime;
    
    @JsonProperty("score")
    private int score;
    
    @JsonProperty("hintsUsed")
    private int hintsUsed;
    
    @JsonProperty("isCompleted")
    private boolean isCompleted;
    
    @JsonProperty("puzzleSessions")
    private List<PuzzleSession> puzzleSessions;

    public GameSession() {
        this.puzzleSessions = new ArrayList<>();
        this.hintsUsed = 0;
        this.isCompleted = false;
    }

    @JsonCreator
    public GameSession(
        @JsonProperty("user") User user,
        @JsonProperty("room") Room room,
        @JsonProperty("startTime") long startTime,
        @JsonProperty("endTime") long endTime,
        @JsonProperty("score") int score,
        @JsonProperty("hintsUsed") int hintsUsed,
        @JsonProperty("isCompleted") boolean isCompleted,
        @JsonProperty("puzzleSessions") List<PuzzleSession> puzzleSessions) {
        this.user = user;
        this.room = room;
        this.startTime = startTime;
        this.endTime = endTime;
        this.score = score;
        this.hintsUsed = hintsUsed;
        this.isCompleted = isCompleted;
        this.puzzleSessions = puzzleSessions != null ? new ArrayList<>(puzzleSessions) : new ArrayList<>();
    }

    public GameSession(User user, Room room) {
        this.user = user;
        this.room = room;
        this.puzzleSessions = new ArrayList<>();
        this.hintsUsed = 0;
        this.isCompleted = false;
        this.score = 0;
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

    public void addPuzzleSession(PuzzleSession puzzleSession) {
        if (puzzleSession != null) {
            puzzleSessions.add(puzzleSession);
        }
    }

    public List<PuzzleSession> getPuzzleSessions() {
        return new ArrayList<>(puzzleSessions);
    }

    public int calculateScore() {
        long timeBonus = Math.max(0, 10000 - getElapsedTime() / 1000);
        int hintPenalty = hintsUsed * 100;
        int puzzleScore = puzzleSessions.stream()
            .filter(ps -> ps.isSolved())
            .mapToInt(ps -> 1000)
            .sum();
        
        score = (int) (puzzleScore + timeBonus - hintPenalty);
        return Math.max(0, score);
    }

    public long getElapsedTime() {
        if (endTime > 0) {
            return endTime - startTime;
        }
        return System.currentTimeMillis() - startTime;
    }

    public User getUser() {
        return user;
    }

    public Room getRoom() {
        return room;
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

    public int getHintsUsed() {
        return hintsUsed;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setCompleted(boolean completed) {
        this.isCompleted = completed;
    }
}