/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import static org.junit.Assert.*;

import org.junit.Test;


/**
 * Tests for ConcreteVerticesGraph.
 * 
 * This class runs the GraphInstanceTest tests against ConcreteVerticesGraph, as
 * well as tests for that particular implementation.
 * 
 * Tests against the Graph spec should be in GraphInstanceTest.
 */
public class ConcreteVerticesGraphTest extends GraphInstanceTest {
    
    /*
     * Provide a ConcreteVerticesGraph for tests in GraphInstanceTest.
     */
    @Override public Graph<String> emptyInstance() {
        return new ConcreteVerticesGraph();
    }
    
    /*
     * Testing ConcreteVerticesGraph...
     */
    
    // Testing strategy for ConcreteVerticesGraph.toString()
    //   partition on number of vertices: zero, at-least one
    //   partition on number of edges: zero, at-least one
    //   partition on number of isolated vertices: zero, at-least one
    
    // tests for ConcreteVerticesGraph.toString()
    
    // zero vertices, zero edges, zero isolated vertex
    @Test
    public void testtoStringZeroVertexZeroEdges() {
    	ConcreteVerticesGraph graph = new ConcreteVerticesGraph();
    	String returnedValue = graph.toString();
    	String expectedValue = "";
    	
    	assertEquals("Returned string does not matches the expected string", returnedValue, expectedValue);
    }
    
    // atleast one vertex, zero edge, at-least isolated vertex
    @Test
    public void testtoStringZeroVertexAtleastOneEdge() {
    	ConcreteVerticesGraph graph = new ConcreteVerticesGraph();
    	graph.add("California");
    	graph.add("New York");
    	String returnedValue = graph.toString();
    	String expectedValue = String.join("\n", "California", "New York");
                
    	assertEquals("Returned string does not matches the expected string", returnedValue, expectedValue);
    }
    
    // at-least one vertex, at-least one edge, zero isolated vertices
    @Test
    public void testtoStringAtleastVertexAtleastOneEdge() {
    	ConcreteVerticesGraph graph = new ConcreteVerticesGraph();
        graph.set("California", "Delhi", 2000);
        graph.set("NewYork", "California", 48);
        graph.set("London", "NewYork", 379);
        graph.set("Berlin", "London", 248);
    	String returnedValue = graph.toString();
    	
    	String expectedValue = String.join("\n",
    			               "NewYork-----(48)----->California",
                               "California-----(2000)----->Delhi", 
                               "London-----(379)----->NewYork",
                               "Berlin-----(248)----->London"
                               );
    	
    	assertEquals("Returned string does not matches the expected string", expectedValue, returnedValue);
    }
    
    // at-least one vertex, at-least one edge, zero isolated vertices
    @Test
    public void testtoStringAtleastVertexAtleastOneEdgeOneIsolatedVertex() {
    	ConcreteVerticesGraph graph = new ConcreteVerticesGraph();
        graph.set("California", "Delhi", 2000);
        graph.set("NewYork", "California", 48);
        graph.set("London", "NewYork", 379);
        graph.set("Berlin", "London", 248);
        graph.add("Hong Kong");
    	String returnedValue = graph.toString();
    	
    	String expectedValue = String.join("\n",
	               "NewYork-----(48)----->California",
                "California-----(2000)----->Delhi", 
                "London-----(379)----->NewYork",
                "Berlin-----(248)----->London",
                "Hong Kong"
                );
    	
    	assertEquals("Returned string does not matches the expected string", returnedValue, expectedValue);
    }
    
    
    /*
     * Testing Vertex...
     */
    
    // Testing strategy for Vertex
    
    // addIncomingEdge(), deleteIncomingEdge(), changeIncomingEdgeWeight, hasEdgeFrom
    // Partition on weather there was an incoming edge from source: 
    // Presence of an incoming edge from source, Absence of an incoming edge from source
    
    // addIncomingEdge(), addOutgoingEdge(), changeIncomingEdgeWeight(), changeOutgoingEdgeWeight()
    // weight is zero, weight is greater than zero
    
    // addOutgoingEdge(), deleteOuthoingEdge(), changeOutgoingEdgeWeight, hasEdgeTo
    // Partition on weather there was an outgoing edge to target: 
    // Presence of an outgoing edge to target, Absence of an incoming edge from target
    
    
    // tests for operations of Vertex
    // addEdgeFrom() hasEdgeTo() hasEdgeFrom() getIncomingEdgeWeight() getOutgoingEdgeWeight()
    // Absence of incoming edge from source, weight is greater than zero
    // Presence of incoming edge from source, weight is greater than zero
    @Test
    public void testEdgeFromWeightPositive() {
    	Vertex California = new Vertex("California");
    	
    	
    	assertFalse("There should be no edge from New York to California", California.hasEdgeFrom("NewYork"));
    	assertEquals("Expected no edge from New York to California and thus zero edge weight", 0,  California.getIncomingEdgeWeight("NewYork"));
    	
    	boolean actualResult1 = California.addEdgeFrom("NewYork",48);
    	
    	assertTrue("Expected true because a new edge was added from New York to California when no edge existed previously", actualResult1);
    	assertTrue("There should be an edge from New York to California", California.hasEdgeFrom("NewYork"));
    	assertEquals("Expected an edge from New York to California and of weight 48", 48,  California.getIncomingEdgeWeight("NewYork"));
    	
    	boolean actualResult2 = California.addEdgeFrom("NewYork",48);
    	
    	assertFalse("Expected false because a edge was added from New York to California when an edge already existed previously", actualResult2);
    	assertTrue("There should be an edge from New York to California", California.hasEdgeFrom("NewYork"));
    	assertEquals("Expected an edge from New York to California and of weight 48", 48,  California.getIncomingEdgeWeight("NewYork"));
    	
    	
    	
    }
    
    
    // addEdgeFrom() hasEdgeTo() hasEdgeFrom() getIncomingEdgeWeight() getOutgoingEdgeWeight()
    // Absence of incoming edge from source, weight is zero
    @Test
    public void testEdgeFromWeightZeroEdgeAbsentFirst() {
    	Vertex California = new Vertex("California");
    	
    	assertFalse("There should be no edge from New York to California", California.hasEdgeFrom("NewYork"));
    	assertEquals("Expected no edge from New York to California and thus zero edge weight", 0,  California.getIncomingEdgeWeight("NewYork"));
    	
    	boolean actualResult1 = California.addEdgeFrom("NewYork",0);
    	
    	assertFalse("Expected false because no edge was added from New York to California", actualResult1);
    	assertFalse("There should be no edge from New York to California", California.hasEdgeFrom("NewYork")); 	
    	assertEquals("Expected an edge from New York to California and of weight zero", 0,  California.getIncomingEdgeWeight("NewYork"));
    	
    }
    
    
    // addEdgeFrom() hasEdgeTo() hasEdgeFrom() getIncomingEdgeWeight() getOutgoingEdgeWeight()
    // Presence of incoming edge from source, weight is zero
    @Test
    public void testEdgeFromWeightZeroEdgePresentFirst() {
    	Vertex NewYork = new Vertex("NewYork");
    
    	assertFalse("There should be no edge to California from New York", NewYork.hasEdgeTo("California"));
    	assertEquals("Expected no edge to California from New York  and thus zero edge weight", 0, NewYork.getOutgoingEdgeWeight("California"));
    	
    	boolean actualResult1 = NewYork.addEdgeFrom("California",48);
    	
    	assertTrue("Expected true because a new edge was added from New York to California when no edge existed previously", actualResult1);
    	assertTrue("There should be an edge to California from New York", NewYork.hasEdgeFrom("California"));
    	assertEquals("Expected an edge to California from New York  and of weight 48", 48, NewYork.getIncomingEdgeWeight("California"));
    	
    	boolean actualResult2 = NewYork.addEdgeFrom("California",0);
    	
    	assertFalse("Expected false because a edge was added from New York to California when an edge already existed previously", actualResult2);
    	assertTrue("There should be an edge to California from New York", NewYork.hasEdgeFrom("California"));
    	assertEquals("Expected an edge to California from New York  and of weight 48", 48, NewYork.getIncomingEdgeWeight("California")); 	
    }
    
    
    // addEdgeTo() hasEdgeTo() hasEdgeFrom() getIncomingEdgeWeight() getOutgoingEdgeWeight()
    // Absence of outgoing edge from target, weight is greater than zero
    // Presence of outgoing edge from target, weight is greater than zero
    @Test
    public void testEdgeToWeightPositive() {  	
    	Vertex NewYork = new Vertex("NewYork");
    
    	assertFalse("There should be no edge to California from New York", NewYork.hasEdgeTo("California"));
    	assertEquals("Expected no edge to California from New York  and thus zero edge weight", 0, NewYork.getOutgoingEdgeWeight("California"));
    	
    	boolean actualResult1 = NewYork.addEdgeTo("California",48);
    	
    	assertTrue("Expected true because a new edge was added from New York to California when no edge existed previously", actualResult1);
    	assertTrue("There should be an edge to California from New York", NewYork.hasEdgeTo("California"));
    	assertEquals("Expected an edge to California from New York  and of weight 48", 48, NewYork.getOutgoingEdgeWeight("California"));
    	
    	boolean actualResult2 = NewYork.addEdgeTo("California",48);
    	
    	assertFalse("Expected false because a edge was added from New York to California when an edge already existed previously", actualResult2);
    	assertTrue("There should be an edge to California from New York", NewYork.hasEdgeTo("California"));
    	assertEquals("Expected an edge to California from New York  and of weight 48", 48, NewYork.getOutgoingEdgeWeight("California")); 	
    }
 
    
    // addEdgeTo() hasEdgeTo() hasEdgeFrom() getIncomingEdgeWeight() getOutgoingEdgeWeight()
    // Absence of outgoing edge from source, weight is zero
    @Test
    public void testEdgeToWeightZeroEdgeAbsentFirst() {
   
    	Vertex NewYork = new Vertex("NewYork");
    	
    	
    	assertFalse("There should be no edge to California from New York", NewYork.hasEdgeTo("California"));
    	assertEquals("Expected no edge to California from New York  and thus zero edge weight", 0, NewYork.getOutgoingEdgeWeight("California"));
    	
    	boolean actualResult1 = NewYork.addEdgeTo("California",0);
    	
    	assertFalse("Expected false because an edge was added from New York to California whose weight was zero.", actualResult1);
    	assertFalse("There should be no edge to California from New York", NewYork.hasEdgeTo("California"));
    	assertEquals("Expected no edge to California from New York  and thus zero edge weight", 0, NewYork.getOutgoingEdgeWeight("California"));
    	
    	
    	
    }
    
    
    // addEdgeTo() hasEdgeTo() hasEdgeFrom() getIncomingEdgeWeight() getOutgoingEdgeWeight()
    // Presence of outgoing edge from source, weight is zero
    @Test
    public void testEdgeToWeightZeroEdgePresentFirst() {
    	Vertex NewYork = new Vertex("NewYork");
    	
    	assertFalse("There should be no edge to California from New York", NewYork.hasEdgeTo("California"));	
    	assertEquals("Expected no edge to California from New York  and thus zero edge weight", 0, NewYork.getOutgoingEdgeWeight("California"));
    	
    	boolean actualResult1 = NewYork.addEdgeTo("California",48);
    	
    	assertTrue("Expected true because a new edge was added from New York to California when no edge existed previously", actualResult1);  	
    	assertTrue("There should be an edge to California from New York", NewYork.hasEdgeTo("California"));
    	assertEquals("Expected an edge to California from New York  and of weight 48", 48, NewYork.getOutgoingEdgeWeight("California"));
    	
    	boolean actualResult2 = NewYork.addEdgeTo("California",0);
    	
    	assertFalse("Expected false because an edge was added from New York to California whose weight was zero.", actualResult2);	
    	assertTrue("There should be an edge to California from New York", NewYork.hasEdgeTo("California"));
    	assertEquals("Expected no edge to California from New York  and of weight 48", 48, NewYork.getOutgoingEdgeWeight("California"));
    	
    }
    
    
    // deleteEdgeFrom() hasEdgeTo() hasEdgeFrom() getIncomingEdgeWeight() getOutgoingEdgeWeight()
    // Presence of incoming edge from source
    @Test
    public void testDeleteEdgeFromPresentEdge() {
    	Vertex California = new Vertex("California");
    	
    	assertFalse("There should be no edge from New York to California", California.hasEdgeFrom("NewYork"));
    	assertEquals("Expected no edge from New York to California and thus zero edge weight", 0,  California.getIncomingEdgeWeight("NewYork"));
    	
    	boolean actualResult1 = California.addEdgeFrom("NewYork",48);
    	
    	assertTrue("Expected true because a new edge was added from New York to California when no edge existed previously", actualResult1);
    	assertTrue("There should be an edge from New York to California", California.hasEdgeFrom("NewYork"));	
    	assertEquals("Expected an edge from New York to California and of weight 48", 48,  California.getIncomingEdgeWeight("NewYork"));
    	
    	
    	boolean actualResult2 = California.deleteEdgeFrom("NewYork");
    	
    	assertTrue("Expected true because a edge was deleted from New York to California when an edge already existed previously", actualResult2);
    	assertFalse("There should be no edge from New York to California", California.hasEdgeFrom("NewYork"));	
    	assertEquals("Expected no edge from New York to California and returned weight to be 0", 0,  California.getIncomingEdgeWeight("NewYork"));
    }
    
    
    // deleteEdgeFrom() hasEdgeTo() hasEdgeFrom() getIncomingEdgeWeight() getOutgoingEdgeWeight()
    // Presence of incoming edge from source
    @Test
    public void testDeleteEdgeFromAbsentEdge() {
    	Vertex California = new Vertex("California");
	
    	boolean actualResult2 = California.deleteEdgeFrom("NewYork");
    	
    	assertFalse("Expected false because an edge was deleted from New York to California when no edge existed previously", actualResult2);
    	assertFalse("There should be no edge from New York to California", California.hasEdgeFrom("NewYork"));
    	assertEquals("Expected no edge from New York to California and returned weight to be 0", 0,  California.getIncomingEdgeWeight("NewYork"));
	
    }
    
    
   // deleteEdgeFrom() hasEdgeTo() hasEdgeFrom() getIncomingEdgeWeight() getOutgoingEdgeWeight()
    // Presence of incoming edge from source
    @Test
    public void testDeleteEdgeToPresentEdge() {
  
    	Vertex NewYork = new Vertex("NewYork");
      	
    	assertFalse("There should be no edge to California from New York", NewYork.hasEdgeTo("California"));
    	assertEquals("Expected no edge to California from New York  and thus zero edge weight", 0, NewYork.getOutgoingEdgeWeight("California"));
    	
    	boolean actualResult1 = NewYork.addEdgeTo("California",48);
    	
    	assertTrue("Expected true because a new edge was added from New York to California when no edge existed previously", actualResult1); 	
    	assertTrue("There should be an edge to California from New York", NewYork.hasEdgeTo("California"));    
    	assertEquals("Expected an edge to California from New York  and of weight 48", 48, NewYork.getOutgoingEdgeWeight("California"));
    	
    	boolean actualResult2 = NewYork.deleteEdgeTo("California");
   
    	assertTrue("Expected true because an edge was deleted from New York to California when an edge already existed previously", actualResult2);
    	assertFalse("There should be no edge to California from New York", NewYork.hasEdgeTo("California"));
    	assertEquals("Expected no edge to California from New York  and returned weight to be 0", 0, NewYork.getOutgoingEdgeWeight("California"));
    	
    	
    }
    
    
    // deleteEdgeFrom() hasEdgeTo() hasEdgeFrom() getIncomingEdgeWeight() getOutgoingEdgeWeight()
    // Presence of incoming edge from source
    @Test
    public void testDeleteEdgeToAbsentEdge() {
    	
    	Vertex NewYork = new Vertex("NewYork");
    	
    	boolean actualResult2 = NewYork.deleteEdgeTo("California");
    	
    	assertFalse("Expected false because an edge was deleted from New York to California when no edge existed previously", actualResult2);
    	assertFalse("There should be no edge to California from New York", NewYork.hasEdgeTo("California"));
    	assertEquals("Expected no edge to California from New York  and returned weight to be 0", 0, NewYork.getOutgoingEdgeWeight("California"));
    	
    	
    }
    

    // changeIncomingEdgeWeight
    // Presence of incoming edge from source, new weight is greater than zero.
    // Presence of incoming edge from source, new weight is zero.
    @Test
    public void testChangeEdgeWeightFromPresentEdge() {
    	Vertex California = new Vertex("California");
    	
    	assertFalse("There should be no edge from New York to California", California.hasEdgeFrom("NewYork"));
    	assertEquals("Expected no edge from New York to California and thus zero edge weight", 0,  California.getIncomingEdgeWeight("NewYork"));
    	
    	boolean actualResult1 = California.addEdgeFrom("NewYork", 48);
    	
    	assertTrue("Expected true because a new edge was added from New York to California when no edge existed previously", actualResult1);
    	assertTrue("There should be an edge from New York to California", California.hasEdgeFrom("NewYork"));
    	assertEquals("Expected an edge from New York to California and of weight 48", 48,  California.getIncomingEdgeWeight("NewYork"));
    	
    	boolean actualResult2 = California.changeEdgeWeightFrom("NewYork", 24);
    	
    	assertTrue("Expected true because edgeweight of edge from New York to California was changed to 24 from 48 when an edge already existed previously", actualResult2);
    	assertTrue("There should be an edge from New York to California", California.hasEdgeFrom("NewYork"));
    	assertEquals("Expected an edge from New York to California and of weight 48", 24,  California.getIncomingEdgeWeight("NewYork"));
    
    	
    	boolean actualResult3 = California.changeEdgeWeightFrom("NewYork", 0);
    	
    	assertFalse("Expected false because edgeweight of edge from New York to California was changed to zero from 24 when an edge already existed previously", actualResult3);
    	assertTrue("There should be an edge from New York to California", California.hasEdgeFrom("NewYork"));
    	assertEquals("Expected an edge from New York to California and of weight 48", 24,  California.getIncomingEdgeWeight("NewYork"));
    
    }
    
    
    // changeIncomingEdgeWeight
    // Absence of incoming edge from source, new weight is greater than zero.
    // Absence of incoming edge from source, new weight is zero.
    @Test
    public void testChangeEdgeWeightFromAbsentEdge() {
    	Vertex California = new Vertex("California");
    	
    	assertFalse("There should be no edge from New York to California", California.hasEdgeFrom("NewYork"));
    	assertEquals("Expected no edge from New York to California and thus zero edge weight", 0,  California.getIncomingEdgeWeight("NewYork"));
    
    	boolean actualResult2 = California.changeEdgeWeightFrom("NewYork", 24);
    	
    	assertFalse("Expected false because edgeweight of edge from New York to California was changed to 24 when no edge already existed previously", actualResult2);
    	assertFalse("There should be no edge from New York to California", California.hasEdgeFrom("NewYork"));
    	assertEquals("Expected no edge from New York to California and returned weight 0", 0,  California.getIncomingEdgeWeight("NewYork"));
    	
    	boolean actualResult3 = California.changeEdgeWeightFrom("NewYork", 0);
    	
    	assertFalse("Expected false because edgeweight of edge from New York to California was changed to zero when an edge already existed previously", actualResult3);
    	assertFalse("There should be no edge from New York to California", California.hasEdgeFrom("NewYork"));
    	assertEquals("Expected no edge from New York to California and returned weight 0", 0,  California.getIncomingEdgeWeight("NewYork"));
 
    }
   

    // changeIncomingEdgeWeight
    // Presence of incoming edge from source, new weight is greater than zero.
    // Presence of incoming edge from source, new weight is zero.
    @Test
    public void testChangeEdgeWeightToPresentEdge() {
    	
    	Vertex NewYork = new Vertex("NewYork");
    	
    	
    	assertFalse("There should be no edge to California from New York", NewYork.hasEdgeTo("California"));
    	assertEquals("Expected no edge to California from New York  and thus zero edge weight", 0, NewYork.getOutgoingEdgeWeight("California"));
    	
    	boolean actualResult1 = NewYork.addEdgeTo("California", 48);
    	
    	assertTrue("Expected true because a new edge was added from New York to California when no edge existed previously", actualResult1);
    	assertTrue("There should be an edge to California from New York", NewYork.hasEdgeTo("California"));
    	assertEquals("Expected an edge to California from New York  and of weight 48", 48, NewYork.getOutgoingEdgeWeight("California"));
    	
    	boolean actualResult2 = NewYork.changeEdgeWeightTo("California", 24);
    	
    	assertTrue("Expected true because edgeweight of edge from New York to California was changed to 24 from 48 when an edge already existed previously", actualResult2);
    	assertTrue("There should be an edge to California from New York", NewYork.hasEdgeTo("California"));
    	assertEquals("Expected an edge to California from New York  and of weight 48", 24, NewYork.getOutgoingEdgeWeight("California"));
    	
    	boolean actualResult3 = NewYork.changeEdgeWeightTo("California", 0);
    	
    	assertFalse("Expected false because edgeweight of edge from New York to California was changed to zero from 24 when an edge already existed previously", actualResult3);
    	assertTrue("There should be an edge to California from New York", NewYork.hasEdgeTo("California"));
    	assertEquals("Expected an edge to California from New York  and of weight 24", 24, NewYork.getOutgoingEdgeWeight("California"));
    	
    }
    
    
    // changeIncomingEdgeWeight
    // Absence of incoming edge from source, new weight is greater than zero.
    // Absence of incoming edge from source, new weight is zero.
    @Test
    public void testChangeEdgeWeightToAbsentEdge() {
    	
    	Vertex NewYork = new Vertex("NewYork");
    
    	assertFalse("There should be no edge to California from New York", NewYork.hasEdgeTo("California"));
    	assertEquals("Expected no edge to California from New York  and thus zero edge weight", 0, NewYork.getOutgoingEdgeWeight("California"));
    
    	boolean actualResult2 = NewYork.changeEdgeWeightTo("California", 24);
    	
    	assertFalse("Expected false because edgeweight of edge from New York to California was changed to 24 when no edge already existed previously", actualResult2);
    	assertFalse("There should be no edge to California from New York", NewYork.hasEdgeTo("California"));
    	assertEquals("Expected no edge to California from New York and returned weight 0", 0, NewYork.getOutgoingEdgeWeight("California"));
    	
    	boolean actualResult3 = NewYork.changeEdgeWeightTo("California", 0);
    	
    	assertFalse("Expected false because edgeweight of edge from New York to California was changed to zero when an edge already existed previously", actualResult3);
    	assertFalse("There should be no edge to California from New York", NewYork.hasEdgeTo("California"));
    	assertEquals("Expected no edge to California from New York and returned weight 0", 0, NewYork.getOutgoingEdgeWeight("California"));
    	
    }
    
    // isolated vertex
    @Test
    public void testVertextoStringIsolated() {
    	Vertex vertex = new Vertex("California");
    	String returnedValue = vertex.toString();
    	String expectedValue = "California";
    	
    	assertEquals("Returned string does not matches the expected string", returnedValue, expectedValue);
    }
    
    // isolated vertex
    @Test
    public void testVertextoStringConnected() {
    	Vertex vertex = new Vertex("California");
    	vertex.addEdgeFrom("NewYork", 24);
    	vertex.addEdgeTo("Boston", 48);
    	String returnedValue = vertex.toString();
    	
    	String expectedValue = String.join("\n",
                "NewYork-----(24)----->California",
                "California-----(48)----->Boston"
                 );
    	
    	assertEquals("Returned string does not matches the expected string", returnedValue, expectedValue);
    }
    
}


