package com.model;

public class CodePuzzle extends Puzzle {

    private String correctCode;

    public CodePuzzle(String title, String description, String code) {
        super(title, description, code);
        this.correctCode = code;
    }

    @Override
    public boolean solve(String answer) {
        if (answer.equals(correctCode)) {
            isCompleted = true;
            return true;
        }
        return false;
    }

     public boolean checkAnswer(String answer) {
        return answer.equalsIgnoreCase(solution);
    }
}