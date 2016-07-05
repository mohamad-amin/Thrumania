package thrumania.utils;

import javax.swing.*;
import java.awt.*;

/**
 * Created by mohamadamin on 5/18/16.
 */

public class Constants {


    public static final int RANGEOFSCROLL = 80;
    public static final int RATEOFSCROLL = 10;
    //a square that human is in the middle
    public static final int RANDOMMOVERANGE = 20;
    public static boolean isMac = false;
    public static int MATRIX_WIDTH = 80;
    public static int MATRIX_HEIGHT = 50;
    public static int CELL_SIZE = 50;
    public static int INSIDE_CELL_ELEMENT_SIZE = CELL_SIZE / 2;
    public static int STATE_QUEUE_SIZE = 50;
    public static int NUMBER_OF_PLAYERS;
    public static int NETWORK_PORT = 9442;

    public final static int DISTANCE_WATER_WATER = 4,
            DISTANCE_WATER_LAND = 3,
            DISTANCE_LOWLAND_HIGHLAND = 2,
            DISTANCE_LOWLAND_LOWLAND = 1,
            DISTANCE_HIGHLAND_HIGHLAND = 2,
            DISTANCE_ISLAND_DISTINGUISHER = (MATRIX_WIDTH > MATRIX_HEIGHT) ? MATRIX_WIDTH * 5 : MATRIX_HEIGHT * 5;
    public static int OcupationOfQuarry = 5;

    public enum BottomPanelSelected {
        addWorker, addSoldier, addContainerShip, buildingBarak, buildingMinequarry, buildingWoodquarry, buildingPort, mountainwaer, buildingFarm, addFisherShip

    }

    public enum BuildSomething {
        woodquarry, barrak, farm, minequarry, port

    }

    public static int DRAWER_WIDTH = 32;
    public static int Drawer_HIGHT = 18;
    public static int[][] zoomNumbers = {
            {26, 32, 50, 28},
            {32, 40, 40, 22},
            {40, 50, 32, 18},
            {50, 62, 26, 15},
            {62, 78, 21, 12}
    };
    public static long scrollSpeed = 50;
    public static int sizeOfInformationBar = 14;


    public enum Elements {
        ZOOM_IN, ZOOM_OUT, SAVE, LOAD, RIGHT_ARROW, LEFT_ARROW, DOWN_ARROW, UP_ARROW, PREVIEW, EXIT, UNDO, REDO,
        STONE_MINE, GOLD_MINE, HIGH_ALTITTUDE_LAND, LOW_ALTITTUDE_LAND, TREE, FISH, DEEP_SEA, SHALLOW_SEA, AGRICULTURE, Eraser, EMPTY
    }

    public enum ZoomScales {
        NEEGATIVE_TWO_SCALE, NEGATIVE_ONE_SCALE, ZERO_SCALE, POSITIVE_ONE_SCALE, POSITIVE_TWO_SCALE, ZoomScales
    }

    public enum Seasons {
        SPRING(0),
        SUMMER(1),
        AUTMN(2),
        WINTER(3);
        private int value;

        Seasons(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public enum DayTime {
        MORNING, NIGHT
    }

    public enum PlayPanelElements {
        WORKER, SOLDIER, TROOPBUILDING, FARM, SEE_PORT, WORKER_SHIP, CONTAINER_SHIP, MILITARY_SHIP
    }

    public static void mouseInitializer(JFrame frame) {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        toolkit.getBestCursorSize(1, 11);
        frame.setCursor(toolkit.createCustomCursor(
                ImageUtils.getImage("cursorBottomPanel.png"),
                new Point(0, 0), "custom cursor"));
    }


    public static void initializeConstants() {
        if (Toolkit.getDefaultToolkit().getScreenSize().getWidth() < 1920) {
            // mac :
            CELL_SIZE = 40;
            INSIDE_CELL_ELEMENT_SIZE = CELL_SIZE / 2;
            MATRIX_HEIGHT = 98;
            MATRIX_WIDTH = 80;
            isMac = true;
        } else {
            // not mac :
            CELL_SIZE = 50;
            INSIDE_CELL_ELEMENT_SIZE = CELL_SIZE / 2;
        }
    }


    public static int findZoomScaleNumber(ZoomScales zoomScale) {
        if (zoomScale == Constants.ZoomScales.NEEGATIVE_TWO_SCALE) return -2;
        else if (zoomScale == Constants.ZoomScales.NEGATIVE_ONE_SCALE) return -1;
        else if (zoomScale == Constants.ZoomScales.ZERO_SCALE) return 0;
        else if (zoomScale == Constants.ZoomScales.POSITIVE_ONE_SCALE) return 1;
        else return 2;
    }

    public static ZoomScales findZoomScaleElement(int num) {
        if (num == -2) return Constants.ZoomScales.NEEGATIVE_TWO_SCALE;
        else if (num == -1) return Constants.ZoomScales.NEGATIVE_ONE_SCALE;
        else if (num == 0) return Constants.ZoomScales.ZERO_SCALE;
        else if (num == 1) return Constants.ZoomScales.POSITIVE_ONE_SCALE;
        else return Constants.ZoomScales.POSITIVE_TWO_SCALE;
    }

    public static ZoomScales incScale(ZoomScales zoomScale) {
        int num = findZoomScaleNumber(zoomScale);
        if (num < 2) {
            if (isMac) Constants.CELL_SIZE = zoomNumbers[num + 3][0];
            else Constants.CELL_SIZE = zoomNumbers[num + 3][1];
            Constants.DRAWER_WIDTH = zoomNumbers[num + 3][2];
            Constants.Drawer_HIGHT = zoomNumbers[num + 3][3];
            INSIDE_CELL_ELEMENT_SIZE = CELL_SIZE / 2;
            return findZoomScaleElement(num + 1);
        }
        return findZoomScaleElement(num);
    }

    public static ZoomScales decScale(ZoomScales zoomScale) {
        int num = findZoomScaleNumber(zoomScale);

//        System.out.println("num is "+ num);
        if (num > -2) {
            if (isMac) Constants.CELL_SIZE = zoomNumbers[num + 1][0];
            else Constants.CELL_SIZE = zoomNumbers[num + 1][1];
            Constants.DRAWER_WIDTH = zoomNumbers[num + 1][2];
            Constants.Drawer_HIGHT = zoomNumbers[num + 1][3];
            INSIDE_CELL_ELEMENT_SIZE = CELL_SIZE / 2;
            return findZoomScaleElement(num - 1);
        }
        return findZoomScaleElement(num);
    }

    public static final byte
            SEA_ID = 6, LOW_LAND_ID = 0, HIGH_LAND_ID = 3, DEEP_SEA_ID = 8,
            FISH_ID = 7, TREE_ID = 1, STONE_ID = 4, GOLD_ID = 5, AGRICULTURE_ID = 2;

    public static int giveMeZeroScale() {
        if(isMac) return zoomNumbers[2][0];
        else return zoomNumbers[2][1];
    }

}