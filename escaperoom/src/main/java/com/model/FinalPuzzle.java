package com.model;

public class FinalPuzzle extends Puzzle{

    String solution;
   
    public FinalPuzzle(String title, String description, String solution) {
        super(title, description, solution);
        this.solution = solution;
    }
    
    public boolean checkAnswer(String answer) {
        return super.checkAnswer(answer);
    }
    

     
    
}
