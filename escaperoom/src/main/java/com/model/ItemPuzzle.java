package com.model;

import java.util.ArrayList;

public class ItemPuzzle extends Puzzle {
    private ArrayList<String> requiredItems;

    public ItemPuzzle(String title, String description, String solution) {
        super(title, description, solution);
        requiredItems = new ArrayList<>();
    }

    public void addRequiredItem(String item) {
         if (item != null) requiredItems.add(item); 
    }

    public ArrayList<String> getRequiredItems() { 
        return requiredItems; 
    }

    @Override
    public boolean checkAnswer(String answer) {
        return super.checkAnswer(answer);
    }
}
