package com.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class UserList {
  private ArrayList<User> users;
  private static UserList userList;
  private boolean usersLoaded = false;

    private UserList() {
      this.users = new ArrayList<>();
      // Don't load users yet - wait for explicit initialization
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
     * Lazily load users from data file.
     * This is called after RoomList is initialized to avoid circular dependency.
     */
    private void ensureUsersLoaded() {
        if (!usersLoaded) {
            // Make sure RoomList is initialized first
            RoomList.getInstance();
            
            ArrayList<User> loadedUsers = DataLoader.getUsers();
            if (loadedUsers != null && !loadedUsers.isEmpty()) {
                this.users.addAll(loadedUsers);
            }
            usersLoaded = true;
        }
    }

    /**
     * Add a user if the username or email are not already taken.
     * @return true if user was added, false if duplicate found
     */
    public boolean addUser(String username, String email, String password) {
      ensureUsersLoaded();
      
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
    ensureUsersLoaded();
    return Collections.unmodifiableList(users);
   }

    public void saveUsers() {
      DataWriter.saveUsers();
    }

    /**
     * Sign-up that only requires username and password.
     * Email is nothing. Returns false when username is taken or inputs are invalid.
     */
    public boolean signUp(String username, String password) {
      ensureUsersLoaded();
      
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
