
import java.util.List;
import java.util.Random;

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

        //Tomamos un punto al azar del arreglo y desde ahí construimos nuestro camino.
        GeoRef actual = geoRefs.remove(new Random().nextInt(geoRefs.size()));
        conjunct.add(actual);

        double minimum;
        GeoRef newPoint = null;

        while (0 < geoRefs.size()) {

            minimum = Double.POSITIVE_INFINITY;

            // Buscamos el p que no está C, que está más cerca del nodo actual
            for (int i = 0; i < geoRefs.size(); i++) {
                GeoRef p = geoRefs.get(i);

                double dist = actual.distance(p);

                if (dist < minimum) {
                    minimum = dist;
                    newPoint = p;
                }
            }

            roadDistance += minimum;

            conjunct.add(newPoint);
            geoRefs.remove(newPoint);
            actual = newPoint;
        }

        roadDistance += conjunct.get(0).distance(conjunct.get(conjunct.size()-1));
        time = System.nanoTime() - time;
    }
}
