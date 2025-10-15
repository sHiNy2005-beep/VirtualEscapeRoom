package com.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class User {
   
    @JsonProperty("userId")
    private UUID userId;
    
    @JsonProperty("username")
    private String username;
    
    @JsonProperty("email")
    private String email;
    
    @JsonProperty("password")
    private String password;
    
    @JsonProperty("gameSessions")
    private Map<UUID, GameSession> gameSessions;
    
    public User() {
        this.userId = UUID.randomUUID();
        this.gameSessions = new HashMap<>();
    }
    
    public User(String userName, String email, String password) {
        this.userId = UUID.randomUUID();
        this.username = userName;
        this.email = email;
        this.password = password;
        this.gameSessions = new HashMap<>();
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
    
    public Map<UUID, GameSession> getGameSessions() {
        return gameSessions;
    }
    
    public void setUserId(UUID userId) {
        this.userId = userId;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public void setGameSessions(Map<UUID, GameSession> gameSessions) {
        this.gameSessions = gameSessions;
    }
    
    public void addGameSession(GameSession session) {
        gameSessions.put(session.getSessionId(), session);
    }
    
    public GameSession getGameSession(UUID sessionId) {
        return gameSessions.get(sessionId);
    }
    
    public void removeGameSession(UUID sessionId) {
        gameSessions.remove(sessionId);
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userId != null && userId.equals(user.userId);
    }
    
    @Override
    public int hashCode() {
        return userId != null ? userId.hashCode() : 0;
    }
}