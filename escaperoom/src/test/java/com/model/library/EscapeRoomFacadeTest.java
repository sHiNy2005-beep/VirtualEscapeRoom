package com.model.library;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Array;
import java.util.ArrayList;


import org.junit.Test;

import com.model.EscapeRoomFacade;
import com.model.RiddlePuzzle;
import com.model.Room;

public class EscapeRoomFacadeTest {
    
    //by Murewa Adebajo
    
    @Test
    public void TestTesting() 
    {
        assertTrue(true);
    }

    @Test
    public void CreateAccountTest() 
    { 
        EscapeRoomFacade facade = new EscapeRoomFacade();
        boolean accountCreated = facade.createAccount("testuser", "testuser@example.com", "password123");
        assertTrue(accountCreated);
    }

    @Test
    public void loginTest()
    {
        EscapeRoomFacade facade = new EscapeRoomFacade();
        facade.createAccount("testuser", "testuser@example.com", "password123");
        boolean accountLoggedIn = facade.login("testuser", "password123");
        assertTrue(accountLoggedIn);
    }

    @Test
    public void logoutTest()
    {
        EscapeRoomFacade facade = new EscapeRoomFacade();
        facade.createAccount("testuser", "testuser@example.com", "password123");
        facade.login("testuser", "password123");
        facade.logout();
        boolean loggedIn = facade.login("testuser", "password123");
        assertFalse(loggedIn);
    }
    
    @Test
    public void startGameTest() 
    {
        EscapeRoomFacade facade = new EscapeRoomFacade();
        Room Room1 = new Room("Test Room", "Easy", true);
        facade.createAccount("testuser", "testuser@example.com", "password123");
        facade.login("testuser", "password123");
        facade.startGame(Room1);
        Room currentRoom = facade.getCurrentRoom();
        assertEquals(Room1, currentRoom);
    }

    @Test
    public void getCurrentRoomTest() 
    {
        EscapeRoomFacade facade = new EscapeRoomFacade();
        Room Room1 = new Room("Test Room", "Easy", true);
        facade.createAccount("testuser", "testuser@example.com", "password123");
        facade.login("testuser", "password123");
        facade.startGame(Room1);
        Room currentRoom = facade.getCurrentRoom();
        assertEquals(Room1, currentRoom);

    }
    
    @Test
    public void getCurrentRoomPuzzlesTest() 
    {
        EscapeRoomFacade facade = new EscapeRoomFacade();
        ArrayList<RiddlePuzzle> puzzles = new ArrayList<RiddlePuzzle>();
        facade.createAccount("testuser", "testuser@example.com", "password123");
        Room Room1 = new Room("Test Room 1", "Medium", true);
        Room1.addPuzzle("puzzle1", new RiddlePuzzle("Test Puzzle", "This is a test puzzle", "testsolution"));
        facade.login("testuser", "password123");
        facade.startGame(Room1);
        facade.getCurrentRoomPuzzles();
        assertEquals(1, puzzles.size());

    }

    @Test
    public void getPuzzleByTitleTest() 
    {
        EscapeRoomFacade facade = new EscapeRoomFacade();
        RiddlePuzzle puzzle = new RiddlePuzzle("Test Puzzle", "This is a test puzzle", "testsolution");
        facade.getPuzzleByTitle("Test Puzzle");
        assertEquals("Test Puzzle", puzzle.getTitle());
    }

    @Test
    public void getScoreTest() 
    {
        EscapeRoomFacade facade = new EscapeRoomFacade();
        int score = facade.getScore();
        assertEquals(0, score);
    }

    @Test
    public void getCurrentRoomScoreTest()
    {
        EscapeRoomFacade facade = new EscapeRoomFacade();
        int roomScore = facade.getCurrentRoomScore();
        assertEquals(0, roomScore);
    }

    @Test
    public void submitStringAnswerTest() 
    {
        EscapeRoomFacade facade = new EscapeRoomFacade();
        boolean correct = facade.submitAnswer("Test Puzzle", "testsolution");
        assertTrue(correct);

    }

    @Test
    public void submitIntAnswerTest()
    {
        EscapeRoomFacade facade = new EscapeRoomFacade();
        ArrayList<RiddlePuzzle> puzzles = new ArrayList<RiddlePuzzle>();
        Room Room1 = new Room("Test Room 1", "Medium", true);
        Room1.addPuzzle("puzzle1", new RiddlePuzzle("Test Puzzle", "This is a test puzzle", "42"));
        boolean correct = facade.submitAnswer("Test Puzzle", 42);
        assertTrue(correct);
    }

    @Test
    public void useHintTest()
    {
        EscapeRoomFacade facade = new EscapeRoomFacade();
        RiddlePuzzle puzzle = new RiddlePuzzle("Test Puzzle", "This is a test puzzle", "testsolution");
        ArrayList<String> hints = puzzle.getHints();
        puzzle.addHint("This is hint 1");
        facade.useHint("Test Puzzle");
        assertEquals(1, hints.size());
    }

    @Test
    public void getCurrentUserTest()
    {
        EscapeRoomFacade facade = new EscapeRoomFacade();
        facade.createAccount("testuser", "testuser@example.com", "password123");
        facade.login("testuser", "password123");
        facade.getCurrentUser();
        assertEquals("testuser", facade.getCurrentUser());
    }

    @Test
    public void endGameTest()
    {
        EscapeRoomFacade facade = new EscapeRoomFacade();
        facade.createAccount("testuser", "testuser@example.com", "password123");
        facade.login("testuser", "password123");
        Room Room1 = new Room("Test Room", "Easy", true);
        facade.startGame(Room1);
        facade.endGame();
        Room currentRoom = facade.getCurrentRoom();
        assertEquals(null, currentRoom);
    }
    


}