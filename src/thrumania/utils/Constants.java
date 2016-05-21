package thrumania.utils;

import javax.swing.*;
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
    public static enum Elements {
        ZOOM_IN, ZOOM_OUT, SAVE, LOAD, RIGHT_ARROW, LEFT_ARROW, DOWN_ARROW, UP_ARROW, PREVIEW, EXIT , UNDO , REDO,
        STONE_MINE, GOLD_MINE, HIGH_ALTITTUDE_LAND, LOW_ALTITTUDE_LAND, TREE, FISH, DEEP_SEA, SHALLOW_SEA , EMPTY


    }

    public  static enum ZoomScales{
        NEGATIVEONESCALE, ZEROSCALE, POSITIVEONESCALE , POSETIVE_TWO_
    }


    public static void mouseInitializer(JFrame frame){
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        toolkit.getBestCursorSize(1, 11);
        frame.setCursor(toolkit.createCustomCursor(
                ImageUtils.getImage("cursorBottomPanel.png"),
                new Point(0, 0), "custom cursor"));

    }

    public static void initializeConstants() {
        if (Toolkit.getDefaultToolkit().getScreenSize().getWidth() < 1920) {
            CELL_SIZE = 32;
        } else {
            CELL_SIZE = 40;
        }
    }

}
