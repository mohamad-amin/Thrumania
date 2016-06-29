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
public class Worker extends Human  {
    private boolean canGoMountain;
    // TODO : worker's Order
    private int capacityOfCollectingItems;
    private boolean isCapacityOfCollectingItemsFull;
    private int speadOfCollectingItems;
    private PlayPanel playPanel;
    private Map map;
    private Dimension d = new Dimension(Constants.CELL_SIZE , Constants.CELL_SIZE);


    public Worker(PlayPanel playPanel, Map map, int xCord, int yCord) {
        // moshakhasat :
        super.health = 500;
        super.damageUnit = 20;
        super.visibilityUnit = 15;
        super.foodReq = 1000;
        super.woodReq = 0;
        super.goldReq = 0;
        super.ironReq = 0;
        super.speadOfConsumingFood = 1;
        this.capacityOfCollectingItems = 300;
        // TODO : one unit of each
//        this.speadOfCollectingItems =


        super.isSelectedByPlayer = false;
        // mokhtasat :
        super.xCord = xCord;
        super.yCord = yCord;
        super.xEnd = xCord;
        super.yEnd = yCord;
        super.coordinate = IntegerUtils.getCoordinateWithXAndY(xCord, yCord);
        super.endCord = IntegerUtils.getCoordinateWithXAndY(xEnd, yEnd);
        // aks :
        super.picutreName = "manStanding.png";
        // masir
        super.paths = new Stack<>();
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
        this.setLocation(xCord , yCord );


    }

    @Override
    protected void move(Coordinate end) {
        this.endCord = end;
        this.xEnd = IntegerUtils.getXAndYWithCoordinate(end)[0];
        this.yEnd = IntegerUtils.getXAndYWithCoordinate(end)[1];

        isMoving = true;
        while (coordinate.getRow() != end.getRow() || coordinate.getColumn() != end.getColumn() && !movingShouldBeStopped) {
            if (!this.checkWheterTheGoalCellIsWaterOrNot(end)) {
                if (coordinate.getColumn() < end.getColumn())
                    this.xCord += 1;
                else if (coordinate.getColumn() > end.getColumn())
                    this.xCord--;
                if (coordinate.getRow() > end.getRow())
                    this.yCord--;
                else if (coordinate.getRow() < end.getRow())
                    this.yCord++;
                this.setLocation(xCord , yCord);
                coordinate = IntegerUtils.getCoordinateWithXAndY(xCord, yCord);
                try {
                    Thread.sleep((long) (1000 / speedOfMoving));
                } catch (InterruptedException e) {
                    e.printStackTrace();

                }


            } else {
                System.out.println("we are here and player should stop walking");
                movingShouldBeStopped = true;
                return;
            }
        }
        if (!this.paths.isEmpty())
            this.move(this.paths.pop());


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

        if (this.isCapacityOfCollectingItemsFull)
            this.endCord = this.getHomeCastleCoordinate();
    }

    @Override
    public void run() {

        while ( isAlive) {

            if (!paths.isEmpty() && paths.peek().equals(coordinate))
                paths.pop();
            while (!paths.isEmpty()) {

                if (!this.checkWheterTheGoalCellIsWaterOrNot(paths.peek()) && !movingShouldBeStopped) {

                    this.determiningSpeedOfMoving();

                    if (paths.peek().equals(coordinate))
                        paths.pop();

                    if (!paths.isEmpty())
                        this.move(paths.pop());
                } else break;
                if (!movingShouldBeStopped)
                    while (this.xCord != xEnd || this.yCord != yEnd) {

                        this.determiningSpeedOfMoving();
                        if (this.xCord > xEnd)
                            xCord--;
                        else if (this.xCord < xEnd)
                            xCord++;
                        if (this.yCord > yEnd)
                            yCord--;
                        else if (this.yCord < yEnd)
                            yCord++;

                        try {
                            Thread.sleep((long) (1000 / speedOfMoving));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        this.setLocation(xCord, yCord);
                    }

                coordinate = IntegerUtils.getCoordinateWithXAndY(xCord, yCord);

            }
            isMoving = false;
        }


// TODO
    }

    private boolean checkWheterTheGoalCellIsWaterOrNot(Coordinate crd) {
        if (map.getCell(crd.getRow(), crd.getColumn()) instanceof LowLand || map.getCell(crd.getRow(), crd.getColumn()) instanceof HighLand) {
            return false;
        } else {
            return true;
        }


    }

}
