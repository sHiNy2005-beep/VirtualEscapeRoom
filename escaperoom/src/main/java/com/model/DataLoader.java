package com.model;

import java.io.FileReader;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class DataLoader extends DataConstants {

    public static ArrayList<User> getUsers() {
        ArrayList<User> users = new ArrayList<>();
        try {
            FileReader reader = new FileReader(USERS_TEST_FILE);
            JSONArray jsonUsers = (JSONArray) new JSONParser().parse(reader);

            for (Object obj : jsonUsers) {
                JSONObject userJSON = (JSONObject) obj;
                String userName = (String) userJSON.get("userName");
                String email = (String) userJSON.get("email");
                String password = (String) userJSON.get("password");

                users.add(new User(userName, email, password));
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
                String title = (String) roomJSON.get("title");
                String difficulty = (String) roomJSON.get("difficulty");
                boolean isLocked = (boolean) roomJSON.get("isLocked");

                Room room = new Room(title, difficulty, isLocked);

                JSONArray items = (JSONArray) roomJSON.get("items");
                for (Object item : items)
                    room.addItem((String) item);

                rooms.add(room);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rooms;
    }
}