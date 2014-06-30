/**
 * Created by Ian on 30-06-2014.
 */
public class DisjointSet {

    private DisjointSet parent;
    private int rank;

    /**
     * MakeSet from literature
     * @param point
     */
    public DisjointSet(GeoRef point){
        parent = this;
        rank = 0;
    }

    public void union(DisjointSet set){
        DisjointSet root1 = set.find();
        DisjointSet root = find();

        if(root == root1){
            return;
        }

        if(root.rank < root1.rank){
            root.parent = root1;
        } else if(root.rank > root1.rank){
            root1.parent = root;
        } else {
            root1.parent = root;
            root.rank++;
        }
    }

    public DisjointSet find(){
        if(parent != this){
            parent = parent.find();
        }
        return parent;
    }
}
