import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gonzaloafa on 24-06-2014.
 */
public class ClosestPoint extends Algorithm{

    public ClosestPoint(){
        super();
    }

    @Override
    void run() {

        ArrayList<GeoRef> conjunct = new ArrayList<GeoRef>();
        roadDistance = 0;

        //dictatorialmente tomamos el primer número del arreglo y desde ahí construimos nuestro camino.
        conjunct.add(geoRefs.get(0));
        geoRefs.remove(0);



        long minDistance = 999999999;
        GeoRef minP = null;

        long timePass = System.nanoTime();

        while(0 < geoRefs.size()){

            // Buscamos el p \in P que está más cerca de TODOS los puntos del conjunto C
            for (GeoRef p : geoRefs) {

                long d = getDistance(p, conjunct);

                if (d < minDistance)
                    minP = p;
            }

            this.roadDistance = this.roadDistance + (long)distance(conjunct.get(conjunct.size()-1), minP);

            conjunct.add(minP);
            geoRefs.remove(minP);

        }

        this.time = System.nanoTime() - timePass;




    }


    private double distance(GeoRef point1, GeoRef point2){
        return Math.sqrt(
                Math.pow((double)(point1.getX() - point2.getX()), 2) +
                Math.pow((double)(point1.getY() - point2.getY()), 2)
        );
    }


    private long getDistance(GeoRef p, ArrayList<GeoRef> c){
        long distance = 0;

        for (GeoRef tmp : c){
            distance = distance + (long)distance(p, tmp);
        }

        return distance;
    }
}
