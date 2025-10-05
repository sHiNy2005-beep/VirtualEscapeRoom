package com.example.model;
import java.util.UUID;

public abstract class User {
   
    private String username;
    private String email;
    private String password; 

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

    public boolean authenticate(String password) {
    if(password == null)
       return false;
    else
       return true;
    return false;
    }
    
}
