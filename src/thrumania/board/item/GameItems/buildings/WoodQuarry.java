package thrumania.board.item.GameItems.buildings;

import thrumania.board.item.GameItems.LiveElementItems.Health;
import thrumania.board.item.GameItems.LiveElementItems.Side;
import thrumania.board.item.GameItems.LiveElements;
import thrumania.gui.PlayBottomPanel;
import thrumania.utils.Coordinate;

import java.awt.*;

/**
 * Created by AMIR on 6/28/2016.
 */
public class WoodQuarry extends LiveElements {

    //    TODO : @amirhosein :  requirments for building one

    public WoodQuarry(Coordinate realPosition, Coordinate startingPoint , int sideNumber , PlayBottomPanel playBottomPanel) {
        this.playBottomPanel = playBottomPanel;
        this.side = new Side(sideNumber);
        this.startingPoint = startingPoint;
        this.realPosition = realPosition;
        this.health = new Health (2000,2000);
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
    public void findingSelectedObject(int mouseXcord, int mouseYcord) {}

    @Override
    public void constructed() {
        underConstructed = false;
        setWithOnePicture("woodquarry.png");
    }
}
