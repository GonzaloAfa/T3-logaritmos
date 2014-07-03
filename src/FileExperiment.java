import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Gonzaloafa on 24-06-2014.
 */
public class FileExperiment {

    private ArrayList<GeoRefData> geoRefDatas = new ArrayList<GeoRefData>();

    private String[] archives;
    private String directory;

    private PrintWriter writer;
    final String dirResults = "Results";



    public FileExperiment(String directory) throws IOException {

        File dir = new File(directory);
        archives = dir.list();
        this.directory = directory;

        // Listado de archivos
        if (archives == null)
            System.out.println("No hay ficheros en el directorio especificado");


        File theDir = new File(dirResults);
        if (!theDir.exists())
            theDir.mkdir();

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();

        try {
            writer  = new PrintWriter(new BufferedWriter(new FileWriter(dirResults+"/experiment.txt", true)));
            writer.println("Ciudad;Algoritmo;Tiempo;DistanciaTotal");
        } catch (IOException e) {
            System.out.println("ERROR ABRIENDO ARCHIVO!!!");
        }

    }

    public ArrayList<GeoRefData> loadData () throws IOException {

        DataInputStream in = null;

        try {

            for(String archive : this.archives) {
                String strLine;

                FileInputStream fileInputStream = new FileInputStream(this.directory + "/" + archive);
                in                  = new DataInputStream(fileInputStream);
                BufferedReader br   = new BufferedReader(new InputStreamReader(in));

                GeoRefData geoRefData = new GeoRefData();

                for (int i = 0; i < 7; i++) {
                    strLine = br.readLine();

                    // City
                    if (i == 1)
                        geoRefData.addCity(parseCity(strLine));
                }

                while (!(strLine = br.readLine()).equals("EOF"))
                    geoRefData.addGeoRef(parseGeoRef(strLine));

                geoRefDatas.add(geoRefData);

                in.close();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw e;
        } finally {
            if (in != null) {
                in.close();
            }
            // TODO: acá deberia funcionar un in.close(), pero no me resulta xD
        }

        return geoRefDatas;
    }


    private String parseCity(String data) {
        return data.substring(data.indexOf("in") + 3).trim();
    }

    private int parseDimension(String data) {
        String info = data.substring(data.indexOf(":") + 1).trim();
        return Integer.parseInt(info);
    }

    private GeoRef parseGeoRef(String data) {
        String[] splitted = data.split(" ");

        int id = new Integer(splitted[0]);
        double x = new Double(splitted[1]);
        double y = new Double(splitted[2]);

        GeoRef geoRef = new GeoRef(id - 1, x, y);
        return geoRef;
    }

    public void saveData(String city){
        writer.println();
        writer.print(city + ";");
    }

    public void saveData(String algorithm, double time, double distance){
        writer.println(algorithm+";"+time+";"+distance+";");
    }



    public void flush() {
        writer.flush();
    }

    public void close() {
        writer.close();
    }

}
