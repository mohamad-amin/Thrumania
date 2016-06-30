package thrumania.board.item.GameItems.buildings;

import thrumania.board.item.GameItems.LiveElementItems.Health;
import thrumania.board.item.GameItems.LiveElementItems.Side;
import thrumania.board.item.GameItems.LiveElements;
import thrumania.utils.Coordinate;

/**
 * Created by AMIR on 7/1/2016.
 */
public class Farm extends LiveElements {
    public Farm(Coordinate realPosition, Coordinate startingPoint , int sideNumber ) {

        this.side = new Side(sideNumber);
        this.startingPoint = startingPoint;
        this.realPosition = realPosition;
        this.health = new Health(2000,2000);

        String pictureName = "castle.png";
        springPictureName = pictureName;
        summerPictureName = pictureName;
        autumnPictureName = pictureName;
        winterPictureName = pictureName;

    }
}
