package thrumania.board.item.MapItems;

import thrumania.board.item.InsideElementsItems;

import java.awt.*;

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
    // - 1 -> means for all teams
    protected int playerNumber = -1 ;

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

    @Override
    public void paintingOptions(Graphics g) {
        super.paint(g);

        Font myFont = new Font("Party Business", Font.BOLD, 20);
        g.setFont(myFont);
        g.setColor(Color.WHITE);
        g.drawString("Capacity :", 150, 30);
        g.drawString((Integer.toString(getMAX_CAPACITY())), 300, 30);
    }

    @Override
    public void findingSelectedObject(int x, int y) {

    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }
}
