package com.model;

public class PuzzleSession {
    
    private String puzzleTitle;
    private int numHintsUsed;
    private long timeStarted;
    private long timeEnded;
    private boolean solved;
    private String finalAnswer;
    private long duration;

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
        this.finalAnswer = answer;
        this.timeEnded = System.currentTimeMillis();
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

    public void setFinalAnswer(String finalAnswer) {
        this.finalAnswer = finalAnswer;
    }

    public void setSolved(boolean solved) {
        this.solved = solved;
    }
}
