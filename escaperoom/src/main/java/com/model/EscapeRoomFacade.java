package com.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
public class EscapeRoomFacade {
    private UserList userList;
    private RoomList roomList;
    private GameSession currentSession;
    private User currentUser;
    private Room currentRoom;
    private Map<String, Integer> sessionProgress = new HashMap<>();

    

    public EscapeRoomFacade() {
        this.userList = UserList.getInstance();
        this.roomList = RoomList.getInstance();
    }

   public boolean login(String username, String password) {
        for (User u : userList.getUsers()) {
            if (u.getUserName().equals(username) && u.getPassword().equals(password)) {
                currentUser = u;
                return true;
            }
        }
        return false;
    }


    public GameSession startGame(Room room) {
        if (currentUser == null) return null;
        this.currentRoom = room;
        currentSession = new GameSession(currentUser, room);
        currentSession.startSession();
        currentUser.addSession(currentSession);
        return currentSession;
    }

    public ArrayList<Room> getAllRooms() { 
        return roomList.getRooms(); 
    }

    public Room getCurrentRoom() {
         return currentRoom; 
    }

    
    public ArrayList<Puzzle> getCurrentRoomPuzzles() {
        if (currentRoom == null) return new ArrayList<>();
        return currentRoom.getPuzzles();
    }

    public Puzzle getPuzzleByTitle(String title) {
        if (currentRoom == null) return null;
        for (Puzzle p : currentRoom.getPuzzles()) {
            if (p.getTitle().equalsIgnoreCase(title)) {
                return p;
            }
        }
        return null;
    }   

    
    public boolean submitAnswer(String puzzleTitle, String answer) {
        if (currentRoom == null) return false;
        for (Puzzle p : currentRoom.getPuzzles()) {
            if (p.getTitle().equalsIgnoreCase(puzzleTitle)) {
                boolean correct = p.checkAnswer(answer);
                if (correct) {
                    System.out.println("Correct answer for " + puzzleTitle + "!");
                } else {
                    System.out.println("Incorrect answer for " + puzzleTitle);
                }
                return correct;
            }
        }
        return false;
    }

    
    public String useHint(GameSession session) {
        if (currentRoom == null) return "No room!";
        for (Puzzle p : currentRoom.getPuzzles()) {
            if (p.getTitle().equalsIgnoreCase(session)) {
                currentSession.useHint();
                if (!p.getHints().isEmpty()) {
                    return p.getHints().get(Math.min(currentSession.getHintsUsed()-1, p.getHints().size()-1));
                }
                return "No more hints available!";
            }
        }
        return "Puzzle not found.";
    }

    public User getCurrentUser() {
        return currentUser;
    }


    public void endGame() {
        if (currentSession != null) currentSession.endSession();
        DataWriter.saveUsers();
        DataWriter.saveRooms();
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
        userList.addUser(newUser);
        DataWriter.saveUsers();
        return true;
    }

}
