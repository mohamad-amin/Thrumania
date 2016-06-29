package thrumania.board.item.GameItems;

import thrumania.board.item.GameItems.LiveElementItems.Capacity;
import thrumania.board.item.GameItems.LiveElementItems.Health;
import thrumania.board.item.GameItems.LiveElementItems.Side;
import thrumania.board.item.MapItems.DeadElements;
import thrumania.utils.Coordinate;

/**
 * Created by sina on 5/22/16.
 */
public  abstract  class LiveElements extends DeadElements{
    protected Capacity capicity;
    protected Side side;
    protected Health health;
    protected Coordinate realPosition;
    protected Coordinate startingPoint;

    public Coordinate getStartingPoint() {return startingPoint;}
    public Coordinate getCastlePosition() {return realPosition;}
    public void setStartingPoint(Coordinate startingPoint) {this.startingPoint = startingPoint;}
    public void setRealPosition(Coordinate castlePosition) {this.realPosition = castlePosition;}

    public Side getSide() {
        return side;
    }
}
