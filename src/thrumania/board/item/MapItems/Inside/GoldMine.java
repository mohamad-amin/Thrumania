package thrumania.board.item.MapItems.Inside;
import thrumania.board.item.MapItems.DeadElements;

import java.awt.*;

/**
 * Created by sina on 5/22/16.
 */
public class GoldMine extends DeadElements {

    public GoldMine() {

        super.springPictureName = "gold.png";
        super.summerPictureName = "gold.png";
        super.autumnPictureName = "gold.png";
        super.winterPictureName = "gold.png";
        super.eachElementCapacity = 5;

    }

    @Override
    public void paintingOptions(Graphics g) {
        //capacity remaintig
        //side
        //health
    }
}
