package com.model;

import java.util.ArrayList;
import java.util.UUID;;

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

    
    public User(UUID userId, String username, String email, String password) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.sessions = new ArrayList<>();
    }


    
    public UUID getUserId() { 
        return userId; 
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

    public void setSessions(ArrayList<GameSession> sessions) {
        this.sessions = sessions;
    }

    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(username)
          .append(" (ID: ").append(userId).append(")")
          .append(" | Email: ").append(email)
          .append(" | Sessions: ").append(sessions.size())
          .append("\n");

        for (GameSession s : sessions) {
            sb.append("   → ").append(s.toString()).append("\n");
        }
        return sb.toString();
    }
}

