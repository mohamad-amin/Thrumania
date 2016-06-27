package thrumania.board.item.GameItems.people;

import thrumania.board.item.MapItems.HighLand;
import thrumania.board.item.MapItems.LowLand;
import thrumania.board.item.MapItems.Map;
import thrumania.game.MapProcessor;
import thrumania.gui.PlayPanel;
import thrumania.utils.Constants;
import thrumania.utils.Coordinate;

import java.util.Stack;

/**
 * Created by sina on 6/24/16.
 */
public class Worker extends  Human  implements  Runnable{
    private boolean canGoMountain ;
    // TODO : worker's Order
    private  int capacityOfCollectingItems;
    private boolean isCapacityOfCollectingItemsFull;
    private  int speadOfCollectingItems;
    private PlayPanel playPanel;
    private  Map map;
    private MapProcessor mapProcessor ;
    private  boolean isMoving;
    private Stack <Coordinate> paths;




    public Worker(PlayPanel playPanel , Map map , int xCord , int yCord) {

        super.health = 500;
        super.damageUnit = 20 ;
        super.visibilityUnit = 15;
        super.foodReq = 1000;
        super.woodReq = 0;
        super.goldReq = 0;
        super.ironReq = 0;
        super.speadOfConsumingFood = 1;
        super.isAlive = true;
        super.isSelectedByPlayer = false;
        super.xCord = xCord;
        super.yCord = yCord;
        super.xEnd = xCord;
        super.yEnd = yCord;
        this.capacityOfCollectingItems = 300;
        // TODO : one unit of each
//        this.speadOfCollectingItems =
        this.isCapacityOfCollectingItemsFull= false;
        this.canGoMountain = false;
        this.playPanel = playPanel;
        this.map = map;
        this.mapProcessor =  new MapProcessor(map.getCells());

       // TODO : its coordinate
        this.coordinate = new Coordinate(  ((int) Math.ceil((double) yCord / (double)Constants.CELL_SIZE)) ,(int)  Math.ceil((double) xCord /(double) Constants.CELL_SIZE));
        System.out.println("coordinate is \t " + coordinate + "  x is    " + xCord + "    y is   " + yCord);
        this.endCord =this.coordinate;
        this.isMoving = false;
    }

    @Override
    protected void move(Coordinate end) {

        isMoving =true;
        while ( coordinate.getRow() != end.getRow() ||  coordinate.getColumn() != end.getColumn() ) {
            if( coordinate.getColumn() <  end.getColumn())
            this.xCord += 1;
            else if ( coordinate.getColumn() > end.getColumn())
                this.xCord --;
            if( coordinate.getRow() > end.getRow())
            this.yCord  --;
            else if( coordinate.getRow() < end.getRow())
                this.yCord ++;
            coordinate = new Coordinate((int) Math.ceil((double) yCord / (double)Constants.CELL_SIZE), (int) Math.ceil((double) xCord / (double) Constants.CELL_SIZE));
            try {
                Thread.sleep((long) (1000 / speedOfMoving));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }
//        this.xCord +=2;
//        this.yCord +=2;
        coordinate = new Coordinate((int) Math.ceil((double) yCord / (double)Constants.CELL_SIZE), (int) Math.ceil((double) xCord / (double) Constants.CELL_SIZE));
        isMoving = false;


    }

    @Override
    protected void determiningSpeedOfMoving() {
        System.out.println("play panle is" + playPanel);

        if( playPanel.getSeason() == Constants.Seasons.SPRING )
            this.speedOfMoving  = 8;
        else if ( playPanel.getSeason() == Constants.Seasons.SUMMER)
            this.speedOfMoving = 7;
        else if( playPanel.getSeason() == Constants.Seasons.AUTMN)
            this.speedOfMoving = 5;
        else if( playPanel.getSeason() == Constants.Seasons.WINTER)
            this.speedOfMoving = 3;
        if( this.canGoMountain)
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

    @Override
    public void run() {
        this.determiningSpeedOfMoving();

        if( capacityOfCollectingItems == 300 || capacityOfCollectingItems == 0)
            this.isCapacityOfCollectingItemsFull = ! isCapacityOfCollectingItemsFull;

        if( isCapacityOfCollectingItemsFull ){
            endCord = super.HomeCastleCoordinate;
            // TODO : changing his order to go to castle : getting it's team castle's coordinate
        }

        while( ! this.coordinate.equals(endCord) && ! mapProcessor.getPath(coordinate , endCord).isEmpty() && !isMoving  && ! this.checkWheterTheGoalCellIsWaterOrNot(mapProcessor.getPath(coordinate ,endCord).pop())) {



//while ( ! mapProcessor.getPath(coordinate , endCord).isEmpty()){
//        while( ! mapProcessor.getPath(coordinate , endCord).isEmpty() && (this.xCord != endCord.getColumn() * Constants.CELL_SIZE + Constants.CELL_SIZE /3 || this.yCord != endCord.getRow() * Constants.CELL_SIZE + Constants.CELL_SIZE / 3 && !isMoving  && ! this.checkWheterTheGoalCellIsWaterOrNot(mapProcessor.getPath(coordinate ,endCord).pop()))) {

    paths = mapProcessor.getPath(coordinate , endCord);
    if (  paths.peek().equals(coordinate))
        paths.pop();
    this.determiningSpeedOfMoving();

    this.move(paths.pop());



}
        while ( !isMoving && this.xCord + Constants.CELL_SIZE / 2 != xEnd ||  this.yCord + Constants.CELL_SIZE / 2 != yEnd){

            this.determiningSpeedOfMoving();
            if(  this.xCord + Constants.CELL_SIZE / 2  > xEnd)
                xCord -- ;
            else if(  this.xCord + Constants.CELL_SIZE / 2 < xEnd)
                xCord  ++;
            if( this.yCord + Constants.CELL_SIZE / 2 > yEnd)
                yCord -- ;
            else if ( this.yCord + Constants.CELL_SIZE / 2 < yEnd)
                yCord ++ ;
            try {
                Thread.sleep((long) (1000 / speedOfMoving));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }



        }

//        coordinate = new Coordinate((int) Math.ceil((double) yCord / (double)Constants.CELL_SIZE), (int) Math.ceil((double) xCord / (double) Constants.CELL_SIZE));



    }
private  boolean checkWheterTheGoalCellIsWaterOrNot(Coordinate crd) {
    if( map.getCell(crd.getRow() , crd.getColumn()) instanceof LowLand ||  map.getCell(crd.getRow() , crd.getColumn()) instanceof HighLand)
        return  false;
    else return  true;


}

}
