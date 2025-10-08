package com.model;
import java.util.ArrayList;

import javafx.scene.chart.PieChart.Data;

public class UserList 
{
  private ArrayList<User> users;
    private static UserList userList; 

    private UserList() 
    {
      users = new ArrayList<>();
    }

   public static UserList getInstance() 
  {
		if(userList == null) 
    {
			userList = new UserList();
		}
		return userList;
	}

    public void addUser(User user, String userName, String email, String password) 
    {//I added these parameters to match the constructor in User class
      users.add(user);
    }

    public String getUser(String userName) 
    {
      return userName;
    }
    
    private boolean login(String username, String password) 
    {
        for(User user : users)
        {
          if(username == null || password == null)
           return false;
        else if(!user.getUserName().equals(username) || user.getPassword().equals(password))
           return false;
        else
        return true; 
        }
        return true;
    }

    /**
     * Saves the current list of users to a file.
     */
    public void saveUsers() 
    {
      DataWriter.saveUsers(users);
    }

    /**
     * Loads the list of users from a file.
     */
    public void loadUsers() 
    {
      //DataLoader.getUsers();
    }

}
