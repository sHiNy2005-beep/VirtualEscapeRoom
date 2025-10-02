package com.example.model;

import java.util.ArrayList;
public abstract class Puzzle {

    private String puzzleid;
    private String title;
    private String description;
    private String solution;
    private boolean isCompleted;
    protected ArrayList<String> hints;

     public Puzzle(String title, String description, String solution) {
        this.title = title;
        this.description = description;
        this.solution = solution;
        this.isCompleted = true;
        this.hints = new ArrayList<>();
    }

    public abstract boolean solve(String answer);

    public String getHint() {
        return "";
    } 

    public abstract boolean checkAnswer(String answer);

}
