/**
 * Created by Ian on 30-06-2014.
 */
/*****************************************************************************
 * File: DirectedGraph.java
 * Author: Keith Schwarz (htiek@cs.stanford.edu)
 *
 * A class representing an undirected graph where each edge has an associated
 * real-valued length.  Internally, the class is represented by an adjacency
 * list where each edges appears twice - once in the forward direction and
 * once in the reverse.  In fact, this implementation was formed by taking
 * a standard adjacency list and then duplicating the logic to ensure each
 * edge appears twice.
 */
import java.util.*; // For HashMap

public final class UndirectedGraph implements Iterable<GeoRef> {
    /* A map from nodes in the graph to sets of outgoing edges.  Each
     * set of edges is represented by a map from edges to doubles.
     */
    private final Map<GeoRef, Map<GeoRef, Double>> mGraph = new HashMap<GeoRef, Map<GeoRef, Double>>();

    /**
     * Adds a new node to the graph.  If the node already exists, this
     * function is a no-op.
     *
     * @param node The node to add.
     * @return Whether or not the node was added.
     */
    public boolean addNode(GeoRef node) {
        /* If the node already exists, don't do anything. */
        if (mGraph.containsKey(node))
            return false;

        /* Otherwise, add the node with an empty set of outgoing edges. */
        mGraph.put(node, new HashMap<GeoRef, Double>());
        return true;
    }

    public void remove(GeoRef point){
        mGraph.remove(point);
    }

    /**
     * Given two nodes and a length, adds an arc of that length between those
     * nodes.  If the arc already existed, the length is updated to the
     * specified value.  If either endpoint does not exist in the graph, throws
     * a NoSuchElementException.
     *
     * @param one The first node.
     * @param two The second node.
     * @throws NoSuchElementException If either the start or destination nodes
     *                                do not exist.
     */
    public void addEdge(GeoRef one, GeoRef two) {
        /* Confirm both endpoints exist. */
        if (!mGraph.containsKey(one) || !mGraph.containsKey(two))
            throw new NoSuchElementException("Both nodes must be in the graph.");

        double length = one.distance(two);
        
        /* Add the edge in both directions. */
        mGraph.get(one).put(two, length);
        mGraph.get(two).put(one, length);
    }

    /**
     * Removes the edge between the indicated endpoints from the graph.  If the
     * edge does not exist, this operation is a no-op.  If either endpoint does
     * not exist, this throws a NoSuchElementException.
     *
     * @param one The start node.
     * @param two The destination node.
     * @throws NoSuchElementException If either node is not in the graph.
     */
    public void removeEdge(GeoRef one, GeoRef two) {
        /* Confirm both endpoints exist. */
        if (!mGraph.containsKey(one) || !mGraph.containsKey(two))
            throw new NoSuchElementException("Both nodes must be in the graph.");

        /* Remove the edges from both adjacency lists. */
        mGraph.get(one).remove(two);
        mGraph.get(two).remove(one);
    }

    /**
     * Given two endpoints, returns the cost of the edge between them.  If
     * either endpoint does not exist in the graph, or if the edge is not
     * contained in the graph, this throws a NoSuchElementException.
     *
     * @param one The first endpoint.
     * @param two The second endpoint.
     * @return The cost of the edge between the endpoints.
     * @throws NoSuchElementException If the edge is not found or the endpoints
     *                                are not nodes in the graph.
     */
    public double edgeCost(GeoRef one, GeoRef two) {
        /* Confirm both endpoints exist. */
        if (!mGraph.containsKey(one) || !mGraph.containsKey(two))
            throw new NoSuchElementException("Both nodes must be in the graph.");

        /* Look up the edge between the two. */
        double result=  one.distance(two);

        /* Otherwise return the cost. */
        return result;
    }

    /**
     * Given a node in the graph, returns an immutable view of the edges
     * leaving that node, as a map from endpoints to costs.
     *
     * @param node The node whose edges should be queried.
     * @return An immutable view of the edges leaving that node.
     * @throws NoSuchElementException If the node does not exist.
     */
    public Map<GeoRef, Double> edgesFrom(GeoRef node) {
        /* Check that the node exists. */
        Map<GeoRef, Double> arcs = mGraph.get(node);
        if (arcs == null)
            throw new NoSuchElementException("Source node does not exist.");

        return Collections.unmodifiableMap(arcs);
    }

    /**
     * Returns whether a given node is contained in the graph.
     *
     * @param node: The node to test for inclusion.
     * @return Whether that node is contained in the graph.
     */
    public boolean containsNode(GeoRef node) {
        return mGraph.containsKey(node);
    }

    /**
     * Returns an iterator that can traverse the nodes in the graph.
     *
     * @return An iterator that traverses the nodes in the graph.
     */
    public Iterator<GeoRef> iterator() {
        return mGraph.keySet().iterator();
    }

    /**
     * Returns the number of nodes in the graph.
     *
     * @return The number of nodes in the graph.
     */
    public int size() {
        return mGraph.size();
    }

    /**
     * Returns whether the graph is empty.
     *
     * @return Whether the graph is empty.
     */
    public boolean isEmpty() {
        return mGraph.isEmpty();
    }
}