package thrumania.board.item.GameItems.people;

/**
 * Created by sina on 6/24/16.
 */
public class Worker extends  Human  implements  Runnable{
    private boolean canGoMountain ;
    // TODO : worker's Order
    private  int capacityOfCollectingItems;
    private boolean isCapacityOfCollectingItemsFull;
    private  int speadOfCollectingItems;



    public Worker() {

        super.health = 500;
        super.damageUnit = 20 ;
        super.visibilityUnit = 15;
        super.foodReq = 1000;
        super.woodReq = 0;
        super.goldReq = 0;
        super.ironReq = 0;
        super.speadOfConsumingFood = 1;
        super.isAlive = true;
        // TODO:
//        super.xCord;
//        super.yCord

        this.capacityOfCollectingItems = 300;
        // TODO : one unit of each
//        this.speadOfCollectingItems =
        this.isCapacityOfCollectingItemsFull= false;
        this.canGoMountain = false;
    }

    @Override
    protected void move() {



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
        super.determiningSpeedOfMoving();
        if( canGoMountain)
            speedOfMoving = speedOfMoving / 2;
        else if ( ! canGoMountain)
            speedOfMoving = speedOfMoving * 2;
        if( capacityOfCollectingItems == 300 || capacityOfCollectingItems == 0)
            this.isCapacityOfCollectingItemsFull = ! isCapacityOfCollectingItemsFull;

        if( isCapacityOfCollectingItemsFull ){

            // TODO : changing his order to go to castle
        }

    }
}
