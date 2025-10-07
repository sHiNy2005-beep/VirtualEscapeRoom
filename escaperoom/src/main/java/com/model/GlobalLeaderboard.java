package com.model;

import java.util.ArrayList;
import java.util.HashMap;

public class GlobalLeaderboard {
    
    private ArrayList<LeaderboardEntry> entries;
    private HashMap<String, ArrayList<LeaderboardEntry>> roomLeaderboards;
    private HashMap<String, LeaderboardEntry> playerBestScores;
    private static GlobalLeaderboard instance;

    private GlobalLeaderboard() {
        entries = new ArrayList<>();
        roomLeaderboards = new HashMap<>();
        playerBestScores = new HashMap<>();
    }

    public static GlobalLeaderboard getInstance() {
        if (instance == null)
            instance = new GlobalLeaderboard();
        return instance;
    }

    public void addEntry(LeaderboardEntry entry) {
        entries.add(entry);
    }

    public ArrayList<LeaderboardEntry> getTopEntries(int limit) {
        return entries;
    }

    public ArrayList<LeaderboardEntry> getEntriesForRoom(String roomId) {
        return roomLeaderboards.get(roomId);
    }

    public LeaderboardEntry getUserBestScore(String username) {
        return playerBestScores.get(username);
    }

    public void saveLeaderboard() {}
    public void loadLeaderboard() {}
}
