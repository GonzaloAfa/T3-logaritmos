/**
 * Created by Gonzaloafa on 24-06-2014.
 */
abstract class Algorithm {

    protected long time;
    protected long roadDistance;
    protected GeoRef[] geoRefs;

    public Algorithm(){
        this.time       = 0;
        this.roadDistance = 0;
    }

    public void loadData(GeoRef[] data){
        this.time       = 0;
        this.roadDistance = 0;
        this.geoRefs    = data.clone();
    }

    abstract void run();


    public long getTime(){
        return this.time;
    }

    public long getRoadDistance(){
        return this.roadDistance;
    }

}
