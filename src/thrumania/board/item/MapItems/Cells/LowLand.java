package thrumania.board.item.MapItems.Cells;

import thrumania.board.item.GameItems.buildings.Barrack;
import thrumania.board.item.GameItems.buildings.Castle;
import thrumania.board.item.GameItems.buildings.Farm;
import thrumania.board.item.GameItems.buildings.WoodQuarry;
import thrumania.board.item.GameItems.people.Soldier;
import thrumania.board.item.GameItems.people.Worker;
import thrumania.board.item.MapItems.Inside.Agliculture;
import thrumania.board.item.MapItems.Inside.Tree;
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
                setInsideElementsItems(new Tree());
                break;
            case Constants.AGRICULTURE_ID:
                setInsideElementsItems(new Agliculture());
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
        if (getInsideElementsItems() instanceof Tree) {
            return Constants.TREE_ID;
        } else if (getInsideElementsItems() instanceof Agliculture) {
            return Constants.AGRICULTURE_ID;
        } else if (getInsideElementsItems() instanceof Barrack) {
            return Constants.BARRACK_ID;
        } else if (getInsideElementsItems() instanceof Castle) {
            return Constants.CASTLE_ID;
        } else if (getInsideElementsItems() instanceof Farm) {
            return Constants.FARM_ID;
        } else if (getInsideElementsItems() instanceof WoodQuarry) {
            return Constants.WOOD_QUARRY_ID;
        } else if (getInsideElementsItems() instanceof Soldier) {
            return Constants.SOLDIER_ID;
        } else if (getInsideElementsItems() instanceof Worker) {
            return Constants.WORKER_ID;
        } else return Constants.LOW_LAND_ID;
    }

}
