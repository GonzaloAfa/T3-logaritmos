package kruskal;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Ian on 30-06-2014.
 */
public class Kruskal {

    DisjointSet spanningTree;
    List<DisjointSet> disjointSets;
    GraphEdge[][] edges;


    public Kruskal(List<GeoRef> points) {

        // Create disjoint forests
        for (int i = points.size() - 1; i >= 0; i--) {
            disjointSets.add(new DisjointSet(points.get(i)));
        }

        edges = new GraphEdge[points.size()][points.size()];

        // Create edges for each vertex
        for (int i = 0; i < points.size(); i++) {
            for (int j = 0; j < points.size(); j++) {
                double weight = points.get(i).distance(points.get(j));
                edges[i][j] = new GraphEdge(i,j,weight);
            }
        }

        Arrays.sort(edges);








    }
}
