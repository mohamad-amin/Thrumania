package thrumania.board.item.GameItems.buildings;

import thrumania.board.item.GameItems.LiveElements;
import thrumania.utils.Coordinate;

/**
 * Created by AMIR on 6/28/2016.
 */
public class WoodQuarry extends LiveElements {

    public WoodQuarry(Coordinate castlePosition, Coordinate startingPoint) {
        String pictureName = "castle.png";
        this.startingPoint = startingPoint;
        this.realPosition = castlePosition;
    }
}
