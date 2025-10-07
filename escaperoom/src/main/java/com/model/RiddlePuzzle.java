package com.model;

public class RiddlePuzzle extends Puzzle {

    public RiddlePuzzle(String title, String description, String answer) {
        super(title, description, answer);
    }

    @Override
    public boolean solve(String answer) {
        if (answer.equalsIgnoreCase(solution)) {
            isCompleted = true;
            return true;
        }
        return false;
    }
}