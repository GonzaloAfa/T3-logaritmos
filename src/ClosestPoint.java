
import java.util.List;

/**
 * Created by Gonzaloafa on 24-06-2014.
 */
public class ClosestPoint extends Algorithm {
    @Override
    String getNameAlgorithm() {
        return "PuntosCercanos";
    }

    public ClosestPoint() {
        super();
    }

    @Override
    void run() {
        roadDistance = 0;

        time = System.nanoTime();

        //dictatorialmente tomamos el primer número del arreglo y desde ahí construimos nuestro camino.
        conjunct.add(geoRefs.remove(0));

        GeoRef minP;
        DistanceToSet minimum;

        while (0 < geoRefs.size()) {

            minP = null;
            minimum = null;

            // Buscamos el p que no está C, que está más cerca del conjunto C
            for (int i = 0; i < geoRefs.size(); i++) {
                GeoRef p = geoRefs.get(i);
                DistanceToSet distance = getDistanceToSet(p, conjunct);

                if (minimum == null || distance.minDistance < minimum.minDistance) {
                    minimum = distance;
                    minP = p;
                }
            }

            this.roadDistance += minimum.minDistance;

            conjunct.add(minimum.minIndex + 1, minP);
            geoRefs.remove(minP);

        }
        time = System.nanoTime() - time;
    }

    private DistanceToSet getDistanceToSet(GeoRef outerPoint, List<GeoRef> circuit) {
        double minimum = Double.MAX_VALUE;
        int minIndex = -1;

        for (int i = 0; i < circuit.size(); i++) {

            double dist = outerPoint.distance(circuit.get(i));

            if (dist < minimum) {
                minimum = dist;
                minIndex = i;
            }
        }

        return new DistanceToSet(minimum, minIndex);
    }
}
