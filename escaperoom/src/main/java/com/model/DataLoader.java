package com.model;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class DataLoader extends DataConstants {

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

            
            
            JSONArray sessionsArray = (JSONArray) userJSON.get("sessions");
            if (sessionsArray != null) {
                for (Object sObj : sessionsArray) {
                    JSONObject sJSON = (JSONObject) sObj;
                    JSONObject roomJSON = (JSONObject) sJSON.get("room");

                    Room room = new Room(
                        (String) roomJSON.get("title"),
                        (String) roomJSON.get("difficulty"),
                        false
                    );

                    GameSession session = new GameSession(user, room);
                    session.setStartTime((long) sJSON.get("startTime"));
                    session.setEndTime((long) sJSON.get("endTime"));
                    session.setScore(((Long) sJSON.get("score")).intValue());
                    session.setHintsUsed(((Long) sJSON.get("hintsUsed")).intValue());
                    session.setCompleted((boolean) sJSON.get("isCompleted"));

                    JSONArray invArray = (JSONArray) sJSON.get("inventory");
                    for (Object item : invArray) {
                     session.collectItem((String) item);

                    JSONArray puzzleSessions = (JSONArray) sJSON.get("puzzleSessions");
                    if (puzzleSessions != null) {
                    for (Object pObj : puzzleSessions) {
                    JSONObject psJSON = (JSONObject) pObj;
                    PuzzleSession ps = new PuzzleSession((String) psJSON.get("puzzleTitle"));
                    for (int i = 0; i < ((Long) psJSON.get("numHintsUsed")).intValue(); i++) {
                     ps.useHint();
                   }
                   if ((boolean) psJSON.get("solved")) {
                   ps.markSolved((String) psJSON.get("finalAnswer"));
                   }
                   session.getPuzzleSessions().add(ps);
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

                
                JSONArray items = (JSONArray) roomJSON.get("items");
                for (Object item : items) {
                    room.addItem((String) item);
                }

                
                JSONArray puzzles = (JSONArray) roomJSON.get("puzzles");
                for (Object pObj : puzzles) {
                    JSONObject pJSON = (JSONObject) pObj;

                 String type = (String) pJSON.get("type");
                 String title = (String) pJSON.get("title");
                 String description = (String) pJSON.get("description");
                 String solution = (String) pJSON.get("solution");
                 Puzzle puzzle = null;

                if ("Code".equalsIgnoreCase(type)) {
                    puzzle = new CodePuzzle(title, description, solution);
                } else if ("Riddle".equalsIgnoreCase(type)) {
                    puzzle = new RiddlePuzzle(title, description, solution);
                } else if ("Item".equalsIgnoreCase(type)) {
                    puzzle = new ItemPuzzle(title, description, solution);
                    JSONArray required = (JSONArray) pJSON.get("requiredItems");
                    if (required != null) {
                        for (Object item : required) {
                            ((ItemPuzzle)puzzle).addRequiredItem((String)item);
                        }
                    }
                } else if ("Math".equalsIgnoreCase(type)) {
                    try {
                        int code = Integer.parseInt(solution);
                        puzzle = new MathPuzzle(title, description, code);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid math puzzle solution: " + solution);
                    }
                }
                else if ("Matching".equalsIgnoreCase(type)) {
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


