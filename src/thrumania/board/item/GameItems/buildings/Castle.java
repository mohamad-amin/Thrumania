package thrumania.board.item.GameItems.buildings;

import thrumania.board.item.GameItems.LiveElementItems.Side;
import thrumania.board.item.GameItems.LiveElements;
import thrumania.utils.Coordinate;

/**
 * Created by sina on 6/24/16.
 */
public class Castle extends LiveElements {


    public Castle(Coordinate realPosition, Coordinate startingPoint ,int sideNumber ) {

        this.side = new Side(sideNumber);
        this.startingPoint = startingPoint;
        this.realPosition = realPosition;

        String pictureName = "castle.png";
        springPictureName = pictureName;
        summerPictureName = pictureName;
        autumnPictureName = pictureName;
        winterPictureName = pictureName;

    }

}
