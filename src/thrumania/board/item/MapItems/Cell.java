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
    private MapElement insideMapElemetn;


    int code;

    public Cell(Coordinate position) {
        this.position = position;
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

    public void setPictureName(String pictureName) {
        this.pictureName = pictureName;
    }

    public int getCode() {
        return code;
    }

    public MapElement getInsideMapElemetn() {
        return insideMapElemetn;
    }

    public void setInsideMapElemetn(MapElement insideMapElemetn) {
        this.insideMapElemetn = insideMapElemetn;
    }

}