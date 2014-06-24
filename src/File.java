import java.io.*;

/**
 * Created by Gonzaloafa on 24-06-2014.
 */
public class File {

    private GeoRef[] geoRef;
    private String city;

    public File(String archive){

        DataInputStream in = null;

        try {

            FileInputStream fileInputStream = new FileInputStream(archive);

            in  = new DataInputStream(fileInputStream);
            BufferedReader br   = new BufferedReader(new InputStreamReader(in));

            String strLine;

            for (int i = 0; i < 7; i++) {

                strLine = br.readLine();

                // Dimension
                if (i == 4){
                    System.out.println (strLine);
                }
            }

            while(!(strLine = br.readLine()).equals("EOF")){
                System.out.println (strLine);
            }



            in.close();


        } catch (FileNotFoundException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // acá deberia funcionar un in.close(), pero no me resulta xD
        }
        //   System.err.println("Error: " + e.getMessage());

    }

    public GeoRef[] getGeoRef(){
        return this.geoRef;
    }




}
