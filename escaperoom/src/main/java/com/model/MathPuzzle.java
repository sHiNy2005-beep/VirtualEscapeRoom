package com.model;
public class MathPuzzle extends Puzzle{

    /*
     * the integer solution for this puzzle
     */
    int solutionValue;
   
    /**
     * Create a MathPuzzle with a numeric solution.
     * @param title human-readable title
     * @param description prompt or description shown to players
     * @param solutionValue integer solution value
     */
    public MathPuzzle(String title, String description, int solutionValue) {
        super(title, description, String.valueOf(solutionValue));
        this.solutionValue = solutionValue;
        
    }

    /**
     * Check an integer answer against the stored solution.
     * Marks the puzzle as solved when the answer is matched with the submission.
     * @param answer integer answer supplied by the player
     * @return {@code true} if the answer matches the solution, otherwise {@code false}
     */
     public boolean checkAnswer(int answer) {
        boolean ok = (answer == solutionValue);
        if (ok) isSolved = true;
        return ok;
    }


    /**
     * Parse a string answer into an integer and check it. Returns false if
     * the string cannot be parsed as an integer.
     * @param answer player's submitted answer as text
     * @return true if the parsed integer matches the solution
     */
    @Override
    public boolean checkAnswer(String answer) {
        try {
            int val = Integer.parseInt(answer.trim());
            return checkAnswer(val);
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    /**
     * Return the integer solution code for this puzzle.
     * @return solution value
     */
     public int getSolutionCode() {
        return solutionValue;
    }
}

