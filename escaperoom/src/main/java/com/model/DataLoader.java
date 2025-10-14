package com.model;

<<<<<<< HEAD
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class DataLoader extends DataConstants {

    /**
     * Returns rooms from the RoomList singleton (in-memory).
     * Use loadRooms() to populate from disk first.
     */
    public ArrayList<Room> getRooms() {
        return RoomList.getInstance().getRooms();
    }

    /**
     * Returns users from the UserList singleton (in-memory).
     * Use loadUsers() to populate from disk first.
     */
    public ArrayList<User> getUsers() {
        return UserList.getInstance().getUsers();
    }

    /**
     * Loads rooms from ROOMS_FILE and populates the RoomList singleton.
     * Each line is parsed by Room.fromString().
     */
    public void loadRooms() {
        ArrayList<Room> loaded = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(ROOMS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Room r = Room.fromString(line);
                if (r != null) loaded.add(r);
            }
            // Replace contents of singleton with loaded rooms
            RoomList list = RoomList.getInstance();
            list.getRooms().clear();
            list.getRooms().addAll(loaded);

            System.out.println("Loaded " + loaded.size() + " rooms from " + ROOMS_FILE);
        } catch (IOException e) {
            // If file doesn't exist or can't be read, just report and keep current in-memory rooms
            System.err.println("Could not load rooms from " + ROOMS_FILE + ": " + e.getMessage());
        }
    }

    /**
     * Loads users from USERS_FILE and populates the UserList singleton.
     * Each line is parsed by User.fromString().
     */
    public void loadUsers() {
        ArrayList<User> loaded = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                User u = User.fromString(line);
                if (u != null) loaded.add(u);
            }
            // Replace contents of singleton with loaded users
            UserList list = UserList.getInstance();
            list.getUsers().clear();
            list.getUsers().addAll(loaded);

            System.out.println("Loaded " + loaded.size() + " users from " + USERS_FILE);
        } catch (IOException e) {
            System.err.println("Could not load users from " + USERS_FILE + ": " + e.getMessage());
        }
    }

    /**
     * Placeholder - returns persisted game sessions for a user if you later implement them.
     * For now returns an empty list.
     */
    public ArrayList<GameSession> getGameSessions(User user) {
        return new ArrayList<GameSession>();
    }
}
=======
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
>>>>>>> 779b880d0597b881d4cbb3823e612a1ec1b9916c
