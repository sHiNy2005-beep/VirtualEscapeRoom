package com.model;
import java.util.*;


public class GlobalLeaderboard {
    private final List<LeaderboardEntry> entries;
    private final Map<String, List<LeaderboardEntry>> roomLeaderboards;
    private final Map<String, LeaderboardEntry> playerBestScores;

    private static GlobalLeaderboard instance;

    private GlobalLeaderboard() {
        this.entries = new ArrayList<>();
        this.roomLeaderboards = new HashMap<>();
        this.playerBestScores = new HashMap<>();
    }

    public static synchronized GlobalLeaderboard getInstance() {
        if (instance == null) {
            instance = new GlobalLeaderboard();
        }
        return instance;
    }

    public synchronized void addEntry(LeaderboardEntry entry) {
        if (entry == null) return;
        entries.add(entry);

        String roomId = entry.getRoom() != null ? entry.getRoom().getRoomId() : "unknown";
        roomLeaderboards.computeIfAbsent(roomId, k -> new ArrayList<>()).add(entry);

        String playerName = entry.getPlayer() != null ? entry.getPlayer().getUserName() : null;
        if (playerName != null) {
            updatePlayerBestScore(entry);
        }
    }

    public synchronized List<LeaderboardEntry> getTopEntries(int limit) {
        List<LeaderboardEntry> copy = new ArrayList<>(entries);
        copy.sort(Comparator.comparingInt(LeaderboardEntry::getScore).reversed());
        if (limit <= 0 || limit >= copy.size()) {
            return copy;
        }
        return copy.subList(0, limit);
    }


    public synchronized List<LeaderboardEntry> getEntriesForRoom(String roomId) {
        List<LeaderboardEntry> list = roomLeaderboards.get(roomId);
        return list == null ? Collections.emptyList() : new ArrayList<>(list);
    }


    public synchronized LeaderboardEntry getPlayerBestScore(String playerName) {
        return playerBestScores.get(playerName);
    }

 
    public synchronized void updatePlayerBestScore(LeaderboardEntry entry) {
        if (entry == null || entry.getPlayer() == null) return;
        String name = entry.getPlayer().getUserName();
        LeaderboardEntry existing = playerBestScores.get(name);
        if (existing == null || entry.getScore() > existing.getScore()) {
            playerBestScores.put(name, entry);
        }
    }

    public synchronized List<LeaderboardEntry> getRankings() {
        return getTopEntries(entries.size());
    }

    public synchronized void saveLeaderboard() {
   
        System.out.println("saveLeaderboard() called — implement persistence here.");
    }

    public synchronized void loadLeaderboard() {
        System.out.println("loadLeaderboard() called — implement loading here.");
    }



    public synchronized void clearAll() {
        entries.clear();
        roomLeaderboards.clear();
        playerBestScores.clear();
    }
}
