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

        List<GeoRef> road = new ArrayList<GeoRef>();

        roadDistance    = 0;

        //dictatorialmente tomamos el primer número del arreglo y desde ahí construimos nuestro camino.
        geoRefs[0].setMark();
        road.add(geoRefs[0]);

        long timePass = System.nanoTime();

        for (int j = 0; j < geoRefs.length; j++) {

            double minDistance  = 999999999;
            int id              = 0;
            long distance       = 0;

            for (int i = 0; i < geoRefs.length ; i++) {

                // Busco la menor distancia entre los GeoRef que no están marcados.
                if(!geoRefs[i].isMark()){
                    distance = (long)distance(road.get(road.size()-1), geoRefs[i]);

                    // si es el minimo, mantengo la información del arreglo.
                    if ( distance < minDistance ) {
                        minDistance = distance;
                        id = i;
                    }
                }
            }


            /* Almaceno el nodo que tiene la menor distancia entre todos los nodos no marcados */
            geoRefs[id].setMark();
            road.add(geoRefs[id]);
            this.roadDistance = this.roadDistance + distance;
        }

        this.time = System.nanoTime() - timePass;

    }


    private double distance(GeoRef point1, GeoRef point2){
        return Math.sqrt(
                Math.pow((double)(point1.getX() - point2.getX()), 2) +
                Math.pow((double)(point1.getY() - point2.getY()), 2)
        );
    }

}
