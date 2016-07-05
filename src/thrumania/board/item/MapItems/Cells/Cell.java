package thrumania.board.item.MapItems.Cells;

import thrumania.board.item.InsideElementsItems;
import thrumania.utils.Constants;
import thrumania.utils.Coordinate;
import thrumania.utils.IntegerUtils;

/**
 * Created by mohamadamin on 5/18/16.
 */

public abstract class Cell {
    public boolean getCanSetBuilding(){return canSetBuilding;}

    public void setCanSetBuilding(boolean canSetBuilding) {
        this.canSetBuilding = canSetBuilding;
    }

    protected boolean canSetBuilding = true;
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

    public Cell getNeighborLand(Cell[][] cells) {
        int i = getPosition().getRow(), j = getPosition().getColumn();
        if (IntegerUtils.isInRange(0, cells.length-1, i-1) && IntegerUtils.isInRange(0, cells[0].length-1, j-1) &&
                cells[i-1][j-1].getId() < Constants.HIGH_LAND_ID) return cells[i-1][j-1];
        if (IntegerUtils.isInRange(0, cells.length-1, i-1) && cells[i-1][j].getId() < Constants.HIGH_LAND_ID) return cells[i-1][j];
        if (IntegerUtils.isInRange(0, cells.length-1, i-1) && IntegerUtils.isInRange(0, cells[0].length-1, j+1) &&
                cells[i-1][j+1].getId() < Constants.HIGH_LAND_ID) return cells[i-1][j+1];
        if (IntegerUtils.isInRange(0, cells.length-1, i+1) && IntegerUtils.isInRange(0, cells[0].length-1, j-1) &&
                cells[i+1][j-1].getId() < Constants.HIGH_LAND_ID) return cells[i+1][j-1];
        if (IntegerUtils.isInRange(0, cells.length-1, i+1) && cells[i+1][j].getId() < Constants.HIGH_LAND_ID) return cells[i+1][j];
        if (IntegerUtils.isInRange(0, cells.length-1, i+1) && IntegerUtils.isInRange(0, cells[0].length-1, j+1) &&
                cells[i+1][j+1].getId() < Constants.HIGH_LAND_ID) return cells[i+1][j+1];
        if (IntegerUtils.isInRange(0, cells[0].length-1, j-1) && cells[i][j-1].getId() < Constants.HIGH_LAND_ID) return cells[i][j-1];
        if (IntegerUtils.isInRange(0, cells[0].length-1, j+1) && cells[i][j+1].getId() < Constants.HIGH_LAND_ID) return cells[i][j+1];
        return null;
    }

    public Cell getNeighbourSea(Cell[][] cells){
        int i = getPosition().getRow(), j = getPosition().getColumn();
        if (IntegerUtils.isInRange(0, cells.length-1, i-1) && IntegerUtils.isInRange(0, cells[0].length-1, j-1) &&
                cells[i-1][j-1].getId() > Constants.SEA_ID - 1) return cells[i-1][j-1];
        if (IntegerUtils.isInRange(0, cells.length-1, i-1) && cells[i-1][j].getId() > Constants.SEA_ID - 1) return cells[i-1][j];
        if (IntegerUtils.isInRange(0, cells.length-1, i-1) && IntegerUtils.isInRange(0, cells[0].length-1, j+1) &&
                cells[i-1][j+1].getId() > Constants.SEA_ID - 1) return cells[i-1][j+1];
        if (IntegerUtils.isInRange(0, cells.length-1, i+1) && IntegerUtils.isInRange(0, cells[0].length-1, j-1) &&
                cells[i+1][j-1].getId() > Constants.SEA_ID - 1) return cells[i+1][j-1];
        if (IntegerUtils.isInRange(0, cells.length-1, i+1) && cells[i+1][j].getId() > Constants.SEA_ID - 1) return cells[i+1][j];
        if (IntegerUtils.isInRange(0, cells.length-1, i+1) && IntegerUtils.isInRange(0, cells[0].length-1, j+1) &&
                cells[i+1][j+1].getId() > Constants.SEA_ID - 1) return cells[i+1][j+1];
        if (IntegerUtils.isInRange(0, cells[0].length-1, j-1) && cells[i][j-1].getId() > Constants.SEA_ID - 1) return cells[i][j-1];
        if (IntegerUtils.isInRange(0, cells[0].length-1, j+1) && cells[i][j+1].getId() > Constants.SEA_ID - 1) return cells[i][j+1];
        return null;
    }

}