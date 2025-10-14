package com.model;

public class CodePuzzle extends Puzzle {

    public CodePuzzle(String title, String description, String solution) {
        super(title, description, solution);
    }

    @Override
    public boolean checkAnswer(String answer) {
        if (answer == null || solution == null) return false;
        boolean ok = solution.equals(answer.trim());
        if (ok) isSolved = true;
        return ok;
    }
}