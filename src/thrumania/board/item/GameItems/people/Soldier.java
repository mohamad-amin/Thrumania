package thrumania.board.item.GameItems.people;

import thrumania.board.item.MapItems.Cells.Cell;
import thrumania.board.item.MapItems.Cells.HighLand;
import thrumania.board.item.MapItems.Cells.LowLand;
import thrumania.board.item.MapItems.Map;
import thrumania.game.MapProcessor;
import thrumania.gui.PlayPanel;
import thrumania.managers.HumanManagers;
import thrumania.utils.Constants;
import thrumania.utils.Coordinate;
import thrumania.utils.IntegerUtils;

import java.awt.*;

/**
 * Created by sina on 6/24/16.
 */
public class Soldier extends Human {
    private PlayPanel playPanel;
    private Map map;
    private Dimension d = new Dimension(Constants.CELL_SIZE, Constants.CELL_SIZE);
    private Human humanIsAttacking = null;
    private int distanceShouldKeepWhenAttacking  = Constants.CELL_SIZE / 7;


    public Soldier(PlayPanel playPanel, Map map, int x, int y , int playerNumber) {

        // moshakhasat ;
        super.health = 1000;
        super.damageUnit = 70;
        super.visibilityUnit = 25;
        super.foodReq = 2000;
        super.ironReq = 500;
        super.goldReq = 250;
        super.woodReq = 600;
        super.speedOfConsumingFood = 2;

        // aks :
        // TODO : changing picure of shape
        super.picutreName = "manStanding.png";
        // TODO:

        // mokhtasat
        super.xCord = x;
        super.yCord = y;
//        super.xEnd = xCord;
//        super.yEnd = yCord;
        super.coordinate = IntegerUtils.getCoordinateWithXAndY(xCord, yCord);
//        super.endCord = IntegerUtils.getCoordinateWithXAndY(xEnd, yEnd);
        // masir :



        // other classes :
        this.playPanel = playPanel;
        this.map = map;
        this.mapProcessor = new MapProcessor(map.getCells());
        // TODO : initializing its coordinate
        // booleans

        super.isAlive = true;
        super.isAttackMove = false;
        super.isKillingOpponent = false;
        super.isSelectedByPlayer = false;



        super.playerNumber = playerNumber;

        this.setSize(d);



    }



    @Override
    protected void determiningSpeedOfMoving() {
        if (playPanel.getSeason() == Constants.Seasons.SPRING)
            this.speedOfMoving = 8;
        else if (this.playPanel.getSeason() == Constants.Seasons.SUMMER)
            this.speedOfMoving = 7;
        else if (playPanel.getSeason() == Constants.Seasons.AUTMN)
            this.speedOfMoving = 5;
        else if (playPanel.getSeason() == Constants.Seasons.WINTER)
            this.speedOfMoving = 3;

    }

    @Override
    protected Human seeAnyFoes() {

        for (int i = 0; i < HumanManagers.getSharedInstance().getHumans().length; i++) {
            if (i != this.playerNumber)
                for (int j = 0; j < HumanManagers.getSharedInstance().getHumans()[i].size(); j++)
                    if (isThisHumanVisible(HumanManagers.getSharedInstance().getHumans()[i].get(j)))
                        return HumanManagers.getSharedInstance().getHumans()[i].get(j);
        }

        return null;

    }

    private boolean isThisHumanVisible(Human human) {

        if (IntegerUtils.getDistanceOfTWoIntegers(this.xCord,
                human.getxCord()) < this.visibilityUnit) {

            if (IntegerUtils.getDistanceOfTWoIntegers(this.yCord,
                    human.getyCord()) < this.visibilityUnit) {
                System.out.println("here 3");
                return true;

            }

        }

        return false;


    }

    @Override
    public void run() {

                    while (isAlive) {
                System.out.println("killing is \t"+ isKillingOpponent);
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


                if ( ! isKillingOpponent) {


                    if (humanIsAttacking == null)
                        humanIsAttacking = seeAnyFoes();
                    if (humanIsAttacking != null && this.isThisHumanVisible(humanIsAttacking)) {

                        pathOfCoordinates = mapProcessor.getPath(coordinate, humanIsAttacking.getCoordinate(), this);

                    } else {
                        System.out.println("akhey");

                        isKillingOpponent =false;
                        humanIsAttacking = null;
                    }


                    examiningPath2();
                }

            }



        }


    private boolean checkWheterTheGoalCellIsWaterOrNot(Coordinate crd) {
        if (map.getCell(crd.getRow(), crd.getColumn()) instanceof LowLand || map.getCell(crd.getRow(), crd.getColumn()) instanceof HighLand) {
            return false;
        } else {
            return true;
        }



    }



    protected void move( Coordinate end) {
        int xEnd, yEnd;


        this.xCord = coordinate.getColumn() * Constants.CELL_SIZE + Constants.CELL_SIZE / 10;
        this.yCord = coordinate.getRow() * Constants.CELL_SIZE;
        xEnd = end.getColumn() * Constants.CELL_SIZE + Constants.CELL_SIZE / 10;
        yEnd = end.getRow() * Constants.CELL_SIZE;

        while (this.xCord != xEnd || this.yCord != yEnd) {
            this.determiningSpeedOfMoving();


            if (this.xCord < xEnd)
                xCord++;
            else if (this.xCord > xEnd)
                xCord--;
            if (this.yCord < yEnd)
                yCord++;
            else if (this.yCord > yEnd)
                yCord--;
            this.setLocation(xCord, yCord);
            coordinate = IntegerUtils.getCoordinateWithXAndY(xCord, yCord);

            try {
                Thread.sleep((long) (1000 / (speedOfMoving * 5)));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }
    }
    public void examiningPath2() {

        // TODO : fix this sleep

        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        while (!pathOfCoordinates.isEmpty()) {
            System.out.println("path is \t" + pathOfCoordinates);

            if (pathOfCoordinates.peek().equals(coordinate))
                pathOfCoordinates.pop();
            if (! pathOfCoordinates.isEmpty() && this.checkWetherTheGoalCellIsAvailableForGoing(pathOfCoordinates.peek()))
                move(pathOfCoordinates.pop());
            else {
                while (!pathOfCoordinates.isEmpty())
                    pathOfCoordinates.pop();
            }
        }


    }

    private boolean checkWetherTheGoalCellIsAvailableForGoing(Coordinate crd) {

        if (map.getCell(crd.getRow(), crd.getColumn()) instanceof LowLand || map.getCell(crd.getRow(), crd.getColumn()) instanceof HighLand) {
            Cell cell = map.getCell(crd.getRow(), crd.getColumn());
            if (cell.getInsideElementsItems() == null) {
                return true;
            }

        }
        return false;

    }


}



