package thrumania.board.item.GameItems.people;

import thrumania.board.item.MapItems.Cells.Cell;
import thrumania.board.item.MapItems.Cells.HighLand;
import thrumania.board.item.MapItems.Cells.LowLand;
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
import java.util.Stack;
import java.util.TimerTask;

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
    private int distanceShouldKeepWhenAttacking = Constants.CELL_SIZE / 7;
    private Coordinate resourceCoordinate;

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
        super.speedOfConsumingFood = 1;
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
        super.isAttackMove = false;
        super.isKillingOpponent = false;        this.isCapacityOfCollectingItemsFull = false;
        this.canGoMountain = false;


        // other classes :
        this.map = map;
        this.mapProcessor = new MapProcessor(map.getCells());
        this.playPanel = playPanel;

        // JLabel things :
        this.setSize(d);


    }

    private void regularMove(Coordinate end) {
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
//


//
//            }
        // TODO : check this sleep :|
//            try {
//                Thread.sleep(2);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            isKillingOpponent = true;
        // TODO : kill the opponent


    }

    private void attackMove( Coordinate end) {
        int xEnd, yEnd;

        this.xCord = coordinate.getColumn() * Constants.CELL_SIZE + Constants.CELL_SIZE / 10;
        this.yCord = coordinate.getRow() * Constants.CELL_SIZE;
        xEnd = end.getColumn() * Constants.CELL_SIZE + Constants.CELL_SIZE / 10;
        yEnd = end.getRow() * Constants.CELL_SIZE;


        this.determiningSpeedOfMoving();
        while (IntegerUtils.getDistanceOfTWoIntegers(xCord, xEnd) > distanceShouldKeepWhenAttacking || IntegerUtils.getDistanceOfTWoIntegers(yCord, yEnd) > distanceShouldKeepWhenAttacking) {
//
            if (this.xCord + distanceShouldKeepWhenAttacking < xEnd)
                xCord++;
            else if (this.xCord > xEnd + distanceShouldKeepWhenAttacking)
                xCord--;
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
//            try {
//                Thread.sleep(1);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            System.out.println(isKillingOpponent);
//            System.out.println(canAttack);


//            if (canAttack) {

//                if (humanIsAttacking == null)
//                    humanIsAttacking = seeAnyFoes();
//                if (humanIsAttacking != null && this.isThisHumanVisible(humanIsAttacking) && !isKillingOpponent) {
//                    pathOfCoordinates = mapProcessor.getPath(coordinate, humanIsAttacking.getCoordinate(), this);
//                    super.isInAttackState = true;
//                } else {
//                    isInAttackState = false;
//                    isKillingOpponent = false;
//                    humanIsAttacking = null;
//                }
//
//
//                if (!isKillingOpponent) {

                    examiningPath3();
//                }
//            }


        }


    }

    public void examiningPath2() {

        // TODO : fix this sleep

//        try {
//            Thread.sleep(1);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        while (!pathOfCoordinates.isEmpty()) {
            System.out.println("we are at \t " + coordinate);
            System.out.println("our xcoordinate is    " + xCord + "   and our ycoordinate is   " + yCord);
            if (pathOfCoordinates.peek().equals(coordinate))
                pathOfCoordinates.pop();
            if (!pathOfCoordinates.isEmpty() && this.checkWheterTheGoalCellIsWaterOrNot(pathOfCoordinates.peek())) {
                regularMove(pathOfCoordinates.pop());


            } else {

                while (!pathOfCoordinates.isEmpty())
                    pathOfCoordinates.pop();
            }
        }


    }

    public void examiningPath3() {
//        System.out.println("state move is " + stateOfMove.toString());
        switch (stateOfMove) {
            case STOP: {
                canLookForOpponent = true;
                if (!pathOfCoordinates.isEmpty()) {
                    //  first too see if there is any order :
                    stateOfMove = statesOfMovement.MOVING_BY_ORDERED;
                } else if ( pathOfCoordinates.isEmpty()){
                    //  second to see if there is no ordered , there is any foes to attack

                    if (humanIsAttacking == null)
                        if( canLookForOpponent )
                        humanIsAttacking = seeAnyFoes();
                    else humanIsAttacking  =null;
                    if (humanIsAttacking != null && this.isThisHumanVisible(humanIsAttacking)) {
                        System.out.println("path path path 11111111");
                        pathOfCoordinates = mapProcessor.getPath(coordinate, humanIsAttacking.getCoordinate(), this);
                        stateOfMove = statesOfMovement.ATTACKING;
                        isAttackMove = true;
                    }
                    else if( isCapacityOfCollectingItemsFull){
// going back to the castle

                        if ( coordinate.equals(homeCastleCoordinate)) {
                            /// TODO empting resources and add them to our collected resources;
                            isCapacityOfCollectingItemsFull = false;
                            if (resourceCoordinate != null){
                                pathOfCoordinates = mapProcessor.getPath(coordinate , resourceCoordinate , this);
                                stateOfMove = statesOfMovement.MOVING_BY_ORDERED;
                            }

                        }

                    }else {
                        pathOfCoordinates = mapProcessor.getPath(coordinate , homeCastleCoordinate , this);
                    stateOfMove = statesOfMovement.MOVING_BY_ORDERED ;

                }







                }


                break;


            }

            case MOVING_BY_ORDERED: {
                // TODO : we should check the path for builidings
                if (humanIsAttacking == null)
                    if (canLookForOpponent) {
                        humanIsAttacking = seeAnyFoes();
                        System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
                    }
                else humanIsAttacking = null;
                if (humanIsAttacking != null && this.isThisHumanVisible(humanIsAttacking)) {
                    System.out.println("path path path 3333333");
                    pathOfCoordinates = mapProcessor.getPath(coordinate, humanIsAttacking.getCoordinate(), this);
                    isAttackMove = true;
                    stateOfMove = statesOfMovement.ATTACKING;
                }

                else  if ( ! pathOfCoordinates.isEmpty()){
                    if( checkWetherTheGoalCellIsResourcesOrNot(pathOfCoordinates.peek())) {
                        if( pathOfCoordinates.peek().getRow() > coordinate.getRow())
                            yCord +=10;
                        else if( pathOfCoordinates.peek().getRow() < coordinate.getRow())
                            yCord -=10;
                        if ( pathOfCoordinates.peek().getColumn() >    coordinate.getColumn())
                            xCord +=10;
                        else if ( pathOfCoordinates.pop().getColumn() < coordinate.getColumn())
                            xCord -=10;
                        coordinate = IntegerUtils.getCoordinateWithXAndY(xCord , yCord);
                        // TODO : handle if for tree if one tree is done go for te closest one if jungle is finished
                        resourceCoordinate = coordinate;
                        stateOfMove = statesOfMovement.Collecting_Item;

                            // TODO : handling construnting buildings by using another if ( map...... cell ...inside element is building ... state is constructuin

                    }else {

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
                }
                break;
            }
            case ATTACKING: {

                if( isAttackMove){

                    if( pathOfCoordinates.peek().equals(coordinate))
                        pathOfCoordinates.pop();
                    if ( !pathOfCoordinates.isEmpty() && ! this.checkWheterTheGoalCellIsWaterOrNot(pathOfCoordinates.peek()))
                        attackMove(pathOfCoordinates.pop());
                    else {
                        while (!pathOfCoordinates.isEmpty()) {

                            pathOfCoordinates.pop();
                            isAttackMove = false;
                            stateOfMove = statesOfMovement.STOP;

                        }
                    }
                    if (pathOfCoordinates.isEmpty()) {
                        isKillingOpponent =true;
                        isAttackMove = false;
                        stateOfMove = statesOfMovement.KILLING;
                    }
                }else if (!isAttackMove){
                    canLookForOpponent = false;
                    stateOfMove = statesOfMovement.MOVING_BY_ORDERED;
                }


                break;
            }
            case KILLING:{

                if (isKillingOpponent) {
                    // TODO : handle : dead and kill
                    new java.util.Timer().schedule(new TimerTask() {

                        @Override
                        public void run() {
                            Human  human=   seeAnyFoes();
                            if( human != null)
                            human.setHealth(human.getHealth() - damageUnit);

                        }

                    }, 1000);
                    if( health == 0)
                   stateOfMove = statesOfMovement.STOP;
                    try {
                        Thread.sleep(3);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }else {
                    System.out.println("*******************");
                    canLookForOpponent = false;

                    stateOfMove = statesOfMovement.MOVING_BY_ORDERED;
                }
                break;
            }
            case Collecting_Item: {

                constructingThisBuilding = null;

                    if ( !pathOfCoordinates.isEmpty())
                        stateOfMove= statesOfMovement.MOVING_BY_ORDERED;
                else {
                        if (humanIsAttacking == null)
                            if (canLookForOpponent) {
                                humanIsAttacking = seeAnyFoes();
                            } else humanIsAttacking = null;
                        if (humanIsAttacking != null && this.isThisHumanVisible(humanIsAttacking)) {
                            pathOfCoordinates = mapProcessor.getPath(coordinate, humanIsAttacking.getCoordinate(), this);
                            isAttackMove = true;
                            stateOfMove = statesOfMovement.ATTACKING;
                        }else {


                            //TODO : do collecting

                            stateOfMove = statesOfMovement.COLLECTING_ITEM_IS_DONE;


                        }
                    }



                break;
            }
            case COLLECTING_ITEM_IS_DONE: {
                // TODO  : empting the resources and adding it  to our cellected resources
                // TODO :maybe we can give the closest resource coordinate of that jugle if it is tree of that jumgle as the distination of sentence bellow for geting path
                // TODO : notice that if the jungel is finished we still shall  go  where jungle used to exist or we might want to stay near the castle
                if( resourceCoordinate != null ){
                    pathOfCoordinates = mapProcessor.getPath(coordinate, resourceCoordinate, this);
                    stateOfMove = statesOfMovement.MOVING_BY_ORDERED;
                }else {


                    stateOfMove = statesOfMovement.STOP;
                }

                break;
            }
            case  CONSTRUCTING_ITEM:
            {
                resourceCoordinate = null;
                if ( !pathOfCoordinates.isEmpty())
                    stateOfMove= statesOfMovement.MOVING_BY_ORDERED;
                else {
                    if (humanIsAttacking == null)
                        if (canLookForOpponent) {
                            humanIsAttacking = seeAnyFoes();
                        } else humanIsAttacking = null;
                    if (humanIsAttacking != null && this.isThisHumanVisible(humanIsAttacking)) {
                        pathOfCoordinates = mapProcessor.getPath(coordinate, humanIsAttacking.getCoordinate(), this);
                        isAttackMove = true;
                        stateOfMove = statesOfMovement.ATTACKING;
                    }else {


                        //TODO : CONSTRUCITNG

                        stateOfMove = statesOfMovement.CONSTRUCTING_ITEM_IS_DONE;


                    }
                }


                break;
            }

            case CONSTRUCTING_ITEM_IS_DONE: {
                if( resourceCoordinate != null)
                    pathOfCoordinates  = mapProcessor.getPath(coordinate , resourceCoordinate , this);
                stateOfMove = statesOfMovement.MOVING_BY_ORDERED;

                break;
            }

        }
    }

    public void examiningPath() {
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
            if (this.checkWheterTheGoalCellIsWaterOrNot(path.peek()))
                regularMove(path.pop());
            else {

                while (!path.isEmpty())
                    path.pop();

            }
        }


    }

    // TODO : we should check the path for builidings
    private boolean checkWetherTheGoalCellIsResourcesOrNot(Coordinate crd) {



        if (map.getCell(crd.getRow(), crd.getColumn()) instanceof LowLand || map.getCell(crd.getRow(), crd.getColumn()) instanceof HighLand) {
            Cell cell = map.getCell(crd.getRow(), crd.getColumn());
            if (cell.getInsideElementsItems() != null) {
                if( cell.getInsideElementsItems() instanceof Tree || cell.getInsideElementsItems() instanceof StoneMine || cell.getInsideElementsItems() instanceof GoldMine)
                    return  true;
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

}
