package com.model;

import java.util.UUID;

public class User {
    private String userId;
    private String username;
    private int totalScore;

    public User(String username) {
        this.userId = UUID.randomUUID().toString();
        this.username = username == null ? "Unknown" : username;
        this.totalScore = 0;
    }

    public User(String userId, String username, int totalScore) {
        this.userId = userId;
        this.username = username;
        this.totalScore = totalScore;
    }

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void addScore(int delta) {
        this.totalScore += delta;
    }

    public String toLine() {
        return String.join("|", userId, username == null ? "" : username, String.valueOf(totalScore));
    }

    public static User fromString(String line) { // added new s
        if (line == null || line.trim().isEmpty()) return null;
        String[] parts = line.split("\\|", -1);
        String id = parts.length > 0 && !parts[0].isEmpty() ? parts[0] : UUID.randomUUID().toString();
        String name = parts.length > 1 ? parts[1] : "Unknown";
        int score = 0;
        try {
            if (parts.length > 2 && parts[2] != null && !parts[2].isEmpty())
                score = Integer.parseInt(parts[2]);
        } catch (NumberFormatException e) {
            score = 0;
        }
        return new User(id, name, score);
    }
}
