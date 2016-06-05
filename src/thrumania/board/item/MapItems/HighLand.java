package thrumania.board.item.MapItems;

import thrumania.board.item.MapItems.Cell;
import thrumania.utils.Constants;
import thrumania.utils.Coordinate;

/**
 * Created by mohamadamin on 5/18/16.
 */

public class HighLand extends Cell {

    public HighLand(Coordinate position) {
        super(position);
    }

    @Override
    public Coordinate getPosition() {
        return super.getPosition();
    }

    @Override
    public void setPosition(Coordinate position) {
        super.setPosition(position);
    }

    @Override
    public String getPictureName() {
        return super.getPictureName();
    }

    @Override
    public void setPictureName(String pictureName) {
        super.setPictureName(pictureName);
    }

    @Override
    public byte getId() {
        if (getInsideMapElemetn() instanceof StoneMine) {
            return Constants.STONE_ID;
        } else if (getInsideMapElemetn() instanceof GoldMine) {
            return Constants.GOLD_ID;
        } else return Constants.HIGH_LAND_ID;
    }

}
