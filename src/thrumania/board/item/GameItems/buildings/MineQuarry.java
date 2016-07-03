package thrumania.board.item.GameItems.buildings;

import thrumania.board.item.GameItems.LiveElementItems.Health;
import thrumania.board.item.GameItems.LiveElementItems.Side;
import thrumania.board.item.GameItems.LiveElements;
import thrumania.utils.Coordinate;

import java.awt.*;

/**
 * Created by AMIR on 7/1/2016.
 */
public class MineQuarry extends LiveElements{
    //    TODO : @amirhosein :  requirments for building one
    public MineQuarry(Coordinate realPosition, Coordinate startingPoint , int sideNumber ) {
        side = new Side(sideNumber);
        this.startingPoint = startingPoint;
        this.realPosition = realPosition;
        this.health = new Health(2000,2000);
        setWithOnePicture("construction.png");
    }

    @Override
    public void paintingOptions(Graphics g) {
        //health
        //side
        //capacity
    }

    @Override
    public void constructed() {
        underConstructed = false;
        setWithOnePicture("minequarry.png");
    }
}
