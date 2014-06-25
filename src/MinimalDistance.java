/**
 * Created by Gonzaloafa on 25-06-2014.
 */
public class MinimalDistance {

    private GeoRef p;
    private GeoRef p1;
    private GeoRef p2;
    private int ID;

    public MinimalDistance(GeoRef p, GeoRef p1, GeoRef p2, int i){
        this.p  = p;
        this.p1 = p1;
        this.p2 = p2;
        this.ID = i;
    }


    public GeoRef getP() {
        return p;
    }

    public GeoRef getP1() {
        return p1;
    }

    public GeoRef getP2() {
        return p2;
    }

    public int getID() {
        return ID;
    }
}
