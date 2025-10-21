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
    private String DELIM = "\t";

    

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

    
    public String useHint(String puzzleTitle) {
        if (currentRoom == null) return "No room!";
        for (Puzzle p : currentRoom.getPuzzles()) {
            if (p.getTitle().equalsIgnoreCase(puzzleTitle)) {
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
    }

    
   

    public ArrayList<String> displayLeaderboard(Room room) {
        ArrayList<String> leaderboardData = new ArrayList<>();
    
        if (room == null) {
            leaderboardData.add("No room selected");
            return leaderboardData;
        }
    
        HashMap<User, Integer> leaderboard = room.getLeaderboard();
    
        if (leaderboard.isEmpty()) {
            leaderboardData.add("No scores recorded yet");
            return leaderboardData;
        }
    
        ArrayList<Map.Entry<User, Integer>> sortedLeaderboard = new ArrayList<>(leaderboard.entrySet());
        sortedLeaderboard.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));
    
        int rank = 1;
        int previousScore = -1;
        int displayRank = 1;
    
        for (Map.Entry<User, Integer> entry : sortedLeaderboard) {
            User user = entry.getKey();
            Integer score = entry.getValue();
        
            if (score != previousScore) {
                displayRank = rank;
            }
        
            String leaderboardEntry = displayRank + "|" + user.getUserName() + "|" + score;
            leaderboardData.add(leaderboardEntry);
        
            previousScore = score;
            rank++;
        }
    
        return leaderboardData;
    }

    public ArrayList<String> displayLeaderboard() {
        ArrayList<String> leaderboardData = new ArrayList<>();
    
        if (currentRoom == null) {
            leaderboardData.add("No room selected");
            return leaderboardData;
        }
    
        HashMap<User, Integer> leaderboard = currentRoom.getLeaderboard();
    
        if (leaderboard.isEmpty()) {
            leaderboardData.add("No scores recorded yet");
            return leaderboardData;
        }

        ArrayList<Map.Entry<User, Integer>> sortedLeaderboard = new ArrayList<>(leaderboard.entrySet());
        sortedLeaderboard.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));
    
        int rank = 1;
        int previousScore = -1;
        int displayRank = 1;
    
        for (Map.Entry<User, Integer> entry : sortedLeaderboard) {
            User user = entry.getKey();
            Integer score = entry.getValue();
        
            if (score != previousScore) {
                displayRank = rank;
            }
        
            String leaderboardEntry = displayRank + DELIM + user.getUserName() + DELIM + score;
            leaderboardData.add(leaderboardEntry);
        
            previousScore = score;
            rank++;
        }
    
        return leaderboardData;
    }

}
