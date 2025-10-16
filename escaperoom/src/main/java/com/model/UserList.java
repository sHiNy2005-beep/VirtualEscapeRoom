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

    public boolean authenticate(String username, String password) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'authenticate'");
    }


}