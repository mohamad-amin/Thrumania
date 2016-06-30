package thrumania.board.item.GameItems.buildings;

import thrumania.board.item.GameItems.LiveElementItems.Health;
import thrumania.board.item.GameItems.LiveElementItems.Side;
import thrumania.board.item.GameItems.LiveElements;
import thrumania.utils.Coordinate;

/**
 * Created by AMIR on 7/1/2016.
 */
public class Port extends LiveElements {
private Coordinate neighborsea;
    public Port (Coordinate realPosition, Coordinate startingPoint, Coordinate neighbourSea, int sideNumber ) {
        this.side = new Side(sideNumber);
        this.startingPoint = startingPoint;
        this.realPosition = realPosition;
        this.neighborsea= neighbourSea;
        this.health = new Health(3000,3000);

        String pictureName = "castle.png";
        springPictureName = pictureName;
        summerPictureName = pictureName;
        autumnPictureName = pictureName;
        winterPictureName = pictureName;

    }

}
