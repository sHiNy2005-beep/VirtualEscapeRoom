package com.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class UserList {
    private ArrayList<User> users;
    private static UserList instance;

    private UserList() {
        users = new ArrayList<>();
    }

    public static UserList getInstance() {
        if (instance == null) {
            synchronized (UserList.class) {
                if (instance == null) instance = new UserList();
            }
        }
        return instance;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void addUser(User u) {
        if (u != null) users.add(u);
    }

    public User findById(String id) {
        for (User u : users) {
            if (u.getUserId().equals(id)) return u;
        }
        return null;
    }

    
    public static void saveUsers() {
        UserList userList = UserList.getInstance();
        ArrayList<User> users = userList.getUsers();

        if (users == null || users.isEmpty()) {
            System.out.println("No users to save."); //empty when there is nothing to save.
            return;
        }

        try (FileWriter writer = new FileWriter(USERS_FILE)) {
            for (User user : users) {
                writer.write(user.toLine());
                writer.write(System.lineSeparator());
            }
            writer.flush();
            System.out.println("Users successfully saved to: " + USERS_FILE);
        } catch (IOException e) {
            System.err.println("Error while saving users: " + e.getMessage()); //default message 
            e.printStackTrace();
        }
    }

    public static ArrayList<User> loadUsers() {
    ArrayList<User> users = new ArrayList<>();
    try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE))) {
        String line;
        while ((line = reader.readLine()) != null) {
            User user = User.fromString(line);
            if (user != null) users.add(user);
        }
        System.out.println("Loaded " + users.size() + " users from " + USERS_FILE);
    } catch (IOException e) {
        System.err.println("Error while loading users: " + e.getMessage());
    }
    return users;
  }
}
