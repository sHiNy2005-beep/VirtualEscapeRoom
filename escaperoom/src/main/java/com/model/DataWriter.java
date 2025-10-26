package com.model;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


public class DataWriter extends DataConstants {

    /**
     * Serialize all users (and their sessions) to the users JSON file.
     */
    public static void saveUsers() {
        UserList userList = UserList.getInstance();
        ArrayList<User> users = (ArrayList<User>) userList.getUsers();

        JSONArray jsonUsers = new JSONArray();

        for (User user : users) {
            jsonUsers.add(getUserJSON(user));
        }

        try (FileWriter file = new FileWriter(USERS_FILE)) {
            file.write(jsonUsers.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Build a {@link JSONObject} representing the provided {@link User},
     * including their saved sessions, inventory and puzzle sessions.
     *
     * @param user the user to convert
     * @return a JSONObject  to be added to the users array
     */
    private static JSONObject getUserJSON(User user) {
        JSONObject userDetails = new JSONObject();
        userDetails.put("userId", user.getUserId().toString());
        userDetails.put("username", user.getUserName());
        userDetails.put("email", user.getEmail());
        userDetails.put("password", user.getPassword());

        
        JSONArray sessionArray = new JSONArray();
        for (GameSession session : user.getSessions()) {
            JSONObject sJSON = new JSONObject();
            sJSON.put("sessionId", session.getSessionId());
            sJSON.put("startTime", session.getStartTime());
            sJSON.put("endTime", session.getEndTime());
            sJSON.put("score", session.getScore());
            sJSON.put("hintsUsed", session.getHintsUsed());
            sJSON.put("isCompleted", session.isCompleted());

            JSONArray puzzlesArray = new JSONArray();
        for (PuzzleSession ps : session.getPuzzleSessions()) {
        JSONObject psJSON = new JSONObject();
           psJSON.put("puzzleTitle", ps.getPuzzleTitle());
           psJSON.put("numHintsUsed", ps.getNumHintsUsed());
           psJSON.put("solved", ps.isSolved());
           psJSON.put("finalAnswer", ps.getFinalAnswer());
           puzzlesArray.add(psJSON);
      }
          sJSON.put("puzzleSessions", puzzlesArray);

            
            JSONArray invArray = new JSONArray();
            for (String item : session.getInventory()) {
                invArray.add(item);
            }
            sJSON.put("inventory", invArray);

            
            JSONObject roomJSON = new JSONObject();
            roomJSON.put("roomId", session.getRoom().getRoomId());
            roomJSON.put("title", session.getRoom().getTitle());
            roomJSON.put("difficulty", session.getRoom().getDifficulty());
            sJSON.put("room", roomJSON);

            sessionArray.add(sJSON);
        }
        userDetails.put("sessions", sessionArray);
        return userDetails;
    }



    /**
     * Show rooms (their puzzles, items and leaderboards) to the rooms JSON file.
     */
    public static void saveRooms() {
        RoomList roomList = RoomList.getInstance();
        ArrayList<Room> rooms = (ArrayList<Room>) roomList.getRooms();

        JSONArray jsonRooms = new JSONArray();

        for (Room room : rooms) {
            jsonRooms.add(getRoomJSON(room));
        }

        try (FileWriter file = new FileWriter(ROOMS_FILE)) {
            file.write(jsonRooms.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Build a {@link JSONObject} representing the provided {@link Room},
     * including items, puzzles and leaderboard entries.
     *
     * @param room the room to convert
     * @return a JSONObject ready to be added to the rooms array
     */
    private static JSONObject getRoomJSON(Room room) {
        JSONObject roomDetails = new JSONObject();
        roomDetails.put("roomId", room.getRoomId());
        roomDetails.put("title", room.getTitle());
        roomDetails.put("difficulty", room.getDifficulty());
        roomDetails.put("isLocked", room.isLocked());

        
        JSONArray itemsArray = new JSONArray();
        for (String item : room.getItems()) {
            itemsArray.add(item);
        }
        roomDetails.put("items", itemsArray);

        
        JSONArray puzzleArray = new JSONArray();
        for (Puzzle puzzle : room.getPuzzles()) {
            JSONObject pJSON = new JSONObject();
            pJSON.put("title", puzzle.getTitle());
            pJSON.put("description", puzzle.getDescription());
            pJSON.put("solution", puzzle.getSolution());
            pJSON.put("hints", puzzle.getHints());
            puzzleArray.add(pJSON);
        }
        roomDetails.put("puzzles", puzzleArray);

       
        JSONArray leaderboardArray = new JSONArray();
        for (Map.Entry<User, Integer> entry : room.getLeaderboard().entrySet()) {
            JSONObject lbJSON = new JSONObject();
            lbJSON.put("username", entry.getKey().getUserName());
            lbJSON.put("score", entry.getValue());
            leaderboardArray.add(lbJSON);
        }
        roomDetails.put("leaderboard", leaderboardArray);

        return roomDetails;
    }

    /**
     * Development helper that writes current in-memory users and rooms to disk.
     * Not used during normal application flow but useful when testing data persistence.
     *
     * @param args ignored
     */
    public static void main(String[] args) {
        saveUsers();
        saveRooms();
    }
}