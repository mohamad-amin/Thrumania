package thrumania.board.item.MapItems;

import thrumania.utils.Constants;
import thrumania.utils.Coordinate;
import thrumania.utils.IntegerUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mohamadamin on 5/18/16.
 */

public abstract class Cell {

    private String pictureName;
    private Coordinate position;
    private MapElement insideMapElement;

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

    public MapElement getInsideMapElemetn() {
        return insideMapElement;
    }

    public void setInsideMapElemetn(MapElement insideMapElemetn) {
        this.insideMapElement = insideMapElemetn;
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