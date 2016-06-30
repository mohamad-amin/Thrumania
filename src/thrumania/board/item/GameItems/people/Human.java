package thrumania.board.item.GameItems.people;

import thrumania.board.item.InsideElementsItems;
import thrumania.game.MapProcessor;
import thrumania.utils.Coordinate;

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
    protected  int speadOfConsumingFood;
    // requirments:
    protected int foodReq;
    protected int ironReq;
    protected  int woodReq;
    protected int goldReq;
    // TODO : add a class called : human position instead of code below
    protected int xCord;
    protected  int yCord;
    protected  boolean isAlive;
    protected  Coordinate coordinate;
    protected  Coordinate distination;



    protected Coordinate HomeCastleCoordinate;
    protected abstract void determiningSpeedOfMoving();
    protected String picutreName;
    private boolean shouldDraw;
    protected  boolean isSelectedByPlayer;
    protected   boolean isMoving;


    protected MapProcessor mapProcessor;
    protected boolean isExecuted = false;
    protected int playerNumber;
    protected boolean canAttack ;
    protected  boolean isInAttackState;
    protected  boolean isKillingOpponent = false;
    protected Stack<Coordinate> pathOfCoordinates = new Stack<>();




    // TODO  : deterimining team name !!

    protected  abstract Human seeAnyFoes();








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

    public boolean isShouldDraw() {
        return shouldDraw;
    }

    public void setShouldDraw(boolean shouldDraw) {
        this.shouldDraw = shouldDraw;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }


    public Coordinate getHomeCastleCoordinate() {
        return HomeCastleCoordinate;
    }

    public void setHomeCastleCoordinate(Coordinate homeCastleCoordinate) {
        HomeCastleCoordinate = homeCastleCoordinate;
    }

    public boolean isMoving() {
        return isMoving;
    }

    public void setMoving(boolean moving) {
        isMoving = moving;
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


    public Coordinate getDistination() {
        return distination;
    }

    public void setDistination(Coordinate distination) {
        this.distination = distination;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public void setPathOfCoordinates(Stack<Coordinate> pathOfCoordinates) {
        this.pathOfCoordinates = pathOfCoordinates;
    }

    public boolean isInAttackState() {
        return isInAttackState;
    }

    public boolean isCanAttack() {
        return canAttack;
    }

    public void setCanAttack(boolean canAttack) {
        this.canAttack = canAttack;
    }

    public void setKillingOpponent(boolean killingOpponent) {
        isKillingOpponent = killingOpponent;
    }

    public void setInAttackState(boolean inAttackState) {
        isInAttackState = inAttackState;
    }
}