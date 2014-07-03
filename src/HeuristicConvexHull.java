import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Gonzaloafa on 24-06-2014.
 */
public class HeuristicConvexHull extends Algorithm {

    public HeuristicConvexHull() {
        super();
    }

    @Override
    String getNameAlgorithm() {
        return "HeuristicaConvexHull";
    }

    public void run() {
        time = System.nanoTime();
        roadDistance = 0;

        FastConvexHull fastConvexHull = new FastConvexHull();
        conjunct = fastConvexHull.execute(geoRefs);

        // Obtenemos el complemento
        geoRefs.removeAll(conjunct);

        // Listado de puntos cercanos.
        Map<GeoRef,MinimalDistance> minimalDistances = new HashMap<GeoRef, MinimalDistance>(geoRefs.size());

        // Listado de todos los puntos del complemento del convexhull con su punto más cercano al conjunct
        for (GeoRef innerPoint : geoRefs) {
            // Minima distancia que tiene el punto comparado con las paredes del conjunct
            MinimalDistance minDist = new MinimalDistance(conjunct, innerPoint);
            minimalDistances.put(innerPoint, minDist);
        }

        System.out.println("Calculados minimos iniciales. Quedan:");

        // We add the remaining points to the circuit
        for (int j = geoRefs.size(); 0 < j; j--) {

            if(j%500==0){
                System.out.print(" "+j);
            }

            MinimalDistance bestToAdd = null;
            double ratioDistance = Double.POSITIVE_INFINITY;
            double ratioDistanceAux;

            // Buscamos el punto que tiene menor distancia con el conjunct.
            for (MinimalDistance actual : minimalDistances.values()) {
                ratioDistanceAux = actual.getRatio();

                if (ratioDistanceAux < ratioDistance) {
                    ratioDistance = ratioDistanceAux;
                    bestToAdd = actual;
                }
            }

            conjunct.add(bestToAdd.getInsertionIndex() + 1, bestToAdd.getP());
            geoRefs.remove(bestToAdd.getP());

            // We need to remove it from the list because now is part of the CH
            minimalDistances.remove(bestToAdd.getP());

            updateCHCosts(minimalDistances, bestToAdd.getmPath());
        }

        roadDistance = countDistance(conjunct);
        time = System.nanoTime() - time;
        System.out.println("");
    }

    private void updateCHCosts(Map<GeoRef, MinimalDistance> minimalDistances, PathProposal path) {

        PathProposal.insertInCHCosts(path);

        // For each remaining inner point
        for (int i=0;i<geoRefs.size();i++) {
            MinimalDistance actual = minimalDistances.get(geoRefs.get(i));

            actual.updateCosts(path, conjunct);
        }
    }

    private void viewList(ArrayList<GeoRef> list) {
        for (GeoRef tmp : list) {
            System.out.println("ID: " + tmp.getId() + " - " + tmp.getX() + " - " + tmp.getY());
        }

    }

    private double countDistance(List<GeoRef> c) {
        double road = 0;

        for (int i = 0; i < c.size() - 1; i++) {
            road += c.get(i).distance(c.get(i + 1));
        }

        road += c.get(0).distance(c.get(c.size() - 1));

        return road;
    }

}

