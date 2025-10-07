package com.model;

import java.util.Objects;

public class LeaderboardEntry {
    private final Player player;
    private final Room room;
    private final int score;
    private final long completionTime;
private final int hintsUsed;

public LeaderboardEntry(Player player, Room room, int score, long completionTime, int hintsUsed) {
    this.player = player;
    this.room = room;
    this.score = score;
    this.completionTime = completionTime;
    this.hintsUsed = hintsUsed;
}

    public Player getPlayer() {
        return player;
    }

    public Room getRoom() {
        return room;
    }

    public int getScore() {
        return score;
    }

    public long getCompletionTime() {
        return completionTime;
    }

    public int getHintsUsed() {
        return hintsUsed;
    }

    @Override
    public String toString() {
        return "LeaderboardEntry{" +
                "player=" + (player != null ? player.getUserName() : "null") +
                ", room=" + (room != null ? room.getTitle() : "null") +
                ", score=" + score +
                ", completionTime=" + completionTime +
                ", hintsUsed=" + hintsUsed +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LeaderboardEntry that = (LeaderboardEntry) o;
        return score == that.score
                && completionTime == that.completionTime
                && hintsUsed == that.hintsUsed
                && Objects.equals(player, that.player)
                && Objects.equals(room, that.room);
    }

    @Override
    public int hashCode() {
        return Objects.hash(player, room, score, completionTime, hintsUsed);
    }
}
