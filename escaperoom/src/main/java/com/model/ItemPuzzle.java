package com.model;

import java.util.ArrayList; //import for arryas 
public class ItemPuzzle extends Puzzle {
    private ArrayList<String> requiredItems;

    /**
     * @param title       the puzzle title
     * @param description the puzzle description shown to players
     * @param solution    the canonical solution string
     */
    public ItemPuzzle(String title, String description, String solution) {
        super(title, description, solution);
        requiredItems = new ArrayList<>();
    }

    /**
     * Add an item name to the list of items required to solve this puzzle.
     * @param item item name to require (ignored if null)
     */
    public void addRequiredItem(String item) {
         if (item != null) requiredItems.add(item);
    }

    /**
     * Return the list of required item names for this puzzle.
     * @return mutable list of required item names
     */
    public ArrayList<String> getRequiredItems() {
        return requiredItems;
    }

    /**
     * @param answer player's submitted answer
     * @return true if the answer matches the stored solution, false otherwise
     */
    @Override
    public boolean checkAnswer(String answer) {
        return super.checkAnswer(answer);
    }
}
