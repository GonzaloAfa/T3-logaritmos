import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ian on 30-06-2014.
 */
public class HeapNode {
    private GeoRef data;
    private HeapNode parent;
    private List<HeapNode> sons;
    private double distance;

    public HeapNode(GeoRef data, HeapNode parent, double distance) {
        this.data = data;
        this.parent = parent;
        this.distance = distance;
        this.sons = new ArrayList<HeapNode>();
    }

    public GeoRef getData() {
        return data;
    }

    public HeapNode getParent() {
        return parent;
    }

    public void setParent(HeapNode parent) {
        this.parent = parent;
        parent.addSon(this);
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public void addSon(HeapNode sons) {
        this.sons.add(sons);
    }

    public List<HeapNode> getSons() {
        return sons;
    }

    @Override
    public String toString() {
        String sonIds = "";
        for (HeapNode sone : sons) {
            sonIds += ", " + sone.getData().getId();
        }
        int parentId = parent == null ? -1 : parent.getData().getId();
        return "Point " + data + " Distance: " + distance + " Son: " + sonIds + " Parent: " + parentId;
    }
}
