import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gonzaloafa on 24-06-2014.
 */
abstract class Algorithm {

    protected long time;
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

    abstract void run();


    public long getTime(){
        return this.time;
    }

    public double getRoadDistance(){
        return this.roadDistance;
    }

}
