package com.model;

public class RiddlePuzzle extends Puzzle {
   
  public RiddlePuzzle(String title, String description, String answer) {
        super(title, description, answer);
    }

    @Override
    public boolean solve(String answer) {
        return true; 
    }

    @Override
    public boolean checkAnswer(String answer) {
        return true; 
    }
    
}
