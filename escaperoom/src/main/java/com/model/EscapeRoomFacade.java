package com.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Facade class that provides a simplified interface for the escape room game.
 * Now manages game sessions that can track progress across multiple rooms.
 */
public class EscapeRoomFacade {
    private UserList userList;
    private RoomList roomList;
    private GameSession currentSession;
    private User currentUser;
    private Room currentRoom;

    public EscapeRoomFacade() {
        this.userList = UserList.getInstance();
        this.roomList = RoomList.getInstance();
    }

    /**
     * Create a new user account.
     * 
     * @param username the username
     * @param email the email address
     * @param password the password
     * @return true if account was created successfully
     */
    public boolean createAccount(String username, String email, String password) {
        return userList.addUser(username, email, password);
    }

    /**
     * Log in a user.
     * 
     * @param username the username
     * @param password the password
     * @return true if login was successful
     */
    public boolean login(String username, String password) {
        for (User u : userList.getUsers()) {
            if (u.getUserName().equals(username) && u.getPassword().equals(password)) {
                currentUser = u;
                currentSession = null;
                if (!currentUser.getSessions().isEmpty()) {
                    for (int i = currentUser.getSessions().size() - 1; i >= 0; i--) {
                        GameSession gs = (GameSession) currentUser.getSessions().get(i);
                        if (!gs.isSessionCompleted()) {
                            currentSession = gs;
                            System.out.println("Resuming existing session: " + gs.getSessionId());
                            break;
                        }
                    }
                }
                if (currentSession == null) {
                    currentSession = new GameSession(currentUser);
                    currentUser.addSession(currentSession);
                    System.out.println("Created new session: " + currentSession.getSessionId());
                }
                
                return true;
            }
        }
        return false;
    }

    /**
     * Log out the current user.
     */
    public void logout() {
        if (currentUser != null) {
            System.out.println(currentUser.getUserName() + " logging out.");
            DataWriter.saveUsers();
        }
        currentUser = null;
        currentSession = null;
        currentRoom = null;
    }

    /**
     * Start or continue playing in a specific room.
     * This will create or resume room session within the current game session.
     * 
     * @param room the room to enter
     * @return the RoomSession for this room
     */
    public RoomSession startGame(Room room) {
        if (currentUser == null || room == null) return null;
        
        if (currentSession == null) {
            currentSession = new GameSession(currentUser);
            currentUser.addSession(currentSession);
        }
        
        currentRoom = room;
        RoomSession roomSession = currentSession.enterRoom(room);
        return roomSession;
    }

    /**
     * Get existing room session for a specific room.
     * 
     * @param room the room to check
     * @return RoomSession or null if no session exists
     */
    public RoomSession getExistingRoomSession(Room room) {
        if (currentSession == null || room == null) return null;
        return currentSession.getRoomSession(room);
    }

    /**
     * Continue playing in a specific room.
     * 
     * @param room the room to continue in
     * @return the RoomSession for this room
     */
    public RoomSession continueRoom(Room room) {
        if (currentUser == null || room == null) return null;
        
        if (currentSession == null) {
            currentSession = new GameSession(currentUser);
            currentUser.addSession(currentSession);
        }
        
        currentRoom = room;
        RoomSession roomSession = currentSession.enterRoom(room);
        System.out.println("Resuming session in " + room.getTitle());
        return roomSession;
    }

    /**
     * Get all available rooms.
     * 
     * @return list of all rooms
     */
    public ArrayList<Room> getAllRooms() { 
        return new ArrayList<>(roomList.getRooms());
    }

    /**
     * Get the current room being played.
     * 
     * @return the current Room
     */
    public Room getCurrentRoom() {
        return currentRoom; 
    }
    
    /**
     * Get all puzzles in the current room.
     * 
     * @return list of puzzles
     */
    public ArrayList<Puzzle> getCurrentRoomPuzzles() {
        if (currentRoom == null) return new ArrayList<>();
        return currentRoom.getPuzzles();
    }

    /**
     * Get a specific puzzle by title from the current room.
     * 
     * @param title the puzzle title
     * @return the Puzzle or null if not found
     */
    public Puzzle getPuzzleByTitle(String title) {
        if (currentRoom == null) return null;
        for (Puzzle p : currentRoom.getPuzzles()) {
            if (p.getTitle().equalsIgnoreCase(title)) {
                return p;
            }
        }
        return null;
    }

    /**
     * Get the total score across all rooms in the current session.
     * 
     * @return the total score
     */
    public int getScore() {
        if (currentSession == null) return 0;
        return currentSession.calculateTotalScore();
    }

    /**
     * Get the score for the current room.
     * 
     * @return the room score
     */
    public int getCurrentRoomScore() {
        if (currentSession == null || currentRoom == null) return 0;
        RoomSession roomSession = currentSession.getRoomSession(currentRoom);
        if (roomSession == null) return 0;
        return roomSession.calculateScore(currentRoom.getDifficulty());
    }

    /**
     * Submit an answer for a puzzle in the current room.
     * 
     * @param puzzleTitle the puzzle title
     * @param answer the answer
     * @return true if the answer is correct
     */
    public boolean submitAnswer(String puzzleTitle, String answer) {
        if (currentSession == null || currentRoom == null) return false;
        return currentSession.submitAnswer(puzzleTitle, answer, currentRoom);
    }

    /**
     * Submit a numeric answer for a puzzle.
     * 
     * @param title the puzzle title
     * @param answer the numeric answer
     * @return true if the answer is correct
     */
    public boolean submitAnswer(String title, int answer) {
        return submitAnswer(title, String.valueOf(answer));
    }

    /**
     * Use a hint for a puzzle in the current room.
     * 
     * @param puzzleTitle the puzzle title
     * @return the hint text
     */
    public String useHint(String puzzleTitle) {
        if (currentRoom == null || currentSession == null) return "No room!";

        for (Puzzle p : currentRoom.getPuzzles()) {
            if (p.getTitle().equalsIgnoreCase(puzzleTitle)) {
                currentSession.useHint(puzzleTitle);
                
                RoomSession roomSession = currentSession.getCurrentRoomSession();
                if (roomSession == null) return "No session found!";

                if (p.getHints().isEmpty()) return "No more hints available!";

                int index = Math.min(roomSession.getHintsUsed() - 1, p.getHints().size() - 1);
                return p.getHints().get(index);
            }
        }

        return "Puzzle not found.";
    }

    /**
     * Get the current user.
     * 
     * @return the current User
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * Get the current game session.
     * 
     * @return the current GameSession
     */
    public GameSession getCurrentSession() {
        return currentSession;
    }

    /**
     * End the current game session and update leaderboard.
     */
    public void endGame() {
        if (currentSession != null) {
            currentSession.endSession();
            
            for (Map.Entry<String, RoomSession> entry : currentSession.getAllRoomSessions().entrySet()) {
                RoomSession roomSession = entry.getValue();
                Room room = findRoomById(entry.getKey());
                if (room != null) {
                    int score = roomSession.calculateScore(room.getDifficulty());
                    room.getLeaderboard().put(currentUser, score);
                }
            }
            
            DataWriter.saveUsers();
            DataWriter.saveRooms();
        }
    }

    /**
     * Helper method to find a room by ID.
     * 
     * @param roomId the room ID
     * @return the Room or null if not found
     */
    private Room findRoomById(String roomId) {
        for (Room r : roomList.getRooms()) {
            if (r.getRoomId().equals(roomId)) {
                return r;
            }
        }
        return null;
    }

    /**
     * Get a sorted leaderboard for a specific room.
     * 
     * @param room the room
     * @return sorted map of users to scores
     */
    public Map<User, Integer> getSortedLeaderboard(Room room) {
        HashMap<User, Integer> leaderboard = room.getLeaderboard();
        return leaderboard.entrySet().stream()
            .sorted(Map.Entry.<User, Integer>comparingByValue().reversed())
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue,
                (e1, e2) -> e1,
                LinkedHashMap::new
            ));
    }

    /**
     * Get the leaderboard for a specific room.
     * 
     * @param room the room
     * @return map of users to scores
     */
    public HashMap<User, Integer> getLeaderboard(Room room) {
        return room.getLeaderboard();
    }

    /**
     * Update the leaderboard for a specific room.
     * 
     * @param room the room
     * @param score the score to record
     */
    public void updateLeaderboard(Room room, int score) {
        room.getLeaderboard().put(currentUser, score);
        DataWriter.saveRooms();
    }

    /**
     * Get session statistics for the current session.
     * 
     * @return a formatted string with session statistics
     */
    public String getSessionStats() {
        if (currentSession == null) return "No active session";
        
        StringBuilder stats = new StringBuilder();
        stats.append("Session Statistics:\n");
        stats.append("Rooms Visited: ").append(currentSession.getVisitedRoomsCount()).append("\n");
        stats.append("Rooms Completed: ").append(currentSession.getCompletedRoomsCount()).append("\n");
        stats.append("Total Puzzles Solved: ").append(currentSession.getTotalPuzzlesSolved()).append("\n");
        stats.append("Total Hints Used: ").append(currentSession.getTotalHintsUsed()).append("\n");
        stats.append("Overall Progress: ").append(currentSession.getOverallCompletionPercent()).append("%\n");
        
        return stats.toString();
    }
}
