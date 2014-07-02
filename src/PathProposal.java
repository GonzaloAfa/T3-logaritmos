import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nolda on 01-07-2014.
 */
public class PathProposal {
    public static List<Double> CHCost = new ArrayList<Double>();
    private int insertionIndex;

    private double newPathCost, firstSegment, secondSegment, difference;

    public PathProposal(int i, GeoRef p, GeoRef p1, GeoRef p2) {
        firstSegment = p.distance(p1);
        secondSegment = p.distance(p2);
        newPathCost = firstSegment + secondSegment;

        insertionIndex = i;

        double oldCostDistance;
        try {
            // Use the stored value
            oldCostDistance = CHCost.get(insertionIndex);
        } catch (IndexOutOfBoundsException e) {
            // Store de value for later use
            oldCostDistance = p1.distance(p2);
            CHCost.add(oldCostDistance);
        }

        difference = newPathCost - oldCostDistance;
    }

    public double getNewPathCost() {
        return newPathCost;
    }

    public double getFirstSegment() {
        return firstSegment;
    }

    public double getSecondSegment() {
        return secondSegment;
    }

    public double getDifference() {
        return difference;
    }

    public double getOldPathCost() {
        return CHCost.get(insertionIndex);
    }

    public int getInsertionIndex() {
        return insertionIndex;
    }

    public static void insertInCHCosts(PathProposal path) {
        //Update the old values
        PathProposal.CHCost.set(path.getInsertionIndex(), path.getFirstSegment());
        // Add the new value
        PathProposal.CHCost.add(path.getInsertionIndex() + 1, path.getSecondSegment());
    }

    public void updateInsertionIndex() {
        insertionIndex++;
    }
}
