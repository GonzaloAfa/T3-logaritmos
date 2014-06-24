import java.io.*;

/**
 * Created by Gonzaloafa on 24-06-2014.
 */
public class File {

    private GeoRef[] geoRef;
    private String city = "none";
    private int size    = 0;

    public File(String archive){

        DataInputStream in = null;

        try {
            String strLine;
            size = 0;

            FileInputStream fileInputStream = new FileInputStream(archive);

            in                  = new DataInputStream(fileInputStream);
            BufferedReader br   = new BufferedReader(new InputStreamReader(in));


            for (int i = 0; i < 7; i++) {
                strLine = br.readLine();

                // City
                if (i == 1)
                    city = parseCity(strLine);

                // Dimension
                if (i == 4) {
                    size = parseDimension(strLine);
                    geoRef = new GeoRef[size];
                }
            }


            for (int i = 0; !(strLine = br.readLine()).equals("EOF") ; i++) {
                geoRef[i] = parseGeoRef(strLine);
            }

            in.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // TODO: acá deberia funcionar un in.close(), pero no me resulta xD
        }

    }

    public GeoRef[] getGeoRef(){
        return this.geoRef;
    }

    private String parseCity(String data){
        return data.substring(data.indexOf("in")+1).trim();
    }

    private int parseDimension(String data){
        String info = data.substring(data.indexOf(":")+1).trim();
        return Integer.parseInt(info);
    }

    private GeoRef parseGeoRef(String data){
        int p1 = data.indexOf(" ");
        int p2 = data.indexOf(" ", p1+1);

        int id      = Integer.parseInt(data.substring(0, p1).trim());
        double x    = Double.parseDouble(data.substring(p1+1, p2).trim());
        double y    = Double.parseDouble(data.substring(p2+1).trim());

        GeoRef geoRef = new GeoRef(id-1, x, y);
        return geoRef;
    }

    public String getCity(){
        return city;
    }





}
