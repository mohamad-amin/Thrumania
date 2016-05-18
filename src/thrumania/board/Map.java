package thrumania.board;

import thrumania.board.item.Cell;
import thrumania.board.item.Sea;
import thrumania.utils.Coordinate;

/**
 * Created by mohamadamin on 5/18/16.
 */

// Row, Column

public class Map {

    private Cell[][] cells;
    private int width, height;

    public Map(int width, int height) {
        this.width = width;
        this.height = height;
        this.cells = new Cell[width][height];
        fillCells();
    }

    public Cell[][] getCells() {
        return cells;
    }

    public void setCells(Cell[][] cells) {
        this.cells = cells;
    }

    public void fillCells() {
        for (int i=0; i<width; i++) {
            for (int j=0; j<height; j++) {
                Cell cell = new Sea(new Coordinate(i, j));
                cell.setPictureName("ocean1.jpg");
                cells[i][j] = cell;
            }
        }
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
