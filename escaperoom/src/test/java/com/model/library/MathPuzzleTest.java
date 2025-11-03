package com.model.library;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import com.model.MathPuzzle;

public class MathPuzzleTest {
    
    // Constructor tests
    @Test
    public void testConstructor_StoresCorrectValues() {
        MathPuzzle puzzle = new MathPuzzle("Addition Test", "What is 5 + 3?", 8);
        assertEquals("Addition Test", puzzle.getTitle());
        assertEquals("What is 5 + 3?", puzzle.getDescription());
        assertEquals(8, puzzle.getSolutionCode());
    }
    
    @Test
    public void testConstructor_SetsSolutionAsString() {
        MathPuzzle puzzle = new MathPuzzle("Addition Test", "What is 5 + 3?", 8);
        assertEquals("8", puzzle.getSolution());
    }
    
    @Test
    public void testConstructor_WithNegativeSolution() {
        MathPuzzle negativePuzzle = new MathPuzzle("Negative", "Test", -42);
        assertEquals(-42, negativePuzzle.getSolutionCode());
        assertEquals("-42", negativePuzzle.getSolution());
    }
    
    @Test
    public void testConstructor_WithZeroSolution() {
        MathPuzzle zeroPuzzle = new MathPuzzle("Zero", "Test", 0);
        assertEquals(0, zeroPuzzle.getSolutionCode());
        assertEquals("0", zeroPuzzle.getSolution());
    }
    
    @Test
    public void testCheckAnswerInt_CorrectAnswer_ReturnsTrue() {
        MathPuzzle puzzle = new MathPuzzle("Addition Test", "What is 5 + 3?", 8);
        assertTrue(puzzle.checkAnswer(8));
    }
    
    @Test
    public void testCheckAnswerInt_CorrectAnswer_MarksPuzzleAsSolved() {
        MathPuzzle puzzle = new MathPuzzle("Addition Test", "What is 5 + 3?", 8);
        assertFalse(puzzle.isSolved());
        puzzle.checkAnswer(8);
        assertTrue(puzzle.isSolved());
    }
    
    @Test
    public void testCheckAnswerInt_IncorrectAnswer_ReturnsFalse() {
        MathPuzzle puzzle = new MathPuzzle("Addition Test", "What is 5 + 3?", 8);
        assertFalse(puzzle.checkAnswer(7));
        assertFalse(puzzle.checkAnswer(9));
    }
    
    @Test
    public void testCheckAnswerInt_IncorrectAnswer_DoesNotMarkAsSolved() {
        MathPuzzle puzzle = new MathPuzzle("Addition Test", "What is 5 + 3?", 8);
        puzzle.checkAnswer(7);
        assertFalse(puzzle.isSolved());
    }
    
    @Test
    public void testCheckAnswerInt_WithNegativeSolution() {
        MathPuzzle negativePuzzle = new MathPuzzle("Negative", "Test", -10);
        assertTrue(negativePuzzle.checkAnswer(-10));
        assertFalse(negativePuzzle.checkAnswer(10));
    }
    
    // checkAnswer(String) tests
    @Test
    public void testCheckAnswerString_CorrectAnswer_ReturnsTrue() {
        MathPuzzle puzzle = new MathPuzzle("Addition Test", "What is 5 + 3?", 8);
        assertTrue(puzzle.checkAnswer("8"));
    }
    
    @Test
    public void testCheckAnswerString_CorrectAnswer_MarksPuzzleAsSolved() {
        MathPuzzle puzzle = new MathPuzzle("Addition Test", "What is 5 + 3?", 8);
        assertFalse(puzzle.isSolved());
        puzzle.checkAnswer("8");
        assertTrue(puzzle.isSolved());
    }
    
    @Test
    public void testCheckAnswerString_IncorrectAnswer_ReturnsFalse() {
        MathPuzzle puzzle = new MathPuzzle("Addition Test", "What is 5 + 3?", 8);
        assertFalse(puzzle.checkAnswer("7"));
        assertFalse(puzzle.checkAnswer("100"));
    }
    
    @Test
    public void testCheckAnswerString_WithLeadingWhitespace() {
        MathPuzzle puzzle = new MathPuzzle("Addition Test", "What is 5 + 3?", 8);
        assertTrue(puzzle.checkAnswer("  8"));
    }
    
    @Test
    public void testCheckAnswerString_WithTrailingWhitespace() {
        MathPuzzle puzzle = new MathPuzzle("Addition Test", "What is 5 + 3?", 8);
        assertTrue(puzzle.checkAnswer("8  "));
    }
    
    @Test
    public void testCheckAnswerString_WithLeadingAndTrailingWhitespace() {
        MathPuzzle puzzle = new MathPuzzle("Addition Test", "What is 5 + 3?", 8);
        assertTrue(puzzle.checkAnswer("  8  "));
    }
    
    @Test
    public void testCheckAnswerString_WithNegativeNumber() {
        MathPuzzle negativePuzzle = new MathPuzzle("Negative", "Test", -5);
        assertTrue(negativePuzzle.checkAnswer("-5"));
    }
    
    @Test
    public void testCheckAnswerString_NonNumericString_ReturnsFalse() {
        MathPuzzle puzzle = new MathPuzzle("Addition Test", "What is 5 + 3?", 8);
        assertFalse(puzzle.checkAnswer("abc"));
        assertFalse(puzzle.checkAnswer("eight"));
    }
    
    @Test
    public void testCheckAnswerString_EmptyString_ReturnsFalse() {
        MathPuzzle puzzle = new MathPuzzle("Addition Test", "What is 5 + 3?", 8);
        assertFalse(puzzle.checkAnswer(""));
    }
    
    @Test
    public void testCheckAnswerString_WhitespaceOnly_ReturnsFalse() {
        MathPuzzle puzzle = new MathPuzzle("Addition Test", "What is 5 + 3?", 8);
        assertFalse(puzzle.checkAnswer("   "));
    }
    
    @Test
    public void testCheckAnswerString_DecimalNumber_ReturnsFalse() {
        MathPuzzle puzzle = new MathPuzzle("Addition Test", "What is 5 + 3?", 8);
        assertFalse(puzzle.checkAnswer("8.0"));
        assertFalse(puzzle.checkAnswer("8.5"));
    }
    
    @Test
    public void testCheckAnswerString_IntegerOverflow_ReturnsFalse() {
        MathPuzzle puzzle = new MathPuzzle("Addition Test", "What is 5 + 3?", 8);
        assertFalse(puzzle.checkAnswer("999999999999999999999999"));
    }
    
    @Test
    public void testCheckAnswerString_MixedContent_ReturnsFalse() {
        MathPuzzle puzzle = new MathPuzzle("Addition Test", "What is 5 + 3?", 8);
        assertFalse(puzzle.checkAnswer("8a"));
        assertFalse(puzzle.checkAnswer("a8"));
        assertFalse(puzzle.checkAnswer("8 8"));
    }
    
    @Test
    public void testCheckAnswerString_NonNumericDoesNotMarkAsSolved() {
        MathPuzzle puzzle = new MathPuzzle("Addition Test", "What is 5 + 3?", 8);
        puzzle.checkAnswer("invalid");
        assertFalse(puzzle.isSolved());
    }
    
    // getSolutionCode tests
    @Test
    public void testGetSolutionCode_ReturnsCorrectValue() {
        MathPuzzle puzzle = new MathPuzzle("Addition Test", "What is 5 + 3?", 8);
        assertEquals(8, puzzle.getSolutionCode());
    }
    
    @Test
    public void testGetSolutionCode_WithLargeNumber() {
        MathPuzzle largePuzzle = new MathPuzzle("Large", "Test", Integer.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE, largePuzzle.getSolutionCode());
    }
    
    @Test
    public void testGetSolutionCode_WithMinimumInteger() {
        MathPuzzle minPuzzle = new MathPuzzle("Min", "Test", Integer.MIN_VALUE);
        assertEquals(Integer.MIN_VALUE, minPuzzle.getSolutionCode());
    }
    
    // Integration tests
    @Test
    public void testMultipleIncorrectAttempts_BeforeCorrectAnswer() {
        MathPuzzle puzzle = new MathPuzzle("Addition Test", "What is 5 + 3?", 8);
        assertFalse(puzzle.checkAnswer(1));
        assertFalse(puzzle.isSolved());
        assertFalse(puzzle.checkAnswer("wrong"));
        assertFalse(puzzle.isSolved());
        assertTrue(puzzle.checkAnswer(8));
        assertTrue(puzzle.isSolved());
    }
    
    @Test
    public void testCorrectAnswerRemainsSolvedAfterIncorrectAttempt() {
        MathPuzzle puzzle = new MathPuzzle("Addition Test", "What is 5 + 3?", 8);
        assertTrue(puzzle.checkAnswer(8));
        assertTrue(puzzle.isSolved());
        assertFalse(puzzle.checkAnswer(7));
        assertTrue(puzzle.isSolved());
    }
    
    @Test
    public void testStringAndIntCheckAnswerBothWork() {
        MathPuzzle puzzle1 = new MathPuzzle("Test1", "Desc1", 42);
        MathPuzzle puzzle2 = new MathPuzzle("Test2", "Desc2", 42);
        
        assertTrue(puzzle1.checkAnswer(42));
        assertTrue(puzzle2.checkAnswer("42"));
        assertTrue(puzzle1.isSolved());
        assertTrue(puzzle2.isSolved());
    }
}