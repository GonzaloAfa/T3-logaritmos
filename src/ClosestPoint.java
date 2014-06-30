
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gonzaloafa on 24-06-2014.
 */
public class ClosestPoint extends Algorithm{
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

        //dictatorialmente tomamos el primer número del arreglo y desde ahí construimos nuestro camino.
        conjunct.add(geoRefs.remove(0));

        long timePass = System.nanoTime();

        while (0 < geoRefs.size()) {

            GeoRef minP = null;
            DistanceToSet minimum = null;

            // Buscamos el p \in P que está más cerca de TODOS los puntos del conjunto C
            for (GeoRef p : geoRefs) {

                DistanceToSet distance= getDistanceToSet(p, conjunct);

                if (minimum == null || distance.totalDistance < minimum.totalDistance) {
                    minimum = distance;
                    minP = p;
                }
            }

            this.roadDistance += minimum.minDistance;

            conjunct.add(minimum.minIndex + 1, minP);
            geoRefs.remove(minP);

        }
        this.time = System.nanoTime() - timePass;
    }

    private DistanceToSet getDistanceToSet(GeoRef p, List<GeoRef> c) {
        double totalDistance = 0,
                minimum = Double.MAX_VALUE;
        int minIndex = -1;

        for (int i = 0; i < c.size(); i++) {

            double dist = p.distance(c.get(i));
            totalDistance += dist;

            if (dist < minimum) {
                minimum = dist;
                minIndex = i;
            }
        }

        return new DistanceToSet(totalDistance, minimum, minIndex);
    }
}
