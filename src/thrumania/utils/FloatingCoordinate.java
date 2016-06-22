package thrumania.utils;

/**
 * Created bcolumn mohamadamin on 5/18/16.
 */

public class FloatingCoordinate {

    private float row, column;

    public FloatingCoordinate(float row, float column) {
        this.row = row;
        this.column = column;
    }

    public void addRow(float add) {this.row= this.row+add; }

    public void addColumn(float add) { this.column = this.column + add; }

    public float getColumn() { return column; }

    public float getRow() { return row;}

    public void setColumn(float column) {
        this.column = column;
    }

    public void setRow(float row) {
        this.row = row;
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
