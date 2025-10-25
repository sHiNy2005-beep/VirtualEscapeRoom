package com.model;

import java.util.ArrayList;
import java.util.HashMap;

public class FinalPuzzle extends Puzzle{

   private HashMap<String, String> correctPairs;

    public FinalPuzzle(String title, String description, ArrayList<String> left, ArrayList<String> right) {
        super(title, description, "");
        this.correctPairs = new HashMap<>();

       
        for (int i = 0; i < left.size() && i < right.size(); i++) {
            this.correctPairs.put(left.get(i).trim().toLowerCase(), right.get(i).trim().toLowerCase());
        }
    }

    @Override
    public boolean checkAnswer(String answer) {
        if (answer == null || answer.isEmpty()) return false;

        String[] pairs = answer.split(",");
        int correctCount = 0;

        for (String p : pairs) {
            String[] parts = p.split("=");
            if (parts.length != 2) continue;

            String left = parts[0].trim().toLowerCase();
            String right = parts[1].trim().toLowerCase();

            if (correctPairs.containsKey(left) && correctPairs.get(left).equals(right)) {
                correctCount++;
            }
        }

        
        boolean ok = (correctCount == correctPairs.size());
        if (ok) isSolved = true;
        return ok;
    }

    public HashMap<String, String> getCorrectPairs() {
        return correctPairs;
    }
}

     
    

