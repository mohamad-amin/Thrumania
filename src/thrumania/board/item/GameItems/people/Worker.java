package thrumania.board.item.GameItems.people;

import thrumania.board.item.GameItems.LiveElements;
import thrumania.board.item.InsideElementsItems;
import thrumania.board.item.MapItems.Cells.Cell;
import thrumania.board.item.MapItems.Cells.HighLand;
import thrumania.board.item.MapItems.Cells.LowLand;
import thrumania.board.item.MapItems.DeadElements;
import thrumania.board.item.MapItems.Inside.GoldMine;
import thrumania.board.item.MapItems.Inside.StoneMine;
import thrumania.board.item.MapItems.Inside.Tree;
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
import thrumania.utils.ImageUtils;
import thrumania.utils.IntegerUtils;

import java.awt.*;
import java.util.TimerTask;

/**
 * Created by sina on 6/24/16.
 */
// TODO : Program's speed due to  two tings : having ThreadPool ,  and  curculating through all humans cuz of having one array list :
// TODO :  we can hve hashmap  and for
public class Worker extends Human {
    private boolean canGoMountain;
    // TODO : worker's Order
    private int capacityOfCollectingWood = 0;
    private int capacityOfCollectingStone = 0;
    private int capacityOfCollectingGold = 0;


    private boolean isCapacityOfCollectingItemsFull;
    private int speadOfCollectingItems;
    private PlayPanel playPanel;
    private Map map;
    private Dimension d = new Dimension(Constants.CELL_SIZE - 5, Constants.CELL_SIZE - 5);
    private int MAX_RESOURCE_CAPACITY = 300;

    private Coordinate resourceCoordinate;

    public Worker(PlayPanel playPanel, Map map, int xCord, int yCord, int playerNumber, PlayBottomPanel playBottomPanel) {
        this.playBottomPanel = playBottomPanel;
        // moshakhasat :
        super.health = 500;
        // TODO: DAMAGE IS IN RATE OF .5
        super.damageUnit = 20;
        //TODO : 15
        super.visibilityUnit = 80;
        // requiments :
        super.foodReq = 1000;
        super.woodReq = 0;
        super.goldReq = 0;
        super.ironReq = 0;
        super.speedOfConsumingFood = 1;
        super.playerNumber = playerNumber;
        // TODO : one unit of each


//        super.isSelectedByPlayer = false;
        // mokhtasat :
        super.xCord = xCord;
        super.yCord = yCord;

        super.coordinate = IntegerUtils.getCoordinateWithXAndY(xCord, yCord);

        // aks :
        // TODO : setting pictures while moving ( sequence of pictures )
        super.picutreName = "W" + playerNumber % 4 + "33.png";
        // masir

        // booleans :
        super.isAlive = true;

        this.isCapacityOfCollectingItemsFull = false;
        this.canGoMountain = false;


        // other classes :
        this.map = map;
        this.mapProcessor = new MapProcessor(map.getCells());
        this.playPanel = playPanel;

        // JLabel things :
        this.setSize(d);


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

    protected void determiningSpeedOfCollectingItems(InsideElementsItems element) {

        if (!((DeadElements) element).getHaveBuildingAssignedTo())
            speadOfCollectingItems = 20;
        else
            speadOfCollectingItems = 40;
        // TODO : if that source has  its quarry the speed should : * 2
        // TODO @amirhosein  :   first amirhosein should put a boolean  variable to the trees and gold mines and stone mines too  see wether there is any quarry assigned to that resource or not


    }

    @Override
    protected Human seeAnyFoes() {

        synchronized (HumanManagers.getSharedInstance().getHumans()) {
            for (int i = 0; i < HumanManagers.getSharedInstance().getHumans().length; i++) {
                if (i != this.playerNumber)
                    for (int j = 0; j < HumanManagers.getSharedInstance().getHumans()[i].size(); j++) {
//                    System.out.println("error is here \t" + HumanManagers.getSharedInstance().getHumans()[i].get(j));
                        if (isThisHumanVisible(HumanManagers.getSharedInstance().getHumans()[i].get(j)))
                            return HumanManagers.getSharedInstance().getHumans()[i].get(j);

                    }
            }


            return null;

        }

    }


    public boolean isCanGoMountain() {
        return canGoMountain;
    }

    public void setCanGoMountain(boolean canGoMountain) {
        this.canGoMountain = canGoMountain;
    }

    public int getCapacityOfCollectinWood() {
        return capacityOfCollectingWood;
    }

    public void setCapacityOfCollectingItems(int capacityOfCollectingWood) {
        this.capacityOfCollectingWood = capacityOfCollectingWood;
    }

    private void checkWheterCapacityIsFull() {
        if (capacityOfCollectingWood == MAX_RESOURCE_CAPACITY || capacityOfCollectingGold == MAX_RESOURCE_CAPACITY || capacityOfCollectingStone == MAX_RESOURCE_CAPACITY)
            isCapacityOfCollectingItemsFull = true;
        else isCapacityOfCollectingItemsFull = false;


    }

    @Override
    public void run() {
        while (isAlive) {
            examiningPath3();
        }


    }

    public void examiningPath3() {

        switch (stateOfMove) {
            case STOP: {
                canLookForOpponent = true;
                if (!pathOfCoordinates.isEmpty()) {
// first if there is any order to go
                    stateOfMove = statesOfMovement.MOVING_BY_ORDERED;
                } else if (pathOfCoordinates.isEmpty()) {
// second if someone is attacking , go for it
                    checkWheterCapacityIsFull();
                    if (humanIsAttacking == null)
                        if (canLookForOpponent)
                            humanIsAttacking = seeAnyFoes();
                        else humanIsAttacking = null;
                    if (humanIsAttacking != null && this.isThisHumanVisible(humanIsAttacking)) {
                        stateOfMove = statesOfMovement.ATTACKING;
                    } else if (isCapacityOfCollectingItemsFull) {
// going back to the castle

                        if (coordinate.equals(homeCastleCoordinate)) {
                            playPanel.setIronRes(capacityOfCollectingStone);
                            playPanel.setWoordRes(capacityOfCollectingWood);
                            playPanel.setGoldRes(capacityOfCollectingGold);
                            capacityOfCollectingWood = 0;
                            capacityOfCollectingStone = 0;
                            capacityOfCollectingGold = 0;

                            this.checkWheterCapacityIsFull();
                            if (resourceCoordinate != null) {
                                pathOfCoordinates = mapProcessor.getPath(coordinate, resourceCoordinate, this);
                                stateOfMove = statesOfMovement.MOVING_BY_ORDERED;
                            }


                        } else {
                            pathOfCoordinates = getMapProcessor().getPath(coordinate, getHomeCastleCoordinate(), this);
                            stateOfMove = statesOfMovement.MOVING_BY_ORDERED;

                        }

                    } //TODO check for bugs


                }


                break;


            }

            case MOVING_BY_ORDERED: {

                // TODO : we should check the path for builidings
                if (humanIsAttacking == null)
                    if (canLookForOpponent) {
                        humanIsAttacking = seeAnyFoes();
                    } else humanIsAttacking = null;
                if (humanIsAttacking != null && this.isThisHumanVisible(humanIsAttacking)) {
                    System.out.println("path path path 3333333");
                    while (!pathOfCoordinates.isEmpty())
                        pathOfCoordinates.pop();

                    stateOfMove = statesOfMovement.ATTACKING;

                } else if (!pathOfCoordinates.isEmpty()) {

                    if (pathOfCoordinates.size() == 1 && checkWetherTheGoalCellIsResourcesOrNot(pathOfCoordinates.peek())) {
                        Coordinate tempC = pathOfCoordinates.pop();
                        resourceCoordinate = tempC;
//                        coordinate = tempC;
                        if (this.checkWetherThisWorkerCanCollectThisItem(map.getCell(resourceCoordinate.getRow(), resourceCoordinate.getColumn()).getInsideElementsItems())) {
                            System.out.println("naya naya naya inja");
                            elementIsBeingCollected = map.getCell(resourceCoordinate.getRow(), resourceCoordinate.getColumn()).getInsideElementsItems();
                            stateOfMove = statesOfMovement.Collecting_Item;
                        } else {

                            resourceCoordinate = null;
                            stateOfMove = statesOfMovement.STOP;
                        }

//                        System.out.println("paht 1 is \t" + pathOfCoordinates);
//                        elementIsBeingCollected = map.getCell(resourceCoordinate.getRow() , resourceCoordinate.getColumn()).getInsideElementsItems();
//                        if (tempC.getRow() > coordinate.getRow())
//                            yCord += 10;
//                        else if (tempC.getRow() < coordinate.getRow())
//                            yCord -= 10;
//                        System.out.println("paht 2 is \t" + pathOfCoordinates);
//                        if (tempC.getColumn() > coordinate.getColumn()) {
//                            xCord += 10;
//                            System.out.println("paht 3 is \t" + pathOfCoordinates);
//                        }
//                        else if (tempC.getColumn() < coordinate.getColumn())
//                            xCord -= 10;
//                        coordinate = IntegerUtils.getCoordinateWithXAndY(xCord, yCord);
                        // TODO : handle it for tree if one tree is done go for te closest one if jungle is finished


                        // TODO : handling construnting buildings by using another if ( map...... cell ...inside element is building ... state is constructuin

                    } else if (!pathOfCoordinates.isEmpty() && checkWetherTheGoalCellIsBullidng(pathOfCoordinates.peek())) {
                        if (onTheWayBuilding != null && ((LiveElements) onTheWayBuilding).getSide().getNumberOfPlayer() == this.playerNumber) {
                            if (((LiveElements) onTheWayBuilding).isUnderConstructed()) {
                                if (((LiveElements) onTheWayBuilding).getConstructorsWorking() < ((LiveElements) onTheWayBuilding).getMaxOfConstructor()) {
                                    pathOfCoordinates.pop();
                                    stateOfMove = statesOfMovement.CONSTRUCTING_ITEM;


                                } else {

                                    while (!pathOfCoordinates.isEmpty())
                                        pathOfCoordinates.pop();
                                    stateOfMove = statesOfMovement.STOP;

                                }


                            } else {
                                while (!pathOfCoordinates.isEmpty())
                                    pathOfCoordinates.pop();
                                stateOfMove = statesOfMovement.STOP;
                            }

                        } else {
                            while (! pathOfCoordinates.isEmpty())
                                pathOfCoordinates.pop();
                            stateOfMove = statesOfMovement.DESTRUCTION_BUILDINGS;
                        }

                        // TODO check next one is building which is under construction or not ....... if it is then ......

                    } else {

                        if (pathOfCoordinates.peek().equals(coordinate))
                            pathOfCoordinates.pop();
                            if (!pathOfCoordinates.isEmpty() && (!this.checkWheterTheGoalCellIsWaterOrNot(pathOfCoordinates.peek()))) {
                                if (canGoMountain || !checkWetherGoalCellIsHighLand(pathOfCoordinates.peek()))
                                    regularMove(pathOfCoordinates.pop());
                                else {
                                    while (!pathOfCoordinates.isEmpty()) {
                                        pathOfCoordinates.pop();
                                        stateOfMove = statesOfMovement.STOP;
                                    }

                                }
                            }
                         if (! pathOfCoordinates.isEmpty() && checkWheterTheGoalCellIsWaterOrNot(pathOfCoordinates.peek()))
                            while (! pathOfCoordinates.isEmpty()) {
                                pathOfCoordinates.pop();
                                stateOfMove = statesOfMovement.STOP;

                            }

//                        if (pathOfCoordinates.isEmpty())
//                            stateOfMove = statesOfMovement.STOP;
                    }
                } else stateOfMove = statesOfMovement.STOP;
                break;
            }
            case ATTACKING: {


                canLookForOpponent = false;
                if (!pathOfCoordinates.isEmpty()) {
                    humanIsAttacking = null;
                    stateOfMove = statesOfMovement.MOVING_BY_ORDERED;
                } else if (humanIsAttacking != null) {

                    if (IntegerUtils.getDistanceOfTWoIntegers(xCord, humanIsAttacking.getxCord()) <= distanceShouldKeepWhenAttacking && IntegerUtils.getDistanceOfTWoIntegers(yCord, humanIsAttacking.getyCord()) <= distanceShouldKeepWhenAttacking) {
                        while (!pathOfCoordinates.isEmpty())
                            pathOfCoordinates.pop();
                        this.stateOfMove = statesOfMovement.KILLING;

                    } else {
                        if (isThisHumanVisible(humanIsAttacking))
                            attackMove(humanIsAttacking);
                        else stateOfMove = statesOfMovement.STOP;
                    }
                } else stateOfMove = statesOfMovement.STOP;


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
            case Collecting_Item: {
                System.out.println("Now We are going to collect :D :D :D :D");
                constructingThisBuilding = null;

                if (!pathOfCoordinates.isEmpty())
                    stateOfMove = statesOfMovement.MOVING_BY_ORDERED;
                else {
                    if (humanIsAttacking == null)
                        if (canLookForOpponent) {
                            humanIsAttacking = seeAnyFoes();
                        } else humanIsAttacking = null;
                    if (humanIsAttacking != null && this.isThisHumanVisible(humanIsAttacking)) {
                        while (!pathOfCoordinates.isEmpty())
                            pathOfCoordinates.pop();
                        stateOfMove = statesOfMovement.ATTACKING;
                    } else {

                        if (elementIsBeingCollected != null) {
                            checkWheterCapacityIsFull();
                            if (!isCapacityOfCollectingItemsFull) {

//                                        new java.util.Timer().schedule(new TimerTask() {
//
//                                            @Override
//                                            public void run() {
//                                                if(  ( (DeadElements )elementIsBeingCollected ).getMAX_CAPACITY() == 0)
//                                                {
//                                                    map.getCell( resourceCoordinate.getRow() , resourceCoordinate.getColumn()).setInsideElementsItems(null);
//                                                    playPanel.dispatchEvent(new SimpleMessages(playPanel , Messages.REPAINT ));
//                                                    if( isCapacityOfCollectingItemsFull)
//                                                        stateOfMove = statesOfMovement.COLLECTING_ITEM_IS_DONE;
//                                                    else
//                                                        stateOfMove = statesOfMovement.STOP;
//                                                }
//                                                if( elementIsBeingCollected instanceof  Tree) {
//                                                    if( ((Tree) elementIsBeingCollected).getMAX_CAPACITY() < ((Tree) elementIsBeingCollected).getEachElementCapacity()) {
//                                                        capacityOfCollectingWood += ((Tree) elementIsBeingCollected).getMAX_CAPACITY();
//                                                        ((Tree) elementIsBeingCollected).setMAX_CAPACITY(((Tree) elementIsBeingCollected).getMAX_CAPACITY());
//
//                                                    }else {
//                                                        capacityOfCollectingWood += ((DeadElements) elementIsBeingCollected).getEachElementCapacity();
//                                                        ((DeadElements) elementIsBeingCollected).setMAX_CAPACITY(((DeadElements) elementIsBeingCollected).getEachElementCapacity());
//                                                    }
//
//                                                }
//                                                else if( elementIsBeingCollected instanceof GoldMine) {
//                                                    if( ((GoldMine) elementIsBeingCollected).getMAX_CAPACITY() < capacityOfCollectingGold)
//                                                    {
//                                                        capacityOfCollectingGold +=  ((GoldMine) elementIsBeingCollected).getMAX_CAPACITY();
//                                                        ((GoldMine) elementIsBeingCollected).setMAX_CAPACITY(((GoldMine) elementIsBeingCollected).getMAX_CAPACITY());
//
//                                                    }else {
//                                                        capacityOfCollectingGold += ((GoldMine) elementIsBeingCollected).getEachElementCapacity();
//                                                        ((GoldMine) elementIsBeingCollected).setMAX_CAPACITY(((GoldMine) elementIsBeingCollected).getEachElementCapacity());
//                                                    }
//                                                }
//                                                else if( elementIsBeingCollected instanceof  StoneMine) {
//                                                    if (((StoneMine) elementIsBeingCollected).getMAX_CAPACITY() < ((StoneMine) elementIsBeingCollected).getEachElementCapacity()){
//                                                        capacityOfCollectingStone += ((StoneMine) elementIsBeingCollected).getMAX_CAPACITY();
//                                                        ((StoneMine) elementIsBeingCollected).setMAX_CAPACITY(((StoneMine) elementIsBeingCollected).getMAX_CAPACITY());
//
//                                                    }
//                                                    capacityOfCollectingStone += ((StoneMine) elementIsBeingCollected).getEachElementCapacity();
//                                                    ((StoneMine) elementIsBeingCollected).setMAX_CAPACITY(((StoneMine) elementIsBeingCollected).getEachElementCapacity());
//                                                }
//
//                                            }

//                                        }, 1000 / speadOfCollectingItems);
                                try {
                                    Thread.sleep(1000 / speadOfCollectingItems);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                if (((DeadElements) elementIsBeingCollected).getMAX_CAPACITY() == 0) {


                                    map.getCell(resourceCoordinate.getRow(), resourceCoordinate.getColumn()).setInsideElementsItems(null);
                                    playPanel.dispatchEvent(new SimpleMessages(playPanel, Messages.REPAINT));
                                    if (isCapacityOfCollectingItemsFull)
                                        stateOfMove = statesOfMovement.COLLECTING_ITEM_IS_DONE;
                                    else
                                        stateOfMove = statesOfMovement.STOP;


                                }
                                if (elementIsBeingCollected instanceof Tree) {
                                    if (((Tree) elementIsBeingCollected).getMAX_CAPACITY() < ((Tree) elementIsBeingCollected).getEachElementCapacity()) {
                                        capacityOfCollectingWood += ((Tree) elementIsBeingCollected).getMAX_CAPACITY();
                                        ((Tree) elementIsBeingCollected).setMAX_CAPACITY(((Tree) elementIsBeingCollected).getMAX_CAPACITY());

                                    } else {
                                        capacityOfCollectingWood += ((DeadElements) elementIsBeingCollected).getEachElementCapacity();
                                        ((DeadElements) elementIsBeingCollected).setMAX_CAPACITY(((DeadElements) elementIsBeingCollected).getEachElementCapacity());
                                    }

                                } else if (elementIsBeingCollected instanceof GoldMine) {
                                    if (((GoldMine) elementIsBeingCollected).getMAX_CAPACITY() < capacityOfCollectingGold) {
                                        capacityOfCollectingGold += ((GoldMine) elementIsBeingCollected).getMAX_CAPACITY();
                                        ((GoldMine) elementIsBeingCollected).setMAX_CAPACITY(((GoldMine) elementIsBeingCollected).getMAX_CAPACITY());

                                    } else {
                                        capacityOfCollectingGold += ((GoldMine) elementIsBeingCollected).getEachElementCapacity();
                                        ((GoldMine) elementIsBeingCollected).setMAX_CAPACITY(((GoldMine) elementIsBeingCollected).getEachElementCapacity());
                                    }
                                } else if (elementIsBeingCollected instanceof StoneMine) {
                                    if (((StoneMine) elementIsBeingCollected).getMAX_CAPACITY() < ((StoneMine) elementIsBeingCollected).getEachElementCapacity()) {
                                        capacityOfCollectingStone += ((StoneMine) elementIsBeingCollected).getMAX_CAPACITY();
                                        ((StoneMine) elementIsBeingCollected).setMAX_CAPACITY(((StoneMine) elementIsBeingCollected).getMAX_CAPACITY());

                                    }
                                    capacityOfCollectingStone += ((StoneMine) elementIsBeingCollected).getEachElementCapacity();
                                    ((StoneMine) elementIsBeingCollected).setMAX_CAPACITY(((StoneMine) elementIsBeingCollected).getEachElementCapacity());
                                }


                            } else
                                stateOfMove = statesOfMovement.COLLECTING_ITEM_IS_DONE;


                        } else {
                            stateOfMove = statesOfMovement.STOP;
                            System.out.println("oh oh oh woods are getting fucked here LOOL");
                        }

// TODO check for bug


                    }


                }


                break;
            }
            case COLLECTING_ITEM_IS_DONE: {
                // TODO :maybe we can give the closest resource coordinate of that jungle if it is tree of that jungle as the distination of sentence bellow for geting path
                // TODO : notice that if the jungel is finished we still shall  go  where jungle used to exist or we might want to stay near the castle
//                if (resourceCoordinate != null) {
//                    pathOfCoordinates = mapProcessor.getPath(coordinate, resourceCoordinate, this);
//                    stateOfMove = statesOfMovement.MOVING_BY_ORDERED;
//                } //TODO : check for bugs
                constructingThisBuilding = null;
                if (humanIsAttacking == null) {
                    humanIsAttacking = seeAnyFoes();
                } else humanIsAttacking = null;
                if (humanIsAttacking != null && this.isThisHumanVisible(humanIsAttacking)) {
//                        pathOfCoordinates = mapProcessor.getPath(coordinate, humanIsAttacking.getCoordinate(), this);
//                        isAttackMove = true;
                    while (!pathOfCoordinates.isEmpty())
                        pathOfCoordinates.pop();
                    stateOfMove = statesOfMovement.ATTACKING;
                }
                if (!pathOfCoordinates.isEmpty()) {

                    stateOfMove = statesOfMovement.MOVING_BY_ORDERED;
                } else if (pathOfCoordinates.isEmpty()) {
                    pathOfCoordinates = mapProcessor.getPath(coordinate, homeCastleCoordinate, this);
                    stateOfMove = statesOfMovement.MOVING_BY_ORDERED;
                }

                break;
            }
            case CONSTRUCTING_ITEM: {
                resourceCoordinate = null;
                if (!pathOfCoordinates.isEmpty())
                    stateOfMove = statesOfMovement.MOVING_BY_ORDERED;
                else {
                    if (humanIsAttacking == null)
                        if (canLookForOpponent) {
                            humanIsAttacking = seeAnyFoes();
                        } else humanIsAttacking = null;
                    if (humanIsAttacking != null && this.isThisHumanVisible(humanIsAttacking)) {
//                        pathOfCoordinates = mapProcessor.getPath(coordinate, humanIsAttacking.getCoordinate(), this);
//                        isAttackMove = true;
                        while (!pathOfCoordinates.isEmpty())
                            pathOfCoordinates.pop();
                        stateOfMove = statesOfMovement.ATTACKING;
                    } else {

                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        ((LiveElements) onTheWayBuilding).constructed();
                        playPanel.dispatchEvent(new SimpleMessages(playPanel, Messages.REPAINT));
                        stateOfMove = statesOfMovement.STOP;


                    }
                }


                break;
            }

            case CONSTRUCTING_ITEM_IS_DONE: {
                if (humanIsAttacking == null)
                    if (canLookForOpponent) {
                        humanIsAttacking = seeAnyFoes();
                    } else humanIsAttacking = null;
                if (humanIsAttacking != null && this.isThisHumanVisible(humanIsAttacking)) {
//                        pathOfCoordinates = mapProcessor.getPath(coordinate, humanIsAttacking.getCoordinate(), this);
//                        isAttackMove = true;
                    while (!pathOfCoordinates.isEmpty())
                        pathOfCoordinates.pop();
                    stateOfMove = statesOfMovement.ATTACKING;
                } else if (resourceCoordinate != null) {
                    pathOfCoordinates = mapProcessor.getPath(coordinate, resourceCoordinate, this);
                    stateOfMove = statesOfMovement.MOVING_BY_ORDERED;
                } else stateOfMove = statesOfMovement.STOP;

                break;
            }
            case DESTRUCTION_BUILDINGS: {
                System.out.println("****************");
                if (humanIsAttacking == null)
                    if (canLookForOpponent) {
                        humanIsAttacking = seeAnyFoes();
                    } else humanIsAttacking = null;
                if (humanIsAttacking != null && this.isThisHumanVisible(humanIsAttacking)) {
//                        pathOfCoordinates = mapProcessor.getPath(coordinate, humanIsAttacking.getCoordinate(), this);
//                        isAttackMove = true;
                    while (!pathOfCoordinates.isEmpty())
                        pathOfCoordinates.pop();
                    stateOfMove = statesOfMovement.ATTACKING;
                }
                if (!pathOfCoordinates.isEmpty())
                    stateOfMove = statesOfMovement.MOVING_BY_ORDERED;
                else if (pathOfCoordinates.isEmpty()) {

                    if (((LiveElements) onTheWayBuilding).isUnderConstructed()) {
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        ((LiveElements) onTheWayBuilding).destroy();
                        onTheWayBuilding = null;
                        stateOfMove = statesOfMovement.STOP;
                        playPanel.dispatchEvent(new SimpleMessages(playPanel, Messages.REPAINT));
                    } else {

                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println("nononononnonononnonoonn");

                        ((LiveElements) onTheWayBuilding).destroy();
                        onTheWayBuilding = null;
                        stateOfMove = statesOfMovement.STOP;
                        playPanel.dispatchEvent(new SimpleMessages(playPanel, Messages.REPAINT));


                    }


                }


                break;
            }

        }
    }


    private boolean checkWetherThisWorkerCanCollectThisItem(InsideElementsItems crd) {
//        Cell cell = map.getCell(crd.getRow() , crd.getColumn());
        if (crd instanceof DeadElements) {
            System.out.println("element is \t"+ crd + " with player number \t"+ ((DeadElements) crd).getPlayerNumber());

            if ((((DeadElements) crd).getPlayerNumber() == -1 || ((DeadElements) crd).getPlayerNumber() == this.playerNumber))
                return true;
            else return false;
        }
        return false;


    }


    // TODO : we should check the path for builidings

    private boolean checkWetherTheGoalCellIsResourcesOrNot(Coordinate crd) {


        if (map.getCell(crd.getRow(), crd.getColumn()) instanceof LowLand || map.getCell(crd.getRow(), crd.getColumn()) instanceof HighLand) {
            Cell cell = map.getCell(crd.getRow(), crd.getColumn());
            if (cell.getInsideElementsItems() != null) {
                if (cell.getInsideElementsItems() instanceof Tree || cell.getInsideElementsItems() instanceof StoneMine
                        || cell.getInsideElementsItems() instanceof GoldMine)
                    return true;
            }

        }
        return false;

    }

    private boolean checkWheterTheGoalCellIsWaterOrNot(Coordinate crd) {
        if (map.getCell(crd.getRow(), crd.getColumn()) instanceof LowLand || map.getCell(crd.getRow(), crd.getColumn()) instanceof HighLand) {
            return false;
        } else {
            return true;
        }


    }

    private boolean checkWetherGoalCellIsHighLand(Coordinate crd) {


        Cell cell = map.getCell(crd.getRow(), crd.getColumn());
        if (cell instanceof HighLand)
            return true;
        else return false;


    }

    @Override
    public void paintingOptions(Graphics g) {
        super.paint(g);

        Font myFont = new Font("Party Business", Font.BOLD, 20);
        g.setFont(myFont);
        g.setColor(Color.WHITE);
        g.drawString("health :", 150, 30);
        g.drawString((Integer.toString(health)), 300, 30);
        g.drawString("Side :", 150, 60);
        g.drawString((Integer.toString(playerNumber + 1)), 300, 60);

        int elementCounter = Constants.sizeOfInformationBar;
        if (!b1IsSelected)
            g.drawImage(ImageUtils.getImage("barakmaking.png"), elementCounter * spaceBetweenElements, sizeOfBottom.height / 4, elementsSize, elementsSize, null);
        else if (b1IsSelected) {
            g.drawImage(ImageUtils.getImage("barakmaking2.png"), elementCounter * spaceBetweenElements, sizeOfBottom.height / 4, elementsSize, elementsSize, null);
        }
        elementCounter += 2;
        if (!b2IsSelected)
            g.drawImage(ImageUtils.getImage("minequarrymaking.png"), elementCounter * spaceBetweenElements, sizeOfBottom.height / 4, elementsSize, elementsSize, null);
        else if (b2IsSelected) {
            g.drawImage(ImageUtils.getImage("minequarrymaking2.png"), elementCounter * spaceBetweenElements, sizeOfBottom.height / 4, elementsSize, elementsSize, null);
        }
        elementCounter += 2;
        if (!b3IsSelected)
            g.drawImage(ImageUtils.getImage("woodquarrymaking.png"), elementCounter * spaceBetweenElements, sizeOfBottom.height / 4, elementsSize, elementsSize, null);
        else if (b3IsSelected) {
            g.drawImage(ImageUtils.getImage("woodquarrymaking2.png"), elementCounter * spaceBetweenElements, sizeOfBottom.height / 4, elementsSize, elementsSize, null);
        }
        elementCounter += 2;
        if (!b4IsSelected)
            g.drawImage(ImageUtils.getImage("portmaking.png"), elementCounter * spaceBetweenElements, sizeOfBottom.height / 4, elementsSize, elementsSize, null);
        else if (b4IsSelected) {
            g.drawImage(ImageUtils.getImage("portmaking2.png"), elementCounter * spaceBetweenElements, sizeOfBottom.height / 4, elementsSize, elementsSize, null);
        }
        elementCounter += 2;
        if (!b5IsSelected)
            g.drawImage(ImageUtils.getImage("farmmaking.png"), elementCounter * spaceBetweenElements, sizeOfBottom.height / 4, elementsSize, elementsSize, null);
        else if (b5IsSelected) {
            g.drawImage(ImageUtils.getImage("farmmaking2.png"), elementCounter * spaceBetweenElements, sizeOfBottom.height / 4, elementsSize, elementsSize, null);
        }
        elementCounter += 2;
        if (!b6IsSelected)
            g.drawImage(ImageUtils.getImage("mountainwear.png"), elementCounter * spaceBetweenElements, sizeOfBottom.height / 4, elementsSize, elementsSize, null);
        else if (b6IsSelected) {
            g.drawImage(ImageUtils.getImage("mountainwear2.png"), elementCounter * spaceBetweenElements, sizeOfBottom.height / 4, elementsSize, elementsSize, null);
        }
    }

    @Override
    public void findingSelectedObject(int mouseXcord, int mouseYcord) {
        int elementCounter = Constants.sizeOfInformationBar;

        //Barrak
        if (IntegerUtils.isInSideTheRangeOfCordinates(elementCounter * spaceBetweenElements, sizeOfBottom.height / 4, elementCounter * spaceBetweenElements + elementsSize, sizeOfBottom.height / 4 + elementsSize, mouseXcord, mouseYcord)) {
            playBottomPanel.setBottomPanelSelected(Constants.BottomPanelSelected.buildingBarak);
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

        //minequarry
        elementCounter += 2;
        if (IntegerUtils.isInSideTheRangeOfCordinates(elementCounter * spaceBetweenElements, sizeOfBottom.height / 4, elementCounter * spaceBetweenElements + elementsSize, sizeOfBottom.height / 4 + elementsSize, mouseXcord, mouseYcord)) {
            playBottomPanel.setBottomPanelSelected(Constants.BottomPanelSelected.buildingMinequarry);
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

        elementCounter += 2;
        if (IntegerUtils.isInSideTheRangeOfCordinates(elementCounter * spaceBetweenElements, sizeOfBottom.height / 4, elementCounter * spaceBetweenElements + elementsSize, sizeOfBottom.height / 4 + elementsSize, mouseXcord, mouseYcord)) {
            playBottomPanel.setBottomPanelSelected(Constants.BottomPanelSelected.buildingWoodquarry);
            playBottomPanel.function();
            b3IsSelected = true;
            playBottomPanel.repaint();
            new java.util.Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    b3IsSelected = false;
                    playBottomPanel.repaint();
                }
            }, 110);
        }

        elementCounter += 2;
        if (IntegerUtils.isInSideTheRangeOfCordinates(elementCounter * spaceBetweenElements, sizeOfBottom.height / 4, elementCounter * spaceBetweenElements + elementsSize, sizeOfBottom.height / 4 + elementsSize, mouseXcord, mouseYcord)) {
            playBottomPanel.setBottomPanelSelected(Constants.BottomPanelSelected.buildingPort);
            playBottomPanel.function();
            b4IsSelected = true;
            playBottomPanel.repaint();
            new java.util.Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    b4IsSelected = false;
                    playBottomPanel.repaint();
                }
            }, 110);
        }

        elementCounter += 2;
        if (IntegerUtils.isInSideTheRangeOfCordinates(elementCounter * spaceBetweenElements, sizeOfBottom.height / 4, elementCounter * spaceBetweenElements + elementsSize, sizeOfBottom.height / 4 + elementsSize, mouseXcord, mouseYcord)) {
            playBottomPanel.setBottomPanelSelected(Constants.BottomPanelSelected.buildingFarm);
            playBottomPanel.function();
            b5IsSelected = true;
            playBottomPanel.repaint();
            new java.util.Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    b5IsSelected = false;
                    playBottomPanel.repaint();
                }
            }, 110);
        }

        elementCounter += 2;
        if (IntegerUtils.isInSideTheRangeOfCordinates(elementCounter * spaceBetweenElements, sizeOfBottom.height / 4, elementCounter * spaceBetweenElements + elementsSize, sizeOfBottom.height / 4 + elementsSize, mouseXcord, mouseYcord)) {
            playBottomPanel.setBottomPanelSelected(Constants.BottomPanelSelected.mountainwaer);
            playBottomPanel.function();
            b6IsSelected = true;
            playBottomPanel.repaint();
            new java.util.Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    b6IsSelected = false;
                    playBottomPanel.repaint();
                }
            }, 110);
        }
    }


    protected boolean checkWetherTheGoalCellIsBullidng(Coordinate crd) {
        if (map.getCell(crd.getRow(), crd.getColumn()) instanceof LowLand || map.getCell(crd.getRow(), crd.getColumn()) instanceof HighLand) {
            Cell cell = map.getCell(crd.getRow(), crd.getColumn());
            if (cell.getInsideElementsItems() != null && cell.getInsideElementsItems() instanceof LiveElements) {
                this.onTheWayBuilding = cell.getInsideElementsItems();
                return true;
            }
        }

        return false;

    }

}