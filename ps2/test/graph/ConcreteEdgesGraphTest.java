/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests for ConcreteEdgesGraph.
 * 
 * This class runs the GraphInstanceTest tests against ConcreteEdgesGraph, as
 * well as tests for that particular implementation.
 * 
 * Tests against the Graph spec should be in GraphInstanceTest.
 */
public class ConcreteEdgesGraphTest extends GraphInstanceTest {
    
    /*
     * Provide a ConcreteEdgesGraph for tests in GraphInstanceTest.
     */
    @Override public Graph<String> emptyInstance() {
        return new ConcreteEdgesGraph();
    }
    
    /*
     * Testing ConcreteEdgesGraph...
     */
    
    // Testing strategy for ConcreteEdgesGraph.toString()
    //   TODO
    
    // TODO tests for ConcreteEdgesGraph.toString()
    
    /*
     * Testing Edge...
     */
    
    // Testing strategy for Edge
    //   partition on possibleHead: absent, present
    //   partition on vertex: absent, present
    
    // tests for operations of Edge
    // 
    @Test
    public void testgetHead() {
    	Edge edge = new Edge("California", "New York", 48);
    	String returnedHead = edge.getHead();
    
    	assertEquals("Expected to get California", "California", returnedHead);
    
    }
    
    @Test
    public void testgetTail() {
    	Edge edge = new Edge("California", "New York", 48);
    	String returnedTail = edge.getTail();
    
    	assertEquals("Expected to get New York", "New York", returnedTail);
    
    }
    
    @Test
    public void testgetEdgeWeight() {
    	Edge edge = new Edge("California", "New York", 48);
    	int returnedEdgeWeight = edge.getEdgeWeight();
    
    	assertEquals("Expected to get New York", 48, returnedEdgeWeight);
    
    }
    
    // both the possibleHead and possibleTail are absent
    @Test
    public void testgetEdgeBetween1() {
    	Edge edge = new Edge("California", "New York", 48);
    	boolean returnedValue = edge.hasEdgeBetween("New Delhi", "Mumbai");
    
    	assertFalse("Expected to get false, there is no edge between New Delhi to Mumbai", returnedValue);
    
    }
    
    // both the possibleHead and possibleTail are present
    @Test
    public void testgetEdgeBetween2() {
    	Edge edge = new Edge("California", "New York", 48);
    	boolean returnedValue = edge.hasEdgeBetween("California", "New York");
    
    	assertFalse("Expected to get true, there is an edge between California to New York", returnedValue);
    }
    
    // possibleHead is present and possibleTail is absent
    @Test
    public void testgetEdgeBetween3() {
    	Edge edge = new Edge("California", "New York", 48);
    	boolean returnedValue = edge.hasEdgeBetween("California", "London");
    
    	assertFalse("Expected to get true, there is no edge between California to London", returnedValue);
    }
    
    // possibleHead is absent and possibleTail is present
    @Test
    public void testgetEdgeBetween4() {
    	Edge edge = new Edge("California", "New York", 48);
    	boolean returnedValue = edge.hasEdgeBetween("Tel Aviv", "New York");
    
    	assertFalse("Expected to get true, there is no edge between Tel Aviv to New York", returnedValue);
    }
    
    // possibleHead is at head
    @Test
    public void testgetEdgeFrom1() {
    	Edge edge = new Edge("California", "New York", 48);
    	boolean returnedValue = edge.hasEdgeFrom("California");
    
    	assertTrue("Expected to get true, there is edge California.", returnedValue);
    }
    
    // possibleHead is not at head
    @Test
    public void testgetEdgeFrom2() {
    	Edge edge = new Edge("California", "New York", 48);
    	boolean returnedValue = edge.hasEdgeFrom("Tel Aviv");
    
    	assertFalse("Expected to get false, there is no edge from Tel Aviv", returnedValue);
    }
    
    // possibleTail is at tail
    @Test
    public void testgetEdgeTo1() {
    	Edge edge = new Edge("California", "New York", 48);
    	boolean returnedValue = edge.hasEdgeTo("NewYork");
    
    	assertTrue("Expected to get true, there is edge to New York.", returnedValue);
    }
    
    // possibleTail is not at tail
    @Test
    public void testgetEdgeTo2() {
    	Edge edge = new Edge("California", "New York", 48);
    	boolean returnedValue = edge.hasEdgeTo("Tel Aviv");
    
    	assertFalse("Expected to get false, there is no edge to Tel Aviv", returnedValue);
    }
    
}
