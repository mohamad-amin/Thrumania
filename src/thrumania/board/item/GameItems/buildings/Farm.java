package thrumania.board.item.GameItems.buildings;

import thrumania.board.item.GameItems.LiveElementItems.Health;
import thrumania.board.item.GameItems.LiveElementItems.Side;
import thrumania.board.item.GameItems.LiveElements;
import thrumania.board.item.MapItems.Map;
import thrumania.gui.PlayBottomPanel;
import thrumania.utils.Coordinate;

import java.awt.*;

/**
 * Created by AMIR on 7/3/2016.
 */
public class Farm extends LiveElements {
    private  Coordinate portsCoordinate;
    public Farm (Coordinate realPosition, Coordinate startingPoint, int sideNumber , PlayBottomPanel playBottomPanel, Map map) {
        this.map = map;
        map.getCell(realPosition.getRow(),realPosition.getColumn()).setCanSetBuilding(false);
        map.getCell(startingPoint.getRow(),startingPoint.getColumn()).setCanSetBuilding(false);
        this.playBottomPanel = playBottomPanel;
        this.side = new Side(sideNumber);
        this.playerNumber = sideNumber;
        this.startingPoint = startingPoint;
        this.realPosition = realPosition;
        this.health = new Health(3000,3000);

        setWithOnePicture("construction.png");

    }


    public Coordinate getPortsCoordinate() {
        return portsCoordinate;
    }

    public void setPortsCoordinate(Coordinate portsCoordinate) {
        this.portsCoordinate = portsCoordinate;
    }

    @Override
    public void constructed() {

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
}
