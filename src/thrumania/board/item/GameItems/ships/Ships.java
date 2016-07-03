package thrumania.board.item.GameItems.ships;

import thrumania.board.item.InsideElementsItems;
import thrumania.board.item.MapItems.Map;
import thrumania.game.MapProcessor;
import thrumania.gui.PlayPanel;
import thrumania.utils.Coordinate;

import java.util.Stack;

/**
 * Created by sina on 7/3/16.
 */
public abstract  class Ships extends InsideElementsItems implements  Runnable {

    protected Coordinate coordinate;
    protected Stack<Coordinate> pathOfCoordinates = new Stack<>();
    protected int speedOfMoving = 10 ;
    protected int unitOfConsumingFood ;
    protected PlayPanel playPanel ;
    protected Map map;
    protected int xCord;
    protected  int yCord;
    protected  int PlayerNumber;
    protected String PictureName;
    protected  Coordinate HomeCastleCoordinate;
    protected MapProcessor mapProcessor;
    protected int foodReq ;
    protected int woodReq;
    protected  int ironReq ;
    protected  int goldReq;
    protected boolean canMove = true;
    protected  boolean isAlive;








    public int getSpeedOfMoving() {
        return speedOfMoving;
    }

    public void setSpeedOfMoving(int speedOfMoving) {
        this.speedOfMoving = speedOfMoving;
    }

    public Stack<Coordinate> getPathOfCoordinates() {

        return pathOfCoordinates;
    }

    public void setPathOfCoordinates(Stack<Coordinate> pathOfCoordinates) {
        this.pathOfCoordinates = pathOfCoordinates;
    }

    public Coordinate getCoordinate() {

        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public int getUnitOfConsumingFood() {
        return unitOfConsumingFood;
    }

    public int getxCord() {
        return xCord;
    }

    public void setxCord(int xCord) {
        this.xCord = xCord;
    }

    public int getPlayerNumber() {
        return PlayerNumber;
    }


    public void setPlayerNumber(int playerNumber) {
        PlayerNumber = playerNumber;
    }

    public String getPictureName() {
        return PictureName;
    }

    public void setPictureName(String pictureName) {
        PictureName = pictureName;
    }

    public int getyCord() {
        return yCord;
    }

    public void setyCord(int yCord) {
        this.yCord = yCord;
    }

    public Coordinate getHomeCastleCoordinate() {
        return HomeCastleCoordinate;
    }

    public void setHomeCastleCoordinate(Coordinate homeCastleCoordinate) {
        HomeCastleCoordinate = homeCastleCoordinate;
    }

    public MapProcessor getMapProcessor() {
        return mapProcessor;
    }
}
