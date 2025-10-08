package com.model;

import java.util.ArrayList;

public class ItemPuzzle extends Puzzle {
    private ArrayList<String> requiredItems;

    public ItemPuzzle(String title, String description, String solution, ArrayList<String> items) {
        super(title, description, solution);
        this.requiredItems = items;
    }

    @Override
    public boolean solve(String answer) {
        if (answer.equalsIgnoreCase(solution)) {
            return true;
        }
        return false;
    }

    public void addRequiredItem(String item) {
        requiredItems.add(item);
    }

    public boolean hasRequiredItems(ArrayList<String> playerItems) {
        return playerItems.containsAll(requiredItems);
    }

     public boolean checkAnswer(String answer) {
        return answer.equalsIgnoreCase(solution);
    }
}