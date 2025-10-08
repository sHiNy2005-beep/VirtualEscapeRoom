package com.example.model;

import java.util.ArrayList;
public class GameSession {

    private String sessionId;
    private Player player;
    private Room room;
    private long startTime;
    private long endTime;
    private int score;
    private int hintsUsed;
    private boolean isCompleted;
    private ArrayList<String> inventory;

    public GameSession(Player player, Room room) {
        this.player = player;
        this.room = room;
        this.inventory = new ArrayList<>();
    }

    public void startSession() {
    }

    public void endSession() {
    }

    public int calculateScore() {
        return score;
    }

    public void useHint() {
    }

    public void collectItem(String item) {
        if (inventory == null) inventory = new ArrayList<>();
        if (!inventory.contains(item)) inventory.add(item);
    }

    public ArrayList<String> getInventory() {
        if (inventory == null) inventory = new ArrayList<>();
        return inventory;
    }

    public boolean hasItem(String item) {
        if (inventory == null) return false;
        return inventory.contains(item);
    }

    // recent code for 10/8 dataloader and scrum
    @Override
    public String toString() {
        String playerPart = (player != null) ? player.toString() : "";
        String roomPart = (room != null) ? room.toString() : "";
        return (sessionId != null ? sessionId : "") + "|" +
               playerPart + "|" +
               roomPart + "|" +
               startTime + "|" +
               endTime + "|" +
               score + "|" +
               hintsUsed + "|" +
               isCompleted;
    }

    public static GameSession fromString(String line) {
        if (line == null || line.isEmpty()) return null;
        String[] parts = line.split("\\|", -1);
        if (parts.length < 8) return  null;
        try {
            String sid = parts[0];
            String playerPart = parts[1];
            String roomPart = parts[2];
            long start = parts[3].isEmpty() ? 0L : Long.parseLong(parts[3]);
            long end = parts[4].isEmpty() ? 0L : Long.parseLong(parts[4]);
            int sc = parts[5].isEmpty() ? 0 : Integer.parseInt(parts[5]);
            int hints = parts[6].isEmpty() ? 0 : Integer.parseInt(parts[6]);
            boolean completed = Boolean.parseBoolean(parts[7]);

            Player p = (playerPart == null || playerPart.isEmpty()) ? null : Player.fromString(playerPart);
            Room r = (roomPart == null || roomPart.isEmpty()) ? null : Room.fromString(roomPart);

            GameSession gs = new GameSession(p, r);
            gs.sessionId = sid;
            gs.startTime = start;
            gs.endTime = end;
            gs.score = sc;
            gs.hintsUsed = hints;
            gs.isCompleted = completed;
            return gs;
        } catch (Exception e) {
            return null;
        }
    }

    public boolean belongsTo(User user) {
        if (user == null || player == null) return false;
        try {
            String pid = player.getUserId();
            String uid = user.getId();
            if (pid == null || uid == null) return false;
            return pid.equals(uid);
        } catch (Throwable t) {
            return false;
        }
    }
}
