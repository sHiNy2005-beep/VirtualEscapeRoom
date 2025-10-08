package com.model;

public class RiddlePuzzle extends Puzzle {

    public RiddlePuzzle(String title, String description, String answer) {
        super(title, description, answer);
    }

    @Override
    public boolean solve(String answer) {
        if (answer.equalsIgnoreCase(solution)) {
    
            return true;
        }
        return false;
    }

     public boolean checkAnswer(String answer) {
        return answer.equalsIgnoreCase(solution);
    }
}