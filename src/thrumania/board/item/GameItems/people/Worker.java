package thrumania.board.item.GameItems.people;

import thrumania.board.item.MapItems.Cells.HighLand;
import thrumania.board.item.MapItems.Cells.LowLand;
import thrumania.board.item.MapItems.Map;
import thrumania.game.MapProcessor;
import thrumania.gui.PlayPanel;
import thrumania.utils.Constants;
import thrumania.utils.Coordinate;
import thrumania.utils.IntegerUtils;

import java.awt.*;
import java.util.Stack;

/**
 * Created by sina on 6/24/16.
 */
public class Worker extends Human {
    private boolean canGoMountain;
    // TODO : worker's Order
    private int capacityOfCollectingItems;
    private boolean isCapacityOfCollectingItemsFull;
    private int speadOfCollectingItems;
    private PlayPanel playPanel;
    private Map map;
    private Dimension d = new Dimension(Constants.CELL_SIZE  , Constants.CELL_SIZE );

    public Worker(PlayPanel playPanel, Map map, int xCord, int yCord , int playerNumber) {
        // moshakhasat :
        super.health = 500;
        super.damageUnit = 20;
        super.visibilityUnit = 15;
        super.foodReq = 1000;
        super.woodReq = 0;
        super.goldReq = 0;
        super.ironReq = 0;
        super.speadOfConsumingFood = 1;
        super.playerNumber = playerNumber ;
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
        this.isCapacityOfCollectingItemsFull = false;
        this.canGoMountain = false;
        this.isMoving = false;

        // other classes :
        this.map = map;
        this.mapProcessor = new MapProcessor(map.getCells());
        this.playPanel = playPanel;

    // JLabel things :
        this.setSize(d);



    }

    private void move2( Coordinate end){
        int xEnd , yEnd;

        super.isMoving = true;
          this.xCord = coordinate.getColumn() * Constants.CELL_SIZE + Constants.CELL_SIZE / 10;
        this.yCord = coordinate.getRow() * Constants.CELL_SIZE  ;
        xEnd = end.getColumn() * Constants.CELL_SIZE + Constants.CELL_SIZE / 10;
        yEnd = end. getRow() * Constants.CELL_SIZE  ;

        while ( this.xCord != xEnd ||  this.yCord != yEnd){
            this.determiningSpeedOfMoving();


            if( this.xCord < xEnd)
                xCord ++ ;
            else if (this.xCord > xEnd )
                xCord -- ;
            if ( this.yCord <  yEnd)
                yCord ++;
            else if( this.yCord > yEnd)
                yCord -- ;
            int x ,y;

            coordinate = IntegerUtils.getCoordinateWithXAndY(xCord , yCord);

                try {
                            Thread.sleep((long) (1000 / (speedOfMoving *  5 )  ));
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
        while ( isAlive){
            examiningPath();


        }



// TODO
    }

    public void examiningPath(){
        // TODO : fix this sleep
              if( ! isMoving) {
                  try {
                      Thread.sleep(1);
                  } catch (InterruptedException e) {
                      e.printStackTrace();
                  }

                  if (distinations.size() > 0) {
                        settingEachMove(mapProcessor.getPath(coordinate, distinations.get(0), this));
                        isMoving = false;
                        distinations.remove(0);
                    }
                }

        }


    private void settingEachMove(Stack<Coordinate> path){
        if( !path.isEmpty() )
            if( path.peek().equals(coordinate))
                path.pop();
        while (! path.isEmpty()  ) {
            if (!this.checkWheterTheGoalCellIsWaterOrNot(path.peek()))
            move2(path.pop());
            else {

                while ( ! path.isEmpty())
                    path.pop();

            }
        }
        isMoving = false;





    }
    private boolean checkWheterTheGoalCellIsWaterOrNot(Coordinate crd) {
        if (map.getCell(crd.getRow(), crd.getColumn()) instanceof LowLand || map.getCell(crd.getRow(), crd.getColumn()) instanceof HighLand) {
            return false;
        } else {
            return true;
        }


    }

}
