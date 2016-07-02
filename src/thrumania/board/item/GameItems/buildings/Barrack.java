package thrumania.board.item.GameItems.buildings;

import thrumania.board.item.GameItems.LiveElementItems.Health;
import thrumania.board.item.GameItems.LiveElementItems.Side;
import thrumania.board.item.GameItems.LiveElements;
import thrumania.utils.Coordinate;

import java.awt.*;

/**
 * Created by AMIR on 7/1/2016.
 */
public class Barrack extends LiveElements {

    public Barrack(Coordinate realPosition, Coordinate startingPoint, int sideNumber) {
        side = new Side(sideNumber);
        this.startingPoint = startingPoint;
        this.realPosition = realPosition;
        this.health = new Health(5000, 5000);

        String pictureName = "castle.png";
        springPictureName = pictureName;
        summerPictureName = pictureName;
        autumnPictureName = pictureName;
        winterPictureName = pictureName;
    }

    @Override
    public void paintingOptions(Graphics g) {

    }
}

    // TODO : @amirhosein notice that buldings cant be build at  starting points of castles

