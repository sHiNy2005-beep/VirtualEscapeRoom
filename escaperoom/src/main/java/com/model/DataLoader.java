package com.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
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
                
                User user;
                if (userJSON.containsKey("userId")) {
                    String userIdStr = (String) userJSON.get("userId");
                    try {
                        java.util.UUID userId = java.util.UUID.fromString(userIdStr);
                        user = new User(userId, userName, email, password);
                    } catch (IllegalArgumentException e) {
                        user = new User(userName, email, password);
                    }
                } else {
                    user = new User(userName, email, password);
                }

                JSONArray sessionsArray = (JSONArray) userJSON.get("sessions");
                if (sessionsArray != null) {
                    for (Object sObj : sessionsArray) {
                        JSONObject sJSON = (JSONObject) sObj;
                        
                        GameSession session = new GameSession(user);
                        session.setSessionId((String) sJSON.get("sessionId"));
                        session.setSessionStartTime((long) sJSON.get("sessionStartTime"));
                        session.setSessionEndTime((long) sJSON.get("sessionEndTime"));
                        session.setSessionCompleted((boolean) sJSON.get("isSessionCompleted"));

                        JSONArray roomSessionsArray = (JSONArray) sJSON.get("roomSessions");
                        if (roomSessionsArray != null) {
                            for (Object rsObj : roomSessionsArray) {
                                JSONObject rsJSON = (JSONObject) rsObj;
                                
                                String roomId = (String) rsJSON.get("roomId");
                                String roomTitle = (String) rsJSON.get("roomTitle");
                                
                                Room room = findRoomByIdOrTitle(roomId, roomTitle);
                                if (room != null) {
                                    RoomSession roomSession = session.enterRoom(room);
                                    
                                    roomSession.setStartTime((long) rsJSON.get("startTime"));
                                    roomSession.setEndTime((long) rsJSON.get("endTime"));
                                    roomSession.setCompleted((boolean) rsJSON.get("isCompleted"));
                                    roomSession.setHintsUsed(((Long) rsJSON.get("hintsUsed")).intValue());
                                    
                                    JSONArray invArray = (JSONArray) rsJSON.get("inventory");
                                    ArrayList<String> inventory = new ArrayList<>();
                                    for (Object item : invArray) {
                                        inventory.add((String) item);
                                    }
                                    roomSession.setInventory(inventory);
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
                                                
                                                for (Puzzle puzzle : room.getPuzzles()) {
                                                    if (puzzle.getTitle().equals(ps.getPuzzleTitle())) {
                                                        puzzle.setSolved(true);
                                                        break;
                                                    }
                                                }
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
     * Uses RoomList to get the actual cached room instances.
     * 
     * @param roomId the room ID to search for
     * @param roomTitle fallback title if ID not found
     * @return the matching Room or null
     */
    private static Room findRoomByIdOrTitle(String roomId, String roomTitle) {
        RoomList roomList = RoomList.getInstance();
        for (Room r : roomList.getRooms()) {
            if (r.getRoomId().equals(roomId)) {
                return r;
            }
        }
        
        for (Room r : roomList.getRooms()) {
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
                
                if (roomJSON.containsKey("roomId")) {
                    room.setRoomId((String) roomJSON.get("roomId"));
                }

                JSONArray items = (JSONArray) roomJSON.get("items");
                if (items != null) {
                    for (Object item : items) {
                        room.addItem((String) item);
                    }
                }

                JSONArray puzzles = (JSONArray) roomJSON.get("puzzles");
                if (puzzles != null) {
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
                            Object solutionObj = pJSON.get("solution");
                            try {
                                int code;
                                if (solutionObj instanceof String) {
                                    code = Integer.parseInt((String) solutionObj);
                                } else if (solutionObj instanceof Long) {
                                    code = ((Long) solutionObj).intValue();
                                } else {
                                    code = (int) solutionObj;
                                }
                                puzzle = new MathPuzzle(title, description, code);
                            } catch (Exception e) {
                                System.out.println("Invalid math puzzle solution: " + solutionObj);
                            }
                        } else if ("Matching".equalsIgnoreCase(type)) {
                            Object solutionObj = pJSON.get("solution");
                            if (solutionObj instanceof JSONObject) {
                                JSONObject solutionJSON = (JSONObject) solutionObj;
                                ArrayList<String> leftSide = new ArrayList<>();
                                ArrayList<String> rightSide = new ArrayList<>();

                                for (Object key : solutionJSON.keySet()) {
                                    leftSide.add((String) key);
                                    rightSide.add((String) solutionJSON.get(key));
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
                }

                Object leaderboardObj = roomJSON.get("leaderboard");
                if (leaderboardObj instanceof JSONArray) {
                    JSONArray leaderboardArray = (JSONArray) leaderboardObj;
                    for (Object lbObj : leaderboardArray) {
                        if (lbObj instanceof JSONObject) {
                            JSONObject lbEntry = (JSONObject) lbObj;
                            String username = (String) lbEntry.get("username");
                            Object scoreObj = lbEntry.get("score");
                            
                            if (username != null && scoreObj != null) {
                                int score = 0;
                                if (scoreObj instanceof Long) {
                                    score = ((Long) scoreObj).intValue();
                                } else if (scoreObj instanceof Integer) {
                                    score = (Integer) scoreObj;
                                }
                            }
                        }
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

    private static String getFileWritingPath(String USER_FILE, String USER_FILE_JUNIT) {
		try {
			if(isJUnitTest()){
				URI url = DataLoader.class.getResource(USER_FILE_JUNIT).toURI();
				return url.getPath();
			} else {
				return USER_FILE;
			}
		} catch(Exception e){
			System.out.println("Difficulty getting resource path");
			return "";
		}
	}

	private static BufferedReader getReaderFromFile(String fileName, String jsonFileName){
		try {
			if(isJUnitTest()){
				InputStream inputStream = DataLoader.class.getResourceAsStream(jsonFileName);
				InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
				return new BufferedReader(inputStreamReader);
			} else {
				FileReader reader = new FileReader(fileName);
				return new BufferedReader(reader);
			}
		} catch(Exception e){
			System.out.println("Can't load");
			return null;
		}
			
	}
}

