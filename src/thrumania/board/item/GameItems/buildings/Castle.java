package thrumania.board.item.GameItems.buildings;

import thrumania.board.item.MapItems.MapElement;
import thrumania.utils.Coordinate;

/**
 * Created by sina on 6/24/16.
 */
public class Castle extends MapElement{

    private  String pictureName = "castle.png";
    private Coordinate castlePosition;
    private Coordinate startingPoint;

    public Castle(Coordinate castlePosition, Coordinate startingPoint) {
        this.startingPoint = startingPoint;
        springPictureName = pictureName;
        summerPictureName = pictureName;
        winterPictureName = pictureName;
        autmnPictureName = pictureName;
        this.castlePosition = castlePosition;
    }

}
