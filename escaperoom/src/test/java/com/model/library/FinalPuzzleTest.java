package com.model.library;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;

import org.junit.Test;

import com.model.FinalPuzzle;
import com.model.Puzzle;

public class FinalPuzzleTest {
    
    //by Murewa Adebajo

    @Test
    public void TestTesting() 
    {
        assertTrue(true);
    }

    @Test
    public void checkAnswerTest()
    {
        ArrayList<String> left = new ArrayList<>();
        ArrayList<String> right = new ArrayList<>();
        Puzzle TestPuzzle = new FinalPuzzle("Test Puzzle", "Match the pairs", left, right);

        left.add("A");
        left.add("B");
        left.add("C");

        right.add("1");
        right.add("2");
        right.add("3");

        assertTrue(TestPuzzle.checkAnswer("A=1,B=2,C=3"));
        assertFalse(TestPuzzle.checkAnswer("A=0,B=0,C=0"));
        assertFalse(TestPuzzle.checkAnswer(null));
        assertFalse(TestPuzzle.checkAnswer(""));
    }

    @Test
    public void getCorrectPairsTest()
    {
        ArrayList<String> left = new ArrayList<>();
        ArrayList<String> right = new ArrayList<>();
        FinalPuzzle TestPuzzle = new FinalPuzzle("Test Puzzle", "Match the pairs", left, right);

        left.add("X");
        left.add("Y");
        left.add("Z");

        right.add("10");
        right.add("20");
        right.add("30");

        assertEquals("10", TestPuzzle.getCorrectPairs().get("x"));
        assertEquals("20", TestPuzzle.getCorrectPairs().get("y"));
        assertEquals("30", TestPuzzle.getCorrectPairs().get("z"));
    }

    @Test
    public void asMapTest()
    {
        ArrayList<String> left = new ArrayList<>();
        ArrayList<String> right = new ArrayList<>();
        FinalPuzzle TestPuzzle = new FinalPuzzle("Test Puzzle", "Match the pairs", left, right);

        left.add("M");
        left.add("N");
        left.add("O");

        right.add("100");
        right.add("200");
        right.add("300");

        assertEquals(3, TestPuzzle.getCorrectPairs().size());
    }   

}
