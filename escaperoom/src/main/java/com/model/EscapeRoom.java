package com.model;

import java.util.ArrayList;


public class EscapeRoom {

    private GameSession currentSession;
    private Room currentRoom;
    private RoomList roomList;
    private UserList userList;
    private DataLoader dataLoader;
    private DataWriter dataWriter;

    public EscapeRoom() {
        this.roomList = RoomList.getInstance();
        this.userList = UserList.getInstance();
        this.dataLoader = new DataLoader();
        this.dataWriter = new DataWriter();
    }

    public User login(String username, String password) {
       ArrayList<User> users = userList.getUsers(); 

    for (User u : users) {
        if (u.getUserName().equals(username) && u.getPassword().equals(password)) {
            return u;
        }
    }
        return null;
    }

    public ArrayList<Room> getAllRooms() {
        return roomList.getRooms();
    }

    public GameSession startGame(User user, Room room) {
        currentRoom = room;
        currentSession = new GameSession(user, room);
        currentSession.startSession();
        return currentSession;
    }

    public boolean submitAnswer(String answer) {
        return false;
    }

    public String useHint() {
        currentSession.useHint();
        return "Hint used!";
    }

    public boolean collectItem(String item) {
        currentSession.collectItem(item);
        return true;
    }

    public void endGame() {
        currentSession.endSession();
        
       
    }

    public void saveAllData() {
      //  DataWriter.saveRooms(roomList.getRooms());
       // DataWriter.saveUsers(userList.getUsers());
    }

    public void loadAllData() {
        roomList = RoomList.getInstance();
        userList = UserList.getInstance();
    }

}