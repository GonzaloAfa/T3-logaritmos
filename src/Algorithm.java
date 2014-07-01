import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gonzaloafa on 24-06-2014.
 */
abstract class Algorithm {

    protected long time;
    public String nameAlgorithm;
    protected double roadDistance;
    protected List<GeoRef> geoRefs, conjunct;

    public Algorithm(){
        this.time       = 0;
        this.roadDistance = 0;
    }

    public void loadData(List<GeoRef> data){
        this.time           = 0;
        this.roadDistance   = 0;
        this.geoRefs        = (List<GeoRef>)((ArrayList<GeoRef>) data).clone();
        conjunct = new ArrayList<GeoRef>(geoRefs.size());
    }

    abstract String getNameAlgorithm();
    abstract void run();


    public double getTime(){
        return this.time/1000000000.0;
    }

    public double getRoadDistance(){
        return this.roadDistance;
    }

}
