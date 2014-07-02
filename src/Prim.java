import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Ian on 30-06-2014.
 */
public class Prim extends Algorithm {
    private FibonacciHeap heap;
    private Map<HeapNode, Entry> entryMap;

    @Override
    String getNameAlgorithm() {
        return "Prim";
    }

    @Override
    void run() {
        time = System.nanoTime();
        heap = new FibonacciHeap();
        entryMap = new HashMap<HeapNode, Entry>();
        HeapNode root = null;

        for (int i = 0; i < geoRefs.size(); i++) {
            GeoRef point = geoRefs.get(i);
            HeapNode node;

            if (i == 0) {
                // The root has distance 0 to the root
                node = new HeapNode(point, null, 0);
                root = node;
            } else {
                node = new HeapNode(point, null, Double.POSITIVE_INFINITY);
            }

            Entry entry = heap.enqueue(node, node.getDistance());
            entryMap.put(node, entry);
        }

        while (!heap.isEmpty()) {
            Entry min = heap.dequeueMin();
            HeapNode heapNodeMin = min.getValue();
            entryMap.remove(heapNodeMin);

            // Iterate over the nodes in the fib heap
            for (HeapNode node : entryMap.keySet()) {

                Entry entry = entryMap.get(node);
                double distance = heapNodeMin.getData().distance(node.getData());
                if (entry.getPriority() > distance) {
                    node.setParent(min.getValue());
                    node.setDistance(distance);
                    heap.decreaseKey(entry, distance);
                }
            }
        }

        buildResult(root);

        time = System.nanoTime() - time;

        roadDistance = 0;

        for (int i = 0; i < conjunct.size()-1; i++) {
            roadDistance += conjunct.get(i).distance(conjunct.get(i+1));
        }

        roadDistance += conjunct.get(conjunct.size()-1).distance(conjunct.get(0));

        int i = 9;
    }

    /**
     * Go through all the tree nodes in pre-order
     * @param root
     */
    private void buildResult(HeapNode root) {
        conjunct.add(root.getData());
        for (HeapNode heapNode : root.getChildren()) {
            buildResult(heapNode);
        }

    }
}
