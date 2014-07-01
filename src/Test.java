import java.util.ArrayList;

/**
 * Created by Nolda on 01-07-2014.
 */
public class Test {

    public static void main(String ...args){
        Algorithm[] algorithms = {new ClosestPoint(),new HeuristicConvexHull()};
//            Algorithm[] algorithms = {new Prim()};

        ArrayList<GeoRef> geoRefs = new ArrayList<GeoRef>();

        for (int i=0;i<500;i++) {
            geoRefs.add(new GeoRef(i,Math.random()*1000,Math.random()*1000));
        }


        for (Algorithm alg : algorithms){
            alg.loadData(geoRefs);

            System.out.print("Ejecutando " + alg.getNameAlgorithm());

            alg.run();

            System.out.println(": listo!");

            // TipoAlgoritmo + Tiempo + Distancia
            System.out.println("Tiempo: " + (alg.getTime()/1000)+"\nDistance: "+ alg.getRoadDistance());
        }
    }
}
