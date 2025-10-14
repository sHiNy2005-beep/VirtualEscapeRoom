package com.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Room {

    private String roomId;
    private String title;
    private ArrayList<String> items;
    private String difficulty;
    private boolean isLocked;
    private ArrayList<Puzzle> puzzles;
    private HashMap<User, Integer> leaderboard;

    public Room(String title, String difficulty, boolean isLocked) {
        this.title = title;
        this.difficulty = difficulty;
        this.puzzles = new ArrayList<>();
        this.items = new ArrayList<>();
        this.isLocked = isLocked;
        this.leaderboard = new HashMap<>();
        this.roomId = UUID.randomUUID().toString();
    }

    public ArrayList<Puzzle>getPuzzles() { 
        return puzzles; 
    }

    public void addPuzzle(String puzzleId, Puzzle puzzle) {
         if (puzzle != null) {
            puzzles.add(puzzle);
        }
    }

    public HashMap<User, Integer> getLeaderboard() { 
        return leaderboard; 
    }

    public void addItem(String item) {
         if (item != null) {
            items.add(item); 
         }
    }

    public ArrayList<String> getItems() { 
        return items; 
    }

    public String getTitle() { 
        return title; 
    }

    public String getDifficulty() { 
        return difficulty; 
    }

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

    public String getRoomId() { 
        return roomId; 
    }

    @Override
    public String toString() {
        return "Room:" + "title='" + title + '\'' + ", difficulty='" + difficulty + '\'' + ", items=" + items ;
    }

}