package com.model.library;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Map;
import com.model.*;

public class GameSessionTest {

    private User testUser;
    private Room testRoom1;
    private Room testRoom2;
    private GameSession gameSession;

    @Before
    public void setUp() throws Exception {
        testUser = new User("TestPlayer", "test@example.com", "password");
        RiddlePuzzle puzzle1 = new RiddlePuzzle("Puzzle 1", "First puzzle", "answer1");
        RiddlePuzzle puzzles2 = new RiddlePuzzle("Puzzle 2", "Second puzzle", "answer2");
        RiddlePuzzle puzzles3 = new RiddlePuzzle("Puzzle 3", "Third puzzle", "answer3");
        
        Room testRoom = new Room("room-123", "Easy", true);
        testRoom.addPuzzle("1", puzzle1);
        testRoom.addPuzzle("2", puzzles2);
        testRoom.addPuzzle("3", puzzles3);
        testRoom2 = new Room("room-124", "Easy", true);
    }

    @Test
    public void testConstructorWithValidUser() {
        gameSession = new GameSession(testUser);
        
        assertNotNull(gameSession);
        assertEquals(testUser, gameSession.getUser());
        assertNotNull(gameSession.getSessionId());
        assertTrue(gameSession.getSessionId().contains("testplayer"));
        assertTrue(gameSession.getSessionStartTime() > 0);
        assertEquals(0, gameSession.getSessionEndTime());
        assertFalse(gameSession.isSessionCompleted());
        assertNotNull(gameSession.getRoomSessionMap());
        assertTrue(gameSession.getRoomSessionMap().isEmpty());
        assertNull(gameSession.getCurrentRoomSession());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithNullUser() {
        new GameSession(null);
    }

    @Test
    public void testConstructorGeneratesUniqueSessionIds() throws InterruptedException {
        GameSession session1 = new GameSession(testUser);
        Thread.sleep(10);
        GameSession session2 = new GameSession(testUser);
        
        assertNotEquals(session1.getSessionId(), session2.getSessionId());
    }

    @Test
    public void testConstructorSessionIdFormat() {
        gameSession = new GameSession(testUser);
        
        String sessionId = gameSession.getSessionId();
        assertTrue(sessionId.startsWith("session_"));
        assertTrue(sessionId.toLowerCase().contains("testplayer"));
    }

    @Test
    public void testEnterRoomFirstTime() {
        gameSession = new GameSession(testUser);
        
        RoomSession roomSession = gameSession.enterRoom(testRoom1);
        
        assertNotNull(roomSession);
        assertEquals("room-1", roomSession.getRoomId());
        assertEquals(roomSession, gameSession.getCurrentRoomSession());
        assertEquals(1, gameSession.getVisitedRoomsCount());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEnterRoomWithNull() {
        gameSession = new GameSession(testUser);
        gameSession.enterRoom(null);
    }

    @Test
    public void testEnterRoomResume() {
        gameSession = new GameSession(testUser);
        
        RoomSession firstEntry = gameSession.enterRoom(testRoom1);
        firstEntry.collectItem("key");
        
        gameSession.enterRoom(testRoom2);
        RoomSession secondEntry = gameSession.enterRoom(testRoom1);
        
        assertSame(firstEntry, secondEntry);
        assertTrue(secondEntry.hasItem("key"));
    }

    @Test
    public void testEnterRoomUpdatesCurrentSession() {
        gameSession = new GameSession(testUser);
        
        RoomSession room1Session = gameSession.enterRoom(testRoom1);
        RoomSession room2Session = gameSession.enterRoom(testRoom2);
        
        assertEquals(room2Session, gameSession.getCurrentRoomSession());
        assertNotEquals(room1Session, gameSession.getCurrentRoomSession());
    }

    @Test
    public void testEnterMultipleRooms() {
        gameSession = new GameSession(testUser);
        
        gameSession.enterRoom(testRoom1);
        gameSession.enterRoom(testRoom2);
        
        assertEquals(2, gameSession.getVisitedRoomsCount());
    }

    @Test
    public void testGetRoomSessionExisting() {
        gameSession = new GameSession(testUser);
        
        RoomSession entered = gameSession.enterRoom(testRoom1);
        RoomSession retrieved = gameSession.getRoomSession(testRoom1);
        
        assertSame(entered, retrieved);
    }

    @Test
    public void testGetRoomSessionUnvisited() {
        gameSession = new GameSession(testUser);
        
        RoomSession roomSession = gameSession.getRoomSession(testRoom1);
        
        assertNull(roomSession);
    }

    @Test
    public void testGetRoomSessionWithNull() {
        gameSession = new GameSession(testUser);
        
        RoomSession roomSession = gameSession.getRoomSession(null);
        
        assertNull(roomSession);
    }

    @Test
    public void testGetCurrentRoomSessionInitially() {
        gameSession = new GameSession(testUser);
        
        assertNull(gameSession.getCurrentRoomSession());
    }

    @Test
    public void testGetCurrentRoomSessionActive() {
        gameSession = new GameSession(testUser);
        
        RoomSession roomSession = gameSession.enterRoom(testRoom1);
        
        assertEquals(roomSession, gameSession.getCurrentRoomSession());
    }

    @Test
    public void testSubmitAnswerCorrect() {
        gameSession = new GameSession(testUser);
        gameSession.enterRoom(testRoom1);
        
        boolean result = gameSession.submitAnswer("Math Puzzle", "42", testRoom1);
        
        assertTrue(result);
    }

    @Test
    public void testSubmitAnswerIncorrect() {
        gameSession = new GameSession(testUser);
        gameSession.enterRoom(testRoom1);
        
        boolean result = gameSession.submitAnswer("Math Puzzle", "wrong", testRoom1);
        
        assertFalse(result);
    }

    @Test
    public void testSubmitAnswerMarksSolved() {
        gameSession = new GameSession(testUser);
        RoomSession roomSession = gameSession.enterRoom(testRoom1);
        
        gameSession.submitAnswer("Math Puzzle", "42", testRoom1);
        
        Puzzle mathPuzzle = testRoom1.getPuzzles().get(0);
        PuzzleSession puzzleSession = roomSession.getPuzzleSession(mathPuzzle);
        assertTrue(puzzleSession.isSolved());
    }

    @Test
    public void testSubmitAnswerSetsFinalAnswer() {
        gameSession = new GameSession(testUser);
        RoomSession roomSession = gameSession.enterRoom(testRoom1);
        
        gameSession.submitAnswer("Math Puzzle", "42", testRoom1);
        
        Puzzle mathPuzzle = testRoom1.getPuzzles().get(0);
        PuzzleSession puzzleSession = roomSession.getPuzzleSession(mathPuzzle);
        assertEquals("42", puzzleSession.getFinalAnswer());
    }

    @Test
    public void testSubmitAnswerNullRoom() {
        gameSession = new GameSession(testUser);
        gameSession.enterRoom(testRoom1);
        
        boolean result = gameSession.submitAnswer("Math Puzzle", "42", null);
        
        assertFalse(result);
    }

    @Test
    public void testSubmitAnswerNoCurrentSession() {
        gameSession = new GameSession(testUser);
        
        boolean result = gameSession.submitAnswer("Math Puzzle", "42", testRoom1);
        
        assertFalse(result);
    }

    @Test
    public void testSubmitAnswerNonExistentPuzzle() {
        gameSession = new GameSession(testUser);
        gameSession.enterRoom(testRoom1);
        
        boolean result = gameSession.submitAnswer("Non-existent Puzzle", "42", testRoom1);
        
        assertFalse(result);
    }

    @Test
    public void testSubmitAnswerCaseInsensitive() {
        gameSession = new GameSession(testUser);
        gameSession.enterRoom(testRoom1);
        
        boolean result = gameSession.submitAnswer("MATH PUZZLE", "42", testRoom1);
        
        assertTrue(result);
    }

    @Test
    public void testSubmitAnswerCorrectMessage() {
        gameSession = new GameSession(testUser);
        gameSession.enterRoom(testRoom1);
        
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
        
        gameSession.submitAnswer("Math Puzzle", "42", testRoom1);
        
        System.setOut(originalOut);
        String output = outputStream.toString();
        
        assertTrue(output.contains("Correct!"));
        assertTrue(output.contains("42"));
    }

    @Test
    public void testSubmitAnswerIncorrectMessage() {
        gameSession = new GameSession(testUser);
        gameSession.enterRoom(testRoom1);
        
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
        
        gameSession.submitAnswer("Math Puzzle", "wrong", testRoom1);
        
        System.setOut(originalOut);
        String output = outputStream.toString();
        
        assertTrue(output.contains("Incorrect!"));
    }

    @Test
    public void testUseHint() {
        gameSession = new GameSession(testUser);
        RoomSession roomSession = gameSession.enterRoom(testRoom1);
        
        gameSession.useHint("Math Puzzle");
        
        assertEquals(1, roomSession.getHintsUsed());
    }

    @Test
    public void testUseHintNoCurrentSession() {
        gameSession = new GameSession(testUser);
        
        gameSession.useHint("Math Puzzle");
        
        assertEquals(0, gameSession.getTotalHintsUsed());
    }

    @Test
    public void testCollectItem() {
        gameSession = new GameSession(testUser);
        gameSession.enterRoom(testRoom1);
        
        gameSession.collectItem("key");
        
        assertTrue(gameSession.hasItem("key"));
    }

    @Test
    public void testCollectItemNoCurrentSession() {
        gameSession = new GameSession(testUser);
        
        gameSession.collectItem("key");
        
        assertFalse(gameSession.hasItem("key"));
    }

    @Test
    public void testHasItemTrue() {
        gameSession = new GameSession(testUser);
        gameSession.enterRoom(testRoom1);
        gameSession.collectItem("sword");
        
        assertTrue(gameSession.hasItem("sword"));
    }

    @Test
    public void testHasItemFalse() {
        gameSession = new GameSession(testUser);
        gameSession.enterRoom(testRoom1);
        
        assertFalse(gameSession.hasItem("shield"));
    }

    @Test
    public void testHasItemNoCurrentSession() {
        gameSession = new GameSession(testUser);
        
        assertFalse(gameSession.hasItem("key"));
    }

    @Test
    public void testCompleteCurrentRoom() {
        gameSession = new GameSession(testUser);
        RoomSession roomSession = gameSession.enterRoom(testRoom1);
        
        gameSession.completeCurrentRoom();
        
        assertTrue(roomSession.isCompleted());
    }

    @Test
    public void testCompleteCurrentRoomNoSession() {
        gameSession = new GameSession(testUser);
        
        gameSession.completeCurrentRoom();
        
        assertEquals(0, gameSession.getCompletedRoomsCount());
    }

    @Test
    public void testGetCompletedRoomsCountInitial() {
        gameSession = new GameSession(testUser);
        
        assertEquals(0, gameSession.getCompletedRoomsCount());
    }

    @Test
    public void testGetCompletedRoomsCount() {
        gameSession = new GameSession(testUser);
        
        gameSession.enterRoom(testRoom1);
        gameSession.completeCurrentRoom();
        
        gameSession.enterRoom(testRoom2);
        gameSession.completeCurrentRoom();
        
        assertEquals(2, gameSession.getCompletedRoomsCount());
    }

    @Test
    public void testGetVisitedRoomsCount() {
        gameSession = new GameSession(testUser);
        
        gameSession.enterRoom(testRoom1);
        gameSession.enterRoom(testRoom2);
        
        assertEquals(2, gameSession.getVisitedRoomsCount());
    }

    @Test
    public void testEndSession() {
        gameSession = new GameSession(testUser);
        
        gameSession.endSession();
        
        assertTrue(gameSession.isSessionCompleted());
        assertTrue(gameSession.getSessionEndTime() > 0);
    }

    @Test
    public void testEndSessionTime() throws InterruptedException {
        gameSession = new GameSession(testUser);
        long startTime = gameSession.getSessionStartTime();
        
        Thread.sleep(10);
        gameSession.endSession();
        
        assertTrue(gameSession.getSessionEndTime() > startTime);
    }

    @Test
    public void testCalculateTotalScoreNoRooms() {
        gameSession = new GameSession(testUser);
        
        assertEquals(0, gameSession.calculateTotalScore());
    }

    @Test
    public void testCalculateTotalScore() {
        gameSession = new GameSession(testUser);
        
        gameSession.enterRoom(testRoom1);
        gameSession.enterRoom(testRoom2);
        
        int expectedTotal = 15000 + 15000;
        
        assertEquals(expectedTotal, gameSession.calculateTotalScore());
    }

    @Test
    public void testCalculateTotalScoreUsesMedium() {
        gameSession = new GameSession(testUser);
        RoomSession roomSession = gameSession.enterRoom(testRoom1);
        
        roomSession.setHintsUsed(0);
        
        assertEquals(15000, gameSession.calculateTotalScore());
    }

    @Test
    public void testGetTotalPuzzlesSolvedInitial() {
        gameSession = new GameSession(testUser);
        
        assertEquals(0, gameSession.getTotalPuzzlesSolved());
    }

    @Test
    public void testGetTotalPuzzlesSolved() {
        gameSession = new GameSession(testUser);
        
        RoomSession room1Session = gameSession.enterRoom(testRoom1);
        room1Session.getPuzzleSessions().get(0).setSolved(true);
        
        gameSession.enterRoom(testRoom2);
        
        assertEquals(1, gameSession.getTotalPuzzlesSolved());
    }

    @Test
    public void testGetTotalHintsUsedInitial() {
        gameSession = new GameSession(testUser);
        
        assertEquals(0, gameSession.getTotalHintsUsed());
    }

    @Test
    public void testGetTotalHintsUsed() {
        gameSession = new GameSession(testUser);
        
        gameSession.enterRoom(testRoom1);
        gameSession.useHint("Math Puzzle");
        gameSession.useHint("Logic Puzzle");
        
        gameSession.enterRoom(testRoom2);
        gameSession.useHint("Some Puzzle");
        
        assertEquals(3, gameSession.getTotalHintsUsed());
    }

    @Test
    public void testGetOverallCompletionPercentNoRooms() {
        gameSession = new GameSession(testUser);
        
        assertEquals(0, gameSession.getOverallCompletionPercent());
    }

    @Test
    public void testGetOverallCompletionPercent() {
        gameSession = new GameSession(testUser);
        
        RoomSession room1Session = gameSession.enterRoom(testRoom1);
        room1Session.getPuzzleSessions().get(0).setSolved(true);
        
        gameSession.enterRoom(testRoom2);
        
        assertEquals(25, gameSession.getOverallCompletionPercent());
    }

    @Test
    public void testGetSessionDuration() throws InterruptedException {
        gameSession = new GameSession(testUser);
        
        Thread.sleep(1100);
        
        long duration = gameSession.getSessionDuration();
        
        assertTrue(duration >= 1);
    }

    @Test
    public void testGetSessionDurationAfterEnd() throws InterruptedException {
        gameSession = new GameSession(testUser);
        
        Thread.sleep(1100);
        gameSession.endSession();
        long duration1 = gameSession.getSessionDuration();
        
        Thread.sleep(500);
        long duration2 = gameSession.getSessionDuration();
        
        assertEquals(duration1, duration2);
    }

    @Test
    public void testGetAllRoomSessions() {
        gameSession = new GameSession(testUser);
        
        gameSession.enterRoom(testRoom1);
        gameSession.enterRoom(testRoom2);
        
        Map<String, RoomSession> allSessions = gameSession.getAllRoomSessions();
        
        assertEquals(2, allSessions.size());
        assertTrue(allSessions.containsKey("room-1"));
        assertTrue(allSessions.containsKey("room-2"));
    }

    @Test
    public void testGetAllRoomSessionsDefensiveCopy() {
        gameSession = new GameSession(testUser);
        gameSession.enterRoom(testRoom1);
        
        Map<String, RoomSession> copy = gameSession.getAllRoomSessions();
        copy.clear();
        
        assertEquals(1, gameSession.getVisitedRoomsCount());
    }

    @Test
    public void testGetSessionId() {
        gameSession = new GameSession(testUser);
        
        assertNotNull(gameSession.getSessionId());
    }

    @Test
    public void testSetSessionId() {
        gameSession = new GameSession(testUser);
        
        gameSession.setSessionId("custom-id");
        
        assertEquals("custom-id", gameSession.getSessionId());
    }

    @Test
    public void testGetUser() {
        gameSession = new GameSession(testUser);
        
        assertEquals(testUser, gameSession.getUser());
    }

    @Test
    public void testSetSessionStartTime() {
        gameSession = new GameSession(testUser);
        
        gameSession.setSessionStartTime(1000000L);
        
        assertEquals(1000000L, gameSession.getSessionStartTime());
    }

    @Test
    public void testSetSessionEndTime() {
        gameSession = new GameSession(testUser);
        
        gameSession.setSessionEndTime(2000000L);
        
        assertEquals(2000000L, gameSession.getSessionEndTime());
    }

    @Test
    public void testSetSessionCompleted() {
        gameSession = new GameSession(testUser);
        
        gameSession.setSessionCompleted(true);
        
        assertTrue(gameSession.isSessionCompleted());
    }

    @Test
    public void testSetRoomSessionMap() {
        gameSession = new GameSession(testUser);
        
        gameSession.setRoomSessionMap(new java.util.HashMap<String, RoomSession>());
        
        assertEquals(0, gameSession.getVisitedRoomsCount());
    }

    @Test
    public void testToString() {
        gameSession = new GameSession(testUser);
        
        String result = gameSession.toString();
        
        assertNotNull(result);
        assertTrue(result.contains("TestPlayer"));
        assertTrue(result.contains("roomsVisited=0"));
        assertTrue(result.contains("roomsCompleted=0"));
        assertTrue(result.contains("overallProgress=0%"));
        assertTrue(result.contains("totalHints=0"));
    }

    @Test
    public void testToStringWithProgress() {
        gameSession = new GameSession(testUser);
        
        gameSession.enterRoom(testRoom1);
        gameSession.useHint("Math Puzzle");
        gameSession.completeCurrentRoom();
        
        String result = gameSession.toString();
        
        assertTrue(result.contains("roomsVisited=1"));
        assertTrue(result.contains("roomsCompleted=1"));
        assertTrue(result.contains("totalHints=1"));
    }
}