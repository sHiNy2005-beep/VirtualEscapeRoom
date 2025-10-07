package com.model;

import java.util.ArrayList;

public class Room {

    private String roomId;
    private String title;
    private String description;
    private ArrayList<Puzzle> puzzles;
    private ArrayList<String> items;
    private String difficulty;
    private boolean isLocked;

    public Room( String title, String description, String difficulty) {
        this.title = title;
        this.description = description;
        this.difficulty = difficulty;
        this.puzzles = new ArrayList<>();
        this.items = new ArrayList<>();
        this.isLocked = true; 
    }
    
    public void addPuzzle(Puzzle puzzle) {
        puzzles.add(puzzle);
    }

    public void addItem(String item) {
        items.add(item);
    }

    public void removeItem(String item) {
        items.remove(item);
    }

    public ArrayList<String> getItems() {
        return items;
    }

    public void unlock() {
        isLocked = true;
    }

    public boolean isCompleted() {
       return true;
    }

    public String getRoomId() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getRoomId'");
    }

    public String getTitle() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getTitle'");
    }
}
