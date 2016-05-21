package thrumania.utils;

/**
 * Created bcolumn mohamadamin on 5/18/16.
 */

public class Coordinate {

    private int row, column;

    public Coordinate(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void addRow(int add) { this.row= this.row+add; }

    public void addColumn(int add) { this.column = this.column +add;}

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Coordinate) {
            Coordinate coordinate = (Coordinate) obj;
            return coordinate.getColumn() == column && coordinate.getRow() == row;
        } 
        return false;
    }
}
