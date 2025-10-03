package com.example.model;
import java.util.ArrayList;

public class UserList {
   
    private ArrayList<User> users;
    private UserList userList; 

    private UserList() {
    userList = DataLoader.getUsers();
    }
    public UserList getInstance() {
		if(userList == null) {
			userList = new UserList();
		}
		return userList;
	}

    public void addUser(String userName, String email, String password) {
    users.add(userName, email, password);
    }

    public User getUser(String userName) {
    return userName;
    }

    public boolean authenticateUser(String userName, String password) {
    if(users.contains(userName, password))
		return true;
		
    }

    public void saveUsers() {
    
    }

    public void loadUsers() {
    
    }
    
}
