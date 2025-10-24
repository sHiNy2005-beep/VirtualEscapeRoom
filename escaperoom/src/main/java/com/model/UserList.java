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

    public boolean addUser(String username, String email, String password) {
    for (User u : users) {
        if (u.getUserName().equalsIgnoreCase(username) || u.getEmail().equalsIgnoreCase(email)) {
            return false; 
        }
    }
    users.add(new User(username, email, password));
    DataWriter.saveUsers(); 
    return true;
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

    public boolean signUp(String username, String password) {
      if (username == null || password == null ) return false;
      username = username.trim();
      if (username.isEmpty() || password.isEmpty()) return false;

      for (User u : userList.getUsers()) {
          if (u.getUserName().equalsIgnoreCase(username)) {
            return false; // for the username 
          }
      }
      User newUser = new User(username, password, null);
      DataWriter.saveUsers();
      return true;
    }


}