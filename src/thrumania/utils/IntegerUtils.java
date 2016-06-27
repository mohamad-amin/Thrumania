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

    public static Coordinate getCoordinateWithXAndY( int xCord  , int yCord){

        Coordinate coordinate = new Coordinate((int) Math.floor((double) yCord / (double)Constants.CELL_SIZE), (int) Math.floor((double) xCord / (double) Constants.CELL_SIZE));
        return coordinate;
    }
    public  static int [] getXAndYWithCoordinate(Coordinate crd){
        int []cords = new int[2];
        cords [0] = crd.getColumn() * Constants.CELL_SIZE;
        cords [1 ]= crd.getRow() * Constants.CELL_SIZE ;
        return  cords;

    }
}
