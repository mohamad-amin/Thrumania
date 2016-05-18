package thrumania.utils;

import java.awt.*;

/**
 * Created by mohamadamin on 5/18/16.
 */

public class Constants {

    public static int CELL_SIZE = 40;
    public static int MATRIX_SIZE = 100;
    public static int DRAWER_WIDTH = 40;
    public static int Drawer_HIGHT = 23;

    public static void initializeConstants() {
        if (Toolkit.getDefaultToolkit().getScreenSize().getWidth() > 2000) {
            CELL_SIZE = 30;
            MATRIX_SIZE = 100;
            DRAWER_WIDTH = 40;
            Drawer_HIGHT = 23;
        } else {
            CELL_SIZE = 40;
            MATRIX_SIZE = 100;
            DRAWER_WIDTH = 41;
            Drawer_HIGHT = 22;
        }
    }

}
