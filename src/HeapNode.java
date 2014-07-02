import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ian on 30-06-2014.
 */
public class HeapNode {
    private GeoRef data;
    private HeapNode parent;
    private List<HeapNode> children;
    private double distance;

    public HeapNode(GeoRef data, HeapNode parent, double distance) {
        this.data = data;
        this.parent = parent;
        this.distance = distance;
        this.children = new ArrayList<HeapNode>();
    }

    public GeoRef getData() {
        return data;
    }

    public HeapNode getParent() {
        return parent;
    }

    public void setParent(HeapNode newParent) {
        if(parent!=null) {
            parent.removeSon(this);
        }

        parent = newParent;
        parent.addSon(this);
    }

    private void removeSon(HeapNode son) {
        children.remove(son);
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public void addSon(HeapNode sons) {
        children.add(sons);
    }

    public List<HeapNode> getChildren() {
        return children;
    }

    @Override
    public String toString() {
        String sonIds = "";
        for (HeapNode sone : children) {
            sonIds += ", " + sone.getData().getId();
        }
        int parentId = parent == null ? -1 : parent.getData().getId();
        return "Point " + data + " Distance: " + distance + " Son: " + sonIds + " Parent: " + parentId;
    }
}
