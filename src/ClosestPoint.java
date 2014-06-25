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

        geoRefs.get(0).setMark();
        road.add(geoRefs.get(0));

        long timePass = System.nanoTime();

        for (int j = 0; j < geoRefs.size(); j++) {

            double minDistance  = 999999999;
            int id              = 0;
            long distance       = 0;

            for (int i = 0; i < geoRefs.size(); i++) {

                // Busco la menor distancia entre los GeoRef que no están marcados.
                if(!geoRefs.get(i).isMark()){
                    distance = (long)distance(road.get(road.size()-1), geoRefs.get(i));

                    // si es el minimo, mantengo la información del arreglo.
                    if ( distance < minDistance ) {
                        minDistance = distance;
                        id = i;
                    }
                }
            }


            /* Almaceno el nodo que tiene la menor distancia entre todos los nodos no marcados */
            geoRefs.get(id).setMark();
            road.add(geoRefs.get(id));
            this.roadDistance = this.roadDistance + distance;
        }


        this.time = System.nanoTime() - timePass;

        /*
        System.out.println("Puntos");

        for (GeoRef tmp : geoRefs) {
            System.out.println("id:"+tmp.getId()+ " x:"+ tmp.getX() + " - y:" + tmp.getY());
        }

        System.out.println("Camino");

        for (GeoRef tmp : road) {
            System.out.println("id:"+tmp.getId()+ " x:"+ tmp.getX() + " - y:" + tmp.getY());
        }

        System.out.println("Size: "+geoRefs.size()+ " - "+road.size());
        */

    }


    private double distance(GeoRef point1, GeoRef point2){
        return Math.sqrt(
                Math.pow((double)(point1.getX() - point2.getX()), 2) +
                Math.pow((double)(point1.getY() - point2.getY()), 2)
        );
    }

}
