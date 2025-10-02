package com.example.model;

public class Player extends User{
   
    private String playerId;
    private String username;
    private String email;
    private String password;
    private ArrayList<String> inventory;

    private Player createPlayer(String username, String email, String password) {
        super(username, email, password);
    }
  
    private boolean login(String username, String password) {
        return true; 
    }
  
    private void submitAnswer(String answer) {
        return true; 
    }

    private void addItem(String item) {
      return true;
    }

    private void useItem(String item) {
    return true;
    }

    private void useHint() {
    return null;
    }

    private void endGame() {
    return null;
    }
}
