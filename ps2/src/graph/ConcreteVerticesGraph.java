/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;

/**
 * An implementation of Graph.
 * 
 * <p>PS2 instructions: you MUST use the provided rep.
 */
public class ConcreteVerticesGraph implements Graph<String> {
    
    private final List<Vertex> vertices = new ArrayList<>();
    
    // Abstraction function:
    //  AF(vertices) = A graph with all its vertices in this.vertices and 
    //                 for each vertex in this.vertices, there is a directed edge from all the vertices in 
    //                 vertex.incomingVertices to this vertex and a directed edge from vertex to each of  
    //                 the vertices in vertex.outgoingVertices.
    
    // Representation invariant:
    //   1. All the vertices in this.vertices are unique and have a non empty string as their name
    //   2. For every pair x,w in y.incomingVertices, there exist a pair y,w in x.outgoingVertices
    //      where x,y are a present in this.vertices, and w is a integer greater than zero
    //   3. For every pair x,w in y.outgoingVertices, there exist a pair y,w in x.incomingVertices
    //      where x,y are a Vertex type objects present in this.vertices, and w is a integer greater than zero
  
    // Safety from rep exposure:
    //   TODO
    
    // constructor
    // ConcreteVerticesGraph() to create an empty graph
    // ConcreteVertices(List<Vertex> vertices) to create a graph with vertices and edges
    
    // TODO checkRep
    
    @Override public boolean add(String vertex) {
    	// create a Vertex object and add it to this.vertices
        return addVertex(vertex);
    }
    
    @Override public int set(String source, String target, int weight) {
    	// Case1: weight > 0; An existing edge is being changed or a new edge is being added.
	    	// Check whether there exist an edge, if yes then an existing edge is being changed
	    	// otherwise, a new edge is being added
    	if(weight > 0) {
    		if(hasEdgeBetween(source,target)) {
    			int oldWeight = getEdgeWeight(source, target);
    	    	changeEdgeWeight(source, target, weight);  
    	    	return oldWeight;
    	    }else {
    	    	addEdge(source, target, weight);
    	    	return 0;
    	    }
    	}
    	
    	// Case2: weight = 0; An existing edge is being deleted.
	    	// Check whether there exist an edge from source to target,
    	if(weight == 0){
    		int oldWeight = getEdgeWeight(source, target);
    		deleteEdge(source,target);
    	    return oldWeight;
    	}
    	
    	return 0;
    }
    
    @Override public boolean remove(String vertex) {
    	// if the vertex to be removed is absent from this.vertices then return false
    	if(hasVertex(vertex)) {
    		// find the index of the vertex to be removed
    		int index = getVertexIndex(vertex);
    		Vertex vertexToBeRemoved = this.vertices.get(index);
    		
    		// Delete all the incoming edges to this vertex and delete all the outgoing edges to this vertex 
    		// and then delete the vertex itself
    		vertexToBeRemoved.deleteAllOutgoingEdges();
    		vertexToBeRemoved.deleteAllIncomingEdges();
    		vertices.remove(index);
    		return true;
    		
    	}else {	
    		return false;
    		
    	}
    }
    
    @Override public Set<String> vertices() {
    	// Create a list of vertices by iterating on this.vertices
    	Set<String> verticesName = new HashSet<String>();
    	for(Vertex vertex:vertices) {
    		verticesName.add(vertex.getName());
    	}
        return verticesName;
    }
    
    @Override public Map<String, Integer> sources(String target) {
    	// Find the index of target and return a copy of target.incomingVertices
    	if (hasVertex(target)) {
    		int index = getVertexIndex(target);
    		Vertex targetVertex = vertices.get(index);
    		return targetVertex.getIncomingEdges();
    	}else {
    		return new HashMap<String, Integer>();
    	}
    }
    
    @Override public Map<String, Integer> targets(String source) {
    	// Find the index of source and return a copy of target.outingVertices 
    	if (hasVertex(source)) {
    		int index = getVertexIndex(source);
    		Vertex sourceVertex = vertices.get(index);
    		return sourceVertex.getOutgoingEdges();
    	}else {
    		return new HashMap<String, Integer>();
    	}
    }
    
    /**
     * Returns the weight of the directed edge from source to target from the graph
     * If there exist no directed edge between source to target then it 
     * return -1
     * 
     * @param source, a label of source of the edge
     * @param target, a label of target of the edge
     * @returns returns weight of the directed edge from source to target (if it exist)
     *          otherwise, returns -1
     */
     private int getEdgeWeight(String source, String target) {
   	  	if(hasEdgeBetween(source,target)){
   	  		// Find the index of source from this.vertices  
          	int sourceIndex = getVertexIndex(source);
          	Vertex sourceVertex = vertices.get(sourceIndex);
          	 return sourceVertex.getOutgoingEdgeWeight(target);
     	 }
   	  	return -1;
   	    
     }
    
    /**
     * Adds a weighted directed edge from source to target into the graph
     * If the given weight is positive and there exist no directed edge between 
     * source to target then it adds source or target vertex if they don't exist 
     * and adds an edge from source to target.
     * 
     * @param source, a label of source of the edge
     * @param target, a label of target of the edge
     * @returns true if a directed edge is added from source to target
     *          otherwise, returns false
     */
     private boolean addEdge(String source, String target, Integer weight) {
    	if(hasEdgeBetween(source,target) || weight <= 0) {
    		 return false;
    	 }
    	
    	// Check whether the current vertex is a source or a target vertex
    	// Create new source or target vertex if they don't exist in this.vertices. Keep track of there indices
    	addVertex(source);
    	addVertex(target);
    	int sourceIndex = getVertexIndex(source);
    	int targetIndex = getVertexIndex(target);
    	Vertex sourceVertex = vertices.get(sourceIndex);
    	Vertex targetVertex = vertices.get(targetIndex);
    	
    	// Add to the source vertex an outgoing edge from source vertex  
    	// Add to the target vertex an incoming edge from source vertex 
    	sourceVertex.addOutgoingEdge(targetVertex, weight);
    	targetVertex.addIncomingEdge(sourceVertex, weight);
    	return true;
     }
     
     /**
      * Adds a vertex into the graph if it is not already present in 
      * the graph and returns its position in this.vertices
      * 
      * @param vertex, a label of source of the edge
      * @returns true if a new vertex is added 
      *          otherwise, returns false
      */
      private boolean addVertex(String vertex) {
     	if(hasVertex(vertex)) {
     		return false;
     	}
     	vertices.add(new Vertex(vertex));
     	return true;
      }
      
     /**
      * Deletes the weighted directed edge from source to target into the graph
      * If there exist no directed edge between source to target then it 
      * return false
      * 
      * @param source, a label of source of the edge
      * @param target, a label of target of the edge
      * @returns true if a directed edge is deleted from source to target
      *          otherwise, returns false
      */
      private boolean deleteEdge(String source, String target) {
    	  if(!hasEdgeBetween(source,target)) {
      		 return false;
      	  }
    	  
    	  // Find the indices of source and target from this.vertices  
      	  int sourceIndex = getVertexIndex(source);
      	  int targetIndex = getVertexIndex(target);
      	  Vertex sourceVertex = vertices.get(sourceIndex);
      	  Vertex targetVertex = vertices.get(targetIndex);
      	
          // Delete the target,weight pair from sourceVertex.incomingVertex and
 	      // source,weight pair from targetVertex.outgoingVertex
      	  sourceVertex.deleteOutgoingEdge(targetVertex);
      	  targetVertex.deleteIncomingEdge(sourceVertex);
      	  return true;
      }
    
  
     /**
      * Deletes the weighted directed edge from source to target into the graph
      * If there exist no directed edge between source to target then it 
      * return false
      * 
      * @param source, a label of source of the edge
      * @param target, a label of target of the edge
      * @returns true if a directed edge is deleted from source to target
      *          otherwise, returns false
      */
      private boolean changeEdgeWeight(String source, String target, Integer Weight) {
     	 if(!hasEdgeBetween(source,target)) {
       		 return false;
       	 }
     	
	     // Find the indices of source and target from this.vertices
       	 int sourceIndex = getVertexIndex(source);
       	 int targetIndex = getVertexIndex(target); 	
       	 Vertex sourceVertex = vertices.get(sourceIndex);
       	 Vertex targetVertex = vertices.get(targetIndex);
       	
        // Change the value of sourceVertex.outgoingVertex[target] to new weight and
	    // that of targetVertex.incomingVertex[source] to new weight.
       	 sourceVertex.changeOutgoingEdge(targetVertex, Weight);
       	 targetVertex.changeIncomingEdge(sourceVertex, Weight);
       	 return true;
       }
   
    /**
     * Checks whether this graph has an directed edge between possibleHead 
     * vertex to possibleTail vertex
     * 
     * @param possibleHead, a label for head
     * @param possibleTail, a label for tail
     * @returns returns true if there exist a directed weighted edge from 
     *          possibleHead to possibleTail, otherwise returns false
     */
    private boolean hasEdgeBetween(String possibleHead, String possibleTail) {
    	int headIndex = getVertexIndex(possibleHead);
    	int tailIndex = getVertexIndex(possibleTail);
        
    	if(headIndex == -1 || tailIndex == -1) {
    		return false;
    	}
    	
    	Vertex head = this.vertices.get(headIndex);
    	Vertex tail = this.vertices.get(tailIndex);
    	
    	return head.hasEdgeTo(tail.getName()) && tail.hasEdgeFrom(head.getName()); 
    }
    
    /**
     * Returns the position of vertex within the this.vertices list having 
     * its name equal to possibleVertex
     * 
     * @param possibleVertex, a label of vertex
     * @return returns the position of vertex within this.vertices 
     *         such that 0 <= position < this.vertices.size()
     *         otherwise, returns -1 if vertex is absent from this.vertices
     */
    private int getVertexIndex(String possibleVertex) {
    	for(int index = 0; index < vertices.size(); index++) {
    		Vertex vertex = vertices.get(index);
    		if(vertex.getName().equals(possibleVertex)) {
    			return index;
    		}
    	}
    	return -1;
    }
    
    /**
     * Checks whether this graph has a vertex with label equal to
     * possibleVertexName
     * 
     * @param possibleVertex, a label for vertex
     * @returns returns true if there exist a vertex with name equal to
     *          possibleVertexName in the graph, otherwise returns false
     */
    private boolean hasVertex(String possibleVertexName) {
    	for(Vertex currentVertex:vertices) {
    		if(currentVertex.getName().equals(possibleVertexName)) {
    			return true;
    		}
    	}
    	return false;
    }
    
    // TODO toString()
    
}

/**
 * TODO specification
 * Mutable.
 * This class is internal to the rep of ConcreteVerticesGraph.
 * 
 * <p>PS2 instructions: the specification and implementation of this class is
 * up to you.
 */
class Vertex {
    
    // fields
	private final String name;                                                                 // name of the vertex
	private final Map<String, Vertex>  incomingVertices = new HashMap<String, Vertex>();                
	private final Map<String, Vertex>  outgoingVertices = new HashMap<String, Vertex>(); 
	private final Map<String, Integer> incomingEdges    = new HashMap<String, Integer>(); 
	private final Map<String, Integer> outgoingEdges    = new HashMap<String, Integer>();
    
    // Abstraction function:
    //   AF(name, incomingVertices, outgoingVertices, 
	//            incomingEdge, outgoingEdge) = A vertex such that there is a directed edge with positive weight 
	//                                          from all the vertices in incomingVertices to this vertex and a directed edge with positive weight 
	//                                          from this vertex to all the vertices in outgoingVertices.
    
	// Representation invariant:
    //   name is a non empty string
	//   The directed edges from all the vertices in incomingVertices to this vertex have a positive weight
	//   The directed edges from this vertex to all the vertices in outgoingVertices have a positive weight
    
	// Safety from rep exposure:
    //   
    
    // Constructor
    public Vertex(String name){
    	this.name = name;
    }
    
    public Vertex(String name, Map<String, Vertex> incomingVertices, Map<String, Vertex> outgoingVertices,
    		Map<String, Integer> incomingEdges, Map<String, Integer> outgoingEdges) {
    	this.name = name;
    	this.incomingVertices.putAll(incomingVertices);
    	this.outgoingVertices.putAll(outgoingVertices);
    	this.incomingEdges.putAll(incomingEdges);
    	this.outgoingEdges.putAll(incomingEdges);
    	
    }
	
	
    // TODO checkRep
    // check if name is non empty and all the edges from incomingVertex have positive weight
	// All the edges to outgoingVertex have positive weight
	
    // methods
    
    /**
     * Returns the name of this vertex
     * @return the name of this vertex
     */
     public String getName() {
    	 return this.name;
     }
     
     /**
      * Returns the weight of the directed edge from this vertex to target
      * If there exist no directed edge between this vertex to target then it 
      * return -1
      * 
      * @param target, a label of target of the edge
      * @returns returns weight of the directed edge from source to target (if it exist)
      *          otherwise, returns -1
      */
      public int getOutgoingEdgeWeight(String target) {
 		  // return false if there exist an edge from source or weight is zero
 		  if (!hasEdgeTo(target)) {
 			 return -1;
 		  }
 		  return outgoingEdges.get(target);
      }
      
      /**
       * Returns the weight of the directed edge from source to this vertex 
       * If there exist no directed edge between source to this vertex then it 
       * return -1
       * 
       * @param source, a label of target of the edge
       * @returns returns weight of the directed edge from source to target (if it exist)
       *          otherwise, returns -1
       */
       public int getIncomingEdgeWeight(String source) {
  		  // return false if there exist an edge from source or weight is zero
  		  if (!hasEdgeFrom(source)) {
  			 return -1;
  		  }
  		  return incomingEdges.get(source);
       }
      
      /**
       * Get the source vertices with directed edges to this vertex and the
       * weights of those edges.
       * 
       * @return a map where the key set is the set of labels of vertices such
       *         that this graph includes an edge from that vertex to target, and
       *         the value for each key is the (nonzero) weight of the edge from
       *         the key to target
       */
      public Map<String, Integer> getIncomingEdges(){
    	  return new HashMap<String, Integer>(incomingEdges);
      }
     
      /**
       * Get the target vertices with directed edges from this vertex and the
       * weights of those edges.
       * 
       * @return a map where the key set is the set of labels of vertices such
       *         that this graph includes an edge from source to that vertex, and
       *         the value for each key is the (nonzero) weight of the edge from
       *         source to the key
       */
      public Map<String, Integer> getOutgoingEdges(){
    	  return new HashMap<String, Integer>(outgoingEdges);
      }
      
	/**
	 * Adds a weighted directed edge from this vertex to the target vertex.
	 * If target vertex is already present or edge weight is zero,
	 * the weight directed edge is not added
	 * 
	 * @param target, a label of the target vertex, 
	 * @param weight, a weight of the directed edge 
	 * @return true if directed weight edge is added,
	 *         otherwise,false. 
	 */
	 public boolean addIncomingEdge(Vertex source, Integer weight) {
		 String sourceName = source.getName();
		 // return false if there exist an edge from source or weight is zero
		 if (hasEdgeFrom(sourceName) || weight == 0) {
			 return false;
		 }
		 incomingVertices.put(sourceName,source);
		 incomingEdges.put(sourceName, weight);
		 return true;
	 }
	
	
	/**
	 * Adds a weighted directed edge from the source vertex to the this vertex.
	 * If the source vertex is already present or the edge weight is zero,
	 * the weight directed edge is not added
	 * 
	 * @param source, a label of the source vertex, 
	 * @param weight, a weight of the directed edge 
	 * @return true if directed weight edge is added,
	 *         otherwise,false. 
	 */
	 public boolean addOutgoingEdge(Vertex target, Integer weight) {
		 String targetName = target.getName();
		 if (hasEdgeTo(targetName) || weight == 0) {
			 return false;
		 }
		 outgoingVertices.put(targetName, target);
		 outgoingEdges.put(targetName, weight);
		 return true;
	}
		
	 
	/**
	 * Delete the weighted directed edge from the source vertex to this vertex 
	 * If the weighted directed edge is not present, the vertex is not modified
	 * 
	 * @param source, a label of the source vertex,  
	 * @return true if directed weight edge is deleted,
	 *         otherwise,false. 
	 */
	  public boolean deleteIncomingEdge(Vertex source) {
		 String sourceName = source.getName();
		 // return false if there exist no edge 
		 if (!hasEdgeFrom(sourceName)) {
			return false;
		 }
		 
		 /*
		  * There exist an edge source -> this, so we'll delete from this vertex the incoming edge from source
		  * and delete from source the outgoing edge to this vertex
		  */
		 // delete the incoming edge of this vertex from the source to this vertex
		 incomingVertices.remove(sourceName);
		 incomingEdges.remove(sourceName);
		 
		 // delete the outgoing edge of the source from the source to this vertex 
		 source.deleteOutgoingEdge(this);
		 
		 return true;
	  }
		
	  
	/**
     * Delete the weight directed edge from this vertex to the target vertex 
     * If the weighted directed edge is not present, the vertex is not modified
     * 
	 * @param target, a label of the target vertex,  
	 * @return true if all the incoming directed weight edge are deleted,
	 *         otherwise,false. 
	 */
	 public boolean deleteOutgoingEdge(Vertex target) {
		 String targetName = target.getName();
		 // return false if there exist no edge 
		 if (!hasEdgeFrom(targetName)) {
			return false;
		 }
		 
		 /*
		  * There exist an edge from this -> target so we'll delete from this vertex the outgoing edge to target
		  * and delete from target the incoming edge from this vertex
		  */
		 // delete the outgoing edge of this vertex to target. 
		 outgoingVertices.remove(targetName);
		 outgoingEdges.remove(targetName);
		 
		 // delete the incoming edge of target from this vertex.
		 target.deleteIncomingEdge(this);
		 return true;
	  }
			
		
	/**
	 * Delete all the incoming weighted directed edges to this vertex 
	 * If the weighted directed edges are not present, the vertex is not modified
	 *  
	 * @return true if all the incoming directed weight edges are deleted,
	 *         otherwise,false. 
	 */
	 public boolean deleteAllIncomingEdges() {
		for(Vertex incomingNeighbour:incomingVertices.values()) {
			deleteIncomingEdge(incomingNeighbour);
		}
		return true;
	 }
	
		
	/**
	 * Delete all the outgoing weighted directed edges from this vertex 
	 * If the weighted directed edges are not present, the vertex is not modified
	 *  
	 * @return true if all the outgoing directed weight edges are deleted,
	 *         otherwise,false. 
	 */
	 public boolean deleteAllOutgoingEdges() {
		for(Vertex outgoingNeighbour:outgoingVertices.values()) {
			deleteOutgoingEdge(outgoingNeighbour);
		}
		return true;
	 }
	
	
	 /**
	  * Changes the weight of directed edge from source to this vertex.
	  * If there exist is no edge between source or edge weight is zero,
	  * the weight directed edge is not added
	  * 
	  * @param target, a label of the target vertex, 
	  * @param weight, a weight of the directed edge 
	  * @return true if directed weight edge is added,
	  *         otherwise,false. 
	  */
	  public boolean changeIncomingEdge(Vertex source, Integer weight) {
		  String sourceName = source.getName();
		  // return false if there exist no edge from source or weight is zero
		  if (!hasEdgeFrom(sourceName) || weight == 0) {
			  return false;
		  }
		  
		  incomingEdges.put(sourceName, weight);
		  return true;
	  }
	 
	  /**
	   * Changes the weight of directed edge from this vertex to target vertex.
	   * If there exist is no edge to target vertex or edge weight is zero,
	   * the weight directed edge is not changed
	   * 
	   * @param target, a label of the target vertex, 
	   * @param weight, a weight of the directed edge 
	   * @return true if directed weight edge is changed,
	   *         otherwise,false. 
	   */
	   public boolean changeOutgoingEdge(Vertex target, Integer weight) {
			String targetName = target.getName();
			// return false if there exist no edge to target or weight is zero
			if (!hasEdgeTo(targetName) || weight == 0) {
				return false;
			}
			  
			outgoingEdges.put(targetName, weight);
			return true;
		  }
		 
	  /**
	   * Check whether there is a weight directed edge from the source vertex to
	   * this vertex
	   * 
	   * @param source, a label of the source vertex,  
	   * @return true if all the incoming directed weight edge are deleted,
	   *         otherwise,false. 
	   */
	   boolean hasEdgeFrom(String source) {
		   return (incomingVertices.containsKey(source) && incomingEdges.containsKey(source)); 	
	   }
	
	   /**
		* Check whether there is a weight directed edge from the target vertex to
		* this vertex
		* 
		* @param target, a label of the target vertex,  
		* @return true if all the outing directed weight edge are deleted,
		*         otherwise,false. 
		*/
		boolean hasEdgeTo(String target) {
			 return (outgoingVertices.containsKey(target) && outgoingEdges.containsKey(target)); 
		}
		
    // TODO toString()
    
}
