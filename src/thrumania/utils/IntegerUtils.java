package thrumania.utils;

/**
 * Created by mohamadamin on 5/18/16.
 */

public class IntegerUtils {

    public static boolean isInRange(int start, int end, int target) {
        return target >= start && target <= end;
    }

    public static boolean isInSideTheRangeOfCordinates(int elementXcord1, int elementYcord1, int elementXcord2, int elementyCord2, int mouseXcord, int mouseYcord) {
        if (elementXcord1 <= mouseXcord && elementXcord2 >= mouseXcord)
            if (elementYcord1 <= mouseYcord && elementyCord2 >= mouseYcord)
                return true;
        return false;
    }

    public static Coordinate getCoordinateWithXAndY(int xCord, int yCord) {

        Coordinate coordinate = new Coordinate((int) Math.floor((double) yCord / (double) Constants.CELL_SIZE), (int) Math.floor((double) xCord / (double) Constants.CELL_SIZE));
        return coordinate;
    }

    public static int[] getXAndYWithCoordinate(Coordinate crd) {
        // TODO :
        int[] cords = new int[2];
        cords[0] = crd.getColumn() * Constants.CELL_SIZE + Constants.CELL_SIZE / 10 ;
        cords[1] = crd.getRow() * Constants.CELL_SIZE  + Constants.CELL_SIZE / 10 ;
        return cords;

    }

    public static int getDistanceOfTWoIntegers(int a, int b) {


        return Math.abs(a - b);
    }

    public static double fisaghooresAlgorithm(Coordinate crd1 , Coordinate crd2){
        double result =0 ;

        result = Math.sqrt(  Math.pow(  crd1.getRow()  - crd2.getRow() , 2 ) + Math.pow( crd1.getColumn() - crd2.getColumn() , 2   ));
        return  result;



    }
}
