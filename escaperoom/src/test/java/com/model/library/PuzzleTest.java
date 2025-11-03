package com.model.library;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import com.model.Puzzle;

public class PuzzleTest {
    
    @Test
    public void TestTesting() 
    {
        assertTrue(true);
    }

    @Test
    public void checkAnswerTest() 
    {
        Puzzle puzzle = new Puzzle("Test Puzzle", "This is a test puzzle", "Test Answer");
        boolean Answer1 = puzzle.checkAnswer("Wrong Answer");
        boolean Answer2 = puzzle.checkAnswer("Test Answer");
        assertFalse(Answer1);
        assertTrue(Answer2);
    }

    @Test
    public void addHintTest() 
    {
        Puzzle puzzle = new Puzzle("Test Puzzle", "This is a test puzzle", "Test Answer");
        ArrayList<String> hints = puzzle.getHints();
        puzzle.addHint("This is hint 1");
        puzzle.addHint("This is hint 2");
        assertEquals(2, hints.size());
    }

    @Test
    public void getHintsTest() 
    {
        Puzzle puzzle = new Puzzle("Test Puzzle", "This is a test puzzle", "Test Answer");
        ArrayList<String> hints = puzzle.getHints();
        puzzle.addHint("This is hint A");
        puzzle.addHint("This is hint B");
        boolean AllHints = puzzle.getHints().contains("This is hint A") && puzzle.getHints().contains("This is hint B");
        assertTrue(AllHints);
    }

    @Test
    public void getTitleTest() 
    {
        Puzzle puzzle = new Puzzle("Test Puzzle", "This is a test puzzle", "Test Answer");
        assertEquals("Test Puzzle", puzzle.getTitle());
    }

    @Test
    public void getDescriptionTest() 
    {
        Puzzle puzzle = new Puzzle("Test Puzzle", "This is a test puzzle", "Test Answer");
        assertEquals("This is a test puzzle", puzzle.getDescription());
    }

    @Test
    public void isSolvedTest() 
    {
        Puzzle puzzle = new Puzzle("Test Puzzle", "This is a test puzzle", "Test Answer");
        puzzle.checkAnswer("Test Answer");
        assertTrue(puzzle.isSolved());
    }

    @Test
    public void getSolutionTest() 
    {
        Puzzle puzzle = new Puzzle("Test Puzzle", "This is a test puzzle", "Test Answer");
        assertEquals("Test Answer", puzzle.getSolution());
    }

    

}
