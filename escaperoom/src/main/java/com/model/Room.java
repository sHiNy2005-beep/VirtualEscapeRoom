package com.model;

import java.util.ArrayList;
import java.util.HashMap;

public class Room {

    private String title;
    private ArrayList<String> items;
    private String difficulty;
    private boolean isLocked;
    private HashMap<String, Puzzle> puzzles;

    public Room(String title, String difficulty, boolean isLocked) {
        this.title = title;
        this.difficulty = difficulty;
        this.puzzles = new HashMap<>();
        this.items = new ArrayList<>();
        this.isLocked = isLocked;
    }

    public HashMap<String, Puzzle> getPuzzles() { 
        return puzzles; 
    }

    public Puzzle getPuzzle(String puzzleId) {
        return puzzles.get(puzzleId);
    }

    public void addPuzzle(String puzzleId, Puzzle puzzle) {
        if (puzzleId != null && puzzle != null) puzzles.put(puzzleId, puzzle);
    }

    public void addItem(String item) {
         if (item != null) items.add(item); 
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

    @Override
    public String toString() {
        return "Room:" + "title='" + title + '\'' + ", difficulty='" + difficulty + '\'' + ", items=" + items ;
    }

}