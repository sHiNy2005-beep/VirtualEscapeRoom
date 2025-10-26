package com.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Room {

    private String roomId = UUID.randomUUID().toString();
    private String title;
    private ArrayList<String> items;
    private String difficulty;
    private boolean isLocked;
    private ArrayList<Puzzle> puzzles;
    private HashMap<User, Integer> leaderboard;

    /**
     * @param title room name
     * @param difficulty difficulty level label (Easy/Medium/Hard)
     * @param isLocked  entry is initially locked or not
     */
    public Room(String title, String difficulty, boolean isLocked) {
        this.title = title;
        this.difficulty = difficulty;
        this.puzzles = new ArrayList<>();
        this.items = new ArrayList<>();
        this.isLocked = isLocked;
        this.leaderboard = new HashMap<>();
    }

    /**
     * @return  list of puzzles
     */
    public ArrayList<Puzzle> getPuzzles() {
        return puzzles;
    }

    /**
     * Add a puzzle to the room. The provided puzzle is appended to the list
     * @param puzzleId not used currently (kept for compatibility)
     * @param puzzle the puzzle instance to add
     */
    public void addPuzzle(String puzzleId, Puzzle puzzle) {
         if (puzzle != null) {
            puzzles.add(puzzle);
        }
    }

    /**
     * Return the room leaderboard mapping users to scores.
     * @return map of User -> score
     */
    public HashMap<User, Integer> getLeaderboard() {
        return leaderboard;
    }

    /*
     * Add an item to the room. Null items are ignored.
     * @param item item name to add
     */
    public void addItem(String item) {
         if (item != null) {
            items.add(item);
         }
    }

    /*
     * Return the list of items available in this room.
     */
    public ArrayList<String> getItems() {
        return items;
    }

    /** Room title accessor. */
    public String getTitle() {
        return title;
    }

    /** Difficulty label accessor. */
    public String getDifficulty() {
        return difficulty;
    }

    /** If the room is currently locked. */
    public boolean isLocked() {
        return isLocked;
    }

    public void setTitle(String title) {
         this.title = title;
   }

    public void setDifficulty(String difficulty) {
         this.difficulty = difficulty;
    }

    public void setLocked(boolean locked) {
        this.isLocked = locked;
    }

    /**
     * @return roomId
     */
    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }
    /*
    * @Override
    * Calls for the description of each puzzle in the room
    */
    @Override
    public String toString() { 
        StringBuilder sb = new StringBuilder();
        sb.append("Room: ").append(title)
          .append(" | Difficulty: ").append(difficulty)
          .append(" | Items: ").append(items);

        if (!puzzles.isEmpty()) {
            sb.append("\n  Puzzles:");
            for (Puzzle p : puzzles) {
                sb.append("\n    - ").append(p.getTitle())
                  .append(" (").append(p.getDescription()).append(")");
                 
            }
        }
        return sb.toString();
    }
}