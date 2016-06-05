package thrumania.board.item.MapItems;

import thrumania.board.item.MapItems.Cell;
import thrumania.utils.Constants;
import thrumania.utils.Coordinate;

/**
 * Created by mohamadamin on 5/18/16.
 */

public class LowLand extends Cell {

    public LowLand(Coordinate position) {
        super(position);
        code=1;
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
        if (getInsideMapElemetn() instanceof Tree) {
            return Constants.TREE_ID;
        } else if (getInsideMapElemetn() instanceof Agliculture) {
            return Constants.AGRICULTURE_ID;
        } else return Constants.LOW_LAND_ID;
    }
}
