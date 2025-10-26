package com.model;

import java.util.ArrayList;

public abstract class Puzzle {
    protected String title;
    protected String description;
    protected String solution;
    protected ArrayList<String> hints;
    protected boolean isSolved;

    /**
     * Create a puzzle with the provided metadata.
     * @param title       puzzle title
     * @param description puzzle description
     * @param solution     solution string
     */
    public Puzzle(String title, String description, String solution) {
        this.title = title;
        this.description = description;
        this.solution = solution;
        this.hints = new ArrayList<>();
        this.isSolved = false;
    }

    /**
     * Check solution with provided answer with the puzzle's solution.
     * @param answer player's submitted answer
     * @return {@code true} when the answer matches the solution, {@code false}
        *         otherwise        
     */
    public boolean checkAnswer(String answer) {
        if (answer == null || solution == null) return false;
        boolean ok = solution.equalsIgnoreCase(answer.trim());
        if (ok) isSolved = true;
        return ok;
    }

    /**
     * Add a hint to this puzzle.
     * @param hint
     */
    public void addHint(String hint) {
        if (hint != null) hints.add(hint);
    }

    /**
     * Return the list of hints for this puzzle. 
     * @return list of hint strings
     */
    public ArrayList<String> getHints() {
        return hints;
    }

    /**
     * @return title
     */
    public String getTitle() {
        return title;
    }
    /**
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * See if this puzzle has been solved during the session.
     * @return true if solved
     */
    public boolean isSolved() {
        return isSolved;
    }

    /**
     * @return solution
     */
    public String getSolution() {
        return solution;
    }

    /**
     * Manually set the solved flag for this puzzle.
     * @param solved true if the puzzle should be marked solved
     */
    public void setSolved(boolean solved) {
        isSolved = solved;
    }

}