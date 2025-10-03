package com.example.model;

public class Scoring<GameSession> {

    public int calculateScore(GameSession session) {
        return 0;
    }

    public int calculateTimeBonus(long completionTimeMs) {
        return 0;
    }

    public int calculateHintPenalty(int hintsUsed) {
        return 0;
    }
}
