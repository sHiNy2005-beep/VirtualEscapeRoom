package com.model;

import com.model.GameSession;
import com.model.DataConstants;

public class Score implements IScoringStrategy {

    @Override
    public int calculateScore(GameSession session) {
        int base = DataConstants.BASE_SCORE;
        int timeBonus = calculateTimeBonus(session.getElapsedTime());
        return base + timeBonus;
    }

    public int calculateTimeBonus(long completionTime) {
        if (completionTime == 0) return 0;
        return (int) (10000 / completionTime);
    }

    public int calculateHintPenalty(int hintsUsed) {
        return hintsUsed * DataConstants.HINT_PENALTY;
    }
}
