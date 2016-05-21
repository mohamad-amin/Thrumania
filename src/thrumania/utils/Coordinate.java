package thrumania.utils;

/**
 * Created bcolumn mohamadamin on 5/18/16.
 */

public class Coordinate {

    private int row, column;
    private    int  canAddCounter = 1 ;

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

    public void addRow(int add) {
        if( canAddCounter % 4 == 0)
        this.row= this.row+add;
        canAddCounter ++;
    }

    public void addColumn(int add) {


         if ( canAddCounter % 4 ==0 )
            this.column = this.column + add;
            canAddCounter ++;


    }

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
