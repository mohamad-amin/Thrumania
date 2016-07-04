package thrumania.board.item.GameItems.buildings;

import thrumania.board.item.GameItems.LiveElementItems.Health;
import thrumania.board.item.GameItems.LiveElementItems.Side;
import thrumania.board.item.GameItems.LiveElements;
import thrumania.board.item.MapItems.Map;
import thrumania.gui.PlayBottomPanel;
import thrumania.utils.Constants;
import thrumania.utils.Coordinate;
import thrumania.utils.ImageUtils;
import thrumania.utils.IntegerUtils;

import java.awt.*;
import java.util.TimerTask;

/**
 * Created by AMIR on 7/1/2016.
 */
public class Port extends LiveElements {
    //TODO : @amirhosein :  requirments for building one
    //TODO : @amirhossein : building a ship
private Coordinate neighborsea;
    private  Coordinate portsCoordinate;
    public Port (Coordinate realPosition, Coordinate startingPoint, Coordinate neighbourSea, int sideNumber ,
                 PlayBottomPanel playBottomPanel,Map map) {
        this.map = map;
        map.getCell(realPosition.getRow(),realPosition.getColumn()).setCanSetBuilding(false);
        map.getCell(startingPoint.getRow(),startingPoint.getColumn()).setCanSetBuilding(false);
        this.playBottomPanel = playBottomPanel;
        this.side = new Side(sideNumber);
        this.playerNumber = sideNumber;
        this.startingPoint = startingPoint;
        this.realPosition = realPosition;
        this.neighborsea= neighbourSea;
        this.health = new Health(3000,3000);

        setWithOnePicture("construction.png");

    }


    public Coordinate getPortsCoordinate() {
        return portsCoordinate;
    }

    public void setPortsCoordinate(Coordinate portsCoordinate) {
        this.portsCoordinate = portsCoordinate;
    }

    public Coordinate getNeighborsea() {
        return neighborsea;
    }

    public void setNeighborsea(Coordinate neighborsea) {
        this.neighborsea = neighborsea;
    }

    @Override
    public void paintingOptions(Graphics g) {
        super.paint(g);

        Font myFont = new Font("Party Business", Font.BOLD, 20);
        g.setFont(myFont);
        g.setColor(Color.WHITE);
        g.drawString("health :" , 150 ,30);
        g.drawString((Integer.toString( health.getHealth())),300,30);
        g.drawString("Side :" , 150,60);
        g.drawString((Integer.toString(side.getNumberOfPlayer()+1)),300,60);

        int elementCounter = Constants.sizeOfInformationBar;
        if (!b1IsSelected)
            g.drawImage(ImageUtils.getImage("fishership.png"), elementCounter * spaceBetweenElements, sizeOfBottom.height / 4, elementsSize, elementsSize, null);
        else if (b1IsSelected) {
            g.drawImage(ImageUtils.getImage("fishership2.png"), elementCounter * spaceBetweenElements, sizeOfBottom.height / 4, elementsSize, elementsSize, null);
        }
        elementCounter+=2;
        if (!b2IsSelected)
            g.drawImage(ImageUtils.getImage("containership.png"), elementCounter * spaceBetweenElements, sizeOfBottom.height / 4, elementsSize, elementsSize, null);
        else if (b2IsSelected) {
            g.drawImage(ImageUtils.getImage("containership2.png"), elementCounter * spaceBetweenElements, sizeOfBottom.height / 4, elementsSize, elementsSize, null);
        }
    }

    @Override
    public void findingSelectedObject(int mouseXcord, int mouseYcord) {
        int elementCounter = Constants.sizeOfInformationBar;
        //addingShip
        if (IntegerUtils.isInSideTheRangeOfCordinates(elementCounter * spaceBetweenElements, sizeOfBottom.height / 4, elementCounter * spaceBetweenElements + elementsSize, sizeOfBottom.height / 4 + elementsSize, mouseXcord, mouseYcord)) {
            playBottomPanel.setBottomPanelSelected(Constants.BottomPanelSelected.addFisherShip);
            playBottomPanel.function();
            b1IsSelected = true;
            playBottomPanel.repaint();
            new java.util.Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    b1IsSelected = false;
                    playBottomPanel.repaint();
                }

            }, 110);
        }
        elementCounter+=2;
        if (IntegerUtils.isInSideTheRangeOfCordinates(elementCounter * spaceBetweenElements, sizeOfBottom.height / 4, elementCounter * spaceBetweenElements + elementsSize, sizeOfBottom.height / 4 + elementsSize, mouseXcord, mouseYcord)) {
            playBottomPanel.setBottomPanelSelected(Constants.BottomPanelSelected.addContainerShip);
            playBottomPanel.function();
            b2IsSelected = true;
            playBottomPanel.repaint();
            new java.util.Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    b2IsSelected = false;
                    playBottomPanel.repaint();
                }

            }, 110);
        }
    }

    @Override
    public void constructed() {
        underConstructed = false;
        setWithOnePicture("port.png");
    }
}
