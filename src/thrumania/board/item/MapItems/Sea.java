package thrumania.board.item.MapItems;

import thrumania.board.item.MapItems.Cell;
import thrumania.utils.Constants;
import thrumania.utils.Coordinate;

/**
 * Created by mohamadamin on 5/18/16.
 */
public class Sea extends Cell {

    public Sea(Coordinate position) {
        super(position);
        code= 0;
    }

    public Sea(Coordinate position, byte id) {
        super(position);
        code = 1;
        switch (id) {
            case Constants.FISH_ID:
                setInsideMapElemetn(new SmallFish());
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

        if (getInsideMapElemetn() instanceof SmallFish) {
            return Constants.FISH_ID;
        } else return Constants.SEA_ID;
    }
}
