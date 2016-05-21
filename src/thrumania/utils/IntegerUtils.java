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


}
