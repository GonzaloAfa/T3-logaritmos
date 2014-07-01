import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Gonzaloafa on 24-06-2014.
 */
public class main {

    static public void main (String[]args) throws IOException{

        FileExperiment file = new FileExperiment("Datos");
        ArrayList<GeoRefData> geoRefDatas = file.loadData();

        for (GeoRefData geoRefData : geoRefDatas){

            if(!geoRefData.getCity().equals("Djibouti"))
                continue;

            System.out.println("Ciudad: "+geoRefData.getCity());

            // ciudad
            file.saveData(geoRefData.getCity());

//            Algorithm[] algorithms = {new ClosestPoint(),new HeuristicConvexHull(), new Prim()};
            Algorithm[] algorithms = {new ClosestPoint(),new HeuristicConvexHull()};
//            Algorithm[] algorithms = {new Prim()};

            for (Algorithm alg : algorithms){
                alg.loadData(geoRefData.getGeoRefs());

               System.out.print("Ejecutando " + alg.getNameAlgorithm());

                alg.run();

                System.out.println(": listo!");
                System.out.println("Tiempo: " + (alg.getTime()/1000)+"\nDistance: "+ alg.getRoadDistance());
                // TipoAlgoritmo + Tiempo + Distancia
                file.saveData(alg.getNameAlgorithm() , alg.getTime(), alg.getRoadDistance());
                file.flush();
            }

        }

        file.close();
    }

}
