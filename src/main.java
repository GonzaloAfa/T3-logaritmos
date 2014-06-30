import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Gonzaloafa on 24-06-2014.
 */
public class main {

    static public void main (String[]args) throws IOException{

        FileExperiment file = new FileExperiment("Datos");
        ArrayList<GeoRefData> geoRefDatas = file.loadData();

        for (GeoRefData geoRefData : geoRefDatas){

            // ciudad
            file.saveData(geoRefData.getCity());

            Algorithm[] algorithms = {new ClosestPoint(),new HeuristicConvexHull()};

            for (Algorithm alg : algorithms){
                alg.loadData(geoRefData.getGeoRefs());
                alg.run();

                // TipoAlgoritmo + Tiempo + Distancia
                file.saveData(alg.getNameAlgorithm() , alg.getTime(), alg.getRoadDistance());
                file.flush();
            }

        }

        file.close();

/*
        System.out.println("Experimento Puntos Cercanos");
        Algorithm experiment = new ClosestPoint();
        experiment.loadData(geoRefs);
        experiment.run();

        System.out.println("Distancia: " + experiment.getRoadDistance());
        System.out.println("Tiempo: " + experiment.getTime());



        System.out.println("Experimento Heuristica ");
        Algorithm experiment2 = new HeuristicConvexHull();
        experiment2.loadData(geoRefs);
        experiment2.run();

        System.out.println("Distancia: "+experiment2.getRoadDistance());
        System.out.println("Tiempo: " + experiment2.getTime());
*/

    }

}
