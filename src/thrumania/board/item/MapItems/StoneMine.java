package thrumania.board.item.MapItems;

import thrumania.utils.Coordinate;

/**
 * Created by sina on 5/22/16.
 */
public class StoneMine extends  MapElement{
    private Coordinate stoneRight;
    private Coordinate stoneDown;
    private Coordinate stoneDownRightt;


    public StoneMine() {


        super.springPictureName = "Stone.png";
        super.summerPictureName = "Stone.png";
        super.autmnPictureName = "Stone.png";
        super.winterPictureName = "Stone.png";



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
