package thrumania.utils;

import thrumania.board.item.MapItems.MapElement;

import javax.swing.*;
import java.awt.*;

/**
 * Created by mohamadamin on 5/18/16.
 */

public class Constants {


    public static boolean isMac = false;
    public static int MATRIX_WIDTH = 80;
    public static int MATRIX_HEIGHT = 50;
    public static int CELL_SIZE = 40;
    public static int INSIDE_CELL_ELEMENT_SIZE = CELL_SIZE / 2 ;

    public static int DRAWER_WIDTH = 40;
    public static int Drawer_HIGHT = 22;
    public static int [][] zoomNumbers={
            {21,26,62,35},
            {26,32,50,28},
            {32,40,40,22},
            {40,50,32,18},
            {50,62,26,15}
       };


    public static enum Elements {
            ZOOM_IN, ZOOM_OUT, SAVE, LOAD, RIGHT_ARROW, LEFT_ARROW, DOWN_ARROW, UP_ARROW, PREVIEW, EXIT, UNDO, REDO,
                STONE_MINE, GOLD_MINE, HIGH_ALTITTUDE_LAND, LOW_ALTITTUDE_LAND, TREE, FISH, DEEP_SEA, SHALLOW_SEA, AGRICULTURE,  EMPTY
        }

        public static enum ZoomScales {
            NEEGATIVE_TWO_SCALE, NEGATIVE_ONE_SCALE, ZERO_SCALE, POSITIVE_ONE_SCALE, POSITIVE_TWO_SCALE, ZoomScales;
        }

    public static enum Seasons {
        SPRING(0),
        SUMMER(1),
        AUTMN(2),
        WINTER(3);
        private int value;

        private Seasons(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
        public static enum DayTime {
        MORNING , NIGHT
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
            CELL_SIZE = 32;
            INSIDE_CELL_ELEMENT_SIZE = CELL_SIZE / 2 ;
            MATRIX_HEIGHT = 98;
            MATRIX_WIDTH = 80;
            isMac= true;
        } else {
            CELL_SIZE = 40;
            INSIDE_CELL_ELEMENT_SIZE = CELL_SIZE / 2 ;
        }
    }


    public static int findZoomScaleNumber(ZoomScales zoomScale){
        if(zoomScale==Constants.ZoomScales.NEEGATIVE_TWO_SCALE) return -2;
        else if(zoomScale==Constants.ZoomScales.NEGATIVE_ONE_SCALE) return -1;
        else if(zoomScale==Constants.ZoomScales.ZERO_SCALE) return 0;
        else if(zoomScale==Constants.ZoomScales.POSITIVE_ONE_SCALE) return 1;
        else return 2;
    }

    public static ZoomScales findZoomScaleElement(int num){
        if(num == -2)return Constants.ZoomScales.NEEGATIVE_TWO_SCALE;
        else if(num==-1) return Constants.ZoomScales.NEGATIVE_ONE_SCALE;
        else if(num==0) return Constants.ZoomScales.ZERO_SCALE;
        else if(num==1) return Constants.ZoomScales.POSITIVE_ONE_SCALE;
        else return Constants.ZoomScales.POSITIVE_TWO_SCALE;
    }

    public static ZoomScales incScale(ZoomScales zoomScale) {
        int num = findZoomScaleNumber(zoomScale);
        if(num<2){
            if(isMac) Constants.CELL_SIZE =  zoomNumbers[num+3][0];
            else Constants.CELL_SIZE =  zoomNumbers[num+3][1];
            Constants.DRAWER_WIDTH = zoomNumbers[num+3][2];
            Constants.Drawer_HIGHT = zoomNumbers[num+3][3];
            INSIDE_CELL_ELEMENT_SIZE = CELL_SIZE / 2 ;
            return findZoomScaleElement(num+1);
        }
        return findZoomScaleElement(num);
    }
    public static ZoomScales decScale(ZoomScales zoomScale) {
        int num = findZoomScaleNumber(zoomScale);
        
//        System.out.println("num is "+ num);
        if(num>-2){
            if(isMac) Constants.CELL_SIZE =  zoomNumbers[num+1][0];
            else Constants.CELL_SIZE =  zoomNumbers[num+1][1];
            Constants.DRAWER_WIDTH = zoomNumbers[num+1][2];
            Constants.Drawer_HIGHT = zoomNumbers[num+1][3];
            INSIDE_CELL_ELEMENT_SIZE = CELL_SIZE / 2 ;
            return findZoomScaleElement(num-1);
        }
        return findZoomScaleElement(num);
    }

}
