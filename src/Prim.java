import com.sun.corba.se.impl.orbutil.graph.Graph;

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
        heap = new FibonacciHeap();
        entryMap = new HashMap<HeapNode, Entry>();

        for (int i = 0; i < geoRefs.size(); i++) {
            GeoRef point = geoRefs.get(i);
            HeapNode node;

            if (i == 0) {
                node = new HeapNode(point, null, 0);
            } else {
                node = new HeapNode(point, null, Double.POSITIVE_INFINITY);
            }

            Entry entry = heap.enqueue(node, node.getDistance());
            entryMap.put(node, entry);
        }

        List<HeapNode> result = new ArrayList<HeapNode>();

        while (!heap.isEmpty()) {
            Entry min = heap.dequeueMin();
            HeapNode heapNodeMin = min.getValue();
            entryMap.remove(heapNodeMin);
            result.add(heapNodeMin);
            conjunct.add(heapNodeMin.getData());

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
        double totalDistance = 0;
        for (int i = 0; i < result.size(); i++) {
            totalDistance+=result.get(i).getDistance();
        }
        System.out.println("La distancia es: "+ totalDistance);
        int i = 9;
    }
}
