package com.example.model;

public class CodePuzzle extends Puzzle {
   
    private String correctCode;

    public CodePuzzle(String title, String description, String code) {
        super(title, description, code);
    }

    @Override
    public boolean solve(String answer) {
        return false; 
    }

    @Override
    public boolean checkAnswer(String answer) {
        return false; 
    }
    
}
