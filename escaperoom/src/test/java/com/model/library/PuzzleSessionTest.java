package com.model.library;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import com.model.*;

public class PuzzleSessionTest {

    private PuzzleSession puzzleSession;
    private static final String TEST_PUZZLE_TITLE = "Test Puzzle";

    @Before
    public void setUp() {
        puzzleSession = new PuzzleSession(TEST_PUZZLE_TITLE);
    }

    @Test
    public void testConstructor() {
        assertNotNull(puzzleSession);
        assertEquals(TEST_PUZZLE_TITLE, puzzleSession.getPuzzleTitle());
        assertEquals(0, puzzleSession.getNumHintsUsed());
        assertFalse(puzzleSession.isSolved());
        assertNull(puzzleSession.getFinalAnswer());
        assertTrue(puzzleSession.getTimeStarted() > 0);
        assertTrue(puzzleSession.getTimeEnded() > 0);
    }

    @Test
    public void testConstructorTimes() {
        long beforeCreation = System.currentTimeMillis();
        PuzzleSession session = new PuzzleSession("New Puzzle");
        long afterCreation = System.currentTimeMillis();
        
        assertTrue(session.getTimeStarted() >= beforeCreation);
        assertTrue(session.getTimeStarted() <= afterCreation);
        assertTrue(session.getTimeEnded() >= beforeCreation);
        assertTrue(session.getTimeEnded() <= afterCreation);
    }

    @Test
    public void testConstructorEmptyTitle() {
        PuzzleSession session = new PuzzleSession("");
        
        assertEquals("", session.getPuzzleTitle());
        assertFalse(session.isSolved());
    }

    @Test
    public void testConstructorNullTitle() {
        PuzzleSession session = new PuzzleSession(null);
        
        assertNull(session.getPuzzleTitle());
        assertFalse(session.isSolved());
    }

    @Test
    public void testUseHint() {
        puzzleSession.useHint();
        
        assertEquals(1, puzzleSession.getNumHintsUsed());
    }

    @Test
    public void testUseHintMultipleTimes() {
        puzzleSession.useHint();
        puzzleSession.useHint();
        puzzleSession.useHint();
        
        assertEquals(3, puzzleSession.getNumHintsUsed());
    }

    @Test
    public void testUseHintFromZero() {
        assertEquals(0, puzzleSession.getNumHintsUsed());
        
        puzzleSession.useHint();
        
        assertEquals(1, puzzleSession.getNumHintsUsed());
    }

    @Test
    public void testUseHintAfterSolved() {
        puzzleSession.setSolved(true);
        
        puzzleSession.useHint();
        
        assertEquals(1, puzzleSession.getNumHintsUsed());
        assertTrue(puzzleSession.isSolved());
    }

    @Test
    public void testMarkSolved() {
        puzzleSession.markSolved("correct answer");
        
        assertTrue(puzzleSession.isSolved());
    }

    @Test
    public void testMarkSolvedSetAnswer() {
        puzzleSession.markSolved("42");
        
        assertEquals("42", puzzleSession.getFinalAnswer());
    }

    @Test
    public void testMarkSolvedUpdateEndTime() throws InterruptedException {
        long initialEndTime = puzzleSession.getTimeEnded();
        
        Thread.sleep(10);
        puzzleSession.markSolved("answer");
        
        assertTrue(puzzleSession.getTimeEnded() > initialEndTime);
    }

    @Test
    public void testMarkSolvedNullAnswer() {
        puzzleSession.markSolved(null);
        
        assertTrue(puzzleSession.isSolved());
        assertNull(puzzleSession.getFinalAnswer());
    }

    @Test
    public void testMarkSolvedEmptyAnswer() {
        puzzleSession.markSolved("");
        
        assertTrue(puzzleSession.isSolved());
        assertEquals("", puzzleSession.getFinalAnswer());
    }

    @Test
    public void testMarkSolvedMultipleTimes() throws InterruptedException {
        puzzleSession.markSolved("first answer");
        long firstEndTime = puzzleSession.getTimeEnded();
        
        Thread.sleep(10);
        puzzleSession.markSolved("second answer");
        
        assertTrue(puzzleSession.isSolved());
        assertEquals("second answer", puzzleSession.getFinalAnswer());
        assertTrue(puzzleSession.getTimeEnded() > firstEndTime);
    }

    @Test
    public void testMarkSolvedEndTimeAfterStart() {
        long startTime = puzzleSession.getTimeStarted();
        
        puzzleSession.markSolved("answer");
        
        assertTrue(puzzleSession.getTimeEnded() >= startTime);
    }

    @Test
    public void testIsSolvedInitial() {
        assertFalse(puzzleSession.isSolved());
    }

    @Test
    public void testIsSolvedAfterMark() {
        puzzleSession.markSolved("answer");
        
        assertTrue(puzzleSession.isSolved());
    }

    @Test
    public void testIsSolvedAfterSet() {
        puzzleSession.setSolved(true);
        
        assertTrue(puzzleSession.isSolved());
    }

    @Test
    public void testSetSolvedFalse() {
        puzzleSession.setSolved(true);
        puzzleSession.setSolved(false);
        
        assertFalse(puzzleSession.isSolved());
    }

    @Test
    public void testSetSolvedDoesNotAffectAnswer() {
        puzzleSession.setFinalAnswer("test answer");
        
        puzzleSession.setSolved(true);
        
        assertEquals("test answer", puzzleSession.getFinalAnswer());
    }

    @Test
    public void testGetFinalAnswerInitial() {
        assertNull(puzzleSession.getFinalAnswer());
    }

    @Test
    public void testSetFinalAnswer() {
        puzzleSession.setFinalAnswer("my answer");
        
        assertEquals("my answer", puzzleSession.getFinalAnswer());
    }

    @Test
    public void testSetFinalAnswerNull() {
        puzzleSession.setFinalAnswer("something");
        puzzleSession.setFinalAnswer(null);
        
        assertNull(puzzleSession.getFinalAnswer());
    }

    @Test
    public void testSetFinalAnswerEmpty() {
        puzzleSession.setFinalAnswer("");
        
        assertEquals("", puzzleSession.getFinalAnswer());
    }

    @Test
    public void testSetFinalAnswerDoesNotSolve() {
        puzzleSession.setFinalAnswer("answer");
        
        assertFalse(puzzleSession.isSolved());
    }

    @Test
    public void testSetFinalAnswerMultipleTimes() {
        puzzleSession.setFinalAnswer("first");
        puzzleSession.setFinalAnswer("second");
        puzzleSession.setFinalAnswer("third");
        
        assertEquals("third", puzzleSession.getFinalAnswer());
    }

    @Test
    public void testGetPuzzleTitle() {
        assertEquals(TEST_PUZZLE_TITLE, puzzleSession.getPuzzleTitle());
    }

    @Test
    public void testGetPuzzleTitleExact() {
        PuzzleSession session = new PuzzleSession("Complex Puzzle Name 123!");
        
        assertEquals("Complex Puzzle Name 123!", session.getPuzzleTitle());
    }

    @Test
    public void testGetNumHintsUsedInitial() {
        assertEquals(0, puzzleSession.getNumHintsUsed());
    }

    @Test
    public void testGetNumHintsUsedAfterHints() {
        puzzleSession.useHint();
        puzzleSession.useHint();
        
        assertEquals(2, puzzleSession.getNumHintsUsed());
    }

    @Test
    public void testGetNumHintsUsedLargeNumber() {
        for (int i = 0; i < 100; i++) {
            puzzleSession.useHint();
        }
        
        assertEquals(100, puzzleSession.getNumHintsUsed());
    }

    @Test
    public void testGetTimeStarted() {
        long startTime = puzzleSession.getTimeStarted();
        
        assertTrue(startTime > 0);
        assertTrue(startTime <= System.currentTimeMillis());
    }

    @Test
    public void testGetTimeEnded() {
        long endTime = puzzleSession.getTimeEnded();
        
        assertTrue(endTime > 0);
        assertTrue(endTime <= System.currentTimeMillis());
    }

    @Test
    public void testGetTimeEndedAfterMarkSolved() throws InterruptedException {
        long initialEndTime = puzzleSession.getTimeEnded();
        
        Thread.sleep(50);
        puzzleSession.markSolved("answer");
        long updatedEndTime = puzzleSession.getTimeEnded();
        
        assertTrue(updatedEndTime > initialEndTime);
    }

    @Test
    public void testTimeStartedBeforeTimeEnded() {
        assertTrue(puzzleSession.getTimeStarted() <= puzzleSession.getTimeEnded());
    }

    @Test
    public void testCompletePuzzleSolvingScenario() {
        assertEquals(0, puzzleSession.getNumHintsUsed());
        assertFalse(puzzleSession.isSolved());
        assertNull(puzzleSession.getFinalAnswer());
        
        puzzleSession.useHint();
        puzzleSession.useHint();
        assertEquals(2, puzzleSession.getNumHintsUsed());
        
        puzzleSession.setFinalAnswer("wrong");
        assertFalse(puzzleSession.isSolved());
        
        puzzleSession.markSolved("correct");
        
        assertTrue(puzzleSession.isSolved());
        assertEquals("correct", puzzleSession.getFinalAnswer());
        assertEquals(2, puzzleSession.getNumHintsUsed());
        assertTrue(puzzleSession.getTimeEnded() > puzzleSession.getTimeStarted());
    }

    @Test
    public void testSolvingWithoutHints() {
        puzzleSession.markSolved("genius answer");
        
        assertTrue(puzzleSession.isSolved());
        assertEquals(0, puzzleSession.getNumHintsUsed());
        assertEquals("genius answer", puzzleSession.getFinalAnswer());
    }

    @Test
    public void testMultipleAttempts() {
        puzzleSession.setFinalAnswer("attempt1");
        assertFalse(puzzleSession.isSolved());
        
        puzzleSession.useHint();
        puzzleSession.setFinalAnswer("attempt2");
        assertFalse(puzzleSession.isSolved());
        
        puzzleSession.useHint();
        puzzleSession.markSolved("correct");
        
        assertTrue(puzzleSession.isSolved());
        assertEquals(2, puzzleSession.getNumHintsUsed());
        assertEquals("correct", puzzleSession.getFinalAnswer());
    }

    @Test
    public void testStateConsistency() {
        puzzleSession.useHint();
        puzzleSession.setFinalAnswer("test");
        puzzleSession.setSolved(true);
        
        assertEquals(1, puzzleSession.getNumHintsUsed());
        assertEquals("test", puzzleSession.getFinalAnswer());
        assertTrue(puzzleSession.isSolved());
        assertEquals(TEST_PUZZLE_TITLE, puzzleSession.getPuzzleTitle());
        
        puzzleSession.useHint();
        
        assertEquals(2, puzzleSession.getNumHintsUsed());
        assertEquals("test", puzzleSession.getFinalAnswer());
        assertTrue(puzzleSession.isSolved());
        assertEquals(TEST_PUZZLE_TITLE, puzzleSession.getPuzzleTitle());
    }

    @Test
    public void testMarkSolvedThenUnsolved() {
        puzzleSession.markSolved("answer");
        String finalAnswer = puzzleSession.getFinalAnswer();
        long endTime = puzzleSession.getTimeEnded();
        
        puzzleSession.setSolved(false);
        
        assertFalse(puzzleSession.isSolved());
        assertEquals(finalAnswer, puzzleSession.getFinalAnswer());
        assertEquals(endTime, puzzleSession.getTimeEnded());
    }

    @Test
    public void testVeryLongPuzzleTitle() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 1000; i++) {
            sb.append("A");
        }
        String longTitle = sb.toString();
        PuzzleSession session = new PuzzleSession(longTitle);
        
        assertEquals(longTitle, session.getPuzzleTitle());
        assertFalse(session.isSolved());
    }

    @Test
    public void testSpecialCharactersInTitle() {
        String specialTitle = "Puzzle @#$%^&*() with numbers and text";
        PuzzleSession session = new PuzzleSession(specialTitle);
        
        assertEquals(specialTitle, session.getPuzzleTitle());
    }

    @Test
    public void testSpecialCharactersInAnswer() {
        String specialAnswer = "Answer with @#$%^&*() and numbers";
        
        puzzleSession.markSolved(specialAnswer);
        
        assertEquals(specialAnswer, puzzleSession.getFinalAnswer());
        assertTrue(puzzleSession.isSolved());
    }
}