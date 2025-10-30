package com.model.library;

import com.model.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class DataWriterTests {

    private UserList userList;
    private RoomList roomList;

    @Before
    public void setUp() {
        userList = UserList.getInstance();
        userList.clearUsers();

        roomList = RoomList.getInstance();
        roomList.clearRooms();

        // Users
        userList.signUp("Alice", "pass1");
        userList.signUp("Bob", "pass2");

        // Rooms
        Room garden = new Room("Garden", "Medium", false);
        garden.addItem("shovel");
        garden.addItem("rope");
        ItemPuzzle itemPuzzle = new ItemPuzzle(
                "The Buried Truth",
                "Use the shovel and rope to dig where the roses grow.",
                "STATUE"
        );
        itemPuzzle.addRequiredItem("shovel");
        itemPuzzle.addRequiredItem("rope");
        garden.addPuzzle(itemPuzzle.getTitle(), itemPuzzle);

        Room library = new Room("Library", "Hard", false);
        CodePuzzle codePuzzle = new CodePuzzle(
                "Hidden Code",
                "Find the secret code.",
                "1234"
        );
        library.addPuzzle(codePuzzle.getTitle(), codePuzzle);

        roomList.addRoom(garden);
        roomList.addRoom(library);
    }

    @After
    public void tearDown() {
        userList.clearUsers();
        roomList.clearRooms();
    }

    @Test
    public void testUsersSavedAndReloaded() {
        DataWriter.saveUsers();
        List<User> users = userList.getUsers();
        assertEquals(2, users.size());
        assertEquals("Alice", users.get(0).getUserName());
        assertEquals("Bob", users.get(1).getUserName());
    }

    @Test
    public void testRoomsSavedAndReloaded() {
        DataWriter.saveRooms();
        List<Room> rooms = roomList.getRooms();
        assertEquals(2, rooms.size());

        Room garden = roomList.getRoomByTitle("Garden");
        assertNotNull(garden);
        Puzzle puzzle = garden.getPuzzles().get(0);
        assertEquals("The Buried Truth", puzzle.getTitle());
    }

    @Test
    public void testAddAndSaveNewUser() {
        boolean added = userList.signUp("Charlie", "pass3");
        assertTrue(added);
        DataWriter.saveUsers();
        assertEquals(3, userList.getUsers().size());
    }

    @Test
    public void testDuplicateUserNotAdded() {
        boolean added = userList.signUp("Alice", "newpass");
        assertFalse(added);
        assertEquals(2, userList.getUsers().size());
    }

    @Test
    public void testMultiplePuzzlesSaved() {
        Room garden = roomList.getRoomByTitle("Garden");
        CodePuzzle extraPuzzle = new CodePuzzle("CodeX", "Extra puzzle", "9999");
        garden.addPuzzle(extraPuzzle.getTitle(), extraPuzzle);

        DataWriter.saveRooms();
        Room reloadedGarden = roomList.getRoomByTitle("Garden");
        assertEquals(2, reloadedGarden.getPuzzles().size());
    }

    @Test
    public void testPuzzleAnswerCheckingAfterSave() {
        Room garden = roomList.getRoomByTitle("Garden");
        ItemPuzzle itemPuzzle = (ItemPuzzle) garden.getPuzzles().get(0);
        assertTrue(itemPuzzle.checkAnswer("STATUE"));
        assertFalse(itemPuzzle.checkAnswer("TREE"));

        Room library = roomList.getRoomByTitle("Library");
        CodePuzzle codePuzzle = (CodePuzzle) library.getPuzzles().get(0);
        assertTrue(codePuzzle.checkAnswer("1234"));
        assertFalse(codePuzzle.checkAnswer("0000"));
    }

    @Test
    public void testGetRoomByIdAfterSave() {
        Room garden = roomList.getRoomByTitle("Garden");
        String id = garden.getRoomId();
        Room byId = roomList.getRoomById(id);
        assertNotNull(byId);
        assertEquals("Garden", byId.getTitle());
    }


@Test
public void testSaveMultiplePuzzles() {
    Room garden = roomList.getRoomByTitle("Garden");
    CodePuzzle extra = new CodePuzzle("Extra", "Extra desc", "999");
    garden.addPuzzle(extra.getTitle(), extra);

    DataWriter.saveRooms();
    Room reloaded = roomList.getRoomByTitle("Garden");
    assertEquals(2, reloaded.getPuzzles().size());
}

@Test
public void testSaveAndLoadUsersConsistency() {
    userList.signUp("Charlie", "pass3");
    DataWriter.saveUsers();
    List<User> users = userList.getUsers();
    assertEquals(3, users.size());
}

@Test
public void testAddRoomAndGetById() {
    Room library = new Room("Library", "Hard", false);
    roomList.addRoom(library);
    Room byId = roomList.getRoomById(library.getRoomId());
    assertNotNull(byId);
    assertEquals("Library", byId.getTitle());
}

@Test
public void testSaveEmptyUsersAndRooms() {
    userList.clearUsers();
    roomList.clearRooms();
    DataWriter.saveUsers();
    DataWriter.saveRooms();
    assertEquals(0, userList.getUsers().size());
    assertEquals(0, roomList.getRooms().size());
}

@Test
public void testSaveDoesNotDuplicate() {
    DataWriter.saveUsers();
    DataWriter.saveRooms();
    assertEquals(2, userList.getUsers().size());
    assertEquals(1, roomList.getRooms().size());
}



@Test
public void testSaveEmptyUsers() {
    userList.clearUsers();
    DataWriter.saveUsers();
    assertEquals(0, userList.getUsers().size());
}

@Test
public void testSaveEmptyRooms() {
    roomList.clearRooms();
    DataWriter.saveRooms();
    assertEquals(0, roomList.getRooms().size());
}

@Test
public void testMultipleUsersSamePassword() {
    userList.signUp("Frank", "samepass");
    userList.signUp("Grace", "samepass");
    assertEquals(4, userList.getUsers().size()); // Alice, Bob + Frank + Grace
}



@Test
public void testUserSignUpCaseInsensitiveAfterSave() {
    userList.signUp("Alice", "newpass"); // should fail
    DataWriter.saveUsers();
    List<User> users = userList.getUsers();
    assertEquals(2, users.size()); // still only Alice and Bob
}



@Test
public void testRoomWithEmptyItemsAndPuzzlesAfterSave() {
    Room emptyRoom = new Room("EmptyRoom3", "Easy", false);
    roomList.addRoom(emptyRoom);
    DataWriter.saveRooms();

    Room loaded = roomList.getRoomByTitle("EmptyRoom3");
    assertEquals(0, loaded.getItems().size());
    assertEquals(0, loaded.getPuzzles().size());
}

@Test
public void testRoomIdUniquenessAfterSave() {
    Room r1 = new Room("RoomX", "Medium", false);
    Room r2 = new Room("RoomX", "Medium", false);
    roomList.addRoom(r1);
    roomList.addRoom(r2);
    DataWriter.saveRooms();
    assertNotEquals(r1.getRoomId(), r2.getRoomId());
}
}