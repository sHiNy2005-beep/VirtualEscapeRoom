package com.model;

import java.io.FileReader;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class DataLoader extends DataConstants {

    /**
     * Read users and their saved sessions from the users JSON file.
     *
     * @return a list of User objects populated from the persisted file.
     */
    public static ArrayList<User> getUsers() {
        ArrayList<User> users = new ArrayList<>();

        try {
            FileReader reader = new FileReader(USERS_FILE);
            JSONArray jsonUsers = (JSONArray) new JSONParser().parse(reader);

            for (Object obj : jsonUsers) {
                JSONObject userJSON = (JSONObject) obj;

                String userName = (String) userJSON.get("username");
                String email = (String) userJSON.get("email");
                String password = (String) userJSON.get("password");

                User user = new User(userName, email, password);

                // Load sessions
                JSONArray sessionsArray = (JSONArray) userJSON.get("sessions");
                if (sessionsArray != null) {
                    for (Object sObj : sessionsArray) {
                        JSONObject sJSON = (JSONObject) sObj;
                        
                        GameSession session = new GameSession(user);
                        session.setSessionId((String) sJSON.get("sessionId"));
                        session.setSessionStartTime((long) sJSON.get("sessionStartTime"));
                        session.setSessionEndTime((long) sJSON.get("sessionEndTime"));
                        session.setSessionCompleted((boolean) sJSON.get("isSessionCompleted"));

                        // Load room sessions for each room in this game session
                        JSONArray roomSessionsArray = (JSONArray) sJSON.get("roomSessions");
                        if (roomSessionsArray != null) {
                            for (Object rsObj : roomSessionsArray) {
                                JSONObject rsJSON = (JSONObject) rsObj;
                                
                                // Find the corresponding room
                                String roomId = (String) rsJSON.get("roomId");
                                String roomTitle = (String) rsJSON.get("roomTitle");
                                
                                // Get the actual Room object from RoomList
                                Room room = findRoomByIdOrTitle(roomId, roomTitle);
                                if (room != null) {
                                    RoomSession roomSession = session.enterRoom(room);
                                    
                                    // Restore room session state
                                    roomSession.setStartTime((long) rsJSON.get("startTime"));
                                    roomSession.setEndTime((long) rsJSON.get("endTime"));
                                    roomSession.setCompleted((boolean) rsJSON.get("isCompleted"));
                                    roomSession.setHintsUsed(((Long) rsJSON.get("hintsUsed")).intValue());
                                    
                                    // Restore inventory
                                    JSONArray invArray = (JSONArray) rsJSON.get("inventory");
                                    ArrayList<String> inventory = new ArrayList<>();
                                    for (Object item : invArray) {
                                        inventory.add((String) item);
                                    }
                                    roomSession.setInventory(inventory);
                                    
                                    // Restore puzzle sessions
                                    JSONArray puzzleSessions = (JSONArray) rsJSON.get("puzzleSessions");
                                    if (puzzleSessions != null) {
                                        ArrayList<PuzzleSession> psList = new ArrayList<>();
                                        for (Object pObj : puzzleSessions) {
                                            JSONObject psJSON = (JSONObject) pObj;
                                            PuzzleSession ps = new PuzzleSession((String) psJSON.get("puzzleTitle"));
                                            
                                            for (int i = 0; i < ((Long) psJSON.get("numHintsUsed")).intValue(); i++) {
                                                ps.useHint();
                                            }
                                            
                                            if ((boolean) psJSON.get("solved")) {
                                                ps.markSolved((String) psJSON.get("finalAnswer"));
                                            }
                                            
                                            psList.add(ps);
                                        }
                                        roomSession.setPuzzleSessions(psList);
                                    }
                                }
                            }
                        }

                        user.addSession(session);
                    }
                }

                users.add(user);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return users;
    }
    
    /**
     * Helper method to find a room by ID or title.
     * This is needed when loading user sessions.
     * 
     * @param roomId the room ID to search for
     * @param roomTitle fallback title if ID not found
     * @return the matching Room or null
     */
    private static Room findRoomByIdOrTitle(String roomId, String roomTitle) {
        // First try to find by ID
        for (Room r : getRooms()) {
            if (r.getRoomId().equals(roomId)) {
                return r;
            }
        }
        
        // Fallback to title
        for (Room r : getRooms()) {
            if (r.getTitle().equalsIgnoreCase(roomTitle)) {
                return r;
            }
        }
        
        return null;
    }

    /**
     * Read rooms, their puzzles, items and leaderboards from the rooms JSON file.
     *
     * @return a list of Room objects populated from the persisted file.
     */
    public static ArrayList<Room> getRooms() {
        ArrayList<Room> rooms = new ArrayList<>();

        try {
            FileReader reader = new FileReader(ROOMS_FILE);
            JSONArray jsonRooms = (JSONArray) new JSONParser().parse(reader);

            for (Object obj : jsonRooms) {
                JSONObject roomJSON = (JSONObject) obj;

                Room room = new Room(
                    (String) roomJSON.get("title"),
                    (String) roomJSON.get("difficulty"),
                    (boolean) roomJSON.get("isLocked")
                );
                room.setRoomId((String) roomJSON.get("roomId"));

                // Load items
                JSONArray items = (JSONArray) roomJSON.get("items");
                for (Object item : items) {
                    room.addItem((String) item);
                }

                // Load puzzles
                JSONArray puzzles = (JSONArray) roomJSON.get("puzzles");
                for (Object pObj : puzzles) {
                    JSONObject pJSON = (JSONObject) pObj;

                    String type = (String) pJSON.get("type");
                    String title = (String) pJSON.get("title");
                    String description = (String) pJSON.get("description");
                    Puzzle puzzle = null;

                    if ("Code".equalsIgnoreCase(type)) {
                        String solution = (String) pJSON.get("solution");
                        puzzle = new CodePuzzle(title, description, solution);
                    } else if ("Riddle".equalsIgnoreCase(type)) {
                        String solution = (String) pJSON.get("solution");
                        puzzle = new RiddlePuzzle(title, description, solution);
                    } else if ("Item".equalsIgnoreCase(type)) {
                        String solution = (String) pJSON.get("solution");
                        puzzle = new ItemPuzzle(title, description, solution);
                        JSONArray required = (JSONArray) pJSON.get("requiredItems");
                        if (required != null) {
                            for (Object item : required) {
                                ((ItemPuzzle)puzzle).addRequiredItem((String)item);
                            }
                        }
                    } else if ("Math".equalsIgnoreCase(type)) {
                        String solution = (String) pJSON.get("solution");
                        try {
                            int code = Integer.parseInt(solution);
                            puzzle = new MathPuzzle(title, description, code);
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid math puzzle solution: " + solution);
                        }
                    } else if ("Matching".equalsIgnoreCase(type)) {
                        JSONObject solutionObj = (JSONObject) pJSON.get("solution");
                        if (solutionObj != null) {
                            ArrayList<String> leftSide = new ArrayList<>();
                            ArrayList<String> rightSide = new ArrayList<>();

                            for (Object key : solutionObj.keySet()) {
                                leftSide.add((String) key);
                                rightSide.add((String) solutionObj.get(key));
                            }

                            puzzle = new FinalPuzzle(title, description, leftSide, rightSide);
                        }
                    }

                    if (puzzle != null) {
                        JSONArray hints = (JSONArray) pJSON.get("hints");
                        if (hints != null) {
                            for (Object h : hints) {
                                puzzle.addHint((String) h);
                            }
                        }
                        room.addPuzzle(puzzle.getTitle(), puzzle);
                    }
                }

                rooms.add(room);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rooms;
    }

    /**
     * Small test runner used during dev time to quickly print out
     * the users and rooms that would be loaded by this class. This is not
     * used by the application at runtime but is convenient for manual testing.
     *
     * @param args command-line arguments (ignored)
     */
    public static void main(String[] args) {
        System.out.println("Loaded Users:");
        for (User u : DataLoader.getUsers()) {
            System.out.println(" - " + u.getUserName());
        }

        System.out.println("\nLoaded Rooms:");
        for (Room r : DataLoader.getRooms()) {
            System.out.println(" - " + r);
        }
    }
}
