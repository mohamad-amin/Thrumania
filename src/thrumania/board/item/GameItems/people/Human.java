package thrumania.board.item.GameItems.people;

import thrumania.board.item.InsideElementsItems;
import thrumania.utils.Coordinate;

/**
 * Created by sina on 6/24/16.
 */
public abstract class Human extends InsideElementsItems {
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
    protected int foodReq ;
    protected int ironReq;
    protected  int woodReq;
    protected int goldReq;
    // TODO : add a class called : human position instead of code below
    protected int xCord;
    protected  int yCord;
    protected  boolean isAlive;
    protected  Coordinate coordinate;
    protected  Coordinate endCord;
    protected  int xEnd;
    protected  int yEnd;
    protected Coordinate HomeCastleCoordinate;
    protected abstract void  move(Coordinate end);
    protected abstract void determiningSpeedOfMoving();
    protected String picutreName;
    private boolean shouldDraw;
    protected  boolean isSelectedByPlayer;
    protected   boolean isMoving;
    protected boolean movingShouldBeStopped = false;



    // TODO  : deterimining team name !!






    public int getyCord() {
        return yCord;
    }

    public void setyCord(int yCord) {
        this.yCord = yCord;
    }

    public int getxCord() {

        return xCord;
    }

    public void setxCord(int xCord) {
        this.xCord = xCord;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public Coordinate getEndCord() {
        return endCord;
    }

    public void setEndCord(Coordinate endCord) {
        this.endCord = endCord;
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

    public int getyEnd() {
        return yEnd;
    }

    public void setyEnd(int yEnd) {
        this.yEnd = yEnd;
    }

    public int getxEnd() {

        return xEnd;
    }

    public void setxEnd(int xEnd) {
        this.xEnd = xEnd;
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
}
