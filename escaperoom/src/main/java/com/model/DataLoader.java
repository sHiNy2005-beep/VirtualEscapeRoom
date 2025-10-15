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
                    Puzzle puzzle = new CodePuzzle(
                        (String) pJSON.get("title"),
                        (String) pJSON.get("description"),
                        (String) pJSON.get("solution")
                    );
                    JSONArray hints = (JSONArray) pJSON.get("hints");
                    for (Object h : hints) {
                        puzzle.addHint((String) h);
                    }
                    room.addPuzzle(puzzle.getTitle(), puzzle);
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
    for (User u : UserList.getInstance().getUsers()) {
        System.out.println(" - " + u);
}

    System.out.println("\nLoaded Rooms:");
    for (Room r : RoomList.getInstance().getRooms()) {
        System.out.println(" - " + r);
    }
  }
}

