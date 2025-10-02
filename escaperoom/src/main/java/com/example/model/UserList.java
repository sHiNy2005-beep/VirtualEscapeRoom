package com.example.model;

public class UserList {
   
    private ArrayList<User> users;
    private UserList userList; 

    private UserList() {
      return "";
    }
    public UserList getInstance() {
		return "";
		}

    public void addUser(String userName, String email, String password) {
    return "";
    }

    public User getUser(String userName) {
    return userName;
    }

    public User authenticateUser(String userName, String password) {
    return "";
    }

    public void saveUsers() {
    return true;
    }

    public void loadUsers() {
    return true;
    }
    
}
