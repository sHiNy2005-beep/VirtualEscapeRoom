package com.model;
import java.util.ArrayList;

/**
 * Represents a single play session for a user in a specific room.
 * <p>The session tracks start/end times, collected items, hints used,
 * puzzle progress (via {@link PuzzleSession}), and a session score.
 * Instances are created when a user starts a game and are persisted
 * to the users JSON by {@link DataWriter}.</p>
 */
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
    

    /**
     * Create a new GameSession for the given user and room.
     * The constructor initializes puzzle sessions for each puzzle in the room.
     * @param user the player who owns this session
     * @param room the room being played
     */
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

    /**
     * Return the list of puzzle sessions associated with this game session.
     * @return list of {@link PuzzleSession}
     */
    public ArrayList<PuzzleSession> getPuzzleSessions() {
    return puzzleSessions;
   }
    
    /*
     * Record the session start time.
     */
    public void startSession() {
        this.startTime = System.currentTimeMillis();
    }

    /**
     * Mark the session as ended and record the end time.
     */
    public void endSession() {
        this.endTime = System.currentTimeMillis();
        this.isCompleted = true;
    }

    /**
     * Record the use of a hint for a specific puzzle in this session.
     * Increments the session-level hint counter and the matching PuzzleSession int count.
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
     * @param puzzleTitle the title of the puzzle 
     * @param answer the player's answer
     * @return true if a matching puzzle session was found and updated, false otherwise
     */
    public boolean submitAnswer(String puzzleTitle, String answer) {
    for (PuzzleSession ps : puzzleSessions) {
        if (ps.getPuzzleTitle().equalsIgnoreCase(puzzleTitle)) {
            ps.markSolved(answer);
            return true;
        }
    }
    return false;
   }


    /*
    * Add an item to the session inventory.
    */
    public void collectItem(String item) {
        inventory.add(item);
    }

    /*
     * Check whether the session inventory contains the given item.
     */
    public boolean hasItem(String item) {
        return inventory.contains(item);
    }

    /** Return a list of items collected during the session. */
    public ArrayList<String> getInventory() {
        return inventory;
    }

    /**
     * Calculate and return the session score.
     *
     * <p>This method is currently a stub and returns 0. Implement scoring
     * logic here (for example based on solved puzzles, hints used and time).</p>
     *
     * @return computed score for the session
     */
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