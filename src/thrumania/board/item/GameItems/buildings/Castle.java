package thrumania.board.item.GameItems.buildings;

import thrumania.board.item.InsideElementsItems;
import thrumania.board.item.MapItems.MapElement;

/**
 * Created by sina on 6/24/16.
 */
public class Castle extends MapElement{
    private  String pictureName = "castle.png";

    public Castle() {
        springPictureName = pictureName;
        summerPictureName = pictureName;
        autmnPictureName = pictureName ;
        winterPictureName = pictureName ;
    }
}
