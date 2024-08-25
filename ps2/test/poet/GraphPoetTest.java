/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package poet;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests for GraphPoet.
 */
public class GraphPoetTest {

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

    // TODO tests

}
