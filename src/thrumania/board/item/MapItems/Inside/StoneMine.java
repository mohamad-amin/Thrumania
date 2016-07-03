package thrumania.board.item.MapItems.Inside;

import thrumania.board.item.MapItems.DeadElements;
import thrumania.utils.Coordinate;

/**
 * Created by sina on 5/22/16.
 */
public class StoneMine extends DeadElements {
    private Coordinate stoneRight;
    private Coordinate stoneDown;
    private Coordinate stoneDownRightt;


    public StoneMine() {


        super.springPictureName = "Stone.png";
        super.summerPictureName = "Stone.png";
        super.autumnPictureName = "Stone.png";
        super.winterPictureName = "Stone.png";
        super.eachElementCapacity = 4;


    }

    public Coordinate getStoneDownRightt() {
        return stoneDownRightt;
    }

    public void setStoneDownRightt(Coordinate stoneDownRightt) {
        this.stoneDownRightt = stoneDownRightt;
    }

    public Coordinate getStoneDown() {

        return stoneDown;
    }

    public void setStoneDown(Coordinate stoneDown) {
        this.stoneDown = stoneDown;
    }

    public Coordinate getStoneRight() {

        return stoneRight;
    }

    public void setStoneRight(Coordinate stoneRight) {
        this.stoneRight = stoneRight;
    }


}
