package com.model;

import java.util.ArrayList;

public class RoomSession {
        
    private String roomId;
    private String roomTitle;
    private long startTime;
    private long endTime;
    private boolean isCompleted;
    private int hintsUsed;
    private ArrayList<String> inventory;
    private ArrayList<PuzzleSession> puzzleSessions;
    
    /**
     * Create a new RoomProgress for the given room.
     * Initializes puzzle sessions for each puzzle in the room.
     * 
     * @param room the room whose progress is being tracked
     */
    public RoomSession(Room room) {
        if (room == null) {
            throw new IllegalArgumentException("Room cannot be null");
        }
        this.roomId = room.getRoomId();
        this.roomTitle = room.getTitle();
        this.inventory = new ArrayList<>();
        this.hintsUsed = 0;
        this.isCompleted = false;
        this.puzzleSessions = new ArrayList<>();
        this.startTime = System.currentTimeMillis();
        
        // Initialize a puzzle session for each puzzle in the room
        for (Puzzle p : room.getPuzzles()) {
            puzzleSessions.add(new PuzzleSession(p.getTitle()));
        }
    }
    
    /**
     * Get the puzzle session for a specific puzzle.
     * Creates a new session if one doesn't exist.
     * 
     * @param puzzle the puzzle to get the session for
     * @return the corresponding PuzzleSession
     */
    public PuzzleSession getPuzzleSession(Puzzle puzzle) {
        for (PuzzleSession ps : puzzleSessions) {
            if (ps.getPuzzleTitle().equalsIgnoreCase(puzzle.getTitle())) {
                return ps;
            }
        }
        // Create new puzzle session if not found
        PuzzleSession newPS = new PuzzleSession(puzzle.getTitle());
        puzzleSessions.add(newPS);
        return newPS;
    }
    
    /**
     * Record the use of a hint for a specific puzzle.
     * 
     * @param puzzleTitle title of the puzzle for which a hint was used
     */
    public void useHint(String puzzleTitle) {
        hintsUsed++;
        for (PuzzleSession ps : puzzleSessions) {
            if (ps.getPuzzleTitle().equalsIgnoreCase(puzzleTitle)) {
                ps.useHint();
                break;
            }
        }
    }
    
    /**
     * Add an item to the room inventory.
     * 
     * @param item the item to collect
     */
    public void collectItem(String item) {
        if (item != null && !inventory.contains(item)) {
            inventory.add(item);
        }
    }
    
    /**
     * Check if the inventory contains a specific item.
     * 
     * @param item the item to check for
     * @return true if the item is in the inventory
     */
    public boolean hasItem(String item) {
        return inventory.contains(item);
    }
    
    /**
     * Mark this room as completed.
     */
    public void complete() {
        this.isCompleted = true;
        this.endTime = System.currentTimeMillis();
    }
    
    /**
     * Calculate completion percentage for this room.
     * 
     * @return percentage of puzzles solved (0-100)
     */
    public int getCompletionPercent() {
        if (puzzleSessions == null || puzzleSessions.isEmpty()) {
            return 0;
        }
        long solved = puzzleSessions.stream().filter(PuzzleSession::isSolved).count();
        return (int) ((solved * 100) / puzzleSessions.size());
    }
    
    /**
     * Get the number of solved puzzles in this room.
     * 
     * @return count of solved puzzles
     */
    public int getSolvedCount() {
        int count = 0;
        for (PuzzleSession ps : puzzleSessions) {
            if (ps.isSolved()) {
                count++;
            }
        }
        return count;
    }
    
    /**
     * Get the total number of puzzles in this room.
     * 
     * @return total puzzle count
     */
    public int getTotalPuzzles() {
        return puzzleSessions.size();
    }
    
    /**
     * Calculate the score for this room based on puzzles solved and hints used.
     * 
     * @param difficulty the difficulty level of the room
     * @return calculated score
     */
    public int calculateScore(String difficulty) {
        int totalPuzzlesSolved = getSolvedCount();
        int totalHints = hintsUsed;
        
        int baseScore = 10000;
        int puzzleBonus = totalPuzzlesSolved * 1000;
        int hintPenalty = totalHints * 200;
        
        double difficultyMultiplier = 1.0;
        if ("Easy".equalsIgnoreCase(difficulty)) {
            difficultyMultiplier = 1.0;
        } else if ("Medium".equalsIgnoreCase(difficulty)) {
            difficultyMultiplier = 1.5;
        } else if ("Hard".equalsIgnoreCase(difficulty)) {
            difficultyMultiplier = 2.0;
        }
        
        return (int)((baseScore + puzzleBonus - hintPenalty) * difficultyMultiplier);
    }
    
    /**
     * Get the duration spent in this room in seconds.
     * 
     * @return duration in seconds, or 0 if not yet completed
     */
    public long getDuration() {
        if (endTime == 0) {
            return (System.currentTimeMillis() - startTime) / 1000;
        }
        return (endTime - startTime) / 1000;
    }
    
    // Getters and Setters
    
    public String getRoomId() {
        return roomId;
    }
    
    public String getRoomTitle() {
        return roomTitle;
    }
    
    public long getStartTime() {
        return startTime;
    }
    
    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }
    
    public long getEndTime() {
        return endTime;
    }
    
    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }
    
    public boolean isCompleted() {
        return isCompleted;
    }
    
    public void setCompleted(boolean completed) {
        this.isCompleted = completed;
    }
    
    public int getHintsUsed() {
        return hintsUsed;
    }
    
    public void setHintsUsed(int hintsUsed) {
        this.hintsUsed = hintsUsed;
    }
    
    public ArrayList<String> getInventory() {
        return inventory;
    }
    
    public void setInventory(ArrayList<String> inventory) {
        this.inventory = inventory;
    }
    
    public ArrayList<PuzzleSession> getPuzzleSessions() {
        return puzzleSessions;
    }
    
    public void setPuzzleSessions(ArrayList<PuzzleSession> puzzleSessions) {
        this.puzzleSessions = puzzleSessions;
    }
    
    @Override
    public String toString() {
        return "RoomSession{" +
               "room='" + roomTitle + '\'' +
               ", completed=" + isCompleted +
               ", progress=" + getCompletionPercent() + "%" +
               ", hintsUsed=" + hintsUsed +
               '}';
    }
}
