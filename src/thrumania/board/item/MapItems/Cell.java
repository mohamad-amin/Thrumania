package thrumania.board.item.MapItems;

import thrumania.board.item.InsideElementsItems;
import thrumania.utils.Constants;
import thrumania.utils.Coordinate;
import thrumania.utils.IntegerUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mohamadamin on 5/18/16.
 */

public abstract class Cell {

    private int islandId = -1;
    private String pictureName;
    private Coordinate position;
    private InsideElementsItems insideElementsItems;

    private boolean isLand;
    private boolean canDrawStoneOrGold = false ;

    protected boolean isCompeleteLand = false;

    public Cell(Coordinate position) {
        this.position = position;
    }

    public boolean isCompeleteLand() {
        return isCompeleteLand;
    }

    public void setCompeleteLand(boolean compeleteLand) {
        isCompeleteLand = compeleteLand;
    }

    public Coordinate getPosition() {
        return position;
    }

    public void setPosition(Coordinate position) {
        this.position = position;
    }

    public String getPictureName() {
        return pictureName;
    }

    public String getPictureNameWithoutExtension() {
        return pictureName.substring(0,pictureName.length()-4);
    }

    public void setPictureName(String pictureName) {
        this.pictureName = pictureName;
    }

    public InsideElementsItems getInsideElementsItems() {
        return insideElementsItems;
    }

    public void setInsideElementsItems(InsideElementsItems insideElementsItems) {
        this.insideElementsItems = insideElementsItems;
    }

    public void setIslandId(int id) {
        islandId = id;
    }

    public int getIslandId() {
        return islandId;
    }

    public boolean isLand() {
        return isLand;
    }

    public void setLand(boolean land) {
        isLand = land;
    }

    public byte getId() {
        return Constants.SEA_ID;
    }

}