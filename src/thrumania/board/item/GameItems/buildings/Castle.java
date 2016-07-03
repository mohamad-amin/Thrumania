package thrumania.board.item.GameItems.buildings;

import thrumania.board.item.GameItems.LiveElementItems.Side;
import thrumania.board.item.GameItems.LiveElements;
import thrumania.utils.Constants;
import thrumania.utils.Coordinate;
import thrumania.utils.ImageUtils;

import java.awt.*;

/**
 * Created by sina on 6/24/16.
 */
public class Castle extends LiveElements {
// TODO  : IS NEAR THE OCEAN

    public Castle(Coordinate realPosition, Coordinate startingPoint ,int sideNumber ) {
        System.out.println(realPosition.getRow());
        System.out.println(realPosition.getColumn());

        this.side = new Side(sideNumber);
        this.startingPoint = startingPoint;
        this.realPosition = realPosition;

        String pictureName = "castle.png";
        springPictureName = pictureName;
        summerPictureName = pictureName;
        autumnPictureName = pictureName;
        winterPictureName = pictureName;

    }

    @Override
    public void paintingOptions(Graphics g) {
        g.drawImage(ImageUtils.getImage("castle.jpg"),0,0,Constants.CELL_SIZE,Constants.CELL_SIZE,null);
    }
}
