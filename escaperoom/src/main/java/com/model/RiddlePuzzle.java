package com.model;


public class RiddlePuzzle extends Puzzle {
    
    /**
     * @param title  puzzle title
     * @param description prompt 
     * @param solution  solution 
     */
    public RiddlePuzzle(String title, String description, String solution) {
        super(title, description, solution);
    }

    /**
     * Check whether the provided answer solves the riddle.
     * @param answer player's answer
     * @return true if the answer matches to solution
     */
    @Override
    public boolean checkAnswer(String answer) {
        return super.checkAnswer(answer);
    }
}