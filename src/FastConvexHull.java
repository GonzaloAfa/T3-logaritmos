import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class FastConvexHull implements ConvexHullAlgorithm{

    @Override
    public ArrayList<GeoRef> execute(ArrayList<GeoRef> points){

        ArrayList<GeoRef> xSorted = (ArrayList<GeoRef>) points.clone();
        //Sorting
        Collections.sort(xSorted, new Comparator<GeoRef>() {
            @Override
            public int compare(GeoRef p1, GeoRef  p2){
                return  new Double(p1.getX()).compareTo(new Double(p2.getX()));
            }
        });


        int n = xSorted.size();
        GeoRef[] lUpper = new GeoRef[n];

        lUpper[0] = xSorted.get(0);
        lUpper[1] = xSorted.get(1);

        int lUpperSize = 2;

        for (int i = 2; i < n; i++){

            lUpper[lUpperSize] = xSorted.get(i);
            lUpperSize++;

            while (lUpperSize > 2 && !rightTurn( lUpper[lUpperSize - 3], lUpper[lUpperSize - 2], lUpper[lUpperSize - 1])){

                // Remove the middle point of the three last
                lUpper[lUpperSize - 2] = lUpper[lUpperSize - 1];
                lUpperSize--;
            }
        }

        GeoRef[] lLower = new GeoRef[n];

        lLower[0] = xSorted.get(n - 1);
        lLower[1] = xSorted.get(n - 2);

        int lLowerSize = 2;

        for (int i = n - 3; i >= 0; i--){

            lLower[lLowerSize] = xSorted.get(i);
            lLowerSize++;

            while (lLowerSize > 2 && !rightTurn(lLower[lLowerSize - 3], lLower[lLowerSize - 2], lLower[lLowerSize - 1]))
            {
                // Remove the middle point of the three last
                lLower[lLowerSize - 2] = lLower[lLowerSize - 1];
                lLowerSize--;
            }
        }

        ArrayList<GeoRef> result = new ArrayList<GeoRef>();

        for (int i = 0; i < lUpperSize; i++){
            result.add(lUpper[i]);
        }

        for (int i = 1; i < lLowerSize - 1; i++){
            result.add(lLower[i]);
        }

        return result;
    }

    private boolean rightTurn(GeoRef a, GeoRef b, GeoRef c){
        return (b.getX() - a.getX())*(c.getY() - a.getY()) - (b.getY() - a.getY())*(c.getX() - a.getX()) > 0;
    }



}