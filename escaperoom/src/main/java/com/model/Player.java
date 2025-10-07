package com.model;

public class Player extends User{
   
    private String playerId;
    private String username;
    private String email;
    private String password;

    public Player(String username, String email, String password) {
        super(username, email, password);
    }
  
    boolean login(String username, String password) {
        return true; 
    }
  
    private void submitAnswer(String answer) {
        
    }

    private void addItem(String item) {
      
    }

    private void useItem(String item) {
    
    }

    private void useHint() {
    
    }

    private void endGame() {
    
    }
}
