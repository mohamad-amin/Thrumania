package thrumania.board.item.MapItems.Inside;

import thrumania.board.item.MapItems.DeadElements;

import java.awt.*;

/**
 * Created by sina on 5/18/16.
 */
public class Tree extends DeadElements {

// TODO behind the tree


    public Tree() {
        super.springPictureName = "tree2.png";
        super.summerPictureName = "tree4.png";
        super.autumnPictureName = "tree5.png";
        super.winterPictureName = "tree7.png";
        super.eachElementCapacity = 3;

    }


    @Override
    public void paintingOptions(Graphics g) {

    }
}
