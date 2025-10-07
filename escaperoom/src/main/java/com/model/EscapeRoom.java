package com.model;

import java.util.ArrayList;

public class EscapeRoom {

    private Player currentPlayer;
    private GameSession currentSession;
    private Room currentRoom;
    private RoomList roomList;
    private UserList userList;
    private Scoring scoring;
   // private DataLoader dataLoader;
    //private DataWriter dataWriter;

    public EscapeRoom() {
      //  this.currentPlayer = new Player(null, null, null);
        this.currentRoom = new Room("", "", "");
        this.roomList = RoomList.getInstance();
        this.userList = UserList.getInstance();
        this.scoring = new Scoring();
       // this.dataLoader = new DataLoader();
       // this.dataWriter = new DataWriter();

        // keep like this for now
        this.currentSession = new GameSession(this.currentPlayer, this.currentRoom);
    }

    public Player login(String userName, String password) {
        this.currentPlayer = new Player(userName, userName + "@example.com", password);
        return currentPlayer;
    }

    public Player createPlayer(String userName, String email, String password) {
        this.currentPlayer = new Player(userName, email, password);
        return currentPlayer;
    }

    public ArrayList<Room> getAllRooms() {
        return new ArrayList<>();
    }

    public GameSession startGame(Player player, Room room) {
        this.currentPlayer = player;
        this.currentRoom = room;
        this.currentSession = new GameSession(player, room);
        return currentSession;
    }

    public void endGame() {
       
    }

    public boolean submitAnswer(String answer) {
       
        return true;
    }

    public String useHint() {
        
        return "Hint used.";
    }

    public boolean collectItem(String item) {
       
        return true;
    }

    public ArrayList<String> getPlayerInventory() {
        return new ArrayList<>();
    }

    public ArrayList<LeaderboardEntry> getLeaderboard() {
        return new ArrayList<>();
    }

    public void saveAllData() {
      
    }

    public void loadAllData() {
        
    }

    public void setScoring(Scoring scoring) {
        this.scoring = scoring;
    }
}