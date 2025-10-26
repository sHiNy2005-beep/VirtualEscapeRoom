package com.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class UserList {
  private ArrayList<User> users;
  private static UserList userList;

    private UserList() {
      this.users = DataLoader.getUsers();
      if (this.users == null) this.users = new ArrayList<>();
    }

    /**
     * Return the singleton instance.
     * @return UserList instance
     */
    public static synchronized UserList getInstance() {
      if (userList == null) {
        userList = new UserList();
      }
      return userList;
    }

    /**
     * Add a user if the username or email are not already taken.
     * @return true if user was added, false if duplicate found
     */
    public boolean addUser(String username, String email, String password) {
      for (User u : users) {
          if ((u.getUserName() != null && u.getUserName().equalsIgnoreCase(username)) ||
              (u.getEmail() != null && u.getEmail().equalsIgnoreCase(email))) {
              return false;
          }
      }
      users.add(new User(username, email, password));
      DataWriter.saveUsers();
      return true;
    }

   /**
    * Return an unmodifiable view of the users.
    */
   public List<User> getUsers() {
    return Collections.unmodifiableList(users);
   }

    public void saveUsers() {
      DataWriter.saveUsers();
    }

    /**
     * Sign-up that only requires username and password.
     * Email is nothing. Returns false when username is taken or inputs are ialid.
     */
    public boolean signUp(String username, String password) {
      if (username == null || password == null ) return false;
      username = username.trim();
      if (username.isEmpty() || password.isEmpty()) return false;

      for (User u : users) {
          if (u.getUserName() != null && u.getUserName().equalsIgnoreCase(username)) {
            return false; // username already taken
          }
      }
      User newUser = new User(username, null, password);
      users.add(newUser);
      DataWriter.saveUsers();
      return true;
    }


}