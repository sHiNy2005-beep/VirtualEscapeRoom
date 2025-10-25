package com.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;
public class EscapeRoomFacade {
    private UserList userList;
    private RoomList roomList;
    private GameSession currentSession;
    private User currentUser;
    private Room currentRoom;
    private Map<String, Integer> sessionProgress = new HashMap<>();
    private String DELIM = "\t";

    

    public EscapeRoomFacade() {
        this.userList = UserList.getInstance();
        this.roomList = RoomList.getInstance();
    }

    public boolean createAccount(String username, String email, String password) {
    return userList.addUser(username, email, password);
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

    public void logout() {
            System.out.println(currentUser.getUserName() + " logging out.");
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
            return p.checkAnswer(answer);
        }
    }

    return false;
}

    
    public String useHint(String puzzleTitle) {
    if (currentRoom == null) return "No room!";

    for (Puzzle p : currentRoom.getPuzzles()) {
        if (p.getTitle().equalsIgnoreCase(puzzleTitle)) {
            currentSession.useHint(puzzleTitle);

            if (p.getHints().isEmpty()) return "No more hints available!";

            int index = Math.min(currentSession.getHintsUsed() - 1, p.getHints().size() - 1);
            return p.getHints().get(index);
        }
    }

    return "Puzzle not found.";
}

    public User getCurrentUser() {
        return currentUser;
    }


    public void endGame() {
        if (currentSession != null) currentSession.endSession();
        currentRoom.getLeaderboard().put(currentUser, currentSession.calculateScore());
    }


    public Map<User, Integer> getSortedLeaderboard(Room room) {
        HashMap<User, Integer> leaderboard = room.getLeaderboard();
        Map<User, Integer> sortedLeaderboard = leaderboard.entrySet().stream().sorted(Map.Entry.<User, Integer>comparingByValue().reversed()).collect(Collectors.toMap(
            Map.Entry::getKey,
            Map.Entry::getValue,
            (e1, e2) -> e1,
            LinkedHashMap::new
        ));
    
    return sortedLeaderboard;
    }

    public HashMap<User, Integer> getLeaderboard(Room room) {
    return room.getLeaderboard();
    }

    public void updateLeaderboard(Room room, int score) {
    room.getLeaderboard().put(currentUser, score);
    DataWriter.saveRooms();
    }

    public boolean submitAnswer(String title, int i) {
        if (currentRoom == null) return false;

        for (Puzzle p : currentRoom.getPuzzles()) {
            if (p.getTitle().equalsIgnoreCase(title) && p instanceof MathPuzzle) {
                return ((MathPuzzle) p).checkAnswer(i);
            }
        }

        return false;
    }

}
