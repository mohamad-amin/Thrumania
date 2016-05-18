package thrumania.board.item;

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
    private List<Coordinate> adjacentPositions;

    public Cell(Coordinate position) {
        this.position = position;
        this.adjacentPositions = fillAdjacentPositions();
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

    private List<Coordinate> fillAdjacentPositions() {
        List<Coordinate> cells = new ArrayList<>();
        for (int i=-1; i<2; i++) {
            for (int j=-1; j<2; j++) {
                int row = position.getRow()+i;
                int column = position.getRow()+i;
                if (IntegerUtils.isInRange(0, Constants.MATRIX_SIZE-1, row) && IntegerUtils.isInRange(
                        0, Constants.MATRIX_SIZE-1, column) && !(position.equals(new Coordinate(row, column)))) {
                    cells.add(new Coordinate(row, column));
                }
            }
        }
        return cells;
    }

    public List<Coordinate> getAdjacentPositions() {
        return this.adjacentPositions;
    }

}
