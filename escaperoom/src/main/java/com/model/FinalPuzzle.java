package com.model;

public class FinalPuzzle extends Puzzle{

    String solution; 
    /*
     * Solution for the puzzle answer.
     */
   
    /*
     * FinalPuzzle with title, description and solution.
     * @param title  
     * @param description puzzle description
     * @param solution  solution for the puzzle answer
     */
    public FinalPuzzle(String title, String description, String solution) {
        super(title, description, solution);
        this.solution = solution;
    }
    
    /*
     * @param answer player's submitted answer
     * @return true if the answer matches the stored solution if not then its false.
     */
    @Override
    public boolean checkAnswer(String answer) {
        return super.checkAnswer(answer);
    }
    
}
