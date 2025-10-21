package com.model;

public class MathPuzzle extends Puzzle{

    int solutionValue;
   
    public MathPuzzle(String title, String description, int solutionValue) {
        super(title, description, String.valueOf(solutionValue));
        this.solutionValue = solutionValue;
        
    }

     public boolean checkAnswer(int answer) {
        boolean ok = (answer == solutionValue);
        if (ok) isSolved = true;
        return ok;
    }


    @Override
    public boolean checkAnswer(String answer) {
        try {
            int val = Integer.parseInt(answer.trim());
            return checkAnswer(val);
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
     public int getSolutionCode() {
        return solutionValue;
    }
}

