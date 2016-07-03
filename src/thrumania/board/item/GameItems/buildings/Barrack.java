package thrumania.board.item.GameItems.buildings;

import thrumania.board.item.GameItems.LiveElementItems.Health;
import thrumania.board.item.GameItems.LiveElementItems.Side;
import thrumania.board.item.GameItems.LiveElements;
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
public class Barrack extends LiveElements {

    public Barrack(Coordinate realPosition, Coordinate startingPoint, int sideNumber, PlayBottomPanel playBottomPanel) {
        this.playBottomPanel = playBottomPanel;
        side = new Side(sideNumber);
        this.startingPoint = startingPoint;
        this.realPosition = realPosition;
        this.health = new Health(5000, 5000);

        setWithOnePicture("construction.png");
    }

    @Override
    public void constructed() {
        underConstructed = false;
        setWithOnePicture("barrak.png");
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
            g.drawImage(ImageUtils.getImage("OceanBottomPanel.png"), elementCounter * spaceBetweenElements, d.height / 4, elementsSize, elementsSize, null);
        else if (b1IsSelected) {
            g.drawImage(ImageUtils.getImage("OceanBottomPanelHoover.png"), elementCounter * spaceBetweenElements, d.height / 4, elementsSize, elementsSize, null);
        }
    }

    @Override
    public void findingSelectedObject(int mouseXcord, int mouseYcord) {
        int elementCounter = Constants.sizeOfInformationBar;

        //addingSoldier
        if (IntegerUtils.isInSideTheRangeOfCordinates(elementCounter * spaceBetweenElements, d.height / 4, elementCounter * spaceBetweenElements + elementsSize, d.height / 4 + elementsSize, mouseXcord, mouseYcord)) {
            playBottomPanel.setBottomPanelSelected(Constants.BottomPanelSelected.addSoldier);
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
    }
}

    // TODO : @amirhosein notice that buldings cant be build at  starting points of castles

