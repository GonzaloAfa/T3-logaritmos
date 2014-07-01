import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Gonzaloafa on 24-06-2014.
 */

/**
 * ***********************************************************************
 * File: Prim.java
 * Author: Keith Schwarz (htiek@cs.stanford.edu)
 * <p/>
 * An implementation of Prim's minimum spanning tree algorithm.  The algorithm
 * takes as input a weighted, undirected graph and returns a new graph that
 * is a tree on the original nodes of minimum weight.
 * <p/>
 * Prim's algorithm is in many ways similar to Dijkstra's algorithm.  Starting
 * at an arbitrary node in the graph, we grow the spanning tree outward one
 * edge at a time by adding the cheapest outgoing edge from the spanned nodes
 * to a node not in the spanning tree.  The key difference between Dijkstra's
 * algorithm and Prim's algorithm is that Dijkstra's algorithm creates a
 * shortest-path tree from the source node, while Prim's algorithm builds an
 * MST from the source node.
 * <p/>
 * The main advantage of Prim's algorithm is that it can be made to run in
 * O(|E| + |V| lg |V|) using some clever optimizations and a Fibonacci heap.
 * The main algorithm is as follows.  First, as in Dijkstra's algorithm,
 * create a Fibonacci heap and assign each node infinite priority.  Next,
 * pick some arbitrary node to use as the source node, set its priority to
 * zero, and then decrease the key of each connected node to the cost of the
 * edge connecting that node to the source node.  Now, we repeat the following
 * procedure until a tree is found:
 * <p/>
 * 1. Dequeue some node from the priority queue.  This node will be the node
 * connected to the existing MST by the least-cost edge.
 * 2. Scan over the edges leaving this node and find the minimum-cost node
 * connecting it to the existing MST nodes.  This is the node that caused
 * the node to have its priority.
 * 3. Add this edge to the MST.
 * <p/>
 * The runtime of this algorithm can be shown to be O(|E| + |V| lg |V|) using
 * a Fibonacci heap as follows.  First, we do O(|V|) insertions into the heap,
 * which takes O(|V|) time.  We then do O(|V|) dequeues (since we only want
 * a total of |V| - 1 edges).  These dequeues take a total of O(|V| lg |V)
 * time, though any one dequeue might take much more than that.  Finally, on
 * each dequeue, we scan all of the outgoing edges from the dequeued node.
 * Since we never consider the same node twice, the total number of edges
 * visited by all iterations of this step must be twice the number of edges
 * in the graph, since each edge will be visited once from each endpoint.
 * This contributes the final O(|E|) term to the runtime, for a net of an
 * elegant O(|E| + |V| lg |V|).
 * <p/>
 * This implementation relies on the existence of a FibonacciHeap class, also
 * from the Archive of Interesting Code.  You can find it online at
 * <p/>
 * http://keithschwarz.com/interesting/code/?dir=fibonacci-heap
 */
public class MST extends Algorithm {

    public FibonacciHeap pq;
    public Map<GeoRef, Map<HeapNode, Entry>> entries;
    public UndirectedGraph result, graph;

    public MST() {
    }

    @Override
    String getNameAlgorithm() {
        return "MST";
    }

    @Override
    public void run() {

        /* The Fibonacci heap we'll use to select nodes efficiently. */
        pq = new FibonacciHeap();

        /* This Fibonacci heap hands back internal handles to the nodes it
         * stores.  This map will associate each node with its entry in the
         * Fibonacci heap.
         */
        entries = new HashMap<GeoRef, Map<HeapNode, Entry>>();

        /* The graph which will hold the resulting MST. */
        result = new UndirectedGraph();

        graph = new UndirectedGraph();

        for (int i = 0; i < geoRefs.size(); i++) {
            graph.addNode(geoRefs.get(i));
        }

        mst();
    }

    /**
     * Given a connected undirected graph with real-valued edge costs,
     * returns an MST of that graph.
     *
     * @return A spanning tree of the graph with minimum total weight.
     */
    public UndirectedGraph mst() {

        /* As an edge case, if the graph is empty, just hand back the empty
         * graph.
         */
        if (graph.isEmpty())
            return result;

        /* Pick an arbitrary starting node. */
        GeoRef startNode = graph.iterator().next();

        /* Add it as a node in the graph.  During this process, we'll use
         * whether a node is in the result graph or not as a sentinel of
         * whether it's already been picked.
         */
        result.addNode(startNode);

        // We never go back to the nodes added to the tree
        graph.remove(startNode);

        // Begin by adding all outgoing edges of this start node to the Fibonacci heap.
        addOutgoingEdges(startNode);

        /* Now, until we have added |V| - 1 edges to the graph, continously
         * pick a node and determine which edge to add.
         */
        for (int i = 0; i < graph.size() - 1; ++i) {
            /* Grab the cheapest node we can add. */
            HeapNode toAdd = pq.dequeueMin().getValue();

            /* Determine which edge we should pick to add to the MST.  We'll
             * do this by getting the endpoint of the edge leaving the current
             * node that's of minimum cost and that enters the visited edges.
             */
            HeapNode endpoint = minCostEndpoint(toAdd);

            /* Add this edge to the graph. */
            result.addNode(toAdd.getData());
            result.addEdge(toAdd.getData(), endpoint.getData());

            /* Explore outward from this node. */
            addOutgoingEdges(toAdd.getData());
        }

        /* Hand back the generated graph. */
        return result;
    }

    /**
     * Given a node in the source graph and a set of nodes that we've visited
     * so far, returns the minimum-cost edge from that node to some node that
     * has been visited before.
     *
     * @param node The node that has not been considered yet.
     */
    private HeapNode minCostEndpoint(HeapNode node) {
        /* Track the best endpoint so far and its cost, initially null and
         * +infinity.
         */
        HeapNode endpoint = null;
        double leastCost = Double.POSITIVE_INFINITY;

        /* Scan each node, checking whether it's a candidate. */
        for (HeapNode heap : entries.get(node).keySet()) {
            // If the endpoint isn't in the nodes constructed so far, don't consider it.
            if (heap.getParent() != null) {
                continue;
            }

            double distance = heap.getData().distance(node.getData());

            /* If the edge costs more than what we know, skip it. */
            if (distance >= leastCost) {
                continue;
            }

            /* Otherwise, update our guess to be this node. */
            endpoint = heap;
            leastCost = distance;
        }

        /* Hand back the result.  We're guaranteed to have found something,
         * since otherwise we couldn't have dequeued this node.
         */
        return endpoint;
    }

    /**
     * Given a node in the graph, updates the priorities of adjacent nodes to
     * take these edges into account.  Due to some optimizations we make, this
     * step takes in several parameters beyond what might seem initially
     * required.  They are explained in the param section below.
     *
     * @param node The node to explore outward from.
     *             param entries A map from nodes to their corresponding heap entries.
     *             We need this so we can call decreaseKey on the correct
     *             elements.
     */
    private void addOutgoingEdges(GeoRef node) {

        Map<HeapNode, Entry> outgoingEdges = entries.get(node);

        // If we haven't visited this node we add all edges (nodes)
        if (outgoingEdges==null) {
            Iterator<GeoRef> it = graph.iterator();
            Map<HeapNode, Entry> map = new HashMap<HeapNode, Entry>();

            // Iterate over all nodes
            while (it.hasNext()) {
                GeoRef point = it.next();
                HeapNode heap = new HeapNode(point, null, Double.POSITIVE_INFINITY);

                // Add nodes to the fib heap
                map.put(heap, pq.enqueue(heap, point.distance(node)));
            }

            // Add mapping of outgoing edges
            entries.put(node, map);

            return;
        }

        /* Start off by scanning over all edges emanating from our node. */
        for (HeapNode arc : entries.get(node).keySet()) {
            /* Given this arc, there are four possibilities.
             *
             * 1. This endpoint has already been added to the graph.  If so,
             *    we ignore the edge since it would form a cycle.
             * 2. This endpoint is not in the graph and has never been in
             *    the heap.  Then we add it to the heap.
             * 3. This endpoint is in the graph, but this is a better edge.
             *    Then we use decreaseKey to update its priority.
             * 4. This endpoint is in the graph, but there is a better edge
             *    to it.  In that case, we similarly ignore it.
             */
            if (arc.getParent() != null) { // Case 1
                continue;
            }
            Map<HeapNode, Entry> map = entries.get(node);
            if (!map.containsKey(arc)) { // Case 2
                map.put(arc, pq.enqueue(arc, arc.getData().distance(node)));
            } else if (map.get(arc).getPriority() > arc.getData().distance(node)) { // Case 3
                pq.decreaseKey(map.get(arc), arc.getData().distance(node));
            }

            // Case 4 handled implicitly by doing nothing.
        }
    }


}
