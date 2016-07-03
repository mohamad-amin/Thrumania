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
import java.util.TimerTask;

/**
 * Created by sina on 6/24/16.
 */
public class Soldier extends Human {
    private PlayPanel playPanel;
    private Map map;
    private Dimension d = new Dimension(Constants.CELL_SIZE, Constants.CELL_SIZE);
    private Human humanIsAttacking = null;
    private int distanceShouldKeepWhenAttacking = Constants.CELL_SIZE / 7;


    public Soldier(PlayPanel playPanel, Map map, int x, int y, int playerNumber) {

        // moshakhasat ;
        super.health = 1000;
        // TODO: DAMAGE IS IN RATE OF .5
        super.damageUnit = 70;
        super.visibilityUnit = 250;
        super.foodReq = 2000;
        super.ironReq = 500;
        super.goldReq = 250;
        super.woodReq = 600;
        super.speedOfConsumingFood = 2;
        super.stateOfMove = statesOfMovement.STOP;

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
                return true;

            }

        }

        return false;


    }

    @Override
    public void run() {

        while (isAlive) {
            examiningPath();
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
            if (!pathOfCoordinates.isEmpty() && this.checkWetherTheGoalCellIsAvailableForGoing(pathOfCoordinates.peek()))
                regularMove(pathOfCoordinates.pop());
            else {
                while (!pathOfCoordinates.isEmpty())
                    pathOfCoordinates.pop();
            }
        }


    }

    private void examiningPath() {
//        System.out.println("state is \t"+ stateOfMove);


        switch (stateOfMove) {

            case STOP: {

                canLookForOpponent = true;
                if (!pathOfCoordinates.isEmpty()) {

                    stateOfMove = statesOfMovement.MOVING_BY_ORDERED;

                } else if (pathOfCoordinates.isEmpty()) {
                    if (humanIsAttacking == null)
                        if (canLookForOpponent)
                            humanIsAttacking = seeAnyFoes();
                        else humanIsAttacking = null;
                    if (humanIsAttacking != null && this.isThisHumanVisible(humanIsAttacking)) {
                        stateOfMove = statesOfMovement.ATTACKING;

                    }

                }



                break;
            }
            case MOVING_BY_ORDERED: {
                if (humanIsAttacking == null)
                    if (canLookForOpponent) {
                        humanIsAttacking = seeAnyFoes();
                    } else humanIsAttacking = null;
                if (humanIsAttacking != null && this.isThisHumanVisible(humanIsAttacking)) {
                    while (! pathOfCoordinates.isEmpty() )
                        pathOfCoordinates.pop();
                    canLookForOpponent = false;
                    stateOfMove = statesOfMovement.ATTACKING;
                } else if (!pathOfCoordinates.isEmpty() && pathOfCoordinates.peek().equals(coordinate))
                    pathOfCoordinates.pop();

                if (!pathOfCoordinates.isEmpty()) {
                    System.out.println("Hello 1");

                    if (!this.checkWheterTheGoalCellIsWaterOrNot(pathOfCoordinates.peek())) {
                        System.out.println("Hello 2");
                        regularMove(pathOfCoordinates.pop());
                    } else {
                        while (!pathOfCoordinates.isEmpty())
                            pathOfCoordinates.pop();
                        stateOfMove = statesOfMovement.STOP;
                    }


                } else if (pathOfCoordinates.isEmpty()) {
                    stateOfMove = statesOfMovement.STOP;
                }


                break;
            }

            case ATTACKING: {
                {

                    canLookForOpponent = false;
                    if (!pathOfCoordinates.isEmpty()) {
                        humanIsAttacking = null;
                        stateOfMove = statesOfMovement.MOVING_BY_ORDERED;
                    } else {
                        if (humanIsAttacking != null && isThisHumanVisible(humanIsAttacking))
                            attackMove(humanIsAttacking);
                        else stateOfMove = statesOfMovement.STOP;
                        if (IntegerUtils.getDistanceOfTWoIntegers(xCord, humanIsAttacking.getxCord()) <= distanceShouldKeepWhenAttacking && IntegerUtils.getDistanceOfTWoIntegers(yCord, humanIsAttacking.getyCord()) <= distanceShouldKeepWhenAttacking) {
                            while (!pathOfCoordinates.isEmpty())
                                pathOfCoordinates.pop();
                            canLookForOpponent = false;
                            this.stateOfMove = statesOfMovement.KILLING;
                        }
                    }


                    break;
                }
            }

            case KILLING: {

                canLookForOpponent = false;
                if (!pathOfCoordinates.isEmpty()) {

                    humanIsAttacking = null;
                    stateOfMove = statesOfMovement.MOVING_BY_ORDERED;


                } else if (humanIsAttacking != null && IntegerUtils.getDistanceOfTWoIntegers(xCord, humanIsAttacking.getxCord()) <= distanceShouldKeepWhenAttacking &&
                        IntegerUtils.getDistanceOfTWoIntegers(yCord, humanIsAttacking.getyCord())
                                <= distanceShouldKeepWhenAttacking) {

                    if (!hasAttacked){
                        hasAttacked = true;

                        new java.util.Timer().schedule(new TimerTask() {


                            @Override

                            public void run() {
                                hasAttacked = false;
                                if (humanIsAttacking != null) {
                                    if (humanIsAttacking.getHealth() > 0) {
                                        System.out.println("health is  +\t " + getHealth() + "team number is"  + getPlayerNumber());
                                        humanIsAttacking.setHealth(damageUnit);
                                    } else {
                                        System.out.println("fuck fuck fuck");
                                        humanIsAttacking.setAlive(false);
                                        playPanel.remove(humanIsAttacking);
                                        HumanManagers.getSharedInstance().getHumans()[humanIsAttacking.getPlayerNumber()].remove(humanIsAttacking);
                                        stateOfMove = statesOfMovement.STOP;


                                    }
                                }

                            }
                        },1000);
                    }
                    // TODO : killing

                } else if (humanIsAttacking != null && this.isThisHumanVisible(humanIsAttacking)) {

                        while (! pathOfCoordinates.isEmpty())
                            pathOfCoordinates.pop();
                    stateOfMove = statesOfMovement.ATTACKING;

                } else if (!this.isThisHumanVisible(humanIsAttacking)) {
//                    isAttackMove = false;
                    humanIsAttacking = null;
                    if (!pathOfCoordinates.isEmpty())
                        stateOfMove = statesOfMovement.MOVING_BY_ORDERED;
                    else stateOfMove = statesOfMovement.STOP;


                } else {

                    // TODO : handle : dead and kill
                    new java.util.Timer().schedule(new TimerTask() {

                        @Override
                        public void run() {
                            Human human = seeAnyFoes();
                            if (human != null)
                                if (human.getHealth() > 0)
                                    human.setHealth(human.getHealth() - damageUnit);
//                            else  TODO : set this guy  dead ; we can also handle this part by means of "message passing"

                        }

                    }, 1000);
                    if (health == 0) {
                        int a;
                        // TODO: is dead
                        // message passing
                    }
                    try {
                        Thread.sleep(3);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
//


                }


                break;
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


    private boolean checkWetherTheGoalCellIsAvailableForGoing(Coordinate crd) {

        if (map.getCell(crd.getRow(), crd.getColumn()) instanceof LowLand || map.getCell(crd.getRow(), crd.getColumn()) instanceof HighLand) {
            Cell cell = map.getCell(crd.getRow(), crd.getColumn());
            if (cell.getInsideElementsItems() == null) {
                return true;
            }

        }
        return false;

    }


    @Override
    public void paintingOptions(Graphics g) {
        //health
        //side
    }
}



