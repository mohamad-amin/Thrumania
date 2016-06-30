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
import java.util.Stack;

/**
 * Created by sina on 6/24/16.
 */
// TODO : Program's speed due to  two tings : having ThreadPool ,  and  curculating through all humans cuz of having one array list :
    // TODO :  we can hve hashmap  and for
public class Worker extends Human {
    private boolean canGoMountain;
    // TODO : worker's Order
    private int capacityOfCollectingItems;
    private boolean isCapacityOfCollectingItemsFull;
    private int speadOfCollectingItems;
    private PlayPanel playPanel;
    private Map map;
    private Dimension d = new Dimension(Constants.CELL_SIZE, Constants.CELL_SIZE);
    private Human humanIsAttacking = null;
    private int distanceShouldKeepWhenAttacking  = Constants.CELL_SIZE / 7;

    public Worker(PlayPanel playPanel, Map map, int xCord, int yCord, int playerNumber) {
        // moshakhasat :
        super.health = 500;
        super.damageUnit = 20;
        //TODO : 15
        super.visibilityUnit = 100;
        super.foodReq = 1000;
        super.woodReq = 0;
        super.goldReq = 0;
        super.ironReq = 0;
        super.speadOfConsumingFood = 1;
        super.playerNumber = playerNumber;
        this.capacityOfCollectingItems = 300;
        // TODO : one unit of each
//        this.speadOfCollectingItems =


        super.isSelectedByPlayer = false;
        // mokhtasat :
        super.xCord = xCord;
        super.yCord = yCord;

        super.coordinate = IntegerUtils.getCoordinateWithXAndY(xCord, yCord);

        // aks :
        super.picutreName = "manStanding.png";
        // masir

        // booleans :
        super.isAlive = true;
        super.canAttack = true;
        super.isInAttackState = false;
        this.isCapacityOfCollectingItemsFull = false;
        this.canGoMountain = false;
        this.isMoving = false;

        // other classes :
        this.map = map;
        this.mapProcessor = new MapProcessor(map.getCells());
        this.playPanel = playPanel;

        // JLabel things :
        this.setSize(d);


    }

    private void move(Coordinate end) {
        int xEnd, yEnd;

        this.xCord = coordinate.getColumn() * Constants.CELL_SIZE + Constants.CELL_SIZE / 10;
        this.yCord = coordinate.getRow() * Constants.CELL_SIZE;
        xEnd = end.getColumn() * Constants.CELL_SIZE + Constants.CELL_SIZE / 10;
        yEnd = end.getRow() * Constants.CELL_SIZE;
        if (!isInAttackState ) {
            while (this.xCord != xEnd || this.yCord != yEnd) {


                this.determiningSpeedOfMoving();


                if (this.xCord < xEnd)
                    xCord++;
                else if (this.xCord > xEnd)
                    xCord--;
                coordinate = IntegerUtils.getCoordinateWithXAndY(xCord, yCord);
                if (this.yCord < yEnd)
                    yCord++;
                else if (this.yCord > yEnd)
                    yCord--;
                coordinate = IntegerUtils.getCoordinateWithXAndY(xCord, yCord);


                try {
                    Thread.sleep((long) (1000 / (speedOfMoving * 5)));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }

            if (!canAttack) {
//                TODO : chec this as well
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("---------------------");
                this.canAttack = true;
            }
            System.out.println("**********************");
            System.out.println(isKillingOpponent);
            if( isKillingOpponent)
                isKillingOpponent = false;


        }
        else if (isInAttackState ) {
            this.determiningSpeedOfMoving();
            while (IntegerUtils.getDistanceOfTWoIntegers(xCord, xEnd) > distanceShouldKeepWhenAttacking || IntegerUtils.getDistanceOfTWoIntegers(yCord, yEnd) > distanceShouldKeepWhenAttacking) {
//
                if (this.xCord + distanceShouldKeepWhenAttacking < xEnd)
                    xCord++;
                else if (this.xCord > xEnd  + distanceShouldKeepWhenAttacking)
                    xCord--;
                coordinate = IntegerUtils.getCoordinateWithXAndY(xCord, yCord);
                if (this.yCord < yEnd)
                    yCord++;
                else if (this.yCord > yEnd)
                    yCord--;
                coordinate = IntegerUtils.getCoordinateWithXAndY(xCord, yCord);


                try {
                    Thread.sleep((long) (1000 / (speedOfMoving * 5)));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            isKillingOpponent = true;
            // TODO : kill the opponent
        }

    }


    @Override
    protected void determiningSpeedOfMoving() {

        if (playPanel.getSeason() == Constants.Seasons.SPRING)
            this.speedOfMoving = 8;
        else if (playPanel.getSeason() == Constants.Seasons.SUMMER)
            this.speedOfMoving = 7;
        else if (playPanel.getSeason() == Constants.Seasons.AUTMN)
            this.speedOfMoving = 5;
        else if (playPanel.getSeason() == Constants.Seasons.WINTER)
            this.speedOfMoving = 3;
        if (this.canGoMountain)
            speedOfMoving = speedOfMoving / 2;
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
                return true;

            }

        }

        return false;


    }


    public boolean isCanGoMountain() {
        return canGoMountain;
    }

    public void setCanGoMountain(boolean canGoMountain) {
        this.canGoMountain = canGoMountain;
    }

    public int getCapacityOfCollectingItems() {
        return capacityOfCollectingItems;
    }

    public void setCapacityOfCollectingItems(int capacityOfCollectingItems) {
        this.capacityOfCollectingItems = capacityOfCollectingItems;
    }

    private void checkWheterCapacityIsFull() {


    }

    @Override
    public void run() {
        while (isAlive) {
            //TODO  : remove this sleep
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(isKillingOpponent);
            System.out.println(canAttack);

                if (canAttack) {

                    if (humanIsAttacking == null)
                        humanIsAttacking = seeAnyFoes();
                    if (humanIsAttacking != null && this.isThisHumanVisible(humanIsAttacking) && !isKillingOpponent) {
                        pathOfCoordinates = mapProcessor.getPath(coordinate, humanIsAttacking.getCoordinate(), this);
                        super.isInAttackState = true;
                    } else {
                        isInAttackState = false;
                        isKillingOpponent =false;
                        humanIsAttacking = null;
                    }



                    if ( ! isKillingOpponent) {

                        examiningPath2();
                    }
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

    public void examiningPath(){
//        for ( int i =0 ; i<distinations.size() ; i++){
//            System.out.println("size is \t"+ distinations.size());
//            if( ! isMoving()) {
//                settingEachMove(mapProcessor.getPath(coordinate, distinations.get(i), this));
//                distinations.remove(i);
//                isMoving = false;
//            }
//        }

    }


    private void settingEachMove(Stack<Coordinate> path) {
        if (!path.isEmpty())
            if (path.peek().equals(coordinate))
                path.pop();
        while (!path.isEmpty()) {
            if (this.checkWetherTheGoalCellIsAvailableForGoing(path.peek()))
                move(path.pop());
            else {

                while (!path.isEmpty())
                    path.pop();

            }
        }


    }

    // TODO : make it better for when inside element is not null
    private boolean checkWetherTheGoalCellIsAvailableForGoing(Coordinate crd) {

        if (map.getCell(crd.getRow(), crd.getColumn()) instanceof LowLand || map.getCell(crd.getRow(), crd.getColumn()) instanceof HighLand) {
            Cell cell = map.getCell(crd.getRow(), crd.getColumn());
            if (cell.getInsideElementsItems() == null) {
                return true;
            }

        }
        return false;

    }
//    private boolean checkWheterTheGoalCellIsWaterOrNot(Coordinate crd) {
//        if (map.getCell(crd.getRow(), crd.getColumn()) instanceof LowLand || map.getCell(crd.getRow(), crd.getColumn()) instanceof HighLand) {
//            return false;
//        } else {
//            return true;
//        }


//    }

}
