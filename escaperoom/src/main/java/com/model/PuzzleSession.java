package com.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PuzzleSession {
    @JsonProperty("puzzleTitle")
    private String puzzleTitle;
    
    @JsonProperty("numHintsUsed")
    private int numHintsUsed;
    
    @JsonProperty("timeStarted")
    private long timeStarted;
    
    @JsonProperty("timeEnded")
    private long timeEnded;
    
    @JsonProperty("solved")
    private boolean solved;
    
    @JsonProperty("finalAnswer")
    private String finalAnswer;

    public PuzzleSession() {
        this.numHintsUsed = 0;
        this.timeStarted = System.currentTimeMillis();
        this.timeEnded = System.currentTimeMillis();
        this.solved = false;
    }

    @JsonCreator
    public PuzzleSession(
        @JsonProperty("puzzleTitle") String puzzleTitle,
        @JsonProperty("numHintsUsed") int numHintsUsed,
        @JsonProperty("timeStarted") long timeStarted,
        @JsonProperty("timeEnded") long timeEnded,
        @JsonProperty("solved") boolean solved,
        @JsonProperty("finalAnswer") String finalAnswer) {
        this.puzzleTitle = puzzleTitle;
        this.numHintsUsed = numHintsUsed;
        this.timeStarted = timeStarted;
        this.timeEnded = timeEnded;
        this.solved = solved;
        this.finalAnswer = finalAnswer;
    }

    public PuzzleSession(String puzzleTitle) {
        this.puzzleTitle = puzzleTitle;
        this.numHintsUsed = 0;
        this.timeStarted = System.currentTimeMillis();
        this.timeEnded = System.currentTimeMillis();
        this.solved = false;
    }

    public void useHint() {
        numHintsUsed++;
    }

    public void markSolved(String answer) {
        this.solved = true;
        this.finalAnswer = answer != null ? answer.trim() : null;
        this.timeEnded = System.currentTimeMillis();
    }

    public long getElapsedTime() {
        return timeEnded - timeStarted;
    }

    public String getPuzzleTitle() {
        return puzzleTitle;
    }

    public int getNumHintsUsed() {
        return numHintsUsed;
    }

    public long getTimeStarted() {
        return timeStarted;
    }

    public long getTimeEnded() {
        return timeEnded;
    }

    public boolean isSolved() {
        return solved;
    }

    public String getFinalAnswer() {
        return finalAnswer;
    }

    public void setTimeEnded(long timeEnded) {
        this.timeEnded = timeEnded;
    }

    public void setSolved(boolean solved) {
        this.solved = solved;
    }
}