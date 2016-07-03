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

        setWithOnePicture("construction.png");
    }

    @Override
    public void paintingOptions(Graphics g) {

    }

    @Override
    public void constructed() {
        underConstructed = false;
        setWithOnePicture("barrak.png");
    }
}

    // TODO : @amirhosein notice that buldings cant be build at  starting points of castles

