package com.model.library;

import com.model.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class DataloaderTests {

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

        // Rooms and puzzles
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
    public void testUsersLoaded() {
        List<User> users = userList.getUsers();
        assertEquals(2, users.size());
        assertEquals("Alice", users.get(0).getUserName());
        assertEquals("Bob", users.get(1).getUserName());
    }

    @Test
    public void testRoomsLoaded() {
        List<Room> rooms = roomList.getRooms();
        assertEquals(2, rooms.size());
        assertNotNull(roomList.getRoomByTitle("Garden"));
        assertNotNull(roomList.getRoomByTitle("Library"));
    }

    @Test
    public void testRoomPuzzles() {
        Room garden = roomList.getRoomByTitle("Garden");
        List<Puzzle> puzzles = garden.getPuzzles();
        assertEquals(1, puzzles.size());
        Puzzle puzzle = puzzles.get(0);
        assertTrue(puzzle instanceof ItemPuzzle);
        assertEquals("The Buried Truth", puzzle.getTitle());

        ItemPuzzle ip = (ItemPuzzle) puzzle;
        assertEquals(2, ip.getRequiredItems().size());
        assertTrue(ip.getRequiredItems().contains("shovel"));
        assertTrue(ip.getRequiredItems().contains("rope"));
    }

    @Test
    public void testCodePuzzle() {
        Room library = roomList.getRoomByTitle("Library");
        List<Puzzle> puzzles = library.getPuzzles();
        assertEquals(1, puzzles.size());
        Puzzle puzzle = puzzles.get(0);
        assertTrue(puzzle instanceof CodePuzzle);
        assertEquals("1234", puzzle.getSolution());
    }

    @Test
    public void testPuzzleAnswerChecking() {
        Room garden = roomList.getRoomByTitle("Garden");
        ItemPuzzle ip = (ItemPuzzle) garden.getPuzzles().get(0);
        assertTrue(ip.checkAnswer("STATUE"));
        assertFalse(ip.checkAnswer("TREE"));

        Room library = roomList.getRoomByTitle("Library");
        CodePuzzle cp = (CodePuzzle) library.getPuzzles().get(0);
        assertTrue(cp.checkAnswer("1234"));
        assertFalse(cp.checkAnswer("0000"));
    }

    @Test
    public void testUserDuplication() {
        boolean added = userList.signUp("Alice", "newpass");
        assertFalse(added);

        boolean addedNew = userList.signUp("Charlie", "pass3");
        assertTrue(addedNew);
        assertEquals(3, userList.getUsers().size());
    }

    @Test
    public void testMultiplePuzzlesInRoom() {
        Room garden = roomList.getRoomByTitle("Garden");
        CodePuzzle extraPuzzle = new CodePuzzle("CodeX", "Extra", "9999");
        garden.addPuzzle(extraPuzzle.getTitle(), extraPuzzle);
        assertEquals(2, garden.getPuzzles().size());
    }

    @Test
    public void testGetRoomById() {
        Room garden = roomList.getRoomByTitle("Garden");
        String id = garden.getRoomId();
        Room byId = roomList.getRoomById(id);
        assertNotNull(byId);
        assertEquals("Garden", byId.getTitle());
    }

    @Test
public void testSignUpWithNullUsername() {
    boolean added = userList.signUp(null, "pass");
    assertFalse(added);
    assertEquals(2, userList.getUsers().size()); // original 2 users
}

@Test
public void testSignUpWithEmptyUsername() {
    boolean added = userList.signUp("   ", "pass");
    assertFalse(added);
    assertEquals(2, userList.getUsers().size());
}

@Test
public void testSignUpWithNullPassword() {
    boolean added = userList.signUp("Charlie", null);
    assertFalse(added);
    assertEquals(2, userList.getUsers().size());
}



@Test
public void testRoomItemsAreCorrect() {
    Room garden = roomList.getRoomByTitle("Garden");
    assertTrue(garden.getItems().contains("shovel"));
    assertTrue(garden.getItems().contains("rope"));
}

@Test
public void testClearUsersAndRooms() {
    userList.clearUsers();
    roomList.clearRooms();
    assertEquals(0, userList.getUsers().size());
    assertEquals(0, roomList.getRooms().size());
}

@Test
public void testAddMultipleItemsToRoom() {
    Room garden = roomList.getRoomByTitle("Garden");
    garden.addItem("bucket");
    garden.addItem("watering can");
    assertTrue(garden.getItems().contains("bucket"));
    assertTrue(garden.getItems().contains("watering can"));
}

@Test
public void testSignUpTrimsUsername() {
    boolean added = userList.signUp("  Charlie  ", "pass3");
    assertTrue(added);
    assertEquals("Charlie", userList.getUsers().get(2).getUserName());
}

@Test
public void testAddMultiplePuzzlesToRoom() {
    Room garden = roomList.getRoomByTitle("Garden");
    CodePuzzle extra = new CodePuzzle("Extra", "Extra desc", "999");
    garden.addPuzzle(extra.getTitle(), extra);
    assertEquals(2, garden.getPuzzles().size());
}

@Test
public void testSignUpInvalidInputs() {
    assertFalse(userList.signUp(null, "pass"));
    assertFalse(userList.signUp("Dave", null));
    assertFalse(userList.signUp("", "pass"));
    assertFalse(userList.signUp("Dave", ""));
}

@Test
public void testAddDuplicateItemToRoom() {
    Room garden = roomList.getRoomByTitle("Garden");
    garden.addItem("shovel");  // already exists
    assertEquals(2, garden.getItems().size());  // should not add duplicate
}

@Test
public void testAddDuplicatePuzzleToRoom() {
    Room garden = roomList.getRoomByTitle("Garden");
    ItemPuzzle existing = (ItemPuzzle) garden.getPuzzles().get(0);
    garden.addPuzzle(existing.getTitle(), existing); // duplicate
    assertEquals(1, garden.getPuzzles().size());  // should remain 1
}

@Test
public void testGetRoomByTitleNotFound() {
    assertNull(roomList.getRoomByTitle("NonExistentRoom"));
}



@Test
public void testRoomWithNoPuzzles() {
    Room emptyRoom = new Room("EmptyRoom", "Easy", false);
    roomList.addRoom(emptyRoom);
    assertEquals(0, emptyRoom.getPuzzles().size());
}

@Test
public void testItemPuzzleWithNoRequiredItems() {
    Room testRoom = new Room("TestRoom", "Easy", false);
    ItemPuzzle puzzle = new ItemPuzzle("SimplePuzzle", "No items required", "ANSWER");
    testRoom.addPuzzle(puzzle.getTitle(), puzzle);
    assertEquals(0, puzzle.getRequiredItems().size());
}

@Test
public void testUsernamesAreCaseInsensitive() {
    // "Alice" already exists
    boolean added = userList.signUp("alice", "newpass");
    assertFalse(added);
}

@Test
public void testPuzzleAnswerCaseSensitivity() {
    Room garden = roomList.getRoomByTitle("Garden");
    ItemPuzzle ip = (ItemPuzzle) garden.getPuzzles().get(0);
    assertFalse(ip.checkAnswer("statue"));     // lowercase
    assertFalse(ip.checkAnswer(" STATUE "));   // spaces
}

@Test
public void testRoomWithEmptyItemsAndPuzzles() {
    Room emptyRoom = new Room("EmptyRoom2", "Easy", false);
    roomList.addRoom(emptyRoom);
    assertEquals(0, emptyRoom.getItems().size());
    assertEquals(0, emptyRoom.getPuzzles().size());
}

@Test
public void testRoomIdUniqueness() {
    Room r1 = new Room("UniqueRoom", "Medium", false);
    Room r2 = new Room("UniqueRoom", "Medium", false); // same title
    roomList.addRoom(r1);
    roomList.addRoom(r2);
    assertNotEquals(r1.getRoomId(), r2.getRoomId());
}
}