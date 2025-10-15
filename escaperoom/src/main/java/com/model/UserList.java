package com.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserList {
    private static UserList userList;
    
    @JsonProperty("users")
    private Map<String, User> usersByUsername;
    
    private UserList() {
        usersByUsername = new HashMap<>();
    }
    
    public static UserList getInstance() {
        if (userList == null) {
            userList = new UserList();
        }
        return userList;
    }
    
    public void addUser(String userName, String email, String password) {
        User user = new User(userName, email, password);
        usersByUsername.put(userName.toLowerCase(), user);
    }
    
    public User getUser(String userName) {
        if (userName == null) return null;
        return usersByUsername.get(userName.toLowerCase());
    }
    
    public boolean login(String username, String password) {
        if (username == null || password == null) return false;
        
        User user = usersByUsername.get(username.toLowerCase());
        return user != null && user.getPassword().equals(password);
    }
    
    public ArrayList<User> getAllUsers() {
        return new ArrayList<>(usersByUsername.values());
    }
    
    public void setUsers(ArrayList<User> users) {
        usersByUsername.clear();
        for (User user : users) {
            if (user.getUserName() != null) {
                usersByUsername.put(user.getUserName().toLowerCase(), user);
            }
        }
    }
    
    public Map<String, User> getUsersByUsername() {
        return usersByUsername;
    }
    
    public void setUsersByUsername(Map<String, User> usersByUsername) {
        this.usersByUsername = usersByUsername;
    }
    
    public boolean userExists(String username) {
        return username != null && usersByUsername.containsKey(username.toLowerCase());
    }
    
    public boolean removeUser(String username) {
        if (username == null) return false;
        return usersByUsername.remove(username.toLowerCase()) != null;
    }
    
    public int getUserCount() {
        return usersByUsername.size();
    }
    
    public void saveUsers() {
        List<User> userList = new ArrayList<>(usersByUsername.values());
        DataWriter.saveUsers(userList);
    }
    
    public void loadUsers() {
        List<User> loadedUsers = DataLoader.getUsers();
        usersByUsername.clear();
        for (User user : loadedUsers) {
            if (user.getUserName() != null) {
                usersByUsername.put(user.getUserName().toLowerCase(), user);
            }
        }
    }
    
    public void clear() {
        usersByUsername.clear();
    }
    
    public static void resetInstance() {
        userList = null;
    }
}