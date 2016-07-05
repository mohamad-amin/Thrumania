package thrumania.board.item.GameItems.people;

import thrumania.board.item.GameItems.LiveElements;
import thrumania.board.item.InsideElementsItems;
import thrumania.board.item.MapItems.Cells.Cell;
import thrumania.board.item.MapItems.Cells.HighLand;
import thrumania.board.item.MapItems.Cells.LowLand;
import thrumania.board.item.MapItems.Map;
import thrumania.game.MapProcessor;
import thrumania.gui.PlayBottomPanel;
import thrumania.gui.PlayPanel;
import thrumania.managers.HumanManagers;
import thrumania.messages.Messages;
import thrumania.messages.RemovingFromPanel;
import thrumania.messages.SimpleMessages;
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
    private InsideElementsItems onTheWayBuilding ;

    public Soldier(PlayPanel playPanel, Map map, int x, int y, int playerNumber, PlayBottomPanel playBottomPanel) {
        this.playBottomPanel = playBottomPanel;
        // moshakhasat ;
        super.health = 1000;
        // TODO: DAMAGE IS IN RATE OF .5
        super.damageUnit = 70;
        super.visibilityUnit = 150;
        super.foodReq = 2000;
        super.ironReq = 500;
        super.goldReq = 250;
        super.woodReq = 600;
        super.speedOfConsumingFood = 2;
        super.stateOfMove = statesOfMovement.STOP;

        // aks :
        // TODO : changing picure of shape
        super.picutreName ="S" + playerNumber %4 + "00.png";

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


    @Override
    public void run() {

        while (isAlive) {
            examiningPath();
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

                // TODO : buildings
//                if (pathOfCoordinates.isEmpty())
                if (humanIsAttacking == null)
                    if (canLookForOpponent) {
                        humanIsAttacking = seeAnyFoes();
                    } else humanIsAttacking = null;
                if (humanIsAttacking != null && this.isThisHumanVisible(humanIsAttacking)) {
                    while (!pathOfCoordinates.isEmpty())
                        pathOfCoordinates.pop();
                    canLookForOpponent = false;
                    stateOfMove = statesOfMovement.ATTACKING;
                }else if( !pathOfCoordinates.isEmpty()) {

                    if ( pathOfCoordinates.peek().equals(coordinate))
                        pathOfCoordinates.pop();


                    if (!this.checkWheterTheGoalCellIsWaterOrNot(pathOfCoordinates.peek())) {

                        regularMove(pathOfCoordinates.pop());
                    }else if ( pathOfCoordinates.size() == 1 &&  checkWetherTheGoalCellIsBullidng(pathOfCoordinates.peek())){

                        if( onTheWayBuilding != null &&  ((LiveElements)onTheWayBuilding).getSide().getNumberOfPlayer() == this.playerNumber){

                            this.stateOfMove  = statesOfMovement.DESTRUCTION_BUILDINGS;
                        } else {

                            while (! pathOfCoordinates.isEmpty())
                                pathOfCoordinates.pop();
                            stateOfMove = statesOfMovement.STOP;
                        }



                    } else{
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

                canLookForOpponent = false;
                if (!pathOfCoordinates.isEmpty()) {
                    humanIsAttacking = null;
                    stateOfMove = statesOfMovement.MOVING_BY_ORDERED;
                }else if (humanIsAttacking != null) {

                    if (IntegerUtils.getDistanceOfTWoIntegers(xCord, humanIsAttacking.getxCord()) <= distanceShouldKeepWhenAttacking && IntegerUtils.getDistanceOfTWoIntegers(yCord, humanIsAttacking.getyCord()) <= distanceShouldKeepWhenAttacking) {
                        while (!pathOfCoordinates.isEmpty())
                            pathOfCoordinates.pop();
                        this.stateOfMove = statesOfMovement.KILLING;

                    } else {
                        if (isThisHumanVisible(humanIsAttacking))
                            attackMove(humanIsAttacking);
                        else stateOfMove = statesOfMovement.STOP;
                    }
                } else stateOfMove = statesOfMovement.STOP ;


                break;
            }

            case KILLING: {
                canLookForOpponent = false;
                if (!pathOfCoordinates.isEmpty()) {
                    humanIsAttacking = null;
                    stateOfMove = statesOfMovement.MOVING_BY_ORDERED;


                } else if (humanIsAttacking != null && IntegerUtils.getDistanceOfTWoIntegers(xCord, humanIsAttacking.getxCord()) <= distanceShouldKeepWhenAttacking &&
                        IntegerUtils.getDistanceOfTWoIntegers(yCord, humanIsAttacking.getyCord())
                                <= distanceShouldKeepWhenAttacking) {

                    if (!hasAttacked) {
                        hasAttacked = true;

                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        hasAttacked = false;
                        if (humanIsAttacking != null) {
                            if (this.health > 0) {
                                System.out.print(getHealth());
                                this.setHealth(humanIsAttacking.getDamageUnit());
                                stateOfMove = statesOfMovement.ATTACKING;
                            }
                            if (health <= 0) {

                                synchronized (HumanManagers.getSharedInstance().getHumans()) {
                                    playPanel.dispatchEvent(new RemovingFromPanel(playPanel, this));

                                    // humanIsAttacking.setStateOfMove(statesOfMovement.STOP);
                                    HumanManagers.getSharedInstance().getHumans()[playerNumber].remove(this);
//                                    humanIsAttacking.setHumanIsAttacking(null);
                                    this.setAlive(false);
//                                    System.out.println(HumanManagers.getSharedInstance().getHumans()[playerNumber]);
                                    break;
                                }

                            }


//                            if( humanIsAttacking.getHealth() > 0){
//                                humanIsAttacking.setHealth(damageUnit);
//                                stateOfMove = statesOfMovement.ATTACKING;
//                            }else {
//
//
//                                if( humanIsAttacking != null) {
//                                    System.out.println("fuck fuck fuck");
//                                    humanIsAttacking.setAlive(false);
//                                    playPanel.remove(humanIsAttacking);
//                                    HumanManagers.getSharedInstance().getHumans()[humanIsAttacking.getPlayerNumber()].remove(humanIsAttacking);
//                                    HumanManagers.getSharedInstance().getThreadPoolExecutor().remove(humanIsAttacking);
//                                    humanIsAttacking = null;
//
//                                    playPanel.removeNotify();
//                                    playPanel.revalidate();
//                                    playPanel.repaint();
//                                    stateOfMove = statesOfMovement.STOP;
//
//                                }else stateOfMove = statesOfMovement.STOP;


                        } else stateOfMove = statesOfMovement.STOP;
                    }


//                    new java.util.Timer().schedule(new TimerTask() {


//                        @Override
//
//                        public void run() {
//                            hasAttacked = false;
//                            if (humanIsAttacking != null) {
//                                if (humanIsAttacking.getHealth() > 0) {
//                                    System.out.println("health is  +\t " + getHealth() + "team number is"  + getPlayerNumber());
//                                    humanIsAttacking.setHealth(damageUnit);
//                                } else {
//                                    System.out.println("fuck fuck fuck");
//                                    humanIsAttacking.setAlive(false);
//                                    playPanel.remove(humanIsAttacking);
//                                    playPanel.revalidate();
//                                    HumanManagers.getSharedInstance().getHumans()[humanIsAttacking.getPlayerNumber()].remove(humanIsAttacking);
//                                    stateOfMove = statesOfMovement.STOP;
//
//
//                                }
//                            }
//
//                        }
//
//
//                    },1000);


                    try {
                        Thread.sleep(3);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
//


                    // TODO : killing

                } else if (humanIsAttacking != null && this.isThisHumanVisible(humanIsAttacking)) {
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    stateOfMove = statesOfMovement.ATTACKING;

                } else if (humanIsAttacking != null && !this.isThisHumanVisible(humanIsAttacking)) {
                    humanIsAttacking = null;
                    if (!pathOfCoordinates.isEmpty())
                        stateOfMove = statesOfMovement.MOVING_BY_ORDERED;
                    else stateOfMove = statesOfMovement.STOP;


                } else {
                    stateOfMove = statesOfMovement.STOP;
                }
                break;
            }
            case  DESTRUCTION_BUILDINGS:{

                if (   ((LiveElements) onTheWayBuilding).isUnderConstructed()){

                    try {
                        Thread.sleep( 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    ((LiveElements) onTheWayBuilding).destroy();
                    onTheWayBuilding = null;
                    stateOfMove = statesOfMovement.STOP;
                    playPanel.dispatchEvent(new SimpleMessages(playPanel , Messages.REPAINT));



                }else {

                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    ((LiveElements) onTheWayBuilding).destroy();
                    onTheWayBuilding = null;
                    stateOfMove = statesOfMovement.STOP;
                    playPanel.dispatchEvent(new SimpleMessages(playPanel , Messages.REPAINT));



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

    @Override
    public void findingSelectedObject(int x, int y) {

    }


    protected boolean checkWetherTheGoalCellIsBullidng(Coordinate crd) {
        System.out.println("coordinate of this check i s\t" + crd);
        System.out.println("map is\t" + map);
        System.out.println(map.getCell(crd.getRow() , crd.getColumn()));
        if(  map.getCell(crd.getRow() , crd.getColumn())  instanceof LowLand || map.getCell(crd.getRow() , crd.getColumn()) instanceof HighLand){
            Cell cell = map.getCell(crd.getRow(), crd.getColumn());
            System.out.println(cell);
            if( cell.getInsideElementsItems() != null && cell.getInsideElementsItems() instanceof LiveElements) {
                this.onTheWayBuilding = cell.getInsideElementsItems();
                return true;
            }
        }

        return false;

    }
}



