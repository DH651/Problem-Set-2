/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import org.junit.Test;

/**
 * Tests for instance methods of Graph.
 * 
 * <p>PS2 instructions: you MUST NOT add constructors, fields, or non-@Test
 * methods to this class, or change the spec of {@link #emptyInstance()}.
 * Your tests MUST only obtain Graph instances by calling emptyInstance().
 * Your tests MUST NOT refer to specific concrete implementations.
 */
public abstract class GraphInstanceTest {
    
    // Testing strategy
    /* Partition on presence of vertex: present in the graph, absent from the graph.
     * Partition on presence of source: present in the graph, absent from the graph.
     * Partition on presence of target: present in the graph, absent from the graph.
     * Partition on presence of edge: present in the graph, absent from the graph.
     * Partition on edge weight:  zero, greater than zero.
     * 
     * Test cases for add
     * 1. Vertex is present in the graph, Vertex is absent from the graph.
     * 
     * Test cases for set
     * 1. Source is present, Target is absent , edge is absent, edge weight is zero.
     * 2. Source is present, Target is absent , edge is absent, edge weight is positive.
     * 3. Source is absent, Target is absent , edge is absent, edge weight is zero.
     * 4. Source is absent, Target is absent, edge is absent, edge weight is positive.
     * 5. Source is absent, Target is present, edge is absent, edge weight is positive.
     * 
     * Test cases for remove
     * 1. Vertex is present in the graph, Vertex is absent from the graph.
     * 
     * Test cases for vertices
     * 1. this graph is empty. 
     * 2. this graph has at least one vertex.
     * 
     * Test cases for sources
     * 1. target is absent.
     * 2. target is present, no incoming neighbour.
     * 3. target is present, atleast one incoming neighbour.
     * 
     * Test cases for target
     * 1. source is absent.
     * 2. source is present, no outgoing neighbour.
     * 3. source is present, atleast one outgoing neighbour.
     */
    
    /**
     * Overridden by implementation-specific test classes.
     * 
     * @return a new empty graph of the particular implementation being tested
     */
    public abstract Graph<String> emptyInstance();
    
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testInitialVerticesEmpty() {
        assertEquals("expected new graph to have no vertices",
                Collections.emptySet(), emptyInstance().vertices());
    }
   

 //-------------------------------------------------------------------------------------------------------------------------------------------------------------------------//   
 
    
    
    // Vertex is present in the graph
    @Test
    public void testAddVertexPresent() {
    	Graph<String> graph = emptyInstance();
    	graph.add("A");
    	boolean returnedValue= graph.add("A");
    
    	assertFalse("The add vertex was already present in the graph. "
    			+ "Hence, add instance method should have returned false.",  returnedValue);
    }
    
    // Vertex is absent in the graph
    @Test
    public void testAddVertexAbsent() {
    	Graph<String> graph = emptyInstance();
    	boolean returnedValue= graph.add("A");
    
    	assertTrue("The add vertex was absent from the graph."
    			+ " Hence, add instance method should have returned true.",  returnedValue);
    
    }
 
    
    
//-------------------------------------------------------------------------------------------------------------------------------------------------------------------------//   

    
    
    // Source is present, Target is absent , edge is absent, edge weight is zero.
    @Test
    public void testAddEdgeSourcePresentTargetAbsentEdgeAbsentWeightZero() {
    	int expectedReturnedValue = 0;
    	Graph<String> graph = emptyInstance();
    	graph.add("California");
    
    	int actualReturnedValue = graph.set("California", "New York", 0);
    	assertEquals("The source vertex was absent,target vertex was absent and thus, "
    			+ "edge was absent from the graph and edge weight was absent.", expectedReturnedValue , actualReturnedValue);
    }
    
    // Source is present, Target is absent , edge is absent, edge weight is positive.
    @Test
    public void testAddEdgeSourcePresentTargetAbsentEdgeAbsentWeightPositive() {
    	int expectedReturnedValue = 0;
    	Graph<String> graph = emptyInstance();
    	graph.add("California");
    	
    	int actualReturnedValue = graph.set("California", "New York", 43);
    	assertEquals("The source vertex was absent,target vertex was absent and thus, "
    			+ "edge was absent from the graph and edge weight was absent.", expectedReturnedValue , actualReturnedValue);
    	
    }
    
    // Source is absent, Target is absent , edge is absent, edge weight is zero.
    @Test
    public void testAddEdgeSourceAbsentTargetAbsentEdgeAbsentWeightZero() {
    	int expectedReturnedValue = 0;
    	Graph<String> graph = emptyInstance();

    	int actualReturnedValue = graph.set("California", "New York", 0);
    	assertEquals("The source vertex was absent,target vertex was absent and thus, "
    			+ "edge was absent from the graph and edge weight was absent.", expectedReturnedValue , actualReturnedValue);
    	
    }
    
    // Source is absent, Target is absent, edge is absent, edge weight is positive.
    @Test
    public void testAddEdgeSourceAbsentTargetAbsentEdgeAbsentWeightPositive() {
    	int expectedReturnedValue = 0;
    	Graph<String> graph = emptyInstance();

    	int actualReturnedValue = graph.set("California", "New York", 43);
    	assertEquals("The source vertex was absent,target vertex was absent and thus, "
    			+ "edge was absent from the graph and edge weight was absent.", expectedReturnedValue , actualReturnedValue);
    	
    }
    
    // Source is absent, Target is present, edge is absent, edge weight is positive.
    @Test
    public void testAddEdge5() {
    	int expectedReturnedValue = 0;
    	Graph<String> graph = emptyInstance();
    	graph.add("New York");

    	int actualReturnedValue = graph.set("California", "New York", 43);
    	assertEquals("The source vertex was absent,target vertex was absent and thus, "
    			+ "edge was absent from the graph and edge weight was absent.", expectedReturnedValue , actualReturnedValue);
    	
    }
    

    
 //-------------------------------------------------------------------------------------------------------------------------------------------------------------------------//   
    
    // Vertex is present in the graph
    @Test
    public void testRemoveVertexPresent() {
    	Graph<String> graph = emptyInstance();
    	graph.add("A");
    	boolean returnedValue= graph.remove("A");
    
    	assertTrue("The add vertex was already present in the graph. "
    			+ "Hence, remove instance method should have returned false.",  returnedValue);
    }
   
    // Vertex is absent in the graph
    @Test
    public void testRemoveVertexAbsent() {
    	Graph<String> graph = emptyInstance();
    	boolean returnedValue= graph.remove("A");
    
    	assertFalse("The add vertex was absent from the graph. "
    			+ "Hence, remove instance method should have returned true.",  returnedValue);
    }
 
 
    
//-------------------------------------------------------------------------------------------------------------------------------------------------------------------------//   

    // Vertex is present in the graph
    @Test
    public void testVerticesEmptyGraph() {
    	Graph<String> graph = emptyInstance();
    	Set<String> expectedgraphVertices = new HashSet<String>(Arrays.asList());
    	Set<String> returnedgraphVertices = graph.vertices();
        
    	assertEquals("expected new graph to have no vertices",
    			       expectedgraphVertices, returnedgraphVertices);	
    }
    
    // Vertex is absent in the graph
    @Test
    public void testAddVertexAtleastOneVertex() {
    	Graph<String> graph = emptyInstance();
    	graph.add("A");
    	graph.add("B");
    	graph.add("C");
    	graph.add("D");
    	
    	Set<String> returnedgraphVertices = graph.vertices(); 
    	Set<String> expectedgraphVertices = new HashSet<String>(Arrays.asList("A", "B", "C", "D"));
    	
    	assertEquals("expected new graph to have no vertices",
    			expectedgraphVertices, returnedgraphVertices);
    }
 
    
    
//-------------------------------------------------------------------------------------------------------------------------------------------------//
    
    // target is absent.
    @Test
    public void testsourcesAbsent() {
    	Graph<String> graph = emptyInstance();
    	Map<String, Integer> returnedSources = graph.sources("London");
    	Map<String, Integer> expectedSources = new HashMap<String, Integer>();
        
    	assertEquals("expected new graph to have no vertices",
    			       expectedSources, returnedSources);	
    }
    
    // target is present, no incoming neighbour.
    @Test
    public void testsourcesNoIncomingNeighbour() {
    	Graph<String> graph = emptyInstance();
    	graph.add("London");
    	Map<String, Integer> returnedSources = graph.sources("London");
    	
    	Map<String, Integer> expectedSources = new HashMap<String, Integer>();
        
    	assertEquals("expected new graph to have no vertices",
    			       expectedSources, returnedSources);		
    }
    
    // target is present, atleast one incoming neighbour.
    @Test
    public void testsourcesAtleastOneIncomingNeighbour() {
    	Graph<String> graph = emptyInstance();
    	graph.set("Delhi" , "West Bengal", 26);
    	graph.set("Hyderabad" , "West Bengal", 28);
    	graph.set("Bangalore" , "West Bengal", 35);
    	Map<String, Integer> returnedSources = graph.sources("West Bengal");
    	
    	Map<String, Integer> expectedSources = new HashMap<String, Integer>();
    	expectedSources.put("Delhi", 26);
    	expectedSources.put("Hyderabad", 28);
    	expectedSources.put("Bangalore", 35);
    	
    	assertEquals("expected new graph to have no vertices",
    			       expectedSources, returnedSources);	
    }
   
   

//-------------------------------------------------------------------------------------------------------------------------------------------------//
    
    // source is absent.
    @Test
    public void testtargetsAbsent() {
    	Graph<String> graph = emptyInstance();
    	Map<String, Integer> returnedSources = graph.sources("London");
    	Map<String, Integer> expectedSources = new HashMap<String, Integer>();
        
    	assertEquals("expected new graph to have no vertices",
    			       expectedSources, returnedSources);	
    }
    
    // source is present, no outgoing neighbour.
    @Test
    public void testtargetsNoOutgoingNeighbour() {
    	Graph<String> graph = emptyInstance();
    	graph.add("London");
    	Map<String, Integer> returnedSources = graph.targets("London");
    	
    	Map<String, Integer> expectedSources = new HashMap<String, Integer>();
        
    	assertEquals("expected new graph to have no vertices",
    			       expectedSources, returnedSources);		
    }
    
    // source is present, atleast one outgoing neighbour.
    @Test
    public void testsourcesAtleastOneOutgoingNeighbour() {
    	Graph<String> graph = emptyInstance();
    	graph.set("Pune" , "West Bengal", 34);
    	graph.set("Pune" , "Delhi", 25);
    	graph.set("Pune" , "Mumbai", 5);
    	Map<String, Integer> returnedtargets = graph.targets("Pune");
    	
    	Map<String, Integer> expectedtargets = new HashMap<String, Integer>();
    	expectedtargets.put("West Bengal", 34);
    	expectedtargets.put("Delhi", 25);
    	expectedtargets.put("Mumbai", 5);
    	
    	assertEquals("expected new graph to have no vertices",
    			       expectedtargets, returnedtargets);	
    }
   
    
}
