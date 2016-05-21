package thrumania.utils;

import java.awt.*;

/**
 * Created by mohamadamin on 5/18/16.
 */

public class Constants {

    public static int MATRIX_WIDTH = 100;
    public static int MATRIX_HEIGHT = 75;
    public static int CELL_SIZE = 40;
    public static int MATRIX_SIZE = 100;
    public static int DRAWER_WIDTH = 40;
    public static int Drawer_HIGHT = 22;

    public static void initializeConstants() {
        if (Toolkit.getDefaultToolkit().getScreenSize().getWidth() < 1920) {
            CELL_SIZE = 32;
        } else {
            CELL_SIZE = 40;
        }
    }

}
