package thrumania.board.item.GameItems.buildings;

import thrumania.board.item.GameItems.LiveElementItems.Health;
import thrumania.board.item.GameItems.LiveElementItems.Side;
import thrumania.board.item.GameItems.LiveElements;
import thrumania.gui.PlayBottomPanel;
import thrumania.utils.Coordinate;

import java.awt.*;

/**
 * Created by AMIR on 7/1/2016.
 */
public class MineQuarry extends LiveElements{
    //    TODO : @amirhosein :  requirments for building one
    public MineQuarry(Coordinate realPosition, Coordinate startingPoint , int sideNumber , PlayBottomPanel playBottomPanel) {
        this.side = new Side(sideNumber);
        this.startingPoint = startingPoint;
        this.realPosition = realPosition;
        this.health = new Health(2000,2000);
        setWithOnePicture("construction.png");
    }

    @Override
    public void paintingOptions(Graphics g) {
        super.paint(g);

        Font myFont = new Font("Party Business", Font.BOLD, 20);
        g.setFont(myFont);
        g.setColor(Color.WHITE);
        g.drawString("health :", 150, 30);
        g.drawString((Integer.toString(health.getHealth())), 300, 30);
        g.drawString("Side :", 150, 60);
        g.drawString((Integer.toString(side.getNumberOfPlayer() + 1)), 300, 60);

    }

    @Override
    public void findingSelectedObject(int x, int y) {

    }

    @Override
    public void constructed() {
        underConstructed = false;
        setWithOnePicture("minequarry.png");
    }
}
