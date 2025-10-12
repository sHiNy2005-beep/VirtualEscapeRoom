package com.model;

import java.util.ArrayList;

public class Room {

    private String title;
    private ArrayList<Puzzle> puzzles;
    private ArrayList<String> items;
    private String difficulty;
    private boolean isLocked;

    public Room(String title, String difficulty, boolean isLocked) {
        this.title = title;
        this.difficulty = difficulty;
        this.puzzles = new ArrayList<>();
        this.items = new ArrayList<>();
        this.isLocked = isLocked;
    }

     public ArrayList<Puzzle> getPuzzles() { 
        return puzzles; 
    }

    public void addPuzzle(Puzzle puzzle) { 
        puzzles.add(puzzle); 
    }

    public void addItem(String item) {
         items.add(item); 
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