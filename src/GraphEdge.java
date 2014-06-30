/**
 * Created by Ian on 30-06-2014.
 */
public class GraphEdge implements Comparable<GraphEdge> {
    public final int start, end;
    public final double weight;

    public GraphEdge(int start, int end, double weight) {
        this.start = start;
        this.end = end;
        this.weight = weight;
    }

    @Override
    public int compareTo(GraphEdge o) {
        if (weight < o.weight) {
            return -1;
        } else if (weight > o.weight) {
            return 1;
        } else {
            return 0;
        }
    }
}
