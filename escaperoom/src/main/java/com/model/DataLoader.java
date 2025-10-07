package com.model;

import java.util.ArrayList;
import com.model.*;

public class DataLoader extends DataConstants {
    
    public ArrayList<Room> getRooms() {
        return new ArrayList<Room>();
    }
    
    public  ArrayList<User> getUsers() {
        return new ArrayList<User>();
    }
    
    public ArrayList<GameSession> getGameSessions(User user) {
        return new ArrayList<GameSession>();
    }
}
