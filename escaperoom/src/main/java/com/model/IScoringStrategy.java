package com.model;

import com.model.GameSession;

public interface IScoringStrategy {

    int calculateScore(GameSession session);
}