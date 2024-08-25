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
    @Override
    public Graph<String> emptyInstance() {
	return new ConcreteVerticesGraph<String>();
    }

    /*
     * Testing ConcreteVerticesGraph...
     */

    // Testing strategy for ConcreteVerticesGraph.toString()
    // partition on number of vertices: zero, at-least one
    // partition on number of edges: zero, at-least one
    // partition on number of isolated vertices: zero, at-least one

    // tests for ConcreteVerticesGraph.toString()

    // zero vertices, zero edges, zero isolated vertex
    @Test
    public void testtoStringZeroVertexZeroEdges() {
	ConcreteVerticesGraph<String> graph = new ConcreteVerticesGraph<>();
	String returnedValue = graph.toString();
	String expectedValue = "";

	assertEquals("Returned string does not matches the expected string", returnedValue, expectedValue);
    }

    // atleast one vertex, zero edge, at-least isolated vertex
    @Test
    public void testtoStringZeroVertexAtleastOneEdge() {
	ConcreteVerticesGraph<String> graph = new ConcreteVerticesGraph<>();
	graph.add("California");
	graph.add("New York");
	String returnedValue = graph.toString();
	String expectedValue = String.join("\n", "California", "New York");

	assertEquals("Returned string does not matches the expected string", returnedValue, expectedValue);
    }

    // at-least one vertex, at-least one edge, zero isolated vertices
    @Test
    public void testtoStringAtleastVertexAtleastOneEdge() {
	ConcreteVerticesGraph<String> graph = new ConcreteVerticesGraph<>();
	graph.set("California", "Delhi", 2000);
	graph.set("NewYork", "California", 48);
	graph.set("London", "NewYork", 379);
	graph.set("Berlin", "London", 248);
	String returnedValue = graph.toString();

	String expectedValue = String.join("\n", "NewYork-----(48)----->California", "California-----(2000)----->Delhi",
		"London-----(379)----->NewYork", "Berlin-----(248)----->London");

	assertEquals("Returned string does not matches the expected string", expectedValue, returnedValue);
    }

    // at-least one vertex, at-least one edge, zero isolated vertices
    @Test
    public void testtoStringAtleastVertexAtleastOneEdgeOneIsolatedVertex() {
	ConcreteVerticesGraph<String> graph = new ConcreteVerticesGraph<>();
	graph.set("California", "Delhi", 2000);
	graph.set("NewYork", "California", 48);
	graph.set("London", "NewYork", 379);
	graph.set("Berlin", "London", 248);
	graph.add("Hong Kong");
	String returnedValue = graph.toString();

	String expectedValue = String.join("\n", "NewYork-----(48)----->California", "California-----(2000)----->Delhi",
		"London-----(379)----->NewYork", "Berlin-----(248)----->London", "Hong Kong");

	assertEquals("Returned string does not matches the expected string", returnedValue, expectedValue);
    }

    /*
     * Testing Vertex...
     */

    // Testing strategy for Vertex

    // addIncomingEdge(), deleteIncomingEdge(), changeIncomingEdgeWeight,
    // hasEdgeFrom
    // Partition on weather there was an incoming edge from source:
    // Presence of an incoming edge from source, Absence of an incoming edge from
    // source

    // addIncomingEdge(), addOutgoingEdge(), changeIncomingEdgeWeight(),
    // changeOutgoingEdgeWeight()
    // weight is zero, weight is greater than zero

    // addOutgoingEdge(), deleteOuthoingEdge(), changeOutgoingEdgeWeight, hasEdgeTo
    // Partition on weather there was an outgoing edge to target:
    // Presence of an outgoing edge to target, Absence of an incoming edge from
    // target

    // tests for operations of Vertex
    // addEdgeFrom() hasEdgeTo() hasEdgeFrom() getIncomingEdgeWeight()
    // getOutgoingEdgeWeight()
    // Absence of incoming edge from source, weight is greater than zero
    // Presence of incoming edge from source, weight is greater than zero
    @Test
    public void testEdgeFromWeightPositive() {
	String testVertexName = "California";
	String incomingVertex = "NewYork";
	int weight = 48;

	Vertex<String> California = new Vertex<>(testVertexName);
	this.testNoEdgeFrom(California, incomingVertex, testVertexName);

	boolean actualResult1 = California.addEdgeFrom(incomingVertex, weight);
	this.testAddEdgeFromNewEdgeAddedHelper(California, incomingVertex, testVertexName, weight, actualResult1);

	boolean actualResult2 = California.addEdgeFrom(incomingVertex, weight);
	this.testAddEdgeFromEdgeAlreadyPresentHelper(California, incomingVertex, testVertexName, weight, actualResult2);

    }

    // addEdgeFrom() hasEdgeTo() hasEdgeFrom() getIncomingEdgeWeight()
    // getOutgoingEdgeWeight()
    // Absence of incoming edge from source, weight is zero
    @Test
    public void testEdgeFromWeightZeroEdgeAbsentFirst() {
	String testVertexName = "California";
	String incomingVertex = "NewYork";
	int weight = 0;

	Vertex<String> California = new Vertex<>("California");
	this.testNoEdgeFrom(California, incomingVertex, testVertexName);

	boolean actualResult1 = California.addEdgeFrom("NewYork", weight);
	this.testAddEdgeFromZeroWeightHelper(California, incomingVertex, testVertexName, weight, actualResult1);

    }

    // addEdgeFrom() hasEdgeTo() hasEdgeFrom() getIncomingEdgeWeight()
    // getOutgoingEdgeWeight()
    // Presence of incoming edge from source, weight is zero
    @Test
    public void testEdgeFromWeightZeroEdgePresentFirst() {
	String testVertexName = "NewYork";
	String incomingVertex = "California";
	int weight = 48;
	int zeroWeight = 0;

	Vertex<String> NewYork = new Vertex<>("NewYork");
	this.testNoEdgeFrom(NewYork, incomingVertex, testVertexName);

	boolean actualResult1 = NewYork.addEdgeFrom("California", weight);
	this.testAddEdgeFromNewEdgeAddedHelper(NewYork, incomingVertex, testVertexName, weight, actualResult1);

	boolean actualResult2 = NewYork.addEdgeFrom("California", zeroWeight);
	this.testAddEdgeFromEdgeAlreadyPresentHelper(NewYork, incomingVertex, testVertexName, weight, actualResult2);

    }

    // addEdgeTo() hasEdgeTo() hasEdgeFrom() getIncomingEdgeWeight()
    // getOutgoingEdgeWeight()
    // Absence of outgoing edge from target, weight is greater than zero
    // Presence of outgoing edge from target, weight is greater than zero
    @Test
    public void testEdgeToWeightPositive() {
	String testVertexName = "NewYork";
	String outgoingVertex = "California";
	int weight = 48;

	Vertex<String> NewYork = new Vertex<>(testVertexName);
	this.testNoEdgeTo(NewYork, testVertexName, outgoingVertex);

	boolean actualResult1 = NewYork.addEdgeTo(outgoingVertex, weight);
	this.testAddEdgeToNewEdgeAddedHelper(NewYork, testVertexName, outgoingVertex, weight, actualResult1);

	boolean actualResult2 = NewYork.addEdgeTo("California", 48);
	this.testAddEdgeToEdgeAlreadyPresentHelper(NewYork, testVertexName, outgoingVertex, weight, actualResult2);

    }

    // addEdgeTo() hasEdgeTo() hasEdgeFrom() getIncomingEdgeWeight()
    // getOutgoingEdgeWeight()
    // Absence of outgoing edge from source, weight is zero
    @Test
    public void testEdgeToWeightZeroEdgeAbsentFirst() {
	String testVertexName = "NewYork";
	String outgoingVertex = "California";
	int zeroWeight = 0;

	Vertex<String> NewYork = new Vertex<>(testVertexName);
	this.testNoEdgeTo(NewYork, testVertexName, outgoingVertex);

	boolean actualResult1 = NewYork.addEdgeTo("California", 0);
	this.testAddEdgeToZeroWeightHelper(NewYork, testVertexName, outgoingVertex, zeroWeight, actualResult1);

    }

    // addEdgeTo() hasEdgeTo() hasEdgeFrom() getIncomingEdgeWeight()
    // getOutgoingEdgeWeight()
    // Presence of outgoing edge from source, weight is zero
    @Test
    public void testEdgeToWeightZeroEdgePresentFirst() {
	String testVertexName = "NewYork";
	String outgoingVertex = "California";
	int weight = 48;

	Vertex<String> NewYork = new Vertex<>(testVertexName);
	this.testNoEdgeTo(NewYork, testVertexName, outgoingVertex);

	boolean actualResult1 = NewYork.addEdgeTo("California", 48);
	this.testAddEdgeToNewEdgeAddedHelper(NewYork, testVertexName, outgoingVertex, weight, actualResult1);

	boolean actualResult2 = NewYork.addEdgeTo("California", 0);
	this.testAddEdgeToZeroWeightHelper(NewYork, testVertexName, outgoingVertex, weight, actualResult2);
	assertTrue("There should be an edge to California from New York", NewYork.hasEdgeTo("California"));

    }

    // deleteEdgeFrom() hasEdgeTo() hasEdgeFrom() getIncomingEdgeWeight()
    // getOutgoingEdgeWeight()
    // Presence of incoming edge from source
    @Test
    public void testDeleteEdgeFromPresentEdge() {
	String testVertexName = "California";
	String incomingVertex = "NewYork";
	int weight = 48;

	Vertex<String> California = new Vertex<>("California");
	this.testNoEdgeFrom(California, incomingVertex, testVertexName);

	boolean actualResult1 = California.addEdgeFrom("NewYork", 48);
	this.testAddEdgeFromNewEdgeAddedHelper(California, incomingVertex, testVertexName, weight, actualResult1);

	boolean actualResult2 = California.deleteEdgeFrom("NewYork");

	assertTrue(
		"Expected true because a edge was deleted from New York to California when an edge already existed previously",
		actualResult2);
	this.testNoEdgeFrom(California, incomingVertex, testVertexName);

    }

    // deleteEdgeFrom() hasEdgeTo() hasEdgeFrom() getIncomingEdgeWeight()
    // getOutgoingEdgeWeight()
    // Presence of incoming edge from source
    @Test
    public void testDeleteEdgeFromAbsentEdge() {
	String testVertexName = "California";
	String incomingVertex = "NewYork";

	Vertex<String> California = new Vertex<>("California");

	boolean actualResult2 = California.deleteEdgeFrom("NewYork");
	assertFalse(
		"Expected false because an edge was deleted from New York to California when no edge existed previously",
		actualResult2);
	this.testNoEdgeFrom(California, incomingVertex, testVertexName);

    }

    // deleteEdgeFrom() hasEdgeTo() hasEdgeFrom() getIncomingEdgeWeight()
    // getOutgoingEdgeWeight()
    // Presence of incoming edge from source
    @Test
    public void testDeleteEdgeToPresentEdge() {
	String testVertexName = "NewYork";
	String outgoingVertex = "California";
	int weight = 48;

	Vertex<String> NewYork = new Vertex<>("NewYork");
	this.testNoEdgeTo(NewYork, testVertexName, outgoingVertex);

	boolean actualResult1 = NewYork.addEdgeTo("California", 48);
	this.testAddEdgeToNewEdgeAddedHelper(NewYork, testVertexName, outgoingVertex, weight, actualResult1);

	boolean actualResult2 = NewYork.deleteEdgeTo("California");
	assertTrue(
		"Expected true because an edge was deleted from New York to California when an edge already existed previously",
		actualResult2);
	this.testNoEdgeTo(NewYork, testVertexName, outgoingVertex);

    }

    // deleteEdgeFrom() hasEdgeTo() hasEdgeFrom() getIncomingEdgeWeight()
    // getOutgoingEdgeWeight()
    // Presence of incoming edge from source
    @Test
    public void testDeleteEdgeToAbsentEdge() {
	String testVertexName = "NewYork";
	String outgoingVertex = "California";

	Vertex<String> NewYork = new Vertex<>("NewYork");

	boolean actualResult2 = NewYork.deleteEdgeTo("California");
	assertFalse(
		"Expected false because an edge was deleted from New York to California when no edge existed previously",
		actualResult2);
	this.testNoEdgeTo(NewYork, testVertexName, outgoingVertex);

    }

    // changeIncomingEdgeWeight
    // Presence of incoming edge from source, new weight is greater than zero.
    // Presence of incoming edge from source, new weight is zero.
    @Test
    public void testChangeEdgeWeightFromPresentEdge() {
	String testVertexName = "California";
	String outgoingVertex = "NewYork";

	Vertex<String> California = new Vertex<>("California");
	this.testNoEdgeFrom(California, outgoingVertex, testVertexName);

	boolean actualResult1 = California.addEdgeFrom("NewYork", 48);
	this.testAddEdgeFromNewEdgeAddedHelper(California, outgoingVertex, testVertexName, 48, actualResult1);

	boolean actualResult2 = California.changeEdgeWeightFrom("NewYork", 24);
	assertTrue(
		"Expected true because edgeweight of edge from New York to California was changed to 24 from 48 when an edge already existed previously",
		actualResult2);
	this.testEdgeFrom(California, outgoingVertex, testVertexName, 24);

	boolean actualResult3 = California.changeEdgeWeightFrom("NewYork", 0);
	assertFalse(
		"Expected false because edgeweight of edge from New York to California was changed to zero from 24 when an edge already existed previously",
		actualResult3);
	this.testEdgeFrom(California, outgoingVertex, testVertexName, 24);

    }

    // changeIncomingEdgeWeight
    // Absence of incoming edge from source, new weight is greater than zero.
    // Absence of incoming edge from source, new weight is zero.
    @Test
    public void testChangeEdgeWeightFromAbsentEdge() {
	String testVertexName = "California";
	String incomingVertex = "NewYork";

	Vertex<String> California = new Vertex<>("California");
	this.testNoEdgeFrom(California, incomingVertex, testVertexName);

	boolean actualResult2 = California.changeEdgeWeightFrom("NewYork", 24);
	this.testNoEdgeFrom(California, incomingVertex, testVertexName);
	assertFalse(
		"Expected false because edgeweight of edge from New York to California was changed to 24 when no edge already existed previously",
		actualResult2);

	boolean actualResult3 = California.changeEdgeWeightFrom("NewYork", 0);
	this.testNoEdgeFrom(California, incomingVertex, testVertexName);
	assertFalse(
		"Expected false because edgeweight of edge from New York to California was changed to zero when an edge already existed previously",
		actualResult3);

    }

    // changeIncomingEdgeWeight
    // Presence of incoming edge from source, new weight is greater than zero.
    // Presence of incoming edge from source, new weight is zero.
    @Test
    public void testChangeEdgeWeightToPresentEdge() {
	String testVertexName = "NewYork";
	String outgoingVertex = "California";
	int weight = 48;

	Vertex<String> NewYork = new Vertex<>("NewYork");

	assertFalse("There should be no edge to California from New York", NewYork.hasEdgeTo("California"));
	assertEquals("Expected no edge to California from New York  and thus zero edge weight", 0,
		NewYork.getOutgoingEdgeWeight("California"));
	this.testNoEdgeTo(NewYork, testVertexName, outgoingVertex);

	boolean actualResult1 = NewYork.addEdgeTo("California", 48);
	this.testAddEdgeToNewEdgeAddedHelper(NewYork, testVertexName, outgoingVertex, weight, actualResult1);

	boolean actualResult2 = NewYork.changeEdgeWeightTo("California", 24);
	this.testEdgeTo(NewYork, testVertexName, outgoingVertex, 24);
	assertTrue(
		"Expected true because edgeweight of edge from New York to California was changed to 24 from 48 when an edge already existed previously",
		actualResult2);

	boolean actualResult3 = NewYork.changeEdgeWeightTo("California", 0);
	this.testEdgeTo(NewYork, testVertexName, outgoingVertex, 24);
	assertFalse(
		"Expected false because edgeweight of edge from New York to California was changed to zero from 24 when an edge already existed previously",
		actualResult3);

    }

    // changeIncomingEdgeWeight
    // Absence of incoming edge from source, new weight is greater than zero.
    // Absence of incoming edge from source, new weight is zero.
    @Test
    public void testChangeEdgeWeightToAbsentEdge() {
	String testVertexName = "NewYork";
	String outgoingVertex = "California";

	Vertex<String> NewYork = new Vertex<>("NewYork");
	this.testNoEdgeTo(NewYork, testVertexName, outgoingVertex);

	boolean actualResult2 = NewYork.changeEdgeWeightTo("California", 24);
	this.testNoEdgeTo(NewYork, testVertexName, outgoingVertex);
	assertFalse(
		"Expected false because edgeweight of edge from New York to California was changed to 24 when no edge already existed previously",
		actualResult2);

	boolean actualResult3 = NewYork.changeEdgeWeightTo("California", 0);
	this.testNoEdgeTo(NewYork, testVertexName, outgoingVertex);
	assertFalse(
		"Expected false because edgeweight of edge from New York to California was changed to zero when an edge already existed previously",
		actualResult3);

    }

    // isolated vertex
    @Test
    public void testVertextoStringIsolated() {
	Vertex<String> vertex = new Vertex<>("California");
	String returnedValue = vertex.toString();
	String expectedValue = "California";

	assertEquals("Returned string does not matches the expected string", returnedValue, expectedValue);
    }

    // isolated vertex
    @Test
    public void testVertextoStringConnected() {
	Vertex<String> vertex = new Vertex<>("California");
	vertex.addEdgeFrom("NewYork", 24);
	vertex.addEdgeTo("Boston", 48);
	String returnedValue = vertex.toString();

	String expectedValue = String.join("\n", "NewYork-----(24)----->California", "California-----(48)----->Boston");

	assertEquals("Returned string does not matches the expected string", returnedValue, expectedValue);
    }

    // helper
    private void testAddEdgeFromEdgeAlreadyPresentHelper(Vertex<String> testVertex, String incomingVertex,
	    String testVertexName, int weight, boolean actualResult) {

	assertFalse(String.format(
		"Expected false because an edge was added from %s to %s when an edge already existed previously",
		incomingVertex, testVertexName), actualResult);
	assertTrue(String.format("There should be an edge from %s to %s", incomingVertex, testVertexName),
		testVertex.hasEdgeFrom(incomingVertex));
	assertEquals(String.format("Expected an edge from %s to %s and of weight %s", incomingVertex, testVertexName,
		weight), weight, testVertex.getIncomingEdgeWeight(incomingVertex));
    }

    private void testAddEdgeFromNewEdgeAddedHelper(Vertex<String> testVertex, String incomingVertex,
	    String testVertexName, int weight, boolean actualResult) {

	assertTrue(String.format(
		"Expected true because an new edge was added from %s to %s, when no edge existed previously",
		incomingVertex, testVertexName), actualResult);
	assertTrue(String.format("There should be an edge from %s to %s", incomingVertex, testVertexName),
		testVertex.hasEdgeFrom(incomingVertex));
	assertEquals(String.format("Expected an edge from %s to %s and of weight %s", incomingVertex, testVertexName,
		weight), weight, testVertex.getIncomingEdgeWeight(incomingVertex));
    }

    private void testAddEdgeFromZeroWeightHelper(Vertex<String> testVertex, String incomingVertex,
	    String testVertexName, int weight, boolean actualResult) {

	assertFalse(String.format("Expected false because no edge was added from %s to %s ", incomingVertex,
		testVertexName), actualResult);

	assertEquals(String.format("Expected no edge from %s to %s and thus zero edge weight ", incomingVertex,
		testVertexName, weight), weight, testVertex.getIncomingEdgeWeight(incomingVertex));
    }

    private void testAddEdgeToEdgeAlreadyPresentHelper(Vertex<String> testVertex, String testVertexName,
	    String outgoingVertex, int weight, boolean actualResult) {

	assertFalse(String.format(
		"Expected false because an edge was added from %s to %s when an edge already existed previously",
		testVertexName, outgoingVertex), actualResult);
	assertTrue(String.format("There should be an edge from %s to %s", testVertexName, outgoingVertex),
		testVertex.hasEdgeTo(outgoingVertex));
	assertEquals(String.format("Expected an edge from %s to %s and of weight %s", testVertexName, outgoingVertex,
		weight), weight, testVertex.getOutgoingEdgeWeight(outgoingVertex));
    }

    private void testAddEdgeToNewEdgeAddedHelper(Vertex<String> testVertex, String testVertexName,
	    String outgoingVertex, int weight, boolean actualResult) {

	assertTrue(String.format(
		"Expected true because a new edge was added from %s to %s, when no edge existed previously",
		testVertexName, outgoingVertex), actualResult);
	assertTrue(String.format("There should be an edge from %s to %s", testVertexName, outgoingVertex),
		testVertex.hasEdgeTo(outgoingVertex));
	assertEquals(String.format("Expected an edge from %s to %s and of weight %s", testVertexName, outgoingVertex,
		weight), weight, testVertex.getOutgoingEdgeWeight(outgoingVertex));
    }

    private void testAddEdgeToZeroWeightHelper(Vertex<String> testVertex, String testVertexName, String outgoingVertex,
	    int weight, boolean actualResult) {

	assertFalse(
		String.format("Expected false, because edge weight was zero , thus no edge was added from %s to %s.",
			testVertexName, outgoingVertex),
		actualResult);

	assertEquals(String.format("Expected no edge from %s to %s and thus, zero edge weight", testVertexName,
		outgoingVertex), weight, testVertex.getOutgoingEdgeWeight(outgoingVertex));
    }

    private void testNoEdgeFrom(Vertex<String> testVertex, String incomingVertex, String testVertexName) {
	assertFalse(String.format("There should be no edge from %s to %s", incomingVertex, testVertexName),
		testVertex.hasEdgeFrom(incomingVertex));
	assertEquals(String.format("Expected no edge from %s to %s and thus zero edge weight", incomingVertex,
		testVertexName), 0, testVertex.getIncomingEdgeWeight(incomingVertex));
    }

    private void testNoEdgeTo(Vertex<String> testVertex, String testVertexName, String outgoingVertex) {
	assertFalse(String.format("There should be no edge from %s to %s", testVertexName, outgoingVertex),
		testVertex.hasEdgeTo(outgoingVertex));
	assertEquals(String.format("Expected no edge from %s to %s and thus zero edge weight", testVertexName,
		outgoingVertex), 0, testVertex.getOutgoingEdgeWeight(outgoingVertex));
    }

    private void testEdgeFrom(Vertex<String> testVertex, String incomingVertex, String testVertexName, int weight) {
	assertTrue(String.format("There should be an edge from %s to %s", incomingVertex, testVertexName),
		testVertex.hasEdgeFrom(incomingVertex));
	assertEquals(String.format("Expected edge from %s to %s and thus, weight %s", incomingVertex, testVertexName,
		weight), weight, testVertex.getIncomingEdgeWeight(incomingVertex));
    }

    private void testEdgeTo(Vertex<String> testVertex, String testVertexName, String outgoingVertex, int weight) {
	assertTrue(String.format("There should be an edge from %s to %s", testVertexName, outgoingVertex),
		testVertex.hasEdgeTo(outgoingVertex));
	assertEquals(String.format("Expected no edge from %s to %s and thus, weight %s", testVertexName, outgoingVertex,
		weight), weight, testVertex.getOutgoingEdgeWeight(outgoingVertex));
    }

}
