/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;

/**
 * An implementation of Graph.
 * 
 * <p>PS2 instructions: you MUST use the provided rep.
 */
public class ConcreteEdgesGraph<L> implements Graph<L> {
    // Fields
    private final Set<L> vertices = new HashSet<>();
    private final List<Edge<L>> edges = new ArrayList<>();
    
    // Abstraction function:
    //   AF(vertices, edges) = A directed positive edge weight graph 
    //                         such that all the vertices and edges are in this.vertices and this.edges
                             
    // Representation invariant:
    //   For all the edges in this.edges, the head and tail of the edge is present in this.vertices
    //   The weight of all the edges in the graph is positive.
    
    // Safety from rep exposure:
    //   All the fields are private and final
    //   The mutators like set, remove, vertices, sources, targets don't expose ADT's internal rep to the client
    
    
    // Constructor to create a graph with vertices and edges
    public ConcreteEdgesGraph(Set<L> vertices,List<Edge<L>> edges){
    	this.vertices.addAll(vertices);
    	this.edges.addAll(edges);
    }
    
    // Constructor to create an empty graph
    public ConcreteEdgesGraph(){
    	
    }
    
    // checkRep
    // Check that the rep invariant is true
    private void checkRep() {
    	for(Edge<L> edge:edges) {
    		assert vertices.contains(edge.getHead()) && vertices.contains(edge.getTail());
    	}
    }
    
    @Override public boolean add(L vertex) {
    	// if vertex is already present in vertices, return false 
    	// otherwise, add the vertex in vertices and return true
    	if(vertices.contains(vertex)){
    		return false;
    	}else {
    		vertices.add(vertex);
    		checkRep();
    		return true;
    	}
    	
    }
    
    @Override public int set(L source, L target, int weight) {
    	
    	// Case 1: Weight > 0, edge is added or changed.
    	//         If there is an edge between source to target, this implies edge weight is being changed, 
		//         and since edge is immutable we have to delete the old edge from this.edges and add new edge to this.edges
		//         Otherwise, new edge is being added to the graph, create a new edge and add it to this.edges
    	if (weight > 0) {
    		if(hasEdgeBetween(source, target)) {
    			int priorEdgeWeight = changeEdgeWeight(source, target, weight); 
    			checkRep();
    			return priorEdgeWeight;
    		}else{
    			addEdge(source, target, weight);
    			checkRep();
    			return 0;
    		}
    	}
    	
    	// Case 2: weight = 0, Edge is being removed.
    	//         If edge is exist then removes such an edge otherwise does nothing and returns zero
    	if (weight == 0) {
    		int removedEdgeWeight = removeEdge(source, target); // remove an existing edge 
    		checkRep();
    		return removedEdgeWeight;
	
    	}
    	checkRep();
    	return 0;
    }
    
    @Override public boolean remove(L vertex) {
    	// Removes all the incident from vertex or incident to vertex 
    	for(int index = edges.size() - 1; index > -1; index--) {
    		Edge<L> currentEdge = edges.get(index);
    		if(currentEdge.hasEdgeFrom(vertex) || currentEdge.hasEdgeTo(vertex)) {
    			edges.remove(index);		
    		}
    	}
        boolean isDeleted = vertices.remove(vertex); //remove vertex from this.vertices
    	return isDeleted;
    }
    
    @Override public Set<L> vertices() {
    	// Returns a copy of this.vertices
    	Set<L> newVertices = new HashSet<L>(vertices);
    	checkRep();
        return newVertices;
    }
    
    @Override public Map<L, Integer> sources(L target) {
    	// check if the current edge has the target equal to target  if yes then add the source of current edges to sources
    	Map<L, Integer> sources = new HashMap<L, Integer>(); 
    	for(Edge<L> currentEdge:edges) {
    		if(currentEdge.hasEdgeTo(target)) {
    			sources.put(currentEdge.getHead(), currentEdge.getEdgeWeight());
    		}
    	}
    	checkRep();
    	return sources;
    }
    
    @Override public Map<L, Integer> targets(L source) {
    	// check if the current edge has the source equal to source if yes then add the target of current edges to targets
    	Map<L, Integer> targets = new HashMap<L, Integer>(); 
    	for(Edge<L> currentEdge:edges) {
    		if(currentEdge.hasEdgeFrom(source)) {
    			targets.put(currentEdge.getTail(), currentEdge.getEdgeWeight());
    		}
    	}
    	checkRep();
    	return targets;
    }
    
    
    /**
     * Checks whether this graph has a directed edge from head to tail 
     *
     * @param possibleHead, a label
     * @param possibleTail, a label
     * @return returns true if this graph has a directed edge from head to tail, 
     *         otherwise returns false if no edge exists from head to tail
     */
    private boolean hasEdgeBetween(L possibleHead, L possibleTail) {
    	for(Edge<L> currentEdge:this.edges) {
        		if( currentEdge.hasEdgeBetween(possibleHead, possibleTail) ) {
        			return true;
        		}
        }
    	checkRep();
    	return false;
    }
    
    
    /**
     * Remove an edge from this graph from possibleHead to possibleTail if it exits
     * 
     * @param possibleHead, a label
     * @param possibleTail, a label
     * @return the weight of the removed edge, or zero if there was no such edge
     */
    private int removeEdge(L possibleHead, L possibleTail) {
    	// Iterate over this.edges and if there is an edge from possibleHead to possibleTail removes it
    	// and returns true, Otherwise, return false.
    	for (Edge<L> currentEdge:edges) {
    		if(currentEdge.hasEdgeBetween(possibleHead, possibleTail)) {
    			edges.remove(currentEdge);
    			return currentEdge.getEdgeWeight();
    		}
    	}
    	checkRep();
    	return 0;
    }
    
    /**
     * Adds a weighted directed edge from source to target to this graph. 
     * Vertices with given label are added to this graph if they are not 
     * present.
     * If such an edge exist or the new weight is zero or negative
     * then graph is not modified and new edge is not added
     * 
     * @param source, a label of source of the edge being added
     * @param target, a label of target of the edge being added
     * @param weight, weight of the edge, must  be greater than zero
     * @returns true if a directed edge is added from source to target
     *          otherwise, returns false if no edge was added
     */
     private boolean addEdge(L source, L target, Integer weight) {
    	// If source or target are absent from this.vertices, add source and target to this.vertices; 
    	if (hasEdgeBetween(source, target) | weight <= 0) {
    		return false;
    	}else {
    		if(!vertices.contains(source)) {
     			vertices.add(source);
     		}
     		if(!vertices.contains(target)) {
     			vertices.add(target);
     		}
     		edges.add(new Edge<>(source, target, weight)); // add new edge
     		checkRep();
     		return true;
    	}
    	
     }
     
     /**
      * Changes the weight of directed edge from source to target from this graph. 
      * If such an edge does not exist or the new weight is zero or negative
      * then graph is not modified and edge weight is not changed.
      * 
      * @param source, a label of source of the edge being changed
      * @param target, a label of target of the edge being changed
      * @param newWeight, new weight of the edge being changed, must be greater than zero
      * @returns previous weight of the directed edge if the edge is changed,
      *          otherwise, returns zero if there was no such edge
      */
      private int changeEdgeWeight(L source, L target, Integer newWeight) {
     	// If source or target are absent from this.vertices, add source and target to this.vertices; 
    	if(this.hasEdgeBetween(source, target) && newWeight > 0 ) {
  			int previousEdgeWeight = removeEdge(source, target); // remove exiting edge 
  			edges.add(new Edge<L>(source, target, newWeight)); // add new edge
  			checkRep();
  			return previousEdgeWeight;
  		}
    	
    	return 0;
      }
  
    /**
     * Returns a string representation of the graph 
     * 
     * For Example:
     * ConcreteVerticesGraph graph = new ConcreteVerticesGraph();
     * graph.set(California, Delhi, 2000);
     * graph.set(NewYork, California, 48);
     * graph.set(London, NewYork, 379);
     * graph.set(Berlin, London, 248);
     * graph.add("Hong Kong");
     * System.out.println(graph);
     * 
     * Output:
     * California-----(2000)----->Delhi
     * New York-----(48)----->California
     * London-----(379)----->New York
     * Berlin-----(248)----->London
     * Hong Kong
     * 
     * @return a string representation of the graph
     */
     public String toString() {
    	 Set<L> connectedVertices = new HashSet<L>();
    	 
    	// Add edges to result
    	String stringGraph = "";
      	for(Edge<L> edge:edges) {
 
      		connectedVertices.add(edge.getHead());
      		connectedVertices.add(edge.getTail());
      		
      		 stringGraph += edge.toString() + "\n";
      	}
      	
      	// Add those vertices that don't have incoming and outgoing edges
      	Set<L> isolatedVertices = new HashSet<L>(vertices);
      	isolatedVertices.removeIf(item -> connectedVertices.contains(item));
      	for(L vertex:isolatedVertices) {
      		stringGraph += vertex.toString() + "\n";
      	}
      	
      	return stringGraph.trim();
     }
     
      
    
}

/**
 * This class is internal to the rep of ConcreteEdgesGraph.
 * This is a immutable class that represents an edge from a source vertex to a target vertex having a
 * positive edge weight.
 * <p>PS2 instructions: the specification and implementation of this class is
 * up to you.
 */
class Edge<L> {
    
   // fields
	private final L head;
	private final L tail;
	private final Integer weight;
	
    
    // Abstraction function:
    //   AF(source, target, weight) = An edge from vertex this.source to vertex this.target vertex with edge weight this.weight
    
	// Representation invariant:
    //   this.source and this.target are non empty strings.
	//   this.weight is positive.
    
	// Safety from rep exposure:
    //   all the fields are private and final.
	//   This class has no mutators and producer.
	//   All the observers and creators don't expose the internal representation.
    
    // constructor
    Edge(L head, L tail, int weight){
    	this.head = head;
    	this.tail = tail;
    	this.weight = weight;
    	checkRep();
    }
    
    // checkRep
    // Check that the rep invariant is true
    private void checkRep() {
    	assert !head.equals("");
    	assert !tail.equals("");
    	assert weight > 0;
    }
    
    /** 
     * Returns the head of this directed edge.
     * 
     * @return the source vertex of this directed edge.
     */
    public L getHead() {
    	return head;
    }
     
    
    /** 
     * Returns the tail of this directed edge 
     * 
     * @return the target vertex of this directed edge 
     */
    public L getTail() {
    	return tail;
    }
    
    
    /** 
     * Returns the weight of this directed edge
     * 
     * @return the weight of this directed edge
     */
    public Integer getEdgeWeight() {
    	return weight;
    }
    
    
    /**
     * This method checks whether this directed edge has its head and tail 
     * equal to the given posibleHead to posibleTail respectively.
     *
     * @param possibleHead, a label
     * @param possibleTail, a label
     * @return returns true if this edge has its head & tail vertex equal 
     *         to the given possibleHead & possibleTail, otherwise returns false
     */
    public boolean hasEdgeBetween(L possibleHead, L possibleTail) {
    	return this.head.equals(possibleHead) && this.tail.equals(possibleTail);
    }
    
    
    /**
     * This method checks whether this directed edge has its head 
     * equal to the given possibleHead.
     * 
     * @param posibleHead, a label
     * @return returns true if this edge has its head equal 
     *         to the posibleHead, otherwise returns false
     */
    public boolean hasEdgeFrom(L posibleHead) {
    	return this.head.equals(posibleHead);
    }
    
    
    /**
     * This method checks whether this directed edge has its tail
     * equal to the given posibleTail.
     * 
     * @param vertex, a label
     * @return returns true if this directed edge has its tail equal 
     *         to the vertex, otherwise returns false
     */
    public boolean hasEdgeTo(L vertex) {
    	return this.tail.equals(vertex);
    }
    
    
    /**
     * Returns a string representation of the edge 
     * 
     * For Example:
     * Edge edge = new Edge(California, New York, 48);
     * System.println(edge);
     * 
     * Output:
     * California-----(weight)----->New York
     * 
     * @return a string representation of the edge
     */
    public String toString() {
    	return String.format("%s-----(%d)----->%s",this.head, this.weight, this.tail);
    }
    
}
