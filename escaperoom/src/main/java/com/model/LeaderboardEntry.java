package com.model;

import com.model.Room;
import com.model.User;

public class LeaderboardEntry {
    private User user;
    private Room room;
    private int score;
    private long completionTime;
    private int hintsUsed;

    public LeaderboardEntry(User user, Room room, int score, long time, int hints) {
        this.user = user;
        this.room = room;
        this.score = score;
        this.completionTime = time;
        this.hintsUsed = hints;
    }

    public User getUser() { 
        return user; 
    }

    public int getScore() {
         return score;
    }

    public long getCompletionTime() {
         return completionTime; 
    }
}
