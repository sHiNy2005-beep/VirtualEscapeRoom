package com.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;

import org.junit.Test;

import com.model.ItemPuzzle;

public class ItemPuzzleTest {
 
    @Test
    public void TestTesting() 
    {
        assertTrue(true);
    }

    @Test
    public void addRequiredItemTest()
    {
        ItemPuzzle puzzle = new ItemPuzzle("Harmony's Disguise", "You can't see this instrument, but when you play it, everyone hears it", "voice");
        ArrayList<String> requiredItems = new ArrayList<>();
        puzzle.addRequiredItem("microphone");
        boolean ItemExists = requiredItems.contains("microphone");
        assertTrue(ItemExists);
    }

    @Test
    public void getRequiredItemsTest()
    {
        ItemPuzzle puzzle = new ItemPuzzle("Harmony's Disguise", "You can't see this instrument, but when you play it, everyone hears it", "voice");
        ArrayList<String> requiredItems = puzzle.getRequiredItems();
        puzzle.addRequiredItem("speaker");
        puzzle.addRequiredItem("microphone");
        assertEquals(2, requiredItems.size());
        
    }

    @Test
    public void checkAnswerTest()
    {
        ItemPuzzle puzzle = new ItemPuzzle("Harmony's Disguise", "You can't see this instrument, but when you play it, everyone hears it", "voice");
        boolean Answer = puzzle.checkAnswer("broom");
        assertFalse(Answer);
        boolean CorrectAnswer = puzzle.checkAnswer("VOICE".toLowerCase());
        assertTrue(CorrectAnswer);
    }
}
