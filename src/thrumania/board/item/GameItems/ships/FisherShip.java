package thrumania.board.item.GameItems.ships;

import thrumania.board.item.MapItems.Cells.Cell;
import thrumania.board.item.MapItems.Cells.HighLand;
import thrumania.board.item.MapItems.Cells.LowLand;
import thrumania.board.item.MapItems.Inside.SmallFish;
import thrumania.board.item.MapItems.Map;
import thrumania.game.MapProcessor;
import thrumania.gui.PlayPanel;
import thrumania.utils.Constants;
import thrumania.utils.Coordinate;
import thrumania.utils.IntegerUtils;

import java.awt.*;

/**
 * Created by sina on 7/3/16.
 */
public class FisherShip extends  Ships{
    // in rate of 1 second
    private int speedOfCollectingFood = 50 ;
    private  int capacityOfCollectingRecourses = 1200;
    private  boolean isCapacityOfCollectingResourcesFUll =false;
    private boolean isWinter = false;
    private Dimension d = new Dimension(Constants.CELL_SIZE, Constants.CELL_SIZE);
    private  Coordinate ResourceCoordinate = null;

    public FisherShip(PlayPanel playPanel, Map map, int xCord, int yCord, int playerNumber){
        super.unitOfConsumingFood = 1;
        super.playPanel = playPanel;
        super.map = map;
        super.xCord = xCord;
        super.yCord = yCord;
        super.foodReq = 0;
        super.woodReq = 2000;
        super.ironReq= 500 ;
        super.goldReq =250 ;
        super.isAlive = true;
        this.mapProcessor = new MapProcessor(map.getCells());
        this.setSize(d);
        this.coordinate = IntegerUtils.getCoordinateWithXAndY(xCord , yCord);

    }


    private void consumingFood(){

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }


    private void deterimingCanMove(){

        if ( playPanel.getSeason() != Constants.Seasons.WINTER)
            super.canMove = true;
        else canMove = false;

    }
    private  boolean checkWetherGoalIsLand(Coordinate crd){

        Cell cell = map.getCell(crd.getRow() , crd.getColumn());
        if( cell instanceof HighLand || cell instanceof LowLand)
            return  true;
        return false;
    }
    private  boolean checkWetherGoalHasFishResource(Coordinate crd){

        Cell cell = map.getCell(crd.getRow() , crd.getColumn());
        if( cell.getInsideElementsItems() !=null && cell.getInsideElementsItems() instanceof SmallFish )
            return true;
        else  return  false;



    }

    private void move (Coordinate end){






    }


    @Override
    public void paintingOptions(Graphics g) {

    }

    @Override
    public void run() {

    }
}
