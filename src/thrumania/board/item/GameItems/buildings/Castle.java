package thrumania.board.item.GameItems.buildings;

import thrumania.board.item.GameItems.LiveElementItems.Health;
import thrumania.board.item.GameItems.LiveElementItems.Side;
import thrumania.board.item.GameItems.LiveElements;
import thrumania.board.item.GameItems.people.Human;
import thrumania.board.item.MapItems.Map;
import thrumania.gui.PlayBottomPanel;
import thrumania.gui.PlayFrame;
import thrumania.gui.PlayPanel;
import thrumania.managers.HumanManagers;
import thrumania.managers.ShipsManager;
import thrumania.messages.RemovingFromPanel;
import thrumania.messages.RemovingShipsFromPanel;
import thrumania.utils.Constants;
import thrumania.utils.Coordinate;
import thrumania.utils.ImageUtils;
import thrumania.utils.IntegerUtils;

import java.awt.*;
import java.util.TimerTask;

/**
 * Created by sina on 6/24/16.
 */

public class Castle extends LiveElements {
// TODO  : IS NEAR THE OCEAN
    private boolean isNearWater = false;
    private  Coordinate waterStartingPoint;
    private PlayPanel playPanel;
    private PlayFrame playFrame;

    private int teamId;

    public Castle(Coordinate realPosition, Coordinate startingPoint , int sideNumber, PlayBottomPanel playBottomPanel ,PlayPanel playPanel,PlayFrame playFrame, Map map) {
        this.map=map;
        map.getCell(realPosition.getRow(),realPosition.getColumn()).setCanSetBuilding(false);
        map.getCell(startingPoint.getRow(),startingPoint.getColumn()).setCanSetBuilding(false);
        this.playFrame = playFrame;
        this.playPanel = playPanel;
        this.playBottomPanel= playBottomPanel;
        this.side = new Side(sideNumber);
        this.playerNumber = sideNumber;
        this.startingPoint = startingPoint;
        this.realPosition = realPosition;
        health = new Health(10000,10000);
        setWithOnePicture("castle.png");
    }



    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public boolean isNearWater() {
        return isNearWater;
    }

    public void setNearWater(boolean nearWater) {
        isNearWater = nearWater;
    }

    @Override
    public void constructed() {
        //nothing to say :|||||||
    }

    public Coordinate getWaterStartingPoint() {
        return waterStartingPoint;
    }

    public void setWaterStartingPoint(Coordinate waterStartingPoint) {
        this.waterStartingPoint = waterStartingPoint;
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
            g.drawImage(ImageUtils.getImage("farmer.png"), elementCounter * spaceBetweenElements, sizeOfBottom.height / 4, elementsSize, elementsSize, null);
        else if (b1IsSelected) {
            g.drawImage(ImageUtils.getImage("farmer2.png"), elementCounter * spaceBetweenElements, sizeOfBottom.height / 4, elementsSize, elementsSize, null);
        }
    }

    @Override
    public void findingSelectedObject(int mouseXcord, int mouseYcord) {
        int elementCounter = Constants.sizeOfInformationBar;

        //adding worker
        if (IntegerUtils.isInSideTheRangeOfCordinates(elementCounter * spaceBetweenElements, sizeOfBottom.height / 4, elementCounter * spaceBetweenElements + elementsSize, sizeOfBottom.height / 4 + elementsSize, mouseXcord, mouseYcord)) {
            playBottomPanel.setBottomPanelSelected(Constants.BottomPanelSelected.addWorker);
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

    @Override
    public void destroy() {
        //super.destroy not working because inside adresses are wrong
        super.destroy();
//        playFrame.getVirtals().kill(playerNumber);
        synchronized (HumanManagers.getSharedInstance().getHumans()) {
            while (! HumanManagers.getSharedInstance().getHumans()[playerNumber].isEmpty()) {
                HumanManagers.getSharedInstance().getHumans()[playerNumber].get(0).setAlive(false);
                if( HumanManagers.getSharedInstance().getHumans()[playerNumber].get(0).getHumanIsAttacking() != null) {
                    HumanManagers.getSharedInstance().getHumans()[playerNumber].get(0).getHumanIsAttacking().setHumanIsAttacking(null);
                    HumanManagers.getSharedInstance().getHumans()[playerNumber].get(0).getHumanIsAttacking().setStateOfMove(Human.statesOfMovement.STOP);
                    //playPanel.dispatchEvent( new SimpleMessages(playPanel , Messages.REPAINT));
                }
                playPanel.dispatchEvent(new RemovingFromPanel(playPanel , HumanManagers.getSharedInstance().getHumans()[playerNumber].get(0)));
                HumanManagers.getSharedInstance().getHumans()[playerNumber].remove(0);



            }
        }

        while ( ! ShipsManager.getShipInstance().getShips()[playerNumber].isEmpty()){
            ShipsManager.getShipInstance().getShips()[playerNumber].get(0).setAlive(false);
            playPanel.dispatchEvent(new RemovingShipsFromPanel(playPanel,ShipsManager.getShipInstance().getShips()[playerNumber].get(0)));
            ShipsManager.getShipInstance().getShips()[playerNumber].remove(0);
        }
        playPanel.setTempNumber_Of_people(playPanel.getTempNumber_Of_people() - 1);
        playPanel.repaint();
    }




}
