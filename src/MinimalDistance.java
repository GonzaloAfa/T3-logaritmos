import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Gonzaloafa on 25-06-2014.
 */
public class MinimalDistance {

    private GeoRef p;
    private GeoRef p1;
    private GeoRef p2;
    private PathProposal mPath;

    public MinimalDistance(GeoRef p, GeoRef p1, GeoRef p2, int i) {
        this.p = p;
        this.p1 = p1;
        this.p2 = p2;
    }

    public MinimalDistance(List<GeoRef> convexHull, GeoRef innerPoint) {
        p = innerPoint;

        obtainPath(convexHull);
    }

    private void obtainPath(List<GeoRef> convexHull) {
        PathProposal path;
        mPath = new PathProposal(0, p, convexHull.get(0), convexHull.get(1));

        for (int i = 1; i < convexHull.size() - 1; i++) {

            path = new PathProposal(i, p, convexHull.get(i), convexHull.get(i + 1));

            if (path.getDifference() < mPath.getDifference()) {
                mPath = path;
            }
        }

        p1 = convexHull.get(getInsertionIndex());
        p2 = convexHull.get(getInsertionIndex() + 1);
    }

    public static double getOldCost(int i, GeoRef p1, GeoRef p2) {
        double oldCostDistance;
        try {
            // Use the stored value
            oldCostDistance = PathProposal.CHCost.get(i);
        } catch (IndexOutOfBoundsException e) {
            // Store de value for later use
            oldCostDistance = p1.distance(p2);
            PathProposal.CHCost.add(oldCostDistance);
        }
        return oldCostDistance;
    }

    /*public static double getNewCost(GeoRef p, GeoRef p1, GeoRef p2) {
        double newCost;
        if (newCostsMap.containsKey(p)) {
            // Use the stored value
            newCost = get(newCostsMap, p, p1).getNewPathCost();
        } else {
            // Store de value for later use
            PathProposal path = new PathProposal(i, p, p1, p2);
            newCost = path.getNewPathCost();
            put(newCostsMap, p, p1, path);
        }
        return newCost;
    }*/

    public static PathProposal get(Map<GeoRef, Map<GeoRef, PathProposal>> map, GeoRef p, GeoRef p1) {
        return map.get(p).get(p1);
    }

    public static void put(Map<GeoRef, Map<GeoRef, PathProposal>> newCostsMap, GeoRef p, GeoRef p1, PathProposal path) {
        if (newCostsMap.containsKey(p)) {
            newCostsMap.get(p).put(p1, path);
        } else {
            HashMap<GeoRef, PathProposal> map = new HashMap<GeoRef, PathProposal>();
            map.put(p1, path);
            newCostsMap.put(p, map);
        }

    }

    public GeoRef getP() {
        return p;
    }

    public GeoRef getP1() {
        return p1;
    }

    public GeoRef getP2() {
        return p2;
    }

    public int getInsertionIndex() {
        return this.mPath.getInsertionIndex();
    }

    public double getCHOldDistance() {
        return PathProposal.CHCost.get(getInsertionIndex());
    }

    public double getRatio() {
        return mPath.getNewPathCost() / mPath.getOldPathCost();
    }

    public void updateCosts(PathProposal newPath, List<GeoRef> convexHull) {
        //Check if we destroyed the edge of this point
        if (mPath.getInsertionIndex() == newPath.getInsertionIndex()) {
            mPath = null;
            obtainPath(convexHull);
        } else {
            // We haven't destroyed the edge, so we'll check if the new edges are better than the actual

            // First update the insertion index if it's after the insertion point
            if (mPath.getInsertionIndex() > newPath.getInsertionIndex()) {
                updateInsertionIndex();
            }

            // And Check if one of the 2 new edges are better than the actual one
            for (int i = newPath.getInsertionIndex(); i < newPath.getInsertionIndex() + 2; i++) {
                GeoRef p1 = convexHull.get(i),
                        p2 = convexHull.get(i + 1);
                PathProposal auxPath = new PathProposal(i, p, p1, p2);

                if (auxPath.getDifference() < mPath.getDifference()) {
                    mPath = auxPath;
                    this.p1 = p1;
                    this.p2 = p2;
                }
            }
        }
    }

    public PathProposal getmPath() {
        return mPath;
    }

    public void updateInsertionIndex() {
        mPath.updateInsertionIndex();
    }
}
