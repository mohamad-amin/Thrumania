package thrumania.board.item.GameItems.people;

import thrumania.board.item.GameItems.LiveElements;
import thrumania.board.item.InsideElementsItems;
import thrumania.game.MapProcessor;
import thrumania.utils.Constants;
import thrumania.utils.Coordinate;
import thrumania.utils.IntegerUtils;

import java.util.Stack;

/**
 * Created by sina on 6/24/16.
 */

public abstract class Human extends InsideElementsItems implements Runnable {
    // TODO : teams
    protected  int health;
    // each damage is in scale of 0.5 second
    protected  int damageUnit;
    //TODO : visibility
    // it is in scale of foot;
    protected  int visibilityUnit;
    // in scale of  foot in 1 second :
    protected float  speedOfMoving;
    // in scale of food in 1 second
    protected  int speedOfConsumingFood;
    // requirments:
    protected int foodReq;
    protected int ironReq;
    protected  int woodReq;
    protected int goldReq;
    // TODO : add a class called : human position instead of code below
    protected int xCord;
    protected  int yCord;
    protected  boolean isAlive;
    protected LiveElements constructingThisBuilding = null;
    protected  Coordinate coordinate;
    protected Human humanIsAttacking = null;



    // this enum stands for both worker and soldier , but soldier does not have "COLLECTING_ITEM_IS_DONE" state
    public   enum statesOfMovement  {
           STOP ,  ATTACKING , KILLING , MOVING_BY_ORDERED , COLLECTING_ITEM_IS_DONE , Collecting_Item , CONSTRUCTING_ITEM , CONSTRUCTING_ITEM_IS_DONE
    }
    protected statesOfMovement stateOfMove  = statesOfMovement.ATTACKING.STOP;




    protected Coordinate homeCastleCoordinate;
    protected abstract void determiningSpeedOfMoving();
    protected String picutreName;
    protected boolean canLookForOpponent = true;
    protected  boolean isSelectedByPlayer;



    protected MapProcessor mapProcessor;
    protected boolean isExecuted = false;
    protected int playerNumber;
    protected boolean isAttackMove ;
    protected  boolean isKillingOpponent = false;
    protected boolean constructingIsDone = false;
    protected Stack<Coordinate> pathOfCoordinates = new Stack<>();
    protected int distanceShouldKeepWhenAttacking = Constants.CELL_SIZE;





    // TODO  : deterimining team name !!

    protected  abstract Human seeAnyFoes();

    protected void attackMove(Human human) {


        if (IntegerUtils.getDistanceOfTWoIntegers(xCord, human.getxCord()) > distanceShouldKeepWhenAttacking || IntegerUtils.getDistanceOfTWoIntegers(yCord, human.getyCord()) > distanceShouldKeepWhenAttacking) {
            this.determiningSpeedOfMoving();
            if (xCord < human.getxCord())
                xCord++;
            else if (xCord > human.getxCord())
                xCord--;
            if (yCord < human.getyCord())
                yCord++;
            else if (yCord > human.getyCord())
                yCord--;
            coordinate = IntegerUtils.getCoordinateWithXAndY(xCord, yCord);
            try {
                Thread.sleep((long) (1000 / (speedOfMoving * 5)));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }

    }
    protected void regularMove(Coordinate end) {
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


    }









    public int getyCord() {return yCord;}

    public void setyCord(int yCord) {this.yCord = yCord;}

    public int getxCord() {return xCord;}

    public void setxCord(int xCord) {
        this.xCord = xCord;

    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public boolean isSelectedByPlayer() {
        return isSelectedByPlayer;
    }

    public void setSelectedByPlayer(boolean selectedByPlayer) {
        isSelectedByPlayer = selectedByPlayer;
    }


    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }


    public Coordinate getHomeCastleCoordinate() {
        return homeCastleCoordinate;
    }

    public void setHomeCastleCoordinate(Coordinate homeCastleCoordinate) {
        homeCastleCoordinate = homeCastleCoordinate;
    }


    public MapProcessor getMapProcessor() {
        return mapProcessor;
    }

    public String getPicutreName() {
        return picutreName;
    }

    public void setPicutreName(String picutreName) {
        this.picutreName = picutreName;
    }

    public boolean isExecuted() {
        return isExecuted;
    }

    public void setExecuted(boolean executed) {
        isExecuted = executed;
    }




    public int getPlayerNumber() {
        return playerNumber;
    }

    public void setPathOfCoordinates(Stack<Coordinate> pathOfCoordinates) {
        this.pathOfCoordinates = pathOfCoordinates;
    }



    public boolean isAttackMove() {
        return isAttackMove;
    }

    public void setAttackMove(boolean attackMove) {
        isAttackMove = attackMove;
    }

    public void setKillingOpponent(boolean killingOpponent) {
        isKillingOpponent = killingOpponent;
    }

    public boolean isKillingOpponent() {
        return isKillingOpponent;
    }

    public Stack<Coordinate> getPathOfCoordinates() {
        return pathOfCoordinates;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public statesOfMovement getStateOfMove() {
        return stateOfMove;
    }

    public void setStateOfMove(statesOfMovement stateOfMove) {
        this.stateOfMove = stateOfMove;
    }

    public Human getHumanIsAttacking() {
        return humanIsAttacking;
    }

    public void setHumanIsAttacking(Human humanIsAttacking) {
        this.humanIsAttacking = humanIsAttacking;
    }
}