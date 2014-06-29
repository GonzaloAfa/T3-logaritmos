import java.util.ArrayList;

/**
 * Created by Gonzaloafa on 29-06-2014.
 */
public class GeoRefData {

    private String city;
    private ArrayList<GeoRef> geoRefs;

    public GeoRefData(){
        geoRefs = new ArrayList<GeoRef>();
    }

    public void addCity(String city){
        this.city = city;
    }

    public void addGeoRef(GeoRef geoRef){
        geoRefs.add(geoRef);
    }


    public String getCity() {
        return city;
    }

    public ArrayList<GeoRef> getGeoRefs(){
        return this.geoRefs;
    }
}
