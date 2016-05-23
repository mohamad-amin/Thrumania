package thrumania.board.item.MapItems;

/**
 * Created by sina on 5/18/16.
 */
public class Tree extends  MapElement{


    public Tree(boolean canPutOnLowLand ) {

        super.canPutOnLowLand = canPutOnLowLand;

        super.springPictureName = "tree1.png";
        super.summerPictureName = "tree3.png";
        super.autmnPictureName = "tree5.png";
        super.winterPictureName = "tree7.png";

    }
}
