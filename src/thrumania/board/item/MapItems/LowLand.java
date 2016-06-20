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
    }

    public LowLand(Coordinate position, byte id) {
        super(position);
        switch (id) {
            case Constants.TREE_ID:
                setInsideMapElemetn(new Tree());
                break;
            case Constants.AGRICULTURE_ID:
                setInsideMapElemetn(new Agliculture());
                break;
            default:
                break;
        }
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
        //Todo: @Amirhosein: // FIXME: 6/5/16 with highland
            return Constants.TREE_ID;
        } else if (getInsideMapElemetn() instanceof Agliculture) {
            return Constants.AGRICULTURE_ID;
        } else if (getInsideMapElemetn() instanceof StoneMine) {
            return Constants.STONE_ID;
        } else if (getInsideMapElemetn() instanceof GoldMine) {
            return Constants.GOLD_ID;
        } else return Constants.LOW_LAND_ID;
    }

}
