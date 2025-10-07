package com.example.model;
import java.util.ArrayList;

public class GameSession {


  private String sessionId;
  private Room room;
  private long startTime;
  private long endTime;
  private int score;
  private int hintsUsed;
  private boolean isCompleted;
  private ArrayList<String> inventory;


public GameSession(Room room) 
{
  this.room = room;
}

public void startSession() {

}

public void endSession() {

}

public int calculateScore() {
return score;
}

public void useHint() 
{
  hintsUsed += 1;
  score -= 100; // Deduct points for using a hint
}

public void collectItem(String item) 
{
inventory.add(item);
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
}
