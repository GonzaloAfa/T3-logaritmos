import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Gonzaloafa on 24-06-2014.
 */
public class main {

    static public void main(String[] args) throws IOException {
        /*System.out.println("start");
        int[] a = new int[600000000];
        for (int i = 0; i < a.length; i++) {
            a[i] = (int) (Math.random() * 600000000);
        }
        System.out.println("Start sorting");
        double time = System.currentTimeMillis();
        Arrays.sort(a);
        System.out.println("Time: " + ((System.currentTimeMillis() - time) / 1000) + "[s]");
        for (int i = 0; i < 5000; i++) {
            System.out.print(a[i] + " ");
        }*/
        File dir = new File("Datos");
        String[] ficheros = dir.list();

        // Listado de archivos
        if (ficheros == null)
            System.out.println("No hay ficheros en el directorio especificado");
        else {
            System.out.println("Ficheros");
            for (int x = 0; x < ficheros.length; x++)
                System.out.println(ficheros[x]);
        }

        FileExperiment file = null;

        try {
            file = new FileExperiment("Datos/" + ficheros[11]);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error de lectura de archivos: " + e);
            System.exit(-1);
        }


        System.out.println("City: " + file.getCity());

        ArrayList<GeoRef> geoRefs = file.getGeoRef();

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

        System.out.println("Distancia: " + experiment2.getRoadDistance());
        System.out.println("Tiempo: " + experiment2.getTime());


    }

}
