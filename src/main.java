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

            // Ciudad
            file.saveData(geoRefData.getCity());
            System.out.println(geoRefData.getCity());

            Algorithm[] algorithms = {new ClosestPoint(),new HeuristicConvexHull()};

            for (Algorithm alg : algorithms){
                System.out.println(">> "+alg.getNameAlgorithm());

                alg.loadData(geoRefData.getGeoRefs());
                alg.run();

                // TipoAlgoritmo + Tiempo + Distancia
                file.saveData(alg.getNameAlgorithm() , alg.getTime(), alg.getRoadDistance());
                file.flush();
                System.out.println(">> Time:"+alg.getTime() +" - Distance:"+ alg.getRoadDistance());
            }

        }

        file.close();
    }

}
