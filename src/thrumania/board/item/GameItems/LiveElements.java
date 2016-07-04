package thrumania.board.item.GameItems;

import thrumania.board.item.GameItems.LiveElementItems.Capacity;
import thrumania.board.item.GameItems.LiveElementItems.Health;
import thrumania.board.item.GameItems.LiveElementItems.Side;
import thrumania.board.item.MapItems.DeadElements;
import thrumania.utils.Coordinate;

/**
 * Created by sina on 5/22/16.
 */
public  abstract  class LiveElements extends DeadElements {
    protected boolean underConstructed = true;
    protected Capacity capicity;
    protected Side side;
    protected Health health;
    protected Coordinate realPosition;
    protected Coordinate startingPoint;
    protected boolean canMove = true;
    protected int ConstructorsWorking = 0;
    protected int maxOfConstructor = 1;

    public Coordinate getStartingPoint() {
        return startingPoint;
    }

    public Coordinate getCastlePosition() {
        return realPosition;
    }

    public void setStartingPoint(Coordinate startingPoint) {
        this.startingPoint = startingPoint;
    }

    public void setRealPosition(Coordinate castlePosition) {
        this.realPosition = castlePosition;
    }

    public abstract void constructed();

    protected void setWithOnePicture(String pictureName) {
        springPictureName = pictureName;
        summerPictureName = pictureName;
        autumnPictureName = pictureName;
        winterPictureName = pictureName;
    }

    public void destroy() {
        map.getCell(realPosition.getRow(), realPosition.getColumn()).setInsideElementsItems(null);
    }

    public Side getSide() {
        return side;
    }

    public boolean isUnderConstructed() {
        return underConstructed;
    }

    public void setUnderConstructed(boolean underConstructed) {
        this.underConstructed = underConstructed;
    }

    public int getMaxOfConstructor() {
        return maxOfConstructor;
    }

    public void setMaxOfConstructor(int maxOfConstructor) {
        this.maxOfConstructor = maxOfConstructor;
    }

    public int getConstructorsWorking() {
        return ConstructorsWorking;
    }

    public void setConstructorsWorking(int constructorsWorking) {
        ConstructorsWorking = constructorsWorking;
    }
}
