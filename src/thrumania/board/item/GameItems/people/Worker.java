package thrumania.board.item.GameItems.people;

import thrumania.board.item.GameItems.buildings.*;
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
// TODO : Program's speed due to  two tings : having ThreadPool ,  and  curculating through all humans cuz of having one array list :
// TODO :  we can hve hashmap  and for
public class Worker extends Human {
    private boolean canGoMountain;
    // TODO : worker's Order
    private int capacityOfCollectingWood =0 ;
    private int capacityOfCollectingStone =0;
    private int capacityOfCollectingGold =0 ;
    private boolean hasAttacked = false;

    private boolean isCapacityOfCollectingItemsFull;
    private int speadOfCollectingItems;
    private PlayPanel playPanel;
    private Map map;
    private Dimension d = new Dimension(Constants.CELL_SIZE, Constants.CELL_SIZE);
    private int MAX_RESOURCE_CAPACITY = 300;
    private InsideElementsItems elementIsBeingCollected;

    private Coordinate resourceCoordinate;

    public Worker(PlayPanel playPanel, Map map, int xCord, int yCord, int playerNumber) {
        // moshakhasat :
        super.health = 500;
        // TODO: DAMAGE IS IN RATE OF .5
        super.damageUnit = 20;
        //TODO : 15
        super.visibilityUnit = 100;
        super.foodReq = 1000;
        super.woodReq = 0;
        super.goldReq = 0;
        super.ironReq = 0;
        super.speedOfConsumingFood = 1;
        super.playerNumber = playerNumber;
        // TODO : one unit of each



        super.isSelectedByPlayer = false;
        // mokhtasat :
        super.xCord = xCord;
        super.yCord = yCord;

        super.coordinate = IntegerUtils.getCoordinateWithXAndY(xCord, yCord);

        // aks :
        // TODO : setting pictures while moving ( sequence of pictures )
        super.picutreName = "W033.png";
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
    protected void determiningSpeedOfCollectingItems(InsideElementsItems  element){

            if(! ((DeadElements) element).getHaveBuildingAssignedTo())
                speadOfCollectingItems = 20 ;
            else
                speadOfCollectingItems = 40 ;
        // TODO : if that source has  its quarry the speed should : * 2
        // TODO @amirhosein  :   first amirhosein should put a boolean  variable to the trees and gold mines and stone mines too  see wether there is any quarry assigned to that resource or not


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

    public int getCapacityOfCollectinWood() {
        return capacityOfCollectingWood;
    }

    public void setCapacityOfCollectingItems(int capacityOfCollectingItems) {
        this.capacityOfCollectingWood = capacityOfCollectingWood;
    }

    private boolean checkWheterCapacityIsFull() {
        if( capacityOfCollectingWood == MAX_RESOURCE_CAPACITY || capacityOfCollectingGold == MAX_RESOURCE_CAPACITY || capacityOfCollectingStone == MAX_RESOURCE_CAPACITY)
            return true;
        else return  false;


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
                            capacityOfCollectingStone =0;
                            capacityOfCollectingStone = 0;
                            capacityOfCollectingGold =0;

                            isCapacityOfCollectingItemsFull = false;
                            if (resourceCoordinate != null) {
                                pathOfCoordinates = mapProcessor.getPath(coordinate, resourceCoordinate, this);
                                stateOfMove = statesOfMovement.MOVING_BY_ORDERED;
                            }

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
//                    isAttackMove = true;
//TODO : add picture moving for htis part
                    stateOfMove = statesOfMovement.ATTACKING;

                } else if (!pathOfCoordinates.isEmpty()) {
                    if (checkWetherTheGoalCellIsResourcesOrNot(pathOfCoordinates.peek())) {
                        elementIsBeingCollected = map.getCell(pathOfCoordinates.peek().getRow() , pathOfCoordinates.peek().getColumn()).getInsideElementsItems();
                        if (pathOfCoordinates.peek().getRow() > coordinate.getRow())
                            yCord += 10;
                        else if (pathOfCoordinates.peek().getRow() < coordinate.getRow())
                            yCord -= 10;
                        if (pathOfCoordinates.peek().getColumn() > coordinate.getColumn())
                            xCord += 10;
                        else if (pathOfCoordinates.pop().getColumn() < coordinate.getColumn())
                            xCord -= 10;
                        coordinate = IntegerUtils.getCoordinateWithXAndY(xCord, yCord);
                        // TODO : handle it for tree if one tree is done go for te closest one if jungle is finished
                        resourceCoordinate = coordinate;
                        stateOfMove = statesOfMovement.Collecting_Item;

                        // TODO : handling construnting buildings by using another if ( map...... cell ...inside element is building ... state is constructuin

                    }else if( false){
                        // TODO check next one is building which is under construction or not ....... if it is then ......

                    }
                    else {

                        if (pathOfCoordinates.peek().equals(coordinate))
                            pathOfCoordinates.pop();
                        if (!pathOfCoordinates.isEmpty() && !this.checkWheterTheGoalCellIsWaterOrNot(pathOfCoordinates.peek()))
                            regularMove(pathOfCoordinates.pop());
                        else {
                            while (!pathOfCoordinates.isEmpty()) {
                                pathOfCoordinates.pop();
                                stateOfMove = statesOfMovement.STOP;
                            }

                        }


                        if (pathOfCoordinates.isEmpty())
                            stateOfMove = statesOfMovement.STOP;
                    }
                }else stateOfMove = statesOfMovement.STOP;
                break;
            }
            case ATTACKING: {
                try {
                    Thread.sleep(150);
                } catch (InterruptedException e) {


                }

                canLookForOpponent = false;
                if (!pathOfCoordinates.isEmpty()) {
                    humanIsAttacking = null;
                    stateOfMove = statesOfMovement.MOVING_BY_ORDERED;
                } else {
                    if (isThisHumanVisible(humanIsAttacking))

                        attackMove(humanIsAttacking);
                    if (IntegerUtils.getDistanceOfTWoIntegers(xCord, humanIsAttacking.getxCord()) <= distanceShouldKeepWhenAttacking && IntegerUtils.getDistanceOfTWoIntegers(yCord, humanIsAttacking.getyCord()) <= distanceShouldKeepWhenAttacking) {
//                        isKillingOpponent = true;
                        while (!pathOfCoordinates.isEmpty())
                            pathOfCoordinates.pop();
                        this.stateOfMove = statesOfMovement.KILLING;
                    }
                }


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

                    // TODO : handle : dead and kill
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

                } else if (!this.isThisHumanVisible(humanIsAttacking)) {
//                    isAttackMove = false;
                    humanIsAttacking = null;
                    if (!pathOfCoordinates.isEmpty())
                        stateOfMove = statesOfMovement.MOVING_BY_ORDERED;
                    else stateOfMove = statesOfMovement.STOP;


                } else {
                    System.out.println("++++++++++++++++++++here222 +++++++++++++++++");
                }
                break;
            }
            case Collecting_Item: {

                constructingThisBuilding = null;

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

                            if ( elementIsBeingCollected != null) {

                                    if (!checkWheterCapacityIsFull()) {
                                        this.determiningSpeedOfCollectingItems(elementIsBeingCollected);
                                        new java.util.Timer().schedule(new TimerTask() {

                                            @Override
                                            public void run() {
                                                if( elementIsBeingCollected instanceof  Tree)
                                                capacityOfCollectingWood += ((DeadElements) elementIsBeingCollected).getEachElementCapacity();
                                                else if( elementIsBeingCollected instanceof GoldMine)
                                                    capacityOfCollectingGold += ((GoldMine) elementIsBeingCollected).getEachElementCapacity();
                                                else if( elementIsBeingCollected instanceof  StoneMine)
                                                    capacityOfCollectingStone += ((StoneMine) elementIsBeingCollected).getEachElementCapacity();
                                            }

                                        }, 1000 / speadOfCollectingItems);


                                    } else
                                        stateOfMove = statesOfMovement.COLLECTING_ITEM_IS_DONE;




                            }else stateOfMove = statesOfMovement.STOP;

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
                if (humanIsAttacking == null)
                {
                    humanIsAttacking = seeAnyFoes();
                } else humanIsAttacking = null;
                if (humanIsAttacking != null && this.isThisHumanVisible(humanIsAttacking)) {
//                        pathOfCoordinates = mapProcessor.getPath(coordinate, humanIsAttacking.getCoordinate(), this);
//                        isAttackMove = true;
                    while (!pathOfCoordinates.isEmpty())
                        pathOfCoordinates.pop();
                    stateOfMove = statesOfMovement.ATTACKING;
                }
                if( ! pathOfCoordinates.isEmpty()){

                    stateOfMove = statesOfMovement.MOVING_BY_ORDERED;
                }else if( pathOfCoordinates.isEmpty()){
                        pathOfCoordinates = mapProcessor.getPath(coordinate , homeCastleCoordinate , this);
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



                        //TODO : CONSTRUCITNG

                        stateOfMove = statesOfMovement.CONSTRUCTING_ITEM_IS_DONE;


                    }
                }


                break;
            }

            case CONSTRUCTING_ITEM_IS_DONE: {
                if (resourceCoordinate != null)
                    pathOfCoordinates = mapProcessor.getPath(coordinate, resourceCoordinate, this);
                stateOfMove = statesOfMovement.MOVING_BY_ORDERED;

                break;
            }

        }
    }


    // TODO : we should check the path for builidings
    private boolean checkWetherTheGoalCellIsBullidng(Coordinate crd) {

        if (map.getCell(crd.getRow(), crd.getColumn()) instanceof LowLand || map.getCell(crd.getRow(), crd.getColumn()) instanceof HighLand) {
            Cell cell = map.getCell(crd.getRow(), crd.getColumn());
            if (cell.getInsideElementsItems() instanceof Barrack || cell.getInsideElementsItems() instanceof Castle ||
                    cell.getInsideElementsItems() instanceof Farm || cell.getInsideElementsItems() instanceof MineQuarry
                    || cell.getInsideElementsItems() instanceof WoodQuarry)
                return true;
        }

        return false;

    }

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

    @Override
    public void paintingOptions(Graphics g) {

    }
}
