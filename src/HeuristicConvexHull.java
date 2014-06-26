import java.util.ArrayList;

/**
 * Created by Gonzaloafa on 24-06-2014.
 */
public class HeuristicConvexHull extends Algorithm {

    public HeuristicConvexHull(){
        super();
    }

    void run() {

        FastConvexHull fastConvexHull   = new FastConvexHull();
        ArrayList<GeoRef> convexHull    = fastConvexHull.execute(this.geoRefs);

        ArrayList<MinimalDistance> minimalDistances;

        long timePass = System.nanoTime();
        this.roadDistance = 0;

        // Obtenemos el complemento
        convexHullComplement(convexHull);


        for (int j = geoRefs.size() ; 0 < j   ; j--){

            // Listado de puntos cercanos.
            minimalDistances = new ArrayList<MinimalDistance>();

            // Listado de todos los puntos del complemento del convexhull con su punto más cercano al convexHull
            for (GeoRef p : geoRefs) {
                // Minima distancia que tiene el punto comparado con las paredes del convexHull
                int i = minDistance(convexHull, p);
                minimalDistances.add(new MinimalDistance(p, convexHull.get(i), convexHull.get(i + 1), i));
            }



            int i = 0;
            GeoRef point = null;
            double radioDistance = 999999999;
            double radioDistanceAux;

            // Buscamos el punto que tiene menor distancia con el convexHull.
            for (MinimalDistance tmp : minimalDistances) {
                radioDistanceAux = radioDistance(tmp.getP(), tmp.getP1(), tmp.getP2());

                if (radioDistanceAux < radioDistance) {
                    radioDistance = radioDistanceAux;
                    point = tmp.getP();
                    i = tmp.getID();
                }
            }

            convexHull.add(i, point);
            geoRefs.remove(point);
        }

        this.roadDistance   = countDistance(convexHull);
        this.time           = System.nanoTime() - timePass;



    }


    private void convexHullComplement (ArrayList<GeoRef> convexHull){
        for ( GeoRef tmp : convexHull){
            this.geoRefs.remove(tmp);
        }
    }

    private void viewList(ArrayList<GeoRef> list){
        for (GeoRef tmp : list){
            System.out.println("ID: "+tmp.getId()+ " - "+tmp.getX()+ " - "+tmp.getY());
        }

    }



    private int minDistance(ArrayList<GeoRef> convexHull, GeoRef p){

        double distance;
        double minDistance = 999999999;
        int aux = 0;


        for (int i = 0; i < convexHull.size()-1 ; i++) {

            distance = distance(p,convexHull.get(i), convexHull.get(i+1) );

            if(distance < minDistance){
                minDistance = distance;
                aux         = i;
            }
        }
        return aux;
    }




    private double distance(GeoRef p, GeoRef p1, GeoRef p2){
        return distance(p, p1) + distance(p, p2) - distance(p1,p2);
    }

    private double radioDistance(GeoRef p, GeoRef p1, GeoRef p2){
        return (distance(p, p1) + distance(p, p2)) / (distance(p1, p2));
    }


    private double distance (GeoRef p1, GeoRef p2){
        return Math.sqrt( Math.pow(p1.getX() - p2.getX(), 2) + Math.pow(p1.getY() - p2.getY(), 2));
    }


    private long countDistance(ArrayList<GeoRef> c ){
        long road = 0;

        for (int i = 0; i < c.size() - 1 ; i++)
            road = road + (long)distance(c.get(i), c.get(i+1));

        return road;
    }

}

