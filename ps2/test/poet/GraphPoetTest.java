/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package poet;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

/**
 * Tests for GraphPoet.
 */
public class GraphPoetTest {

    private final String parentFolder = "ps2/src/poet/"; // a path to the folder containing files

    // Testing strategy

    // Testing strategy for input string to poem() and GraphPoet() creator:
    // Partition on number of consecutive whitespace: single, multiple
    // Partition on presence of non-alphanumeric characters: present, absent

    // Testing strategy for input string to poem()
    // Partition on number of bridge words inserted: zero, one, more than one.
    // Partition on location of bride word: start, middle , end.

    @Test(expected = AssertionError.class)
    public void testAssertionsEnabled() {
	assert false; // make sure assertions are enabled with VM argument: -ea
    }

    // alphanumeric characters are present, single whitespace
    // multiple bridge words are added
    // bridge words are added at middle, end.
    @Test
    public void testpoem1() {

	String fileName = "star-trek.txt";
	String input = "Seek to explore new and exciting synergies!";
	String expectedResult = "Seek to explore strange new life and exciting synergies!";
	testGraphPoemHelper(parentFolder + fileName, input, expectedResult);

    }

    // alphanumeric characters are present, single whitespace
    // multiple bridge words are added
    // bridge words are added at start, middle, end.
    @Test
    public void testpoem2() {

	String fileName = "star-trek.txt";
	String input = "This team will seek out life and civilizations.";
	String expectedResult = "This team will seek out new life and new civilizations.";
	testGraphPoemHelper(parentFolder + fileName, input, expectedResult);

    }

    // alphanumeric characters are present, single whitespace
    // multiple bridge words are added
    // bridge words are added at start, middle, end.
    @Test
    public void testpoem3() {

	String fileName = "star-trek.txt";
	String input = "These are voyages of starship Enterprise.";
	String expectedResult = "These are the voyages of the starship Enterprise.";
	testGraphPoemHelper(parentFolder + fileName, input, expectedResult);

    }

    // alphanumeric characters are present, single whitespace
    // multiple bridge words are added
    // bridge words are added at start, middle, end.
    @Test
    public void testpoem4() {

	String fileName = "star-trek.txt";
	String input = "He will boldly go where man has before!";
	String expectedResult = "He will boldly go where no man has gone before!";
	testGraphPoemHelper(parentFolder + fileName, input, expectedResult);

    }

    /**
     * Attempts to open the file at the mentioned location and create a affinity
     * graph from the content of the file. If the file can't be opened then fails
     * the test.
     * 
     * @param fileName, a path to the file
     * @return a affinity graph made from reading the content of the file
     */
    private GraphPoet createGraphPoet(String filePath) {

	GraphPoet graphPoet = null;

	try {
	    graphPoet = new GraphPoet(new File(filePath));
	} catch (IOException exp) {
	    // Handle the exception (e.g., fail the test with a message)
	    fail("Failed to read txt file: " + exp.getMessage());
	}

	return graphPoet;
    }

    /**
     * Compares the actual result with the expected result and fails the test if
     * both are not same.
     * 
     * @param input,any input poem
     * @param expected, expected transformation of the poem
     */
    private void compareThisWithThat(String input, String expected) {
	assertTrue(String.format("Expected %s after the transformation but got %s", expected, input),
		input.equals(expected));
    }

    /**
     * Creates an affinity graph and transforms the poem and checks if the result is
     * same as expected
     * 
     * @param filePath,       the path to the file location
     * @param input,          any poem
     * @param expectedResult, expected transformation of the poem
     */
    private void testGraphPoemHelper(String filePath, String input, String expectedResult) {
	GraphPoet graphPoet = createGraphPoet(filePath);
	String actualResult = graphPoet.poem(input);
	compareThisWithThat(actualResult, expectedResult);
    }

}
