/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import static org.junit.Assert.*;

import java.util.Collections;

import org.junit.Test;

/**
 * Tests for static methods of Graph.
 * 
 * To facilitate testing multiple implementations of Graph, instance methods are
 * tested in GraphInstanceTest.
 */
public class GraphStaticTest {
    
    // Testing strategy
    //   empty()
    //     no inputs, only output is empty graph
    //     observe with vertices()
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testEmptyVerticesEmpty() {
        assertEquals("expected empty() graph to have no vertices",
                Collections.emptySet(), Graph.empty().vertices());
    }
    
    // test other vertex label types in Problem 3.2
    @Test
    public void testIntegerLabel() {
    	Graph<Integer> graph = Graph.empty();
    	graph.set(23, 35, 67);
    	String returnedValue = graph.toString();
    	String expectedValue = String.join("\n", "23-----(67)----->35");
                
    	assertEquals("Returned string does not matches the expected string", expectedValue, returnedValue);
    }
    
 // test other vertex label types in Problem 3.2
    @Test
    public void testFloatLabel() {
    	Graph<Double> graph = Graph.empty();
    	graph.set(23.45, 35.595, 67);
    	String returnedValue = graph.toString();
    	String expectedValue = String.join("\n", "23.45-----(67)----->35.595");
                
    	assertEquals("Returned string does not matches the expected string", expectedValue, returnedValue);
    }
    
 // test other vertex label types in Problem 3.2
    @Test
    public void testCharacterLabel() {
    	Graph<Character> graph = Graph.empty();
    	graph.set('A', 'B', 67);
    	String returnedValue = graph.toString();
    	String expectedValue = String.join("\n", "A-----(67)----->B");
                
    	assertEquals("Returned string does not matches the expected string", expectedValue, returnedValue);
    }
    
}
