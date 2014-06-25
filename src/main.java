import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gonzaloafa on 24-06-2014.
 */
public class main {

    static public void main (String[]args) throws IOException{

        File file = new File("Datos/vm22775.txt");


        ArrayList<GeoRef> geoRefs =  file.getGeoRef();

        Algorithm experiment = new ClosestPoint();
        experiment.loadData(geoRefs);
        experiment.run();

        Algorithm experiment2 = new HeuristicConvexHull();
        experiment2.loadData(geoRefs);
        experiment2.run();

    }

}
