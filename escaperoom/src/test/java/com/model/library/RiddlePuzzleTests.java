package com.model.library;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.List;

public class RiddlePuzzleTests {

    @Test 
    public void riddlePuzzleSanity() {
        assertTrue(true);
    }

    @Test
    public void testBetrayalRiddleConstructionAndAnswer() {
        String title = "The Betrayal Riddle";
        String description = "What exists when one person has it but ceases to exist when another person gets it?";
        String solution = "secret";

        RiddlePuzzle p = new RiddlePuzzle(title, description, solution);

        assertFalse(p.isSolved());
        assertTrue(p.checkAnswer("secret"));
        assertTrue(p.isSolved());
    }

    @Test
    public void testBetrayalRiddleHintsFromJson() {
        RiddlePuzzle p = new RiddlePuzzle(
                "The Betrayal Riddle",
                "What exists when one person has it but ceases to exist when another person gets it?",
                "secret"
        );

        p.addHint("It's something intangible.");
        p.addHint("It's often shared in whispers.");

        List<String> hints = p.getHints();
        assertNotNull(hints);
        assertEquals(2, hints.size());
        assertEquals("It's something intangible.", hints.get(0));
        assertEquals("It's often shared in whispers.", hints.get(1));
    }

    @Test
    public void testWrongAnswerDoesNotSolve() {
        RiddlePuzzle p = new RiddlePuzzle(
                "The Betrayal Riddle",
                "What exists when one person has it but ceases to exist when another person gets it?",
                "secret"
        );

        assertFalse(p.checkAnswer("not it"));
        assertFalse(p.checkAnswer(null));
        assertFalse(p.isSolved());
    }
}
