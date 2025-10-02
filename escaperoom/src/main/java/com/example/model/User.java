package com.example.model;

public class User {
   
    private UUID userId;
    private String username;
    private String email;
    private String password; 

    public User(String userName, String email, String password) {
        this.username = userName;
        this.email = email;
        this.password = password;
    }

    public String getUserName() {
    return "";
    }

    public String getEmail() {
    return "";
    }

    public boolean authenticate(String password) {
    return true;
    }
    
}
