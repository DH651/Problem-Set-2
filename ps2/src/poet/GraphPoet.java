/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package poet;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import graph.Graph;

/**
 * A graph-based poetry generator.
 * 
 * <p>
 * GraphPoet is initialized with a corpus of text, which it uses to derive a
 * word affinity graph. Vertices in the graph are words. Words are defined as
 * non-empty case-insensitive strings of non-space non-newline characters. They
 * are delimited in the corpus by spaces, newlines, or the ends of the file.
 * Edges in the graph count adjacencies: the number of times "w1" is followed by
 * "w2" in the corpus is the weight of the edge from w1 to w2.
 * 
 * <p>
 * For example, given this corpus:
 * 
 * <pre>
 *     Hello, HELLO, hello, goodbye!
 * </pre>
 * <p>
 * the graph would contain two edges:
 * <ul>
 * <li>("hello,") -> ("hello,") with weight 2
 * <li>("hello,") -> ("goodbye!") with weight 1
 * </ul>
 * <p>
 * where the vertices represent case-insensitive {@code "hello,"} and
 * {@code "goodbye!"}.
 * 
 * <p>
 * Given an input string, GraphPoet generates a poem by attempting to insert a
 * bridge word between every adjacent pair of words in the input. The bridge
 * word between input words "w1" and "w2" will be some "b" such that w1 -> b ->
 * w2 is a two-edge-long path with maximum-weight weight among all the
 * two-edge-long paths from w1 to w2 in the affinity graph. If there are no such
 * paths, no bridge word is inserted. In the output poem, input words retain
 * their original case, while bridge words are lower case. The whitespace
 * between every word in the poem is a single space.
 * 
 * <p>
 * For example, given this corpus:
 * 
 * <pre>
 *     This is a test of the Mugar Omni Theater sound system.
 * </pre>
 * <p>
 * on this input:
 * 
 * <pre>
 *     Test the system.
 * </pre>
 * <p>
 * the output poem would be:
 * 
 * <pre>
 *     Test of the system.
 * </pre>
 * 
 * <p>
 * PS2 instructions: this is a required ADT class, and you MUST NOT weaken the
 * required specifications. However, you MAY strengthen the specifications and
 * you MAY add additional methods. You MUST use Graph in your rep, but otherwise
 * the implementation of this class is up to you.
 */
public class GraphPoet {

    private final Graph<String> graph = Graph.empty();

    // Abstraction function:
    // TODO

    // Representation invariant:
    // TODO

    // Safety from rep exposure:
    // TODO

    /**
     * Create a new poet with the graph from corpus (as described above).
     * 
     * @param corpus text file from which to derive the poet's affinity graph
     * @throws IOException if the corpus file cannot be found or read
     */
    public GraphPoet(File corpus) throws IOException {
	try (Scanner scanner = new Scanner(corpus);) {
	    String previousWord = null;
	    String currentWord = null;
	    while (scanner.hasNext()) {
		if (previousWord == null) {

		    previousWord = scanner.next();

		} else {

		    currentWord = scanner.next();
		    // add consecutive words to the affinity graph.
		    addEdgeToAffinityGraph(formatString(previousWord), formatString(currentWord));

		    previousWord = currentWord;
		}
	    }
	} catch (FileNotFoundException exp) {
	    // source not found
	    throw new IOException(exp);
	}

    }

    // TODO checkRep

    /**
     * Generate a poem.
     * 
     * @param input string from which to create the poem
     * @return poem (as described above)
     * 
     */
    public String poem(String input) {

	if (input.isBlank()) {
	    return input;
	}

	StringBuilder result = new StringBuilder();
	List<String> wordsList = Arrays.asList(input.split("\\s+"));
	Iterator<String> iterator = wordsList.iterator();

	String previousWord = iterator.next();
	result.append(previousWord);

	String currentWord;
	String bridgeWord;
	String whiteSpace = " ";

	while (iterator.hasNext()) {
	    currentWord = iterator.next();

	    try {
		// If bridge-word is exist, add whitespace bridge-word whitespace current-word
		bridgeWord = getBridgeWord(formatString(previousWord), formatString(currentWord));
		result.append(whiteSpace);
		result.append(bridgeWord);
		result.append(whiteSpace);
		result.append(currentWord);

	    } catch (PathNotFoundException exp) {
		// No bridge-word found, add whitespace and then current-word only
		result.append(whiteSpace);
		result.append(currentWord);
	    }

	    previousWord = currentWord;
	}

	return result.toString();
    }

    // TODO toString()

    /**
     * Checks if there exist an edge from the source vertex to the target vertex,
     * return true, otherwise false.
     * 
     * @param source, a source vertex of the possible edge
     * @param target, a target vertex of the possible edge
     * @return true, if there exist an edge from source vertex to target vertex,
     *         false, otherwise.
     */
    private boolean hasAnEdge(String source, String target) {
	Map<String, Integer> sources = graph.sources(target);
	Map<String, Integer> targets = graph.targets(source);
	if (sources.containsKey(source) && targets.containsKey(target)) {
	    return sources.get(source).equals(targets.get(target));

	}
	return false;
    }

    /**
     * Returns the weight of an edge from the source vertex to the target vertex,
     * only if their exist an edge from the source to the target vertex, otherwise
     * returns zero.
     * 
     * @param source, a source vertex of the possible edge
     * @param target, a target vertex of the possible edge
     * @return weight of edge from source to target vertex, if there exist an edge
     *         from source vertex to target vertex, zero, otherwise.
     */
    private int getEdgeWeight(String source, String target) {
	if (hasAnEdge(source, target)) {
	    Map<String, Integer> sources = graph.sources(target);
	    return sources.get(source);
	}
	return 0;
    }

    /**
     * Adds an edge from source to target vertex with edge weight one to the
     * affinity graph, adds vertices if they don't exist, only if the there exist no
     * edge between from source to target. Updates the edge weight by one if there
     * is already an edge from source to target.
     * 
     * @param source, any word, must only contain lower-case letters and digits.
     * @param target, any word, must only contain lower-case letters and digits.
     * @return updated edge weight from source to target if the edges is already
     *         present,otherwise, one if a new edge was added from source to target.
     */
    private int addEdgeToAffinityGraph(String source, String target) {
	int result;

	if (hasAnEdge(source, target)) {
	    // Update the existing edge weight if there is an edge.
	    int existingEdgeWeight = getEdgeWeight(source, target);
	    int newEdgeWeight = existingEdgeWeight + 1;
	    graph.set(source, target, newEdgeWeight);
	    result = newEdgeWeight;

	} else {
	    // Create a new edge and add it to the graph.
	    graph.set(source, target, 1);
	    result = 1;

	}

	return result;
    }

    /**
     * Removes any punctuation from the given word and convert it to lower-case.
     * 
     * @param word, any word
     * @return a word in lower-case letters and without punctuation.
     */
    private String formatString(String word) {

	String cleanedText = word.replaceAll("[^\\p{L}\\p{N}]", ""); // remove non-alphanumeric characters.

	String lowerCaseText = cleanedText.toLowerCase(); // convert the word to lower-case.

	return lowerCaseText;
    }

    /**
     * Returns the bridge word in a two edge path (source -> word -> target) with
     * the maximum total weight in the affinity graph, only if there exist such a
     * path.
     * 
     * @param source, the starting word
     * @param target, the ending word
     * @return the bridge word on a two edge path (source -> word -> target) having
     *         maximum weight in the affinity graph.
     * @throw PathNotFoundException, if there doesn't exist a two edge path from
     *        source to target via a bridge word
     */
    private String getBridgeWord(String source, String target) throws PathNotFoundException {

	Map<String, Integer> targets = graph.targets(source);
	int maximumWeight = 0;
	String bridgeWord = "";

	for (Map.Entry<String, Integer> entry : targets.entrySet()) {

	    // if there is a two edge path via the current word and and if the path weight
	    // is maximum, the current word is the bridge word.
	    String possibleBridgeWord = entry.getKey();
	    if (this.hasAnEdge(possibleBridgeWord, target)) {
		int currentWeight = entry.getValue() + getEdgeWeight(possibleBridgeWord, target);
		if (maximumWeight < currentWeight) {
		    maximumWeight = currentWeight;
		    bridgeWord = entry.getKey();
		}
	    }

	}

	// if there is no bridge word then throw PathNotFoundException, otherwise return
	// bridge word.
	if (bridgeWord.isEmpty()) {
	    throw new PathNotFoundException("There is no two edge path from source to target via some bridge word.");
	} else {
	    return bridgeWord;
	}
    }
}

/**
 * This Exception is thrown when a two edge path does not exist from a source to
 * target via some bridge word
 * 
 * @author Admin
 *
 */
class PathNotFoundException extends Exception {
    private static final long serialVersionUID = 1L;

    PathNotFoundException(String message) {
	super(message);
    }

    PathNotFoundException(String message, Throwable cause) {
	super(message, cause);
    }
}
