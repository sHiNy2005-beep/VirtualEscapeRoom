package com.example.model;
import java.util.ArrayList;


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

}

public ArrayList<String> getInventory() {
return inventory;
}

public boolean hasItem(String item) {
if(!inventory.contains(item))
  return false;
else
  return true;
}
