package com.model;
import java.util.ArrayList;

public class UserList 
{
  private ArrayList<User> users;
  private static UserList userList; 

    private UserList() 
    {
      users=DataLoader.getUsers();
    }

    public static UserList getInstance() 
   {
		 if(userList == null) 
     {
			userList = new UserList();
		 }
		  return userList;
  	}

    public void addUser(User user) 
    {
      if (user != null) users.add(user);
    }

   public  ArrayList<User> getUsers() {
    return users;
   }

    public void saveUsers() 
    {
      DataWriter.saveUsers();
    }

s    public User authenticate(String username, String password) {
        User user = getUser(username);
        if(user != null){
            if(user.getPassword() == password){
                return user;
            }
        }
        return null;
    }

    public User getUser(String username) {
      for (User user : users){
        if (user.getUsername().equals(username)) {
          return user;
        }
      }
      return null;
    }
}