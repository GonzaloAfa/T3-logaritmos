import java.awt.Point;
import java.util.ArrayList;
import java.util.List;


public interface ConvexHullAlgorithm
{
    List<GeoRef> execute(List<GeoRef> points);
}
