package thrumania.board.item.GameItems.people;

import thrumania.board.item.MapItems.Map;
import thrumania.game.MapProcessor;
import thrumania.gui.GamePanel;
import thrumania.gui.PlayPanel;
import thrumania.messages.Messages;
import thrumania.utils.Constants;
import thrumania.utils.Coordinate;
import thrumania.utils.ImageUtils;

import javax.swing.*;

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
//    processor.getPath(stast, end);




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
        super.xCord = xCord;
        super.yCord = yCord;

        this.capacityOfCollectingItems = 300;
        // TODO : one unit of each
//        this.speadOfCollectingItems =
        this.isCapacityOfCollectingItemsFull= false;
        this.canGoMountain = false;
        this.playPanel = playPanel;
        this.map = map;
        this.mapProcessor =  new MapProcessor(map.getCells());
        this.setSize(Constants.CELL_SIZE  , Constants.CELL_SIZE );
//        this.setSize(200 ,200);
        this.setIcon( new ImageIcon(ImageUtils.getImage("manStanding.png")));
        this.setLocation(xCord, yCord);
    }

    @Override
    protected void move(Coordinate end) {
        while ( coordinate.getRow() != end.getRow() ||  coordinate.getColumn() != end.getColumn() ) {
            this.xCord += 1;
            this.yCord += 1;
            this.setLocation(xCord, yCord);
            coordinate = new Coordinate((int) Math.ceil(xCord), (int) Math.ceil(yCord));
            try {
                Thread.sleep((long) (1000 / speedOfMoving));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }

    @Override
    protected void determiningSpeedOfMoving() {

        if( playPanel.getSeason() == Constants.Seasons.SPRING )
            this.speedOfMoving  = 8;
        else if ( playPanel.getSeason() == Constants.Seasons.SUMMER)
            this.speedOfMoving = 7;
        else if( playPanel.getSeason() == Constants.Seasons.AUTMN)
            this.speedOfMoving = 5;
        else if( playPanel.getSeason() == Constants.Seasons.WINTER)
            this.speedOfMoving = 3;
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
//
        if( canGoMountain)
            speedOfMoving = speedOfMoving / 2;
        else if ( ! canGoMountain)
            speedOfMoving = speedOfMoving * 2;
        if( capacityOfCollectingItems == 300 || capacityOfCollectingItems == 0)
            this.isCapacityOfCollectingItemsFull = ! isCapacityOfCollectingItemsFull;

        if( isCapacityOfCollectingItemsFull ){

            // TODO : changing his order to go to castle
        }

        this.move(mapProcessor.getPath(coordinate , endCord).pop());

    }



}
