package thrumania.board.item.GameItems.ships;

import thrumania.board.item.InsideElementsItems;
import thrumania.board.item.MapItems.Cells.Cell;
import thrumania.board.item.MapItems.Cells.HighLand;
import thrumania.board.item.MapItems.Cells.LowLand;
import thrumania.board.item.MapItems.Map;
import thrumania.game.MapProcessor;
import thrumania.gui.PlayPanel;
import thrumania.utils.Constants;
import thrumania.utils.Coordinate;
import thrumania.utils.IntegerUtils;

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
    protected  int playerNumber;
    protected String pictureName;
    protected  Coordinate HomeCastleCoordinate;
    protected MapProcessor mapProcessor;
    protected int foodReq ;
    protected int woodReq;
    protected  int ironReq ;
    protected  int goldReq;
    protected boolean canMove = true;
    protected  boolean isAlive ;
    private  int upCounter  = 0, rightCounter = 0 , downCounter = 0 , leftCounter  =0 ;
    private int counter = 0;
    private  boolean wentRight = false;

    public   enum  StatesOfMoving{

        STOP , MOVE_BY_ORDER , COLLECTING_FISH , COLLECTING_FISH_IS_DONE , COLLECTING_HUMAN , COLLECTING_HUMAN_IS_DONE , EMPTYING_HUMAN , EMPTYING_HUMAN_IS_DONE
    }

    public  StatesOfMoving moveState = StatesOfMoving.STOP;
    protected void regularMove(Coordinate end) {
        int xEnd, yEnd;

        this.xCord = coordinate.getColumn() * Constants.CELL_SIZE + Constants.CELL_SIZE / 10;
        this.yCord = coordinate.getRow() * Constants.CELL_SIZE;
        xEnd = end.getColumn() * Constants.CELL_SIZE + Constants.CELL_SIZE / 10;
        yEnd = end.getRow() * Constants.CELL_SIZE;

        while (this.xCord != xEnd || this.yCord != yEnd) {
            deterimingCanMove();
            if (this.xCord < xEnd) {
                xCord++;
                leftCounter = 0 ;
                downCounter = 0 ;
                upCounter = 0 ;
                this.setPicturesOfMoving(1 , rightCounter);
                rightCounter ++ ;
                counter ++;
                wentRight = true;


            }
            else if (this.xCord > xEnd) {
                xCord--;
                upCounter =  rightCounter = downCounter = 0;
                this.setPicturesOfMoving(3 , leftCounter);
                leftCounter ++ ;
                counter++;
                wentRight = true;

            }
//            coordinate = IntegerUtils.getCoordinateWithXAndY(xCord, yCord);
//            if( ! wentRight  ||  counter == 5) {
            counter =0;
            wentRight = false;
            if (this.yCord < yEnd) {
                yCord++;
                upCounter = rightCounter = leftCounter = 0;
                this.setPicturesOfMoving(2, downCounter);
                downCounter++;
            } else if (this.yCord > yEnd) {
                yCord--;
                rightCounter = downCounter = leftCounter = 0;
                this.setPicturesOfMoving(0, upCounter);
                upCounter++;

            }
//            }
            coordinate = IntegerUtils.getCoordinateWithXAndY(xCord, yCord);



            try {
                Thread.sleep((long) (1000 / (speedOfMoving * 5)));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }


    }


    protected   boolean checkWetherGoalIsLand(Coordinate crd){

        Cell cell = map.getCell(crd.getRow() , crd.getColumn());
        if( cell instanceof HighLand || cell instanceof LowLand)
            return  true;
        return false;
    }


    // 0 -> up
    // 1 -> right
    // 2 -> down
    // 3 -> left


    private void setPicturesOfMoving( int direction , int thirdPartOfTheName) {
// tODO : for ship
        if (this instanceof FisherShip ||  this instanceof  ContainerShip) {
            if (direction == 0 && thirdPartOfTheName >= 8)
                 upCounter = 0;
            else if (direction == 1 && thirdPartOfTheName >= 8)
                thirdPartOfTheName = rightCounter = 0;
            else if (direction == 2 && thirdPartOfTheName >= 8)
                thirdPartOfTheName =   downCounter = 0;
            else if (direction == 3 && thirdPartOfTheName >= 8)
                thirdPartOfTheName =   leftCounter = 0;
// TODO : fix the first number : playerNumber
            pictureName = "F" + playerNumber % 4 + "" + direction  +".png";
        }
    }






    protected void deterimingCanMove(){

        if ( playPanel.getSeason() != Constants.Seasons.WINTER)
            canMove = true;
        else canMove = false;

    }


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
        return playerNumber;
    }


    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    public String getPictureName() {
        return pictureName;
    }

    public void setPictureName(String pictureName) {
        this.pictureName = pictureName;
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
