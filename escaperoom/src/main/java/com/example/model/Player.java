package com.example.model;

public class Player extends User{
   
    private String playerId;
    private String username;
    private String email;
    private String password;

    private Player(String username, String email, String password) {
        super(username, email, password);
    }
  
    private boolean login(String username, String password) {
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
