package com.model.library;

import org.junit.Test;

import com.model.DataLoader;
import com.model.Puzzle;
import com.model.Room;

import java.util.List;
import static org.junit.Assert.*;

public class RoomClassTest {

    @Test
    public void loadRooms_viaDataLoader_and_verifyFirstRoom() throws Exception {
        List<Room> rooms = DataLoader.getRooms();
        assertNotNull("DataLoader should return a list (possibly empty)", rooms);
        assertFalse("There should be at least one room in json/Room.json", rooms.isEmpty());

        Room firstRoom = rooms.get(0);
        assertNotNull(firstRoom);
        assertEquals("room_001", firstRoom.getRoomId());
        assertEquals("Library", firstRoom.getTitle());

        List<Puzzle> puzzles = firstRoom.getPuzzles();
        assertNotNull(puzzles);
        assertFalse(puzzles.isEmpty());

        Puzzle p0 = puzzles.get(0);
        assertNotNull(p0.getSolution());
        assertEquals("THOMASISDISOWNED", p0.getSolution());
    }
}
