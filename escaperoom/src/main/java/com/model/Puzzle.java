package com.model;

import java.util.ArrayList;
import java.util.UUID;

public abstract class Puzzle {
    protected String puzzleId;
    protected String title;
    protected String description;
    protected String solution;
    protected boolean isCompleted;
    protected ArrayList<String> hints;

    public Puzzle(String title, String description, String solution) {
        this.puzzleId = UUID.randomUUID().toString();
        this.title = title;
        this.description = description;
        this.solution = solution;
        this.isCompleted = false;
        this.hints = new ArrayList<>();
    }

    public abstract boolean solve(String answer);

    public String getHint() {
        if (hints.isEmpty()) return "No hints available.";
        return hints.get(0); // placeholder
    }

    public boolean isCompleted() { return isCompleted; }
}
