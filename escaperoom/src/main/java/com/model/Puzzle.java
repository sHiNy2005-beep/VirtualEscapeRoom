package com.model;

import java.util.ArrayList;
import java.util.UUID;

public abstract class Puzzle {
    protected String title;
    protected String description;
    protected String solution;
    protected ArrayList<String> hints;

    public Puzzle(String title, String description, String solution) {
        this.title = title;
        this.description = description;
        this.solution = solution;
        this.hints = new ArrayList<>();
    }

    public abstract boolean solve(String answer);

    public String getHint() {
        if (hints.isEmpty()) return "No hints available.";
        return hints.get(0); 
    }

     public boolean addHint(String hint) {
        return hints.add(hint);
    }

    public boolean checkAnswer(String answer) {
        return answer.equalsIgnoreCase(solution);
    }
}
