import java.util.ArrayList;

/**
 * Created by Gonzaloafa on 24-06-2014.
 */
abstract class Algorithm {

    protected long time;
    protected long roadDistance;
    protected ArrayList<GeoRef> geoRefs;
    public String nameAlgorithm;

    public Algorithm(){
        this.time       = 0;
        this.roadDistance = 0;
    }

    public void loadData(ArrayList<GeoRef> data){
        this.time           = 0;
        this.roadDistance   = 0;
        this.geoRefs        = (ArrayList<GeoRef>) data.clone();
    }

    abstract String getNameAlgorithm();
    abstract void run();


    public long getTime(){
        return this.time;
    }

    public long getRoadDistance(){
        return this.roadDistance;
    }

}
