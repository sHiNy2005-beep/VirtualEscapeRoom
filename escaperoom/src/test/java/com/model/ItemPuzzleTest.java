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
        boolean hasItem = requiredItems.contains("microphone");
        boolean doesNotHaveItem = requiredItems.contains("shoes");
        assertTrue(hasItem);
        assertFalse(doesNotHaveItem);
    }

    @Test
    public void getRequiredItemsTest()
    {
        ItemPuzzle puzzle = new ItemPuzzle("Harmony's Disguise", "You can't see this instrument, but when you play it, everyone hears it", "voice");
        ArrayList<String> requiredItems = puzzle.getRequiredItems();
        puzzle.addRequiredItem("speaker");
        puzzle.addRequiredItem("microphone");
        boolean ItemsExists = requiredItems.contains("speaker") && requiredItems.contains("microphone");
        assertEquals(2, requiredItems.size());
        assertEquals("speaker", puzzle.getRequiredItems().get(0));
        assertEquals("microphone", puzzle.getRequiredItems().get(1));
        assertTrue(ItemsExists);
        
    }

    @Test
    public void checkAnswerTest()
    {
        ItemPuzzle puzzle = new ItemPuzzle("Harmony's Disguise", "You can't see this instrument, but when you play it, everyone hears it", "voice");
        boolean Answer = puzzle.checkAnswer("broom");
        boolean CorrectAnswer = puzzle.checkAnswer("VOICE".toLowerCase());
         assertFalse(Answer);
        assertTrue(CorrectAnswer);
    }
}
