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
        return new ConcreteEdgesGraph<String>();
    }
   
    
    /*
     * Testing ConcreteEdgesGraph...
     */
    
    // Testing strategy for ConcreteEdgesGraph.toString()
    //   partition on number of vertices: zero, at-least one
    //   partition on number of edges: zero, at-least one
    //   partition on number of isolated vertices: zero, at-least one
    
    // tests for ConcreteEdgesGraph.toString()
    
    // zero vertices, zero edges, zero isolated vertex
    @Test
    public void testtoStringZeroVertexZeroEdges() {
    	ConcreteEdgesGraph<String> graph = new ConcreteEdgesGraph<String>();
    	String returnedValue = graph.toString();
    	String expectedValue = "";
    	
    	assertEquals("Returned string does not matches the expected string", returnedValue, expectedValue);
    }
    
    // atleast one vertex, zero edge, at-least isolated vertex
    @Test
    public void testtoStringZeroVertexAtleastOneEdge() {
    	ConcreteEdgesGraph<String> graph = new ConcreteEdgesGraph<String>();
    	graph.add("California");
    	graph.add("New York");
    	String returnedValue = graph.toString();
    	String expectedValue = String.join("\n", "New York", "California");
                
    	assertEquals("Returned string does not matches the expected string", expectedValue, returnedValue);
    }
    
    // at-least one vertex, at-least one edge, zero isolated vertices
    @Test
    public void testtoStringAtleastVertexAtleastOneEdge() {
    	ConcreteEdgesGraph<String> graph = new ConcreteEdgesGraph<String>();
        graph.set("California", "Delhi", 2000);
        graph.set("NewYork", "California", 48);
        graph.set("London", "NewYork", 379);
        graph.set("Berlin", "London", 248);
    	String returnedValue = graph.toString();
    	
    	String expectedValue = String.join("\n",
                               "California-----(2000)----->Delhi",
                               "NewYork-----(48)----->California",
                               "London-----(379)----->NewYork",
                               "Berlin-----(248)----->London"
                               );
        
    	assertEquals("Returned string does not matches the expected string", returnedValue, expectedValue);
    }
    
    // at-least one vertex, at-least one edge, zero isolated vertices
    @Test
    public void testtoStringAtleastVertexAtleastOneEdgeOneIsolatedVertex() {
    	ConcreteEdgesGraph<String> graph = new ConcreteEdgesGraph<String>();
        graph.set("California", "Delhi", 2000);
        graph.set("NewYork", "California", 48);
        graph.set("London", "NewYork", 379);
        graph.set("Berlin", "London", 248);
        graph.add("Hong Kong");
    	String returnedValue = graph.toString();
    	
    	String expectedValue = String.join("\n",
                               "California-----(2000)----->Delhi",
                               "NewYork-----(48)----->California",
                               "London-----(379)----->NewYork",
                               "Berlin-----(248)----->London",
                               "Hong Kong");
    	
    	assertEquals("Returned string does not matches the expected string", expectedValue, returnedValue);
    }
      
    
    /*
     * Testing Edge...
     */

    // Testing strategy for Edge
    //   partition on possibleHead: absent, present
    //   partition on vertex: absent, present
    

    // tests for operations of Edge
   
    @Test
    public void testgetHead() {
    	Edge<String> edge = new Edge<>("California", "New York", 48);
    	String returnedHead = edge.getHead();
    
    	assertEquals("Expected to get California", "California", returnedHead);
    
    }
    
    @Test
    public void testgetTail() {
    	Edge<String> edge = new Edge<>("California", "New York", 48);
    	String returnedTail = edge.getTail();
    
    	assertEquals("Expected to get New York", "New York", returnedTail);
    
    }
    
    @Test
    public void testgetEdgeWeight() {
    	Edge<String> edge = new Edge<>("California", "New York", 48);
    	int returnedEdgeWeight = edge.getEdgeWeight();
    
    	assertEquals("Expected to get New York", 48, returnedEdgeWeight);
    
    }
    
    // both the possibleHead and possibleTail are absent
    @Test
    public void testgetEdgeBetweenHeadAndTailAbsent() {
    	Edge<String> edge = new Edge<>("California", "New York", 48);
    	boolean returnedValue = edge.hasEdgeBetween("New Delhi", "Mumbai");
    
    	assertFalse("Expected to get false, there is no edge between New Delhi to Mumbai", returnedValue);
    
    }
    
    // both the possibleHead and possibleTail are present
    @Test
    public void testgetEdgeBetweenHeadAndTailPresent() {
    	Edge<String> edge = new Edge<>("California", "New York", 48);
    	boolean returnedValue = edge.hasEdgeBetween("California", "New York");
      
    	assertTrue("Expected to get true, there is an edge between California to New York", returnedValue);
    }
    
    // possibleHead is present and possibleTail is absent
    @Test
    public void testgetEdgeBetweenHeadPresentTailAbsent() {
    	Edge<String> edge = new Edge<>("California", "New York", 48);
    	boolean returnedValue = edge.hasEdgeBetween("California", "London");
    
    	assertFalse("Expected to get true, there is no edge between California to London", returnedValue);
    }
    
    // possibleHead is absent and possibleTail is present
    @Test
    public void testgetEdgeBetweenHeadAbsentTailPresent() {
    	Edge<String> edge = new Edge<>("California", "New York", 48);
    	boolean returnedValue = edge.hasEdgeBetween("Tel Aviv", "New York");
    
    	assertFalse("Expected to get true, there is no edge between Tel Aviv to New York", returnedValue);
    }
    
    // possibleHead is at head
    @Test
    public void testgetEdgeFromPossibleHeadAtHead() {
    	Edge<String> edge = new Edge<>("California", "New York", 48);
    	boolean returnedValue = edge.hasEdgeFrom("California");
    
    	assertTrue("Expected to get true, there is edge California.", returnedValue);
    }
    
    // possibleHead is not at head
    @Test
    public void testgetEdgeFromPossibleHeadNotAtHead() {
    	Edge<String> edge = new Edge<>("California", "New York", 48);
    	boolean returnedValue = edge.hasEdgeFrom("Tel Aviv");
    
    	assertFalse("Expected to get false, there is no edge from Tel Aviv", returnedValue);
    }
    
    // possibleTail is at tail
    @Test
    public void testgetEdgeToPossibleTailAtTail() {
    	Edge<String> edge = new Edge<>("California", "New York", 48);
    	boolean returnedValue = edge.hasEdgeTo("New York");
        System.out.println(edge.getTail());
    	assertTrue("Expected to get true, there is edge to New York.", returnedValue);
    }
    
    // possibleTail is not at tail
    @Test
    public void testgetEdgeToPossibleTailNotAtTail() {
    	Edge<String> edge = new Edge<>("California", "New York", 48);
    	boolean returnedValue = edge.hasEdgeTo("Tel Aviv");
        
    	assertFalse("Expected to get false, there is no edge to Tel Aviv", returnedValue);
    }
    
    
    @Test
    public void testEdgetoString() {
    	Edge<String> edge = new Edge<>("California", "New York", 48);
    	String returnedValue = edge.toString();
    	String expectedValue = "California-----(48)----->New York";
    	
    	assertEquals("Returned string does not matches the expected string", returnedValue, expectedValue);
    }
    
}
