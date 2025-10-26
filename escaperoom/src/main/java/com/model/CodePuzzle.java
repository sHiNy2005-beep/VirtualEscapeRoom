package com.model;

/**
 * A CodePuzzle represents a puzzle whose answer is a code or text-based solution.
 * It currently uses the base implementation for answer checking.
 */
public class CodePuzzle extends Puzzle {

    /**
     * Create a new CodePuzzle.
     *
     * @param title       title of the puzzle
     * @param description description of the puzzle
     * @param solution    solution string
     */
    public CodePuzzle(String title, String description, String solution) {
        super(title, description, solution);
    }

    /**
     * Check to see if the provided answer is correct for this puzzle.This method performs a case-insensitive comparison against the stored solution.
     * @param answer player's submitted answer
     * @return {@code true} if the answer matches the puzzle's solution,
     *    {@code false} otherwise
     */
    @Override
    public boolean checkAnswer(String answer) {
        return super.checkAnswer(answer);
    }
}