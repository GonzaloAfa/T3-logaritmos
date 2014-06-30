/**
 * Created by Gonzaloafa on 24-06-2014.
 */
public class GeoRef {

    private int id;
    private double x;
    private double y;
    private boolean mark;

    public GeoRef(int id, double x, double y) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.mark = false;
    }

    public GeoRef(double x, double y) {
        this.id = -1;
        this.x = x;
        this.y = y;
        this.mark = false;
    }

    public double distance(GeoRef point) {
        return Math.sqrt(
                Math.pow((double) (getX() - point.getX()), 2) +
                        Math.pow((double) (getY() - point.getY()), 2)
        );
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

    public void setMark() {
        this.mark = true;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Id: " + id + " [" + x + ", " + y + "]";
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }
}
