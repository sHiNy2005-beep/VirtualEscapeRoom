package com.model;

import java.util.ArrayList;
import java.util.UUID;

public class User {
    private UUID userId;
    private String username;
    private String email;
    private String password;
    private ArrayList<GameSession> sessions;

    public User(String username, String email, String password) {
        this.userId = UUID.randomUUID();
        this.username = username;
        this.email = email;
        this.password = password;
        this.sessions = new ArrayList<>();
    }

    public UUID getUserId() { 
        return userId; 
    }

    public void setUserId(UUID id) {
         this.userId = id; 
    }

    public String getUserName() {
         return username; 
    }

    public String getEmail() {
         return email; 
    }

    public String getPassword() {
         return password; 
    }

    public ArrayList<GameSession> getSessions() {
         return sessions; 
    }
    
    public void addSession(GameSession session) {
        if (session != null) sessions.add(session);
    }

    @Override
    public String toString() {
        return "User{" + username + ", " + email + "}";
    }
}
