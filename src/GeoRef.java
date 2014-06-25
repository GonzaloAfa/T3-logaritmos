/**
 * Created by Gonzaloafa on 24-06-2014.
 */
public class GeoRef {

    private int id;
    private double x;
    private double y;
    private boolean mark;

    public GeoRef(int id, double x, double y){
        this.id = id;
        this.x = x;
        this.y = y;
        this.mark = false;
    }


    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public boolean isMark() {
        return mark;
    }

    public void setMark(){
        this.mark = true;
    }

    public int getId() {
        return id;
    }
}
