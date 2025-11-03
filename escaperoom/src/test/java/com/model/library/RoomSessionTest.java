package com.model.library;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import com.model.*;

public class RoomSessionTest {

    private Room testRoom;
    private Room emptyRoom;
    private RoomSession roomSession;

    @Before
    public void setUp() throws Exception {
        RiddlePuzzle puzzle1 = new RiddlePuzzle("Puzzle 1", "First puzzle", "answer1");
        RiddlePuzzle puzzles2 = new RiddlePuzzle("Puzzle 2", "Second puzzle", "answer2");
        RiddlePuzzle puzzles3 = new RiddlePuzzle("Puzzle 3", "Third puzzle", "answer3");
        
        testRoom = new Room("room-123", "Easy", true);
        testRoom.addPuzzle("1", puzzle1);
        testRoom.addPuzzle("2", puzzles2);
        testRoom.addPuzzle("3", puzzles3);

        
        emptyRoom = new Room("empty", "Easy", false);
    }

    @Test
    public void testConstructorWithValidRoom() {
        roomSession = new RoomSession(testRoom);
        
        assertNotNull(roomSession);
        assertEquals("room-123", roomSession.getRoomTitle());
        assertFalse(roomSession.isCompleted());
        assertEquals(0, roomSession.getHintsUsed());
        assertNotNull(roomSession.getInventory());
        assertTrue(roomSession.getInventory().isEmpty());
        assertEquals(3, roomSession.getPuzzleSessions().size());
        assertTrue(roomSession.getStartTime() > 0);
        assertEquals(0, roomSession.getEndTime());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithNullRoom() {
        new RoomSession(null);
    }

    @Test
    public void testConstructorCreatesPuzzleSessions() {
        roomSession = new RoomSession(testRoom);
        
        ArrayList<PuzzleSession> puzzleSessions = roomSession.getPuzzleSessions();
        assertEquals(3, puzzleSessions.size());
        
        assertEquals("Puzzle 1", puzzleSessions.get(0).getPuzzleTitle());
        assertEquals("Puzzle 2", puzzleSessions.get(1).getPuzzleTitle());
        assertEquals("Puzzle 3", puzzleSessions.get(2).getPuzzleTitle());
    }

    @Test
    public void testConstructorWithNoPuzzles() {
        roomSession = new RoomSession(emptyRoom);
        
        assertEquals(0, roomSession.getPuzzleSessions().size());
        assertEquals(0, roomSession.getTotalPuzzles());
    }

    @Test
    public void testCollectItem() {
        roomSession = new RoomSession(testRoom);
        
        roomSession.collectItem("key");
        
        assertTrue(roomSession.hasItem("key"));
        assertEquals(1, roomSession.getInventory().size());
    }

    @Test
    public void testCollectItemNoDuplicates() {
        roomSession = new RoomSession(testRoom);
        
        roomSession.collectItem("key");
        roomSession.collectItem("key");
        
        assertEquals(1, roomSession.getInventory().size());
    }

    @Test
    public void testCollectItemIgnoresNull() {
        roomSession = new RoomSession(testRoom);
        
        roomSession.collectItem(null);
        
        assertTrue(roomSession.getInventory().isEmpty());
    }

    @Test
    public void testCollectMultipleItems() {
        roomSession = new RoomSession(testRoom);
        
        roomSession.collectItem("key");
        roomSession.collectItem("map");
        roomSession.collectItem("compass");
        
        assertEquals(3, roomSession.getInventory().size());
        assertTrue(roomSession.hasItem("key"));
        assertTrue(roomSession.hasItem("map"));
        assertTrue(roomSession.hasItem("compass"));
    }

    @Test
    public void testHasItemReturnsFalse() {
        roomSession = new RoomSession(testRoom);
        
        assertFalse(roomSession.hasItem("nonexistent"));
    }

    @Test
    public void testHasItemReturnsTrue() {
        roomSession = new RoomSession(testRoom);
        
        roomSession.collectItem("map");
        
        assertTrue(roomSession.hasItem("map"));
    }

    @Test
    public void testHasItemWithNull() {
        roomSession = new RoomSession(testRoom);
        
        assertFalse(roomSession.hasItem(null));
    }

    @Test
    public void testUseHintIncrementsTotal() {
        roomSession = new RoomSession(testRoom);
        
        roomSession.useHint("Puzzle 1");
        
        assertEquals(1, roomSession.getHintsUsed());
    }

    @Test
    public void testUseHintMultipleTimes() {
        roomSession = new RoomSession(testRoom);
        
        roomSession.useHint("Puzzle 1");
        roomSession.useHint("Puzzle 2");
        roomSession.useHint("Puzzle 1");
        
        assertEquals(3, roomSession.getHintsUsed());
    }

    @Test
    public void testUseHintCallsPuzzleSession() {
        roomSession = new RoomSession(testRoom);
        
        ArrayList<PuzzleSession> sessions = roomSession.getPuzzleSessions();
        
        roomSession.useHint("Puzzle 1");
        
        assertEquals(1, sessions.get(0).getNumHintsUsed());
        assertEquals(0, sessions.get(1).getNumHintsUsed());
    }

    @Test
    public void testUseHintCaseInsensitive() {
        roomSession = new RoomSession(testRoom);
        
        roomSession.useHint("PUZZLE 1");
        
        assertEquals(1, roomSession.getHintsUsed());
    }

    @Test
    public void testUseHintNonExistentPuzzle() {
        roomSession = new RoomSession(testRoom);
        
        roomSession.useHint("Non-existent Puzzle");
        
        assertEquals(1, roomSession.getHintsUsed());
    }

    @Test
    public void testComplete() {
        roomSession = new RoomSession(testRoom);
        
        roomSession.complete();
        
        assertTrue(roomSession.isCompleted());
        assertTrue(roomSession.getEndTime() > 0);
    }

    @Test
    public void testCompleteSetEndTime() throws InterruptedException {
        roomSession = new RoomSession(testRoom);
        long startTime = roomSession.getStartTime();
        
        Thread.sleep(10);
        
        roomSession.complete();
        
        assertTrue(roomSession.getEndTime() > startTime);
    }

    @Test
    public void testCompleteMultipleTimes() throws InterruptedException {
        roomSession = new RoomSession(testRoom);
        
        roomSession.complete();
        long firstEndTime = roomSession.getEndTime();
        
        Thread.sleep(10);
        
        roomSession.complete();
        
        assertTrue(roomSession.getEndTime() >= firstEndTime);
        assertTrue(roomSession.isCompleted());
    }

    @Test
    public void testCalculateScoreEasy() {
        roomSession = new RoomSession(testRoom);
        roomSession.setHintsUsed(0);
        
        int score = roomSession.calculateScore("Easy");
        
        assertEquals(10000, score);
    }

    @Test
    public void testCalculateScoreMedium() {
        roomSession = new RoomSession(testRoom);
        roomSession.setHintsUsed(0);
        
        int score = roomSession.calculateScore("Medium");
        
        assertEquals(15000, score);
    }

    @Test
    public void testCalculateScoreHard() {
        roomSession = new RoomSession(testRoom);
        roomSession.setHintsUsed(0);
        
        int score = roomSession.calculateScore("Hard");
        
        assertEquals(20000, score);
    }

    @Test
    public void testCalculateScoreWithHints() {
        roomSession = new RoomSession(testRoom);
        roomSession.setHintsUsed(5);
        
        int score = roomSession.calculateScore("Easy");
        
        assertEquals(9000, score);
    }

    @Test
    public void testCalculateScoreWithSolvedPuzzles() {
        roomSession = new RoomSession(testRoom);
        
        roomSession.getPuzzleSessions().get(0).setSolved(true);
        roomSession.getPuzzleSessions().get(1).setSolved(true);
        
        int score = roomSession.calculateScore("Easy");
        
        assertEquals(12000, score);
    }

    @Test
    public void testCalculateScoreComplex() {
        roomSession = new RoomSession(testRoom);
        
        roomSession.getPuzzleSessions().get(0).setSolved(true);
        roomSession.getPuzzleSessions().get(1).setSolved(true);
        roomSession.setHintsUsed(3);
        
        int score = roomSession.calculateScore("Medium");
        
        assertEquals(17100, score);
    }

    @Test
    public void testCalculateScoreUnknownDifficulty() {
        roomSession = new RoomSession(testRoom);
        roomSession.setHintsUsed(0);
        
        int score = roomSession.calculateScore("Unknown");
        
        assertEquals(10000, score);
    }

    @Test
    public void testCalculateScoreCaseInsensitive() {
        roomSession = new RoomSession(testRoom);
        roomSession.setHintsUsed(0);
        
        int scoreUpper = roomSession.calculateScore("HARD");
        int scoreLower = roomSession.calculateScore("hard");
        int scoreMixed = roomSession.calculateScore("HaRd");
        
        assertEquals(20000, scoreUpper);
        assertEquals(20000, scoreLower);
        assertEquals(20000, scoreMixed);
    }

    @Test
    public void testCalculateScoreNullDifficulty() {
        roomSession = new RoomSession(testRoom);
        roomSession.setHintsUsed(0);
        
        int score = roomSession.calculateScore(null);
        
        assertEquals(10000, score);
    }

    @Test
    public void testGetDurationWhileActive() throws InterruptedException {
        roomSession = new RoomSession(testRoom);
        
        Thread.sleep(1100);
        
        long duration = roomSession.getDuration();
        
        assertTrue(duration >= 1);
    }

    @Test
    public void testGetDurationAfterCompletion() throws InterruptedException {
        roomSession = new RoomSession(testRoom);
        
        Thread.sleep(1100);
        roomSession.complete();
        long duration1 = roomSession.getDuration();
        
        Thread.sleep(500);
        long duration2 = roomSession.getDuration();
        
        assertEquals(duration1, duration2);
    }

    @Test
    public void testGetDurationManualTimes() {
        roomSession = new RoomSession(testRoom);
        
        roomSession.setStartTime(1000000L);
        roomSession.setEndTime(1005000L);
        
        long duration = roomSession.getDuration();
        
        assertEquals(5, duration);
    }

    @Test
    public void testGetPuzzleSessionExisting() {
        roomSession = new RoomSession(testRoom);
        Puzzle puzzle1 = testRoom.getPuzzles().get(0);
        
        PuzzleSession session = roomSession.getPuzzleSession(puzzle1);
        
        assertNotNull(session);
        assertEquals("Puzzle 1", session.getPuzzleTitle());
    }

    @Test
    public void testGetPuzzleSessionConsistency() {
        roomSession = new RoomSession(testRoom);
        Puzzle puzzle1 = testRoom.getPuzzles().get(0);
        
        PuzzleSession session1 = roomSession.getPuzzleSession(puzzle1);
        PuzzleSession session2 = roomSession.getPuzzleSession(puzzle1);
        
        assertSame(session1, session2);
    }

    @Test
    public void testGetCompletionPercentZero() {
        roomSession = new RoomSession(testRoom);
        
        assertEquals(0, roomSession.getCompletionPercent());
    }

    @Test
    public void testGetCompletionPercentPartial() {
        roomSession = new RoomSession(testRoom);
        
        roomSession.getPuzzleSessions().get(0).setSolved(true);
        
        assertEquals(33, roomSession.getCompletionPercent());
    }

    @Test
    public void testGetCompletionPercentComplete() {
        roomSession = new RoomSession(testRoom);
        
        for (PuzzleSession ps : roomSession.getPuzzleSessions()) {
            ps.setSolved(true);
        }
        
        assertEquals(100, roomSession.getCompletionPercent());
    }

    @Test
    public void testGetCompletionPercentEmptyList() {
        roomSession = new RoomSession(emptyRoom);
        
        assertEquals(0, roomSession.getCompletionPercent());
    }

    @Test
    public void testGetSolvedCountInitial() {
        roomSession = new RoomSession(testRoom);
        
        assertEquals(0, roomSession.getSolvedCount());
    }

    @Test
    public void testGetSolvedCountWithSolvedPuzzles() {
        roomSession = new RoomSession(testRoom);
        
        roomSession.getPuzzleSessions().get(0).setSolved(true);
        roomSession.getPuzzleSessions().get(2).setSolved(true);
        
        assertEquals(2, roomSession.getSolvedCount());
    }

    @Test
    public void testGetTotalPuzzles() {
        roomSession = new RoomSession(testRoom);
        
        assertEquals(3, roomSession.getTotalPuzzles());
    }

    @Test
    public void testGetRoomId() {
        roomSession = new RoomSession(testRoom);
        
        assertEquals("room-123", roomSession.getRoomId());
    }

    @Test
    public void testGetRoomTitle() {
        roomSession = new RoomSession(testRoom);
        
        assertEquals("Test Room", roomSession.getRoomTitle());
    }

    @Test
    public void testSetStartTime() {
        roomSession = new RoomSession(testRoom);
        long newStartTime = 1000000L;
        
        roomSession.setStartTime(newStartTime);
        
        assertEquals(newStartTime, roomSession.getStartTime());
    }

    @Test
    public void testSetEndTime() {
        roomSession = new RoomSession(testRoom);
        long newEndTime = 2000000L;
        
        roomSession.setEndTime(newEndTime);
        
        assertEquals(newEndTime, roomSession.getEndTime());
    }

    @Test
    public void testSetCompleted() {
        roomSession = new RoomSession(testRoom);
        
        roomSession.setCompleted(true);
        
        assertTrue(roomSession.isCompleted());
    }

    @Test
    public void testSetHintsUsed() {
        roomSession = new RoomSession(testRoom);
        
        roomSession.setHintsUsed(10);
        
        assertEquals(10, roomSession.getHintsUsed());
    }

    @Test
    public void testSetInventory() {
        roomSession = new RoomSession(testRoom);
        ArrayList<String> newInventory = new ArrayList<String>();
        newInventory.add("sword");
        newInventory.add("shield");
        
        roomSession.setInventory(newInventory);
        
        assertEquals(2, roomSession.getInventory().size());
        assertTrue(roomSession.hasItem("sword"));
        assertTrue(roomSession.hasItem("shield"));
    }

    @Test
    public void testSetPuzzleSessions() {
        roomSession = new RoomSession(testRoom);
        ArrayList<PuzzleSession> newSessions = new ArrayList<PuzzleSession>();
        
        roomSession.setPuzzleSessions(newSessions);
        
        assertTrue(roomSession.getPuzzleSessions().isEmpty());
    }

    @Test
    public void testToString() {
        roomSession = new RoomSession(testRoom);
        
        String result = roomSession.toString();
        
        assertNotNull(result);
        assertTrue(result.contains("Test Room"));
        assertTrue(result.contains("completed=false"));
        assertTrue(result.contains("hintsUsed=0"));
        assertTrue(result.contains("progress=0%"));
    }

    @Test
    public void testToStringCompleted() {
        roomSession = new RoomSession(testRoom);
        roomSession.complete();
        
        String result = roomSession.toString();
        
        assertTrue(result.contains("completed=true"));
    }

    @Test
    public void testToStringWithProgress() {
        roomSession = new RoomSession(testRoom);
        roomSession.getPuzzleSessions().get(0).setSolved(true);
        
        String result = roomSession.toString();
        
        assertTrue(result.contains("progress=33%"));
    }
}