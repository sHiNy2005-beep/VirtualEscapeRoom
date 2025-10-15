package com.model;

import java.util.ArrayList;

public abstract class Puzzle {
    protected String title;
    protected String description;
    protected String solution;
    protected ArrayList<String> hints;
    protected boolean isSolved;

    public Puzzle(String title, String description, String solution) {
        this.title = title;
        this.description = description;
        this.solution = solution;
        this.hints = new ArrayList<>();
        this.isSolved = false;
    }

    public boolean checkAnswer(String answer) {
        if (answer == null || solution == null) return false;
        boolean ok = solution.equalsIgnoreCase(answer.trim());
        if (ok) isSolved = true;
        return ok;
    }

    public void addHint(String hint) { 
        if (hint != null) hints.add(hint); 
    }

    public ArrayList<String> getHints() { 
        return hints; 
    }

    public String getTitle() { 
        return title;
 }

    public String getDescription() { 
        return description; 
    }

    public boolean isSolved() {
         return isSolved; 
    }

    public String getSolution() { 
        return solution; 
    }

    public void setSolved(boolean solved) { 
        isSolved = solved; 
    }
}