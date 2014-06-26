import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gonzaloafa on 24-06-2014.
 */
public class main {

    static public void main (String[]args) throws IOException{

        File file = new File("Datos/zi929.txt");


        ArrayList<GeoRef> geoRefs =  file.getGeoRef();

        System.out.println("Experimento Puntos Cercanos");

        Algorithm experiment = new ClosestPoint();
        experiment.loadData(geoRefs);
        experiment.run();

        System.out.println("Distancia: "+experiment.getRoadDistance());
        System.out.println("Tiempo: "   +experiment.getTime());


        System.out.println("Experimento Heuristica ");

        Algorithm experiment2 = new HeuristicConvexHull();
        experiment2.loadData(geoRefs);
        experiment2.run();

        System.out.println("Distancia: "+experiment2.getRoadDistance());
        System.out.println("Tiempo: "   +experiment2.getTime());


    }

}
