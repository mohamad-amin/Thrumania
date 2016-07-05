package thrumania.utils;

/**
 * Created by AMIR on 7/4/2016.
 */
public class Requirements {
    //Todo
    public static boolean Barrak(int foodRes,int goldRes,int ironRes, int woodRes){
        int foodneeded = 0;
        int goldneeded = 0;
        int ironneeded = 0;
        int woodneeded = 0;
        return true;
//        return ((isBiger(foodRes,foodneeded))&&(isBiger(goldRes,goldneeded))&& isBiger(ironRes,ironneeded) && isBiger(woodRes,woodneeded));
    }

    public static boolean Worker(int foodRes,int goldRes,int ironRes,int woodRes) {
        int foodneeded = 1000;
        int goldneeded = 0;
        int ironneeded = 0;
        int woodneeded = 0;
        return true;
//        return ((isBiger(foodRes,foodneeded))&&(isBiger(goldRes,goldneeded))&& isBiger(ironRes,ironneeded) && isBiger(woodRes,woodneeded));
    }

    public static boolean Port(int foodRes, int goldRes, int ironRes,int woodRes) {
        int foodneeded = 0;
        int goldneeded = 0;
        int ironneeded = 0;
        int woodneeded = 0;
        return true;
//        return ((isBiger(foodRes,foodneeded))&&(isBiger(goldRes,goldneeded))&& isBiger(ironRes,ironneeded) && isBiger(woodRes,woodneeded));
    }

    public static boolean WoodQuarry(int foodRes, int goldRes, int ironRes,int woodRes) {
        int foodneeded = 0;
        int goldneeded = 0;
        int ironneeded = 0;
        int woodneeded = 0;
        return true;
//        return ((isBiger(foodRes,foodneeded))&&(isBiger(goldRes,goldneeded))&& isBiger(ironRes,ironneeded) && isBiger(woodRes,woodneeded));
    }

    public static boolean MineQuarry(int foodRes, int goldRes, int ironRes,int woodRes) {
        int foodneeded = 0;
        int goldneeded = 0;
        int ironneeded = 0;
        int woodneeded = 0;
        return true;
//        return ((isBiger(foodRes,foodneeded))&&(isBiger(goldRes,goldneeded))&& isBiger(ironRes,ironneeded) && isBiger(woodRes,woodneeded));
    }

    public static boolean Farm(int foodRes, int goldRes, int ironRes,int woodRes) {
        int foodneeded = 0;
        int goldneeded = 0;
        int ironneeded = 0;
        int woodneeded = 0;
        return true;
//        return ((isBiger(foodRes,foodneeded))&&(isBiger(goldRes,goldneeded))&& isBiger(ironRes,ironneeded) && isBiger(woodRes,woodneeded));
    }

    public static boolean ContainerShip(int foodRes, int goldRes, int ironRes,int woodRes) {
        int foodneeded = 0;
        int goldneeded = 500;
        int ironneeded = 1000;
        int woodneeded = 400;
//        return true;
        return ((isBiger(foodRes,foodneeded))&&(isBiger(goldRes,goldneeded))&& isBiger(ironRes,ironneeded) && isBiger(woodRes,woodneeded));
    }

    public static boolean Soldier(int foodRes, int goldRes, int ironRes,int woodRes) {
        int foodneeded = 2000;
        int goldneeded = 250;
        int ironneeded = 500;
        int woodneeded = 600;
        return true;
//        return ((isBiger(foodRes,foodneeded))&&(isBiger(goldRes,goldneeded))&& isBiger(ironRes,ironneeded) && isBiger(woodRes,woodneeded));
    }

    public static boolean FisherShip(int foodRes, int goldRes, int ironRes,int woodRes) {
        int foodneeded = 0;
        int goldneeded = 250;
        int ironneeded = 500;
        int woodneeded = 600;
        return true;
//        return ((isBiger(foodRes,foodneeded))&&(isBiger(goldRes,goldneeded))&& isBiger(ironRes,ironneeded) && isBiger(woodRes,woodneeded));
    }

    public static boolean isBiger(int num1 , int num2){
        if (num1>=num2) return true;
        else return false;
    }
}
