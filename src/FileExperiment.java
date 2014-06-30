import java.io.*;
import java.util.ArrayList;

/**
 * Created by Gonzaloafa on 24-06-2014.
 */
public class FileExperiment {

    private ArrayList<GeoRef> geoRef;
    private String city = "none";
    private int size = 0;


    public FileExperiment(String archive) throws IOException {

        DataInputStream in = null;

        try {
            String strLine;
            size = 0;

            FileInputStream fileInputStream = new FileInputStream(archive);

            in = new DataInputStream(fileInputStream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            geoRef = new ArrayList<GeoRef>();

            for (int i = 0; i < 7; i++) {
                strLine = br.readLine();

                // City
                if (i == 1) {
                    city = parseCity(strLine);
                }
            }

            while (!(strLine = br.readLine()).equals("EOF")) {

                geoRef.add(parseGeoRef(strLine));
            }

        } catch (FileNotFoundException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        } finally {
            if (in != null) {
                in.close();
            }
            // TODO: acá deberia funcionar un in.close(), pero no me resulta xD
        }

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

    public String getCity() {
        return city;
    }

    public ArrayList<GeoRef> getGeoRef() {
        return this.geoRef;
    }


}
