package com.model;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class DataWriter extends DataConstants {

    /**
     * Serialize all users (and their sessions with room sessions) to the users JSON file.
     */
    @SuppressWarnings("unchecked")
    public static void saveUsers() {
        UserList userlist = UserList.getInstance();
        ArrayList<User> users = new ArrayList<>(userlist.getUsers());
        JSONArray jsonUsers = new JSONArray();

        for (User u : users) {
            jsonUsers.add(getUserJSON(u));
        }

        try (FileWriter file = new FileWriter(USERS_FILE)) {
            file.write(jsonUsers.toJSONString());
            file.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Build a JSONObject representing the provided User,
     * including their game sessions and room sessions.
     *
     * @param user the user to convert
     * @return a JSONObject to be added to the users array
     */
    @SuppressWarnings("unchecked")
    private static JSONObject getUserJSON(User user) {
        JSONObject userDetails = new JSONObject();
        userDetails.put("userId", user.getUserId().toString());
        userDetails.put("username", user.getUserName());
        userDetails.put("email", user.getEmail());
        userDetails.put("password", user.getPassword());

        // Serialize game sessions
        JSONArray sessionArray = new JSONArray();
        for (GameSession session : user.getSessions()) {
            JSONObject sJSON = new JSONObject();
            sJSON.put("sessionId", session.getSessionId());
            sJSON.put("sessionStartTime", session.getSessionStartTime());
            sJSON.put("sessionEndTime", session.getSessionEndTime());
            sJSON.put("isSessionCompleted", session.isSessionCompleted());

            // Serialize room sessions for each room in this game session
            JSONArray roomSessionsArray = new JSONArray();
            for (Map.Entry<String, RoomSession> entry : session.getAllRoomSessions().entrySet()) {
                RoomSession roomSession = entry.getValue();
                JSONObject rsJSON = new JSONObject();
                
                rsJSON.put("roomId", roomSession.getRoomId());
                rsJSON.put("roomTitle", roomSession.getRoomTitle());
                rsJSON.put("startTime", roomSession.getStartTime());
                rsJSON.put("endTime", roomSession.getEndTime());
                rsJSON.put("isCompleted", roomSession.isCompleted());
                rsJSON.put("hintsUsed", roomSession.getHintsUsed());
                
                // Serialize inventory
                JSONArray invArray = new JSONArray();
                for (String item : roomSession.getInventory()) {
                    invArray.add(item);
                }
                rsJSON.put("inventory", invArray);
                
                // Serialize puzzle sessions
                JSONArray puzzlesArray = new JSONArray();
                for (PuzzleSession ps : roomSession.getPuzzleSessions()) {
                    JSONObject psJSON = new JSONObject();
                    psJSON.put("puzzleTitle", ps.getPuzzleTitle());
                    psJSON.put("numHintsUsed", ps.getNumHintsUsed());
                    psJSON.put("solved", ps.isSolved());
                    psJSON.put("finalAnswer", ps.getFinalAnswer());
                    puzzlesArray.add(psJSON);
                }
                rsJSON.put("puzzleSessions", puzzlesArray);
                
                roomSessionsArray.add(rsJSON);
            }
            sJSON.put("roomSessions", roomSessionsArray);

            sessionArray.add(sJSON);
        }
        userDetails.put("sessions", sessionArray);
        return userDetails;
    }

    /**
     * Serialize rooms (their puzzles, items and leaderboards) to the rooms JSON file.
     */
    @SuppressWarnings("unchecked")
    public static void saveRooms() {
        RoomList roomList = RoomList.getInstance();
        ArrayList<Room> rooms = new ArrayList<>(roomList.getRooms());

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
     * Build a JSONObject representing the provided Room,
     * including items, puzzles and leaderboard entries.
     * NOW INCLUDES PUZZLE TYPE INFORMATION!
     *
     * @param room the room to convert
     * @return a JSONObject ready to be added to the rooms array
     */
    @SuppressWarnings("unchecked")
    private static JSONObject getRoomJSON(Room room) {
        JSONObject roomDetails = new JSONObject();
        roomDetails.put("roomId", room.getRoomId());
        roomDetails.put("title", room.getTitle());
        roomDetails.put("difficulty", room.getDifficulty());
        roomDetails.put("isLocked", room.isLocked());

        // Serialize items
        JSONArray itemsArray = new JSONArray();
        for (String item : room.getItems()) {
            itemsArray.add(item);
        }
        roomDetails.put("items", itemsArray);

        // Serialize puzzles WITH TYPE INFORMATION
        JSONArray puzzleArray = new JSONArray();
        for (Puzzle puzzle : room.getPuzzles()) {
            JSONObject pJSON = new JSONObject();
            pJSON.put("title", puzzle.getTitle());
            pJSON.put("description", puzzle.getDescription());
            pJSON.put("solution", puzzle.getSolution());
            
            // Determine and store puzzle type
            if (puzzle instanceof FinalPuzzle) {
                pJSON.put("type", "Matching");
                // Store the correct pairs for FinalPuzzle
                FinalPuzzle fp = (FinalPuzzle) puzzle;
                JSONObject solutionObj = new JSONObject();
                for (Map.Entry<String, String> entry : fp.getCorrectPairs().entrySet()) {
                    solutionObj.put(entry.getKey(), entry.getValue());
                }
                pJSON.put("solution", solutionObj);
            } else if (puzzle instanceof MathPuzzle) {
                pJSON.put("type", "Math");
            } else if (puzzle instanceof ItemPuzzle) {
                pJSON.put("type", "Item");
                ItemPuzzle ip = (ItemPuzzle) puzzle;
                JSONArray reqItems = new JSONArray();
                for (String item : ip.getRequiredItems()) {
                    reqItems.add(item);
                }
                pJSON.put("requiredItems", reqItems);
            } else if (puzzle instanceof RiddlePuzzle) {
                pJSON.put("type", "Riddle");
            } else if (puzzle instanceof CodePuzzle) {
                pJSON.put("type", "Code");
            } else {
                pJSON.put("type", "Code"); // Default fallback
            }
            
            JSONArray hintsArray = new JSONArray();
            for (String hint : puzzle.getHints()) {
                hintsArray.add(hint);
            }
            pJSON.put("hints", hintsArray);
            
            puzzleArray.add(pJSON);
        }
        roomDetails.put("puzzles", puzzleArray);

        // Serialize leaderboard as array of objects
        JSONArray leaderboardArray = new JSONArray();
        for (Map.Entry<User, Integer> entry : room.getLeaderboard().entrySet()) {
            JSONObject lbEntry = new JSONObject();
            lbEntry.put("username", entry.getKey().getUserName());
            lbEntry.put("score", entry.getValue());
            leaderboardArray.add(lbEntry);
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
