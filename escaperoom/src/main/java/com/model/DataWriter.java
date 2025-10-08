
package com.model;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import com.model.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class DataWriter extends DataConstants {

   
    public static void saveUsers() {
      UserList userlist = UserList.getInstance();
      ArrayList<User> users = userlist.getUsers();

        JSONArray jsonUsers = new JSONArray();

      for(int i=0; i< users.size(); i++) {

            jsonUsers.add(getUserJSON(users.get(i)));
        }

        try (FileWriter file = new FileWriter(USERS_TEST_FILE)) {
            file.write(jsonUsers.toJSONString());
            file.flush();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static JSONObject getUserJSON(User user) {
        JSONObject userDetails = new JSONObject();
        userDetails.put("userName", user.getUserName());
        userDetails.put("email", user.getEmail());
        userDetails.put("password", user.getPassword());
       
        return userDetails;
    }

    
    public static void saveRooms() {
        RoomList roomList= RoomList.getInstance();

        
        ArrayList<Room> rooms = roomList.getRooms(); 

        JSONArray jsonRooms = new JSONArray();

        for(int i=0; i< rooms.size(); i++) {

			jsonRooms.add(getRoomJSON(rooms.get(i)));
		}

        try (FileWriter file = new FileWriter(ROOMS_FILE)) {
            file.write(jsonRooms.toJSONString());
            file.flush();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    private static JSONObject getRoomJSON(Room room) {
        JSONObject roomDetails = new JSONObject();
        roomDetails.put("roomId", room.getRoomId());
        roomDetails.put("title", room.getTitle());
        roomDetails.put("difficulty", room.getDifficulty());
        roomDetails.put("isLocked", room.isLocked());
        roomDetails.put("items", room.getItems());

        
        return roomDetails;
    }

    public static void main(String[] args) {
       DataWriter.saveUsers();
       DataWriter.saveRooms();
    }
}