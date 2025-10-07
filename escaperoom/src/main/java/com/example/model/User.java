package com.example.model;
import java.util.UUID;
import java.util.ArrayList;

public abstract class User {
   
    private UUID userId;
    private String username;
    private String email;
    private String password;
    //private ArrayList<GameSession> sessions; 

    public User(String userName, String email, String password) {
        this.username = userName;
        this.email = email;
        this.password = password;
    }

    public String getUserName() {
    return username;
    }

    public String getEmail() {
    return email;
    }

    public String getPassword() // I added this getter to help with login method in UserList class
    {
    return password;
    }

    /*private void submitAnswer(String answer) 
    {
        
    }

    private void addItem(String item) 
    {
      
    }

    private void useItem(String item) 
    {
    
    }

    private void useHint() 
    {
    
    }

    private void endGame() 
    {
    
    }
    */
}
