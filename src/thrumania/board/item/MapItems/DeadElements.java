package thrumania.board.item.MapItems;

import thrumania.board.item.InsideElementsItems;

/**
 * Created by AMIR on 6/28/2016.
 */
public abstract class DeadElements extends InsideElementsItems {
    protected String springPictureName ;
    protected String summerPictureName ;
    protected String autumnPictureName ;
    protected String winterPictureName ;
    protected int eachElementCapacity;
    public int MAX_CAPACITY = 100 ;
    protected  int speedCollectingWood = 20;
    protected boolean haveBuildingAssignedTo = false;

    public void setMAX_CAPACITY(int MAX_CAPACITY) {
        this.MAX_CAPACITY -= MAX_CAPACITY;
    }

    public int getEachElementCapacity() {
        return eachElementCapacity;
    }

    public void setEachElementCapacity(int eachElementCapacity) {
        this.eachElementCapacity = eachElementCapacity;
    }

    public String getSpringPictureName() {
        return springPictureName;
    }

    public void setSpringPictureName(String springPictureName) {
        this.springPictureName = springPictureName;
    }

    public String getSummerPictureName() {
        return summerPictureName;
    }

    public void setSummerPictureName(String summerPictureName) {
        this.summerPictureName = summerPictureName;
    }

    public String getAutumnPictureName() {
        return autumnPictureName;
    }

    public void setAutumnPictureName(String autumnPictureName) {
        this.autumnPictureName = autumnPictureName;
    }

    public String getWinterPictureName() {
        return winterPictureName;
    }

    public void setWinterPictureName(String winterPictureName) {
        this.winterPictureName = winterPictureName;
    }

    public int getMAX_CAPACITY() {
        return MAX_CAPACITY;
    }


    public int getSpeedCollectingWood() {
        return speedCollectingWood;
    }

    public void setSpeedCollectingWood(int speedCollectingWood) {
        this.speedCollectingWood = speedCollectingWood;
    }

    public boolean getHaveBuildingAssignedTo() {
        return haveBuildingAssignedTo;
    }

    public void HaveBuildingAssignedTo(boolean haveBuildingAssignedTo) {
        this.haveBuildingAssignedTo = haveBuildingAssignedTo;
    }
}
