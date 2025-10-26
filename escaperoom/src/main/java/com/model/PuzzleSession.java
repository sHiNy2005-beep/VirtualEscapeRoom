package com.model;

public class PuzzleSession {
    
    private String puzzleTitle;
    private int numHintsUsed;
    private long timeStarted;
    private long timeEnded;
    private boolean solved;
    private String finalAnswer;
    private long duration;

    /**
     * Create a PuzzleSession for the given puzzle title. The session start
     * time is set to the current system time.
     * @param puzzleTitle title of the puzzle this session tracks
     */
    public PuzzleSession(String puzzleTitle) {
        this.puzzleTitle = puzzleTitle;
        this.numHintsUsed = 0;
        this.timeStarted = System.currentTimeMillis();
        this.timeEnded = System.currentTimeMillis();
        this.solved = false;
    }

    /**
     * Increment the hint counter for this puzzle session.
     */
    public void useHint() { 
        numHintsUsed++; 
    }


    /**
     * Mark this puzzle session as solved and record the final answer and end time.
     * @param answer the final answer submitted by the player
     */
    public void markSolved(String answer) {
        this.solved = true;
        this.finalAnswer = answer;
        this.timeEnded = System.currentTimeMillis();
    }

    /**
     * @return the title of the associated puzzle
     */
    public String getPuzzleTitle() { 
        return puzzleTitle; 
    }

    /**
     * @return no. of hints used for this puzzle session.
     */
    public int getNumHintsUsed() { 
        return numHintsUsed; 
    }

    /**
     * @return time when this puzzle session started
     */
    public long getTimeStarted() {
         return timeStarted; 
    }

    /**
     * @return time when the game is ended.
     */
    public long getTimeEnded() {
         return timeEnded; 
    }

    /**
     * @return true if the puzzle was solved in this session
     */
    public boolean isSolved() {
         return solved; 
    }

    /**
     * @return the final answer submitted when the puzzle was marked solved
     */
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
