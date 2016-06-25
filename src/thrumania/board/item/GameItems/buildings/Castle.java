package thrumania.board.item.GameItems.buildings;

import thrumania.board.item.InsideElementsItems;
import thrumania.board.item.MapItems.MapElement;
import thrumania.utils.Coordinate;

/**
 * Created by sina on 6/24/16.
 */
public class Castle extends MapElement{
    private  String pictureName = "castle.png";
    private Coordinate coordinate;



    public Castle(Coordinate coordinate) {
        springPictureName = pictureName;
        summerPictureName = pictureName;
        autmnPictureName = pictureName ;
        winterPictureName = pictureName ;
    }

}
