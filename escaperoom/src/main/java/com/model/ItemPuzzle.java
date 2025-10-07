package com.model;

import java.util.ArrayList;

public class ItemPuzzle extends Puzzle {

    private ArrayList<String> requiredItems;

    public ItemPuzzle(String title, String description, String solution, ArrayList<String> items) {
        super(title, description, solution);
    }

    @Override
    public boolean solve(String answer) {
        return true; 
    }

    @Override
    public boolean checkAnswer(String answer) {
        return true; 

    }

    public void addRequiredItem(String item) {
        requiredItems.add(item);
    }

    public boolean hasRequiredItems(ArrayList<String> playerItems) {
        return true; 
    
}

}
