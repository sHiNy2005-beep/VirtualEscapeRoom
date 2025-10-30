package com.model.library;

import org.junit.Test;

import com.model.CodePuzzle;

import static org.junit.Assert.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

//Mashal Shami

public class CodePuzzleTests {

     @Test
    public void codePuzzleTests(){
        assertTrue(true);
    }

    @Test
    public void testCorrectAnswer() {
        CodePuzzle puzzle = new CodePuzzle(
            "Hidden Will Cipher",
            "Decode the hidden message in Hamton's will.",
            "THOMASISDISOWNED"
        );
        assertTrue("Correct answer should be correct", puzzle.checkAnswer("THOMASISDISOWNED"));
    }

    @Test
    public void testIncorrectAnswer() {
        CodePuzzle puzzle = new CodePuzzle(
            "Hidden Will Cipher",
            "Decode the hidden message in Hamton's will.",
            "THOMASISDISOWNED"
        );
        assertFalse(puzzle.checkAnswer("INCORRECT"));
    }

    
    @Test
    public void testCaseInsenstive() {
        CodePuzzle puzzle = new CodePuzzle(
            "Hidden Will Cipher",
            "Decode the hidden message in Hamton's will.",
            "THOMASISDISOWNED"
        );
        assertTrue(puzzle.checkAnswer("thomasisdisowned"));
        assertTrue(puzzle.checkAnswer("ThomasIsDisowned"));
    }

    //trim in checkAnswer in puzzle class
    @Test
    public void testSpacesAtEnd() {
        CodePuzzle puzzle = new CodePuzzle(
            "Hidden Will Cipher",
            "Decode the hidden message in Hamton's will.",
            "THOMASISDISOWNED"
        );
        assertTrue(puzzle.checkAnswer("  THOMASISDISOWNED"));
        assertTrue(puzzle.checkAnswer("THOMASISDISOWNED  "));
        assertTrue(puzzle.checkAnswer("  THOMASISDISOWNED  "));
    }

    //possible issue,bug
    @Test
    public void testSpacesInMiddle() {
    CodePuzzle puzzle = new CodePuzzle(
        "Hidden Will Cipher",
        "Decode the hidden message in Hamton's will.",
        "THOMASISDISOWNED"
    );
   
    assertFalse("Answer with spaces in middle should be incorrect",
    puzzle.checkAnswer("THOMAS IS DISOWNED"));
}

    
    @Test
    public void testNullandEmpty() {
        CodePuzzle puzzle = new CodePuzzle(
            "Hidden Will Cipher",
            "Decode the hidden message in Hamton's will.",
            "THOMASISDISOWNED"
        );
        assertFalse(puzzle.checkAnswer(""));
        assertFalse(puzzle.checkAnswer(null));
    }

    //possible issue,bug but depends on how we want to handle special characters
    @Test
    public void testSpecialCharacters() {
        CodePuzzle puzzle = new CodePuzzle(
            "Hidden Will Cipher",
            "Decode the hidden message in Hamton's will.",
            "THOMASISDISOWNED"
        );
        assertFalse(puzzle.checkAnswer("THOMAS-IS-DISOWNED"));
        assertFalse(puzzle.checkAnswer("THOMAS_IS_DISOWNED"));
        assertFalse(puzzle.checkAnswer("THOMAS@IS#DISOWNED"));
    }

    
}
