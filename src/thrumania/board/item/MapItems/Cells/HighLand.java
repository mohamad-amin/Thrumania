package thrumania.board.item.MapItems.Cells;

import thrumania.board.item.GameItems.buildings.MineQuarry;
import thrumania.board.item.MapItems.Inside.GoldMine;
import thrumania.board.item.MapItems.Inside.StoneMine;
import thrumania.utils.Constants;
import thrumania.utils.Coordinate;

/**
 * Created by mohamadamin on 5/18/16.
 */

public class HighLand extends Cell {

    public HighLand(Coordinate position) {
        super(position);
    }

    public HighLand(Coordinate position, byte id) {
        super(position);
        switch (id) {
            case Constants.STONE_ID:
                setInsideElementsItems(new StoneMine());
                break;
            case Constants.GOLD_ID:
                setInsideElementsItems(new GoldMine());
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
        if (getInsideElementsItems() instanceof StoneMine) {
            return Constants.STONE_ID;
        } else if (getInsideElementsItems() instanceof GoldMine) {
            return Constants.GOLD_ID;
        } else if (getInsideElementsItems() instanceof MineQuarry) {
            return Constants.MINE_QUARRY_ID;
        } else return Constants.HIGH_LAND_ID;
    }

}
