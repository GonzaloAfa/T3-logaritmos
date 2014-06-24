import java.io.IOException;

/**
 * Created by Gonzaloafa on 24-06-2014.
 */
public class main {

    static public void main (String[]args) throws IOException{

        File file = new File("Datos/vm22775.txt");

        GeoRef[] geoRefs =  file.getGeoRef();

        Algorithm experiment = new ClosestPoint();
        experiment.loadData(geoRefs);
        experiment.run();

    }

}
