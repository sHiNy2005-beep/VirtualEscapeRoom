package com.model.library;

import org.junit.Before;
import org.junit.Test;

import com.model.Room;
import com.model.RoomList;

import java.util.List;
import static org.junit.Assert.*;

public class RoomListTest {

    private RoomList roomList;
    private Room mockRoom1;
    private Room mockRoom2;

    @Before
    public void setUp() {
        roomList = RoomList.getInstance();

        mockRoom1 = new Room("Conference A", "Easy", false);
        mockRoom1.setRoomId("R1");

        mockRoom2 = new Room("Meeting Room", "Medium", false);
        mockRoom2.setRoomId("R2");
    }

    @Test
    public void testSingletonInstance_isSameAcrossCalls() {
        RoomList instance1 = RoomList.getInstance();
        RoomList instance2 = RoomList.getInstance();
        assertSame("getInstance should always return the same object", instance1, instance2);
    }

    @Test
    public void testAddRoom_addsToListAndCache() {
        int initialSize = roomList.getRooms().size();
        roomList.addRoom(mockRoom1);

        assertEquals(initialSize + 1, roomList.getRooms().size());
        assertTrue(roomList.getRooms().contains(mockRoom1));
        assertEquals(mockRoom1, roomList.getRoomById("R1"));
    }

    @Test
    public void testAddRoom_doesNotAddDuplicates() {
        roomList.addRoom(mockRoom1);
        int sizeAfterFirstAdd = roomList.getRooms().size();

        roomList.addRoom(mockRoom1); 
        assertEquals("RoomList should not allow duplicates",
                sizeAfterFirstAdd, roomList.getRooms().size());
    }

    @Test
    public void testGetRoomById_returnsCorrectRoom() {
        roomList.addRoom(mockRoom2);
        Room found = roomList.getRoomById("R2");
        assertNotNull(found);
        assertEquals("R2", found.getRoomId());
    }

    @Test
    public void testGetRoomByTitle_caseInsensitiveMatch() {
        roomList.addRoom(mockRoom2);
        Room found = roomList.getRoomByTitle("meeting room");
        assertNotNull(found);
        assertEquals(mockRoom2, found);
    }

    @Test
    public void testGetRoomById_returnsNullWhenMissing() {
        assertNull(roomList.getRoomById("nonexistent"));
    }

    @Test
    public void testGetRoomByTitle_returnsNullWhenMissing() {
        assertNull(roomList.getRoomByTitle("unknown"));
    }

    @Test
    public void testGetRooms_isUnmodifiable() {
        List<Room> rooms = roomList.getRooms();
        try {
            rooms.add(new Room("Temp", "Easy", false));
            fail("Returned list should be unmodifiable");
        } catch (UnsupportedOperationException expected) {
        }
    }
}
